(ns jorgen.aoc23.dec07-test
  (:require
    [clojure.test :refer :all]
    [jorgen.aoc23.util :as util]
    [jorgen.aoc23.dec07 :refer :all]))


(deftest testing-card-sorting
  (testing "It should sum up all the winnings"
    (let [sample (util/file->lines "dec07_sample.txt")
          input (util/file->lines "dec07_input.txt")]
      (is (= (part1 sample) 6440))
      (is (= (part1 input) 247823654)))))


