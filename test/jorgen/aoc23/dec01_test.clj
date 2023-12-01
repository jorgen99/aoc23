(ns jorgen.aoc23.dec01_test
  (:require
    [clojure.string :as str]
    [clojure.test :refer :all]
    [jorgen.aoc23.util :as util]
    [jorgen.aoc23.dec01 :refer :all]))


  (testing "It should sum the calibration values"
    (let [sample (util/file->lines "dec01_sample.txt")
          input (util/file->lines "dec01_input.txt")]
      (is (= 142 (part1 sample)))
      (is (= 56049 (part1 input)))))
