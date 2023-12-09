(ns jorgen.aoc23.dec09-test
  (:require
    [clojure.test :refer :all]
    [jorgen.aoc23.util :as util]
    [jorgen.aoc23.dec09 :refer :all]))


(deftest testing-
  (testing "It should "
    (let [sample (util/file->lines "dec09_sample.txt")
          input (util/file->lines "dec09_input.txt")]
      (is (= 1 (part1 sample)))
      (is (= 1 (part1 input)))))

  (testing "It should "
    (let [sample (util/file->lines "dec09_sample2.txt")
          input (util/file->lines "dec09_input.txt")]
      (is (= 1 (part2 sample)))
      (is (= 1 (part2 input))))))

