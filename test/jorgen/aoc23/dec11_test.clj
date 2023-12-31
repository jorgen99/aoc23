(ns jorgen.aoc23.dec11-test
  (:require
    [clojure.test :refer :all]
    [jorgen.aoc23.util :as util]
    [jorgen.aoc23.dec11 :refer :all]))


(deftest testing-galaxy-expansion
  (testing "It should calculate the sum of all distances"
    (is (= 374 (part1 (util/file->lines "dec11_sample.txt"))))
    (is (= 9418609 (part1 (util/file->lines "dec11_input.txt"))))
    (is (= 593821230983 (part2 (util/file->lines "dec11_input.txt"))))))


