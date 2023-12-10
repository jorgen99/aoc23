(ns jorgen.aoc23.dec07
  (:require
    [clojure.string :as str]
    [jorgen.aoc23.util :as util]))

(def card-order [\A \K \Q \J \T \9 \8 \7 \6 \5 \4 \3 \2])
(def cards ["AKQJT98765432"])
(defn card-comp [c1 c2]
  (cond
    (> (str/index-of cards c1) (str/index-of cards c2)) -1
    (< (str/index-of cards c1) (str/index-of cards c2)) 1
    :else 0))

(card-comp \A \6)

(defn card-sort [hands]
  (sort-by :hand card-comp hands))

(def hs
  [{:hand "32T3K", :bet "765"}
   {:hand "T55J5", :bet "684"}
   {:hand "KK677", :bet "28"}
   {:hand "KTJJT", :bet "220"}
   {:hand "QQQJA", :bet "483"}])

(->> hs
     (map #(apply str (map (zipmap card-order (range)) (:hand %)))))
(apply str (map (zipmap card-order (range)) (:hand (last hs))))

(let [lines (util/file->lines "dec07_sample.txt")
      hands (->> lines
                 (map #(str/split % #"\s"))
                 (mapv (fn [[hand bet]]
                         (assoc {} :hand hand :bet bet))))]
  (card-sort hands))




(comment
  (time (part1 (util/file->lines "dec07_sample.txt"))))
;(time (part1 (util/file->lines "dec07_input.txt")))
;(time (part2 (util/file->lines "dec07_sample.txt")))
;(time (part2 (util/file->lines "dec07_input.txt"))))


