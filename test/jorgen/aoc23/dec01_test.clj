(ns jorgen.aoc23.dec01-test
  (:require
    [clojure.test :refer :all]
    [jorgen.aoc23.util :as util]
    [jorgen.aoc23.dec01 :refer :all]))


(deftest calibration-values
  (testing "It should sum the digit calibration values"
    (let [sample (util/file->lines "dec01_sample.txt")
          input (util/file->lines "dec01_input.txt")]
      (is (= 142 (part1 sample)))
      (is (= 56049 (part1 input)))))

  (testing "It should sum the text and digit calibration values"
    (let [sample (util/file->lines "dec01_sample2.txt")
          input (util/file->lines "dec01_input.txt")]
      (is (= 281 (part2 sample)))
      (is (= 54530 (part2 input))))))

