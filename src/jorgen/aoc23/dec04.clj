(ns jorgen.aoc23.dec04
  (:require
    [clojure.string :as str]
    [clojure.set :refer [intersection]]
    [jorgen.aoc23.util :as util]))


(defn line->set [numbers]
  (as-> (str/trim numbers) $
        (str/split $ #" ")
        (remove empty? $)
        (map util/parse-int $)
        (set $)))


(defn my-winning-cards [card]
  (let [[_ numbers] (str/split card #":")
        [w m] (str/split numbers #" \| ")
        winning (line->set w)
        my (line->set m)]
    (->> (intersection winning my)
         count)))


(defn score-card
  "Score one card. 1, 2, 4, 8...
   This is what you get from bit-shifting a 1 binary
        0000 0001 1
        0000 0010 2
        0000 0100 4
        0000 1000 8
  "
  [card]
  (let [[_ numbers] (str/split card #":")
        [w m] (str/split numbers #" \| ")
        winning (line->set w)
        my (line->set m)
        my-winning (->> (intersection winning my)
                        count
                        dec)]
    (if (< my-winning 0)
      0
      (bit-shift-left 1 my-winning))))


(defn part1 [cards]
  (->> cards
       (map score-card)
       (reduce +)))


(defn insert-copies-at [games i copies]
  (let [before (subvec games 0 i)
        after (subvec games i)
        g (if (vector? copies) copies [copies])]
    (vec (concat before g after))))


#_(let [card-pile (util/file->lines "dec04_sample.txt")
        c (apply assoc {}
                 (interleave (range 1 (inc (count card-pile)))
                             card-pile))
        _ (clojure.pprint/pprint c)
        current-card (first card-pile)]
    (loop [total-cards card-pile
           card-pile card-pile]
      (if (empty? card-pile)
        total-cards
        (let [current-card (first card-pile)
              card-no (->> (re-matches #"^Card (\d)+:.*$" current-card)
                           second
                           util/parse-int)
              no-of-winning (my-winning-cards current-card)
              ks (vec (range (inc card-no) (+ card-no no-of-winning 1)))
              (vec (vals (select-keys c ks)))]))))



(select-keys {1 "apa" 2 "bepa" 3 "cepa"} (range 1 3))


(comment
  (part1 (util/file->lines "dec04_sample.txt"))
  (part1 (util/file->lines "dec04_input.txt")))
  ;(part2 (util/file->lines "dec04_sample.txt"))
  ;(part2 (util/file->lines "dec04_input.txt")))
