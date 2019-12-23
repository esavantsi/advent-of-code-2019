(ns advent-of-code-2019.core
  (:require [clojure.string :as str]))

(defn fuel [mass] (-> mass
                      (/ 3)
                      int
                      (- 2)))

(defn get-lines [filename] (map
                            #(Integer/parseInt %)
                            (str/split-lines
                             (slurp filename))))

; 1.1
(reduce + (map fuel (get-lines "resources/input.txt")))

(defn total-fuel [mass]
  (->> mass
       (iterate fuel)
       (take-while pos?)
       rest
       (reduce +)))

; 1.2
(reduce + (map total-fuel (get-lines "resources/input.txt")))
