(ns jorgen.aoc23.dec05-test
  (:require
    [clojure.test :refer :all]
    [jorgen.aoc23.util :as util]
    [jorgen.aoc23.dec05 :refer :all]))


(deftest testing-seed-locations
  (testing "It should find the lowest location"
    (let [sample (util/file->lines "dec05_sample.txt")
          input (util/file->lines "dec05_input.txt")]
      (is (= (part1 sample) 35))
      (is (= (part1 input) 107430936)))))
