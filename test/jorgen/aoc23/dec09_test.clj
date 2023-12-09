(ns jorgen.aoc23.dec09-test
  (:require
    [clojure.test :refer :all]
    [jorgen.aoc23.util :as util]
    [jorgen.aoc23.dec09 :refer :all]))


(deftest testing-
  (testing "It should calculate the sum of the last extraploated values "
    (let [sample (util/file->lines "dec09_sample.txt")
          input (util/file->lines "dec09_input.txt")]
      (is (= 114 (part1 sample)))
      (is (= 1772145754 (part1 input)))))

  (testing "It should calculate the sum of the first extrapolated values"
    (let [sample (util/file->lines "dec09_sample.txt")
          input (util/file->lines "dec09_input.txt")]
      (is (= 2 (part2 sample)))
      (is (= 867 (part2 input))))))

