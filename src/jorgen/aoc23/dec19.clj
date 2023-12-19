(ns jorgen.aoc23.dec19
  (:require
    [clojure.string :as str]
    [jorgen.aoc23.util :as util]))






;(defn parse-parts [part-strs]
;  (reduce (fn [acc part]
;            (str/replace part #""))))

(defn parse-workflow [workflow]
  (prn workflow)
  (let [name (first (str/split workflow #"\{"))
        between (second (re-matches #".*?\{(.*?)\}" workflow))
        rule-parts (str/split between #",")]

    rule))

(let [lines (util/file->lines "dec19_sample.txt")
      blocks (util/parse-blocks lines)]
  (->> (first blocks)
       (map parse-workflow)))


(comment
  (time (part1 (util/file->lines "dec19_sample.txt"))))
;(time (part1 (util/file->lines "dec19_input.txt")))
;(time (part2 (util/file->lines "dec19_sample.txt")))
;(time (part2 (util/file->lines "dec19_input.txt"))))


