(ns jorgen.aoc23.dec12
  (:require
    [clojure.string :as str]
    [jorgen.aoc23.util :as util]))


(let [lines (util/file->lines "dec12_sample.txt")]
  lines)


(comment
  (time (part1 (util/file->lines "dec12_sample.txt"))))
;(time (part1 (util/file->lines "dec12_input.txt")))
;(time (part2 (util/file->lines "dec12_sample.txt")))
;(time (part2 (util/file->lines "dec12_input.txt"))))


