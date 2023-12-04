(ns jorgen.aoc23.dec03-test
  (:require
    [clojure.test :refer :all]
    [jorgen.aoc23.util :as util]
    [jorgen.aoc23.dec03 :refer :all]))


(deftest testing-helpers
  (testing "It should take one step"
    (let [current-pos [2 3]]
      (is (= [2 2] (take-step :n current-pos)))
      (is (= [2 4] (take-step :s current-pos)))
      (is (= [3 3] (take-step :e current-pos)))
      (is (= [1 3] (take-step :w current-pos))))))

(deftest testing-possible-parts
  (testing "It should sum the id of the engine parts"
    (let [sample (util/file->lines "dec03_sample.txt")
          input (util/file->lines "dec03_input.txt")]
      (is (= 4361 (part1 sample)))
      (is (= 512794 (part1 input))))))
