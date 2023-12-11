(ns jorgen.aoc23.dec11
  (:require
    [clojure.set :as set]
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


(defn index-of-empty [grid]
  (loop [idx-of-empty []
         idx-lines (map-indexed vector grid)]
    (let [[idx line] (first idx-lines)]
      (if (empty? idx-lines)
        (set idx-of-empty)
        (if (every? #{"."} line)
          (recur (conj idx-of-empty idx) (rest idx-lines))
          (recur idx-of-empty (rest idx-lines)))))))


(defn idx-range [x1 x2]
  (set (if (< x1 x2)
         (range x1 (inc x2))
         (range x2 (inc x1)))))


(defn galaxy-distance [x1 x2 empty-idxs old-factor]
  (let [idx-to-cross (idx-range x1 x2)
        distance (- (max x1 x2)
                    (min x1 x2))
        no-of-empty-to-cross (count (set/intersection empty-idxs idx-to-cross))
        total-distance (+ (* old-factor no-of-empty-to-cross)
                          (- distance no-of-empty-to-cross))]
    total-distance))


(defn total-galaxy-distance [empty-cols empty-rows old-factor [x1 y1] [x2 y2]]
  (+ (galaxy-distance x1 x2 empty-cols old-factor)
     (galaxy-distance y1 y2 empty-rows old-factor)))


(defn solve
  "Expand (or don't expand) the galaxy.
   Find the positions of all the galaxies and store them in a map
   galaxy-no => galaxy position.
   Loop over all galaxy-no pairs and calculate distance between
   all galaxy-pairs.
  "
  [lines expand-fn age-factor]
  (let [grid (util/parse-grid-of-chars lines)
        expanded (expand-fn grid)
        galaxy-positions (util/positions-of-char-in-grid "#" expanded)
        galaxy-map (into {} (map-indexed vector galaxy-positions))
        galaxy-pairs (pairs (range (count galaxy-positions)))
        empty-columns (index-of-empty (util/transpose grid))
        empty-rows (index-of-empty grid)
        long-distance (partial total-galaxy-distance empty-columns empty-rows age-factor)]
    (->> galaxy-pairs
         (map (fn [[p1 p2]]
                (long-distance (get galaxy-map p1)
                               (get galaxy-map p2))))
         (reduce +))))


(defn part1
  "Expansion and age-factor 1"
  [lines]
  (solve lines expand-universe 1))


(defn part2
  "No expansion and age-factor 1 000 000"
  [lines]
  (solve lines identity 1000000))


(comment
  (time (part1 (util/file->lines "dec11_sample.txt")))
  (time (part1 (util/file->lines "dec11_input.txt")))
  (time (part2 (util/file->lines "dec11_input.txt"))))
