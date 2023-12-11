(ns jorgen.aoc23.dec03-test
  (:require
    [clojure.test :refer :all]
    [jorgen.aoc23.util :as util]
    [jorgen.aoc23.dec03 :refer :all]))


(deftest testing-possible-parts
  (testing "It should sum the id of the engine parts"
    (let [sample (util/file->lines "dec03_sample.txt")
          input (util/file->lines "dec03_input.txt")]
      (is (= 4361 (part1 sample)))
      (is (= 512794 (part1 input))))))


(deftest testing-possible-parts
  (testing "It should sum the gear ratios"
    (let [sample (util/file->lines "dec03_sample.txt")
          input (util/file->lines "dec03_input.txt")]
      (is (= 467835 (part2 sample)))
      (is (= 67779080 (part2 input))))))
