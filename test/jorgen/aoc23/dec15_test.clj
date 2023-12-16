(ns jorgen.aoc23.dec15-test
  (:require
    [clojure.test :refer :all]
    [jorgen.aoc23.util :as util]
    [jorgen.aoc23.dec15 :refer :all]))


(deftest testing-hashing
  (testing "It should calculate the sum of hashed parts"
    (is (= 1320 (part1 (util/file->lines "dec15_sample.txt"))))
    (is (= 503154 (part1 (util/file->lines "dec15_input.txt")))))


  (testing "It should calculate lens powers"
    (is (= 145 (part2 (util/file->lines "dec15_sample.txt"))))
    (is (= 251353 (part2 (util/file->lines "dec15_input.txt"))))))

