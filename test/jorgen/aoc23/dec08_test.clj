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
      (is (= (part1 input) 12599))))

  (testing "It should calculate the number of steps when starting at multiple nodes"
    (let [sample (util/file->lines "dec08_sample2.txt")
          input (util/file->lines "dec08_input.txt")]
      (is (= (part2-with-lcm sample) 6))
      (is (= (part2-with-lcm input) 8245452805243)))))
