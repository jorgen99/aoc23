(ns jorgen.aoc23.dec13
  (:require
    [clojure.string :as str]
    [jorgen.aoc23.util :as util]))


(defn find-mirror-idx-horizontal [grid]
  (->> grid
       (partition 2 1)
       (map-indexed vector)
       (reduce (fn [acc [i [l1 l2]]]
                 (if (= l1 l2)
                   (inc i)
                   acc))
               nil)))

(defn find-mirror-idx-vertical [grid]
  (find-mirror-idx-horizontal (util/transpose grid)))

;(defn find-mirror [grid]
;  (if-let [idx]
;     () idx
;     (* 100 (find-mirror-idx grid))))


(let [lines (util/file->lines "dec13_sample.txt")
      parsed-lines (util/parse-grid-of-chars lines)
      grids (util/parse-blocks lines)
      idx (find-mirror-idx-horizontal (second grids))
      _ (prn "idx" idx)
      [first second] (split-at idx (second grids))
      s (reverse first)]
  (empty? (->> (split-at idx (second grids)))))
               ;(map #([(first ) reverse (first %)])))))
               ;(map vector s second))))
               ;(map #(juxt first (fn [v] (reverse (second v)))))
               ;(remove (fn [[l1 l2]] (= l1 l2))))))



(comment
  (time (part1 (util/file->lines "dec13_sample.txt"))))
;(time (part1 (util/file->lines "dec13_input.txt")))
;(time (part2 (util/file->lines "dec13_sample.txt")))
;(time (part2 (util/file->lines "dec13_input.txt"))))

