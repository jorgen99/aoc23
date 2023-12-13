(ns jorgen.aoc23.dec07
  (:require
    [clojure.string :as str]
    [jorgen.aoc23.util :as util]))

(def cards "AKQJT98765432")
(def card-order (seq cards))


(defn card-comp [c1 c2]
  (cond
    (< (str/index-of cards c1) (str/index-of cards c2)) -1
    (> (str/index-of cards c1) (str/index-of cards c2)) 1
    :else 0))


(str "(?:" (str/join "|" (map #(str %1 "{" 5 "}") card-order)) ")")

(def hand-strength
  {:five-of-a-kind 7
   :four-of-a-kind 6
   :full-house 5
   :three-of-a-kind 4
   :two-pair 3
   :one-pair 1
   :high-card 0})


(defn sort-hand [hand]
  (sort-by identity card-comp hand))

(re-matches #"\w{5,}" "AAAA5")

(defn hand-type [hand]
  (let [shand (sort-hand hand)]
    (cond
      (re-matches #"\w{5,}" hand))))

(defn hand-comp [h1 h2]
  (let [s1 (sort-hand h1)
        s2 (sort-hand h2)]))


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
  hands)

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
               (assoc {} :hand hand :bet bet)))))


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


