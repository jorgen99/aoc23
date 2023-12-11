(ns jorgen.aoc23.dec11
  (:require
    [jorgen.aoc23.util :as util]))


(defn duplicate-dotrows [grid]
  (reduce (fn [acc line]
            (if (every? #{"."} line)
              (conj acc line line)
              (conj acc line)))
          []
          grid))


(defn pairs
  "Create pairs of numbers without duplicates where [n m] [m n] are the same.
  Ex.
      (pairs (range 3) =>  ([0 1] [0 2] [1 2])
  "
  [range']
  (for [x range'
        y range'
        :when (< x y)]
    [x y]))


(defn expand-universe [grid]
  (->> grid
       duplicate-dotrows
       util/transpose
       duplicate-dotrows
       util/transpose))


(defn distance [[x1 y1] [x2 y2]]
  (+ (- (max x1 x2)
        (min x1 x2))
     (- (max y1 y2)
        (min y1 y2))))


(defn part1 [lines]
  (let [grid (util/parse-grid-of-chars lines)
        expanded (expand-universe grid)
        galaxy-positions (util/positions-of-char-in-grid "#" expanded)
        galaxy-map (into {} (map-indexed vector galaxy-positions))
        galaxy-pairs (pairs (range (count galaxy-positions)))]
    (->> galaxy-pairs
         (map (fn [[p1 p2]]
                (distance (get galaxy-map p1)
                          (get galaxy-map p2))))
         (reduce +))))


(comment
  (time (part1 (util/file->lines "dec11_sample.txt")))
  (time (part1 (util/file->lines "dec11_input.txt"))))
;(time (part2 (util/file->lines "dec11_sample.txt")))
;(time (part2 (util/file->lines "dec11_input.txt"))))


