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


(comment
  (part1 (util/file->lines "dec04_sample.txt"))
  (part1 (util/file->lines "dec04_input.txt")))
  ;(part2 (util/file->lines "dec04_sample.txt"))
  ;(part2 (util/file->lines "dec04_input.txt")))
