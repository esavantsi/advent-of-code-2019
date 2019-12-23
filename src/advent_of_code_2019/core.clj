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

(reduce + (map fuel (get-lines "resources/input.txt")))

