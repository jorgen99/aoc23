(ns jorgen.aoc23.dec07
  (:require
    [clojure.string :as str]
    [jorgen.aoc23.util :as util]))

(def card-order [\A \K \Q \J \T \9 \8 \7 \6 \5 \4 \3 \2])
(def order [\A \K \Q \J \T \9 \8 \7 \6 \5 \4 \3 \2])


(defn card-sort [hands]
  (let [order-map (zipmap order (range))
        index-string (fn [hand-map]
                       (apply str (map order-map (:hand hand-map))))]
    (sort-by index-string hands)))



(let [lines (util/file->lines "dec07_sample.txt")]
  (->> lines
       (map #(str/split % #"\s"))
       (mapv (fn [[hand bet]]
               (assoc {} :hand hand :bet bet)))
       card-sort))




(comment
  (time (part1 (util/file->lines "dec07_sample.txt"))))
;(time (part1 (util/file->lines "dec07_input.txt")))
;(time (part2 (util/file->lines "dec07_sample.txt")))
;(time (part2 (util/file->lines "dec07_input.txt"))))


