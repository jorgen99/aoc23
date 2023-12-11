(ns jorgen.aoc23.dec10-test
  (:require
    [clojure.test :refer :all]
    [jorgen.aoc23.util :as util]
    [jorgen.aoc23.dec10 :refer :all]))


(deftest testing-the-pipe-maze
  (testing "It should find the furthest point in the path"
    (is (= 4 (part1 (util/file->lines "dec10_sample1.txt"))))
    (is (= 8 (part1 (util/file->lines "dec10_sample2.txt"))))
    (is (= 6867 (part1 (util/file->lines "dec10_input.txt"))))))


