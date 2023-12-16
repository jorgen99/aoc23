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
       (remove nil?)))
;first))


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


(defn diffs-idx [l r]
  (->> (map vector l r)
       (map-indexed (fn [idx [l r]]
                      (when (not= l r)
                        idx)))
       (remove nil?)))



(defn find-smudge-with-idxs [symmetry-indicies grid]
  (->> symmetry-indicies
       (map #(split-at % (map-indexed vector grid)))
       (map (fn [[l r]] [(reverse l) r]))
       (map (fn [[l r]] (map vector l r)))
       (map (fn [lps] (remove (fn [[[i1 l] [i2 r]]] (= l r)) lps)))
       (map (fn [lps]
              ;(clojure.pprint/pprint lps)
              ;;(assert (= 1 (count lps)))
              (let [[[[i1 l] [i2 r]]] lps
                    diffs (diffs-idx l r)]
                (when (= 1 (count diffs))
                  [i1 (first diffs)]))))
       (remove nil?)
       first
       ((fn [o] (prn "oooo" o) o))))


(defn toggle-char [grid pos]
  (update-in grid pos #(if (= % "#") "." "#")))


(defn find-symmetry-indicies [grid]
  (->> grid
       (partition 2 1)
       (map-indexed vector)
       (reduce (fn [acc [i [l1 l2]]]
                 (if (= l1 l2)
                   (conj acc (inc i))
                   acc))
               [])))

(defn correct-smudge [grid]
  (prn "##€€#€#€€€#€##€€#")
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
      (not h-mirror-idx) (->> (find-smudge-with-idxs h-indicies grid)
                              (toggle-char grid))
      (not v-mirror-idx) (->> (find-smudge-with-idxs v-indicies t-grid)
                              (toggle-char t-grid)
                              util/transpose)
      :else (assert false "No smudge?"))))



#_(let [[a b] (split-at 3 first-block)
        _ (clojure.pprint/pprint a)
        _ (clojure.pprint/pprint b)]
    (every? identity (map = (reverse a) b)))

(map-indexed vector [6 5 4 3 2])



;(let [grids (->> (util/file->lines "dec13_input.txt")
;                 (util/parse-blocks)
;                 (map util/parse-grid-of-chars)
;                 vec)
;      _ (prn "(count grids)" (count grids))]
;      ;_ (clojure.pprint/pprint grids)]
;  ;_ (clojure.pprint/pprint (util/transpose (second grids)))
;  ;_ (prn "================")
;  ;second-block (second (->> (util/file->lines "dec13_sample.txt")
;  ;                          (util/parse-blocks)))
;  ;_ (clojure.pprint/pprint second-block)
;  ;_ (prn "UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUu")]
;  (count (->> grids
;          ;(map util/transpose)
;          (map #(find-smudge-with-idxs (range 1 (count %)) %))
;          (remove nil?)
;          (map (fn [[l r]] (inc l))))))
;            ;(reduce +)
;            ;(* 100))))
;       #_(->> grids
;              (map util/transpose)
;              (map #(find-smudge-with-idxs (range 1 (count %)) %))
;              (remove nil?)
;              (map (fn [[l r]] (inc l)))))
;            ;(reduce +)
;            ;(* 100))))
;

(comment
  (time (part1 (util/file->lines "dec13_sample.txt")))
  (time (part1 (util/file->lines "dec13_input.txt"))))
;(time (part2 (util/file->lines "dec13_sample.txt")))
;(time (part2 (util/file->lines "dec13_input.txt"))))

