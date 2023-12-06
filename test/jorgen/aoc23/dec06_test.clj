(ns jorgen.aoc23.dec06-test
  (:require
    [clojure.test :refer :all]
    [jorgen.aoc23.util :as util]
    [jorgen.aoc23.dec06 :refer :all]))


(deftest testing-records
  (testing "It should multiply the number of records"
    (let [sample (util/file->lines "dec06_sample.txt")
          input (util/file->lines "dec06_input.txt")]
      (is (= (part1 sample) 288))
      (is (= (part1 input) 3317888))))

  (testing "It should calculate the number of ways to beat the record"
    (let [sample (util/file->lines "dec06_sample.txt")
          input (util/file->lines "dec06_input.txt")]
      (is (= (part2 sample) 71503))
      (is (= (part2 input) 24655068)))))
