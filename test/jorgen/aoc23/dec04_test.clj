(ns jorgen.aoc23.dec04-test
  (:require
    [clojure.test :refer :all]
    [jorgen.aoc23.util :as util]
    [jorgen.aoc23.dec04 :refer :all]))


(deftest testing-scratch-cards
  (testing "It should sum the scored cards"
    (let [sample (util/file->lines "dec04_sample.txt")
          input (util/file->lines "dec04_input.txt")]
      (is (= 13 (part1 sample)))
      (is (= 19135 (part1 input))))))


