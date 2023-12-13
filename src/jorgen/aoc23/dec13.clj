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


(defn find-mirror-idx [indicies grid]
  (->> indicies
       (map #(symmetric-at-idx grid %))
       (remove nil?)
       first))


(defn pattern-value [grid]
  (let [h-indicies (find-symmetry-indicies grid)
        h-mirror-idx (find-mirror-idx h-indicies grid)
        t-grid (util/transpose grid)
        v-indicies (find-symmetry-indicies t-grid)
        v-mirror-idx (find-mirror-idx v-indicies t-grid)]
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


(defn find-smudge-with-idxs [symmetry-indicies grid]
  (->> symmetry-indicies
       (map #(split-at % grid))
       (map (fn [[l r]] [(reverse l) r]))
       (map (fn [[l r]] (map vector l r)))
       (map (remove (fn [[l r]]
                      (prn "l" l)
                      (prn "r" r)
                      (= l r))))))
       ;empty?))

(let [grid (->> (util/file->lines "dec13_sample.txt")
                (util/parse-blocks)
                (map util/parse-grid-of-chars)
                first)]
  (clojure.pprint/pprint (find-smudge-with-idxs [3] grid)))


(defn find-smudge [grid]
  (prn "##¤#¤¤#¤#¤#########")
  (let [h-indicies (find-symmetry-indicies grid)
        _ (prn "h-indicies" h-indicies)
        h-mirror-idx (find-mirror-idx h-indicies grid)
        _ (prn "h-mirror-idx" h-mirror-idx)
        t-grid (util/transpose grid)
        v-indicies (find-symmetry-indicies t-grid)
        _ (prn "v-indicies" v-indicies)
        v-mirror-idx (find-mirror-idx v-indicies t-grid)
        _ (prn "v-mirror-idx" v-mirror-idx)]
    (cond
      (not h-mirror-idx) (find-smudge-with-idxs h-indicies grid)
      (not v-mirror-idx) (find-smudge-with-idxs v-indicies t-grid)
      ;h-mirror-idx (* 100 h-mirror-idx)
      ;v-mirror-idx v-mirror-idx
      :else (assert false "No symmetry, vertical or horisontal."))))


(comment
  (time (part1 (util/file->lines "dec13_sample.txt")))
  (time (part1 (util/file->lines "dec13_input.txt"))))
;(time (part2 (util/file->lines "dec13_sample.txt")))
;(time (part2 (util/file->lines "dec13_input.txt"))))

