(ns jorgen.aoc23.dec14
  (:require
    [clojure.string :as str]
    [jorgen.aoc23.util :as util]))


(let [lines (util/file->lines "dec14_sample.txt")
      cols (util/transpose lines)]
  cols)


(comment
  (time (part1 (util/file->lines "dec14_sample.txt"))))
;(time (part1 (util/file->lines "dec14_input.txt")))
;(time (part2 (util/file->lines "dec14_sample.txt")))
;(time (part2 (util/file->lines "dec14_input.txt"))))


