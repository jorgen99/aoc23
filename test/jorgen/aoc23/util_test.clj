(ns jorgen.aoc23.util-test
  (:require
    [clojure.test :refer :all]
    [jorgen.aoc23.util :refer :all]))


(deftest testing-helpers
  (testing "It should take one step"
    (let [current-pos [2 3]]
      (is (= [2 2] (take-step current-pos :n)))
      (is (= [2 4] (take-step current-pos :s)))
      (is (= [3 3] (take-step current-pos :e)))
      (is (= [1 3] (take-step current-pos :w))))))

