(ns jorgen.aoc23.dec01
  (:require
    [clojure.string :as str]
    [jorgen.aoc23.util :as util]))



(defn part1 [lines]
  (let [firsts (map util/first-digit lines)
        seconds (map (fn [s] (->> s str/reverse util/first-digit)) lines)]
    (->> (map vector firsts seconds)
         (map str/join)
         (map #(Integer/parseInt %))
         (reduce +))))
