(ns jorgen.aoc23.dec18-test
  (:require
    [clojure.test :refer :all]
    [jorgen.aoc23.util :as util]
    [jorgen.aoc23.dec18 :refer :all]))


(deftest testing-
  (testing "It should "
    (is (= 1 (part1 (util/file->lines "dec18_sample.txt"))))
    (is (= 1 (part1 (util/file->lines "dec18_input.txt"))))))


  ;(testing "It should "
  ;  (is (= 1 (part (util/file->lines "dec18_sample.txt"))))
  ;  (is (= 1 (part2 (util/file->lines "dec18_input.txt"))))))
