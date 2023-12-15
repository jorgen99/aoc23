(ns jorgen.aoc23.dec15
  (:require
    [clojure.string :as str]
    [jorgen.aoc23.util :as util]))



(defn hash-step [token]
  (reduce (fn [acc c]
            (->> (int c)
                 (+ acc)
                 (* 17)
                 (#(mod % 256))))
          0
          token))


(defn part1 [lines]
  (let [steps (-> lines
                  first
                  (str/split #","))]
    (->> steps
         (map hash-step)
         (reduce +))))


(comment
  (time (part1 (util/file->lines "dec15_sample.txt")))
  (time (part1 (util/file->lines "dec15_input.txt"))))
;(time (part2 (util/file->lines "dec15_sample.txt")))
;(time (part2 (util/file->lines "dec15_input.txt"))))


