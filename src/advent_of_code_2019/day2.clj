(ns advent-of-code-2019.day2 (:require [clojure.string :as str]))

(defn create-computer [instruction-pointer memory-dump]
  {:ip instruction-pointer
   :memory memory-dump})

(defn get-program [filename] (vec (map
                                   #(Integer/parseInt %)
                                   (-> filename
                                       (slurp)
                                       (str/split #",")))))

(defn- run-operation [memory operation address1 address2 target]
  (assoc memory target (operation (nth memory address1) (nth memory address2))))

(defn- opcode->operation [opcode]
  (case opcode
    1 +
    2 *))

(defn execute [computer]
  (let [memory (computer :memory)
        ip (computer :ip)
        opcode (nth memory ip)
        address1 (nth memory (+ 1 ip))
        address2 (nth memory (+ 2 ip))
        address3 (nth memory (+ 3 ip))]
    (if (not= (nth memory ip) 99)
      (recur (create-computer (+ 4 ip)
                              (run-operation memory (opcode->operation opcode) address1 address2 address3)))
      computer)))

(defn computer-output [computer] (nth (computer :memory) 0))

(defn set-state [noun verb computer] (create-computer (computer :ip) (-> (computer :memory)
                                                                         (assoc 1 noun)
                                                                         (assoc 2 verb))))

; 2.1
(->> (create-computer 0 (get-program "resources/input-day-2.txt"))
     (set-state 12 2)
     (execute)
     (computer-output))

; 2.2
(defn noun-verb->output [noun verb] (->> (create-computer 0 (get-program "resources/input-day-2.txt"))
                                         (set-state noun verb)
                                         (execute)
                                         (computer-output)))

(->> (for [noun (range 99)
           verb (range 99)]
       [noun verb])
     (filter #(= (apply noun-verb->output %) 19690720))
     (first))