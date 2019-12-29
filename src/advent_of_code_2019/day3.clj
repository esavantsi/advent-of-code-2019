(ns advent-of-code-2019.day3
  (:require [clojure.string :as str]
            [clojure.set]))

(defn manhattan-distance [point1 point2]
  (+ (Math/abs (- (:x point1) (:x point2))) (Math/abs (- (:y point1) (:y point2)))))

(defn read-file [filename] (-> (str/split-lines
                                (slurp filename))))

(defn move [path direction distance]
  (let [location (peek path)]
    (if (> distance 0)
      (recur (conj path (case direction
                          "U" (update location :y inc)
                          "R" (update location :x inc)
                          "D" (update location :y dec)
                          "L" (update location :x dec)))
             direction
             (dec distance))
      path)))

(defn execute-command [command path]
  (let [direction (subs command 0 1)
        distance (Integer/parseInt (subs command 1 (count command)))]
    (move path direction distance)))

(defn execute-commands [input] (reduce #(execute-command %2 %1) [{:x 0 :y 0}] input))

(def input (map #(str/split % #",") (read-file "resources/input-day-3.txt")))

(def path1 (execute-commands (first input)))
(def path2 (execute-commands (second input)))

(def intersections
  (->> (conj [path1] path2)
       (map set)
       (apply clojure.set/intersection)))

; 3.1
(-> intersections
    (disj {:x 0 :y 0})
    (->> (map #(manhattan-distance {:x 0 :y 0} %))
         (apply min)))
; 3.2
(-> intersections
    (disj {:x 0 :y 0})
    (->> (map #(+ (.indexOf path1 %) (.indexOf path2 %)))
         (apply min)))
