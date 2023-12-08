(ns jorgen.aoc23.dec08-test
  (:require
    [clojure.test :refer :all]
    [jorgen.aoc23.util :as util]
    [jorgen.aoc23.dec08 :refer :all]))


(deftest testing-the-map
  (testing "It should count the number of steps to find the exit"
    (let [sample (util/file->lines "dec08_sample.txt")
          input (util/file->lines "dec08_input.txt")]
      (is (= (part1 sample) 2))
      (is (= (part1 input) 12599)))))

  ;(testing "It should calculate the number of ways to beat the record"
  ;  (let [sample (util/file->lines "dec08_sample.txt")
  ;        input (util/file->lines "dec08_input.txt")]
  ;    (is (= (part2 sample) 71503))
  ;    (is (= (part2 input) 24655088)))))
