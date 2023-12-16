(ns jorgen.aoc23.dec07
  (:require
    [clojure.string :as str]
    [jorgen.aoc23.util :as util]))


(def cards "AKQJT98765432")


(defn card-comp [c1 c2]
  (cond
    (> (str/index-of cards c1) (str/index-of cards c2)) -1
    (< (str/index-of cards c1) (str/index-of cards c2)) 1
    :else 0))


(defn sort-hand [hand]
  (->> (sort-by identity card-comp hand)
       str/join))


(defn hand-type [hand]
  (let [shand (sort-hand hand)]
    (cond
      (re-matches #"(\w)\1{4}" shand) 7
      (re-matches #"\w?(\w)\1{3}\w?" shand) 6
      (re-matches #"(?:(\w)\1{2}(\w)\2)|(?:(\w)\3(\w)\4{2})" shand) 5
      (re-matches #"\w*?(\w)\1{2}\w*?" shand) 4
      (re-matches #"\w?(\w)\1\w?(\w)\2\w?" shand) 3
      (re-matches #"\w*?(\w)\1\w*?" shand) 2
      :else 1)))


(defn highest-card [h1 h2]
  (->> (map vector h1 h2)
       (map #(apply card-comp %))
       (drop-while zero?)
       first))


(defn hand-comp [h1 h2]
  (cond
    (< (hand-type h1) (hand-type h2)) -1
    (> (hand-type h1) (hand-type h2)) 1
    :else (highest-card h1 h2)))


(defn ->hands [lines]
  (->> lines
       (map #(str/split % #"\s"))
       (mapv (fn [[hand bet]]
               {:hand hand :bet bet}))))


(defn part1 [lines]
  (->> lines
       ->hands
       (sort-by :hand hand-comp)
       (map-indexed vector)
       (map (fn [[rank {:keys [_ bet]}]]
              (* (inc rank) (util/parse-int bet))))
       (reduce +)))


(comment
  (time (part1 (util/file->lines "dec07_sample.txt")))
  (time (part1 (util/file->lines "dec07_input.txt"))))
;(time (part2 (util/file->lines "dec07_sample.txt")))
;(time (part2 (util/file->lines "dec07_input.txt"))))


