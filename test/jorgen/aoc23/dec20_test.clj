(ns jorgen.aoc23.dec20-test
  (:require
    [clojure.test :refer :all]
    [jorgen.aoc23.util :as util]
    [jorgen.aoc23.dec20 :refer :all]))


(deftest testing-
  (testing "It should "
    (is (= 1 (part1 (util/file->lines "dec20_sample.txt"))))
    (is (= 1 (part1 (util/file->lines "dec20_input.txt"))))))


  ;(testing "It should "
  ;  (is (= 1 (part (util/file->lines "dec20_sample.txt"))))
  ;  (is (= 1 (part2 (util/file->lines "dec20_input.txt"))))))
