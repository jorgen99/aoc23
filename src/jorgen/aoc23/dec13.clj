(ns jorgen.aoc23.dec13
  (:require
    [jorgen.aoc23.util :as util]))


(defn find-symmetry-indicies [grid]
  (->> grid
       (partition 2 1)
       (map-indexed vector)
       (reduce (fn [acc [i [l1 l2]]]
                 (if (= l1 l2)
                   (conj acc (inc i))
                   acc))
               [])))


(defn symmetric-at-idx [grid idx]
  (when (->> (split-at idx grid)
             ((fn [[l r]] [(reverse l) r]))
             ((fn [[l r]] (map vector l r)))
             (remove (fn [[l r]] (= l r)))
             empty?)
    idx))


(defn find-mirror-idx [grid]
  (->> (find-symmetry-indicies grid)
       (map #(symmetric-at-idx grid %))
       (remove nil?)
       first))


(defn pattern-value [grid]
  (let [h-mirror-idx (find-mirror-idx grid)
        v-mirror-idx (find-mirror-idx (util/transpose grid))]
    (cond
      h-mirror-idx (* 100 h-mirror-idx)
      v-mirror-idx v-mirror-idx
      :else (assert false "No symmetry, vertical or horisontal."))))


(defn part1 [lines]
  (->> lines
       (util/parse-blocks)
       (map util/parse-grid-of-chars)
       (map pattern-value)
       (reduce +)))


(comment
  (time (part1 (util/file->lines "dec13_sample.txt")))
  (time (part1 (util/file->lines "dec13_input.txt"))))
;(time (part2 (util/file->lines "dec13_sample.txt")))
;(time (part2 (util/file->lines "dec13_input.txt"))))

