(ns jorgen.aoc23.dec19
  (:require
    [clojure.string :as str]
    [jorgen.aoc23.util :as util]))


(let [lines (util/file->lines "dec19_sample.txt")]
  lines)


(comment
  (time (part1 (util/file->lines "dec19_sample.txt"))))
;(time (part1 (util/file->lines "dec19_input.txt")))
;(time (part2 (util/file->lines "dec19_sample.txt")))
;(time (part2 (util/file->lines "dec19_input.txt"))))


