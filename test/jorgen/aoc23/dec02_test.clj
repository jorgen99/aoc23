(ns jorgen.aoc23.dec02-test
  (:require
    [clojure.test :refer :all]
    [jorgen.aoc23.util :as util]
    [jorgen.aoc23.dec02 :refer :all]))


(deftest testing-possible-games
  (testing "It should sum the id of impossible games"
    (let [sample (util/file->lines "dec02_sample.txt")
          input (util/file->lines "dec02_input.txt")]
      (is (= 8 (part1 sample)))
      (is (= 2716 (part1 input)))))

  (testing "It should sum the power of the cubes in a game"
    (let [sample (util/file->lines "dec02_sample.txt")
          input (util/file->lines "dec02_input.txt")]
      (is (= 2286 (part2 sample)))
      (is (= 72227 (part2 input))))))
