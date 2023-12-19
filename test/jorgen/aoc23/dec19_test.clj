(ns jorgen.aoc23.dec19-test
  (:require
    [clojure.test :refer :all]
    [jorgen.aoc23.util :as util]
    [jorgen.aoc23.dec19 :refer :all]))


(deftest testing-
  (testing "It should "
    (is (= 19114 (part1 (util/file->lines "dec19_sample.txt"))))
    (is (= 342650 (part1 (util/file->lines "dec19_input.txt"))))))


;  ;(testing "It should "
;  ;  (is (= 1 (part (util/file->lines "dec19_sample.txt"))))
;  ;  (is (= 1 (part2 (util/file->lines "dec19_input.txt"))))))
