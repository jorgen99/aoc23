(ns jorgen.aoc23.dec10-test
  (:require
    [clojure.test :refer :all]
    [jorgen.aoc23.util :as util]
    [jorgen.aoc23.dec10 :refer :all]))


(deftest testing-
  (testing "It should "
    (let [sample (util/file->lines "dec10_sample1.txt")
          input (util/file->lines "dec10_input.txt")]
      (is (= 1 (part1 sample)))
      (is (= 1 (part1 input)))))

  (testing "It should "
    (let [sample (util/file->lines "dec10_sample1.txt")
          input (util/file->lines "dec10_input.txt")]
      (is (= 1 (part2 sample)))
      (is (= 1 (part2 input))))))

