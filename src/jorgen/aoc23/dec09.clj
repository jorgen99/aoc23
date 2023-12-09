(ns jorgen.aoc23.dec09
  (:require
    [jorgen.aoc23.util :as util]))


(defn diff
  "Ex. (0 3 6 9 12 15) => (3 3 3 3 3)"
  [values]
  (loop [n (first values)
         values (drop 1 values)
         acc []]
    (if (empty? values)
      acc
      (recur (first values)
             (rest values)
             (conj acc (- (first values) n))))))


(defn extrapolate
  "Ex.
          (0 3 6 9 12 15)
       => [(0 3 6 9 12 15) [3 3 3 3 3] [0 0 0 0]]
  "
  [history]
  (loop [h history
         acc [history]]
    (if (every? #{0} h)
      acc
      (let [next' (diff h)]
        (recur next' (conj acc next'))))))


(defn in-each
  "Calculate the first or last value for each row
  Ex.
          last, [(0 3 6 9 12 15) [3 3 3 3 3] [0 0 0 0]]
       => [0 3 18]

          first, [(0 3 6 9 12 15) [3 3 3 3 3] [0 0 0 0]]
       => [0 3 -3]
  "
  [first-or-last extraploated]
  (let [op (if (= first-or-last first)
             -
             +)]
    (->> extraploated
         reverse
         (map first-or-last)
         (reduce (fn [acc n]
                   (if (empty? acc)
                     (conj acc 0)
                     (conj acc (op n (last acc)))))
                 []))))



(defn solve [first-or-last-in-each lines]
  (->> lines
       (map util/parse-ints)
       (map extrapolate)
       (map first-or-last-in-each)
       (map last)
       (reduce +)))


(defn last-in-each [extrapolated]
  (in-each last extrapolated))


(defn part1 [lines]
   (solve last-in-each lines))


(defn first-in-each [extrapolated]
  (in-each first extrapolated))


(defn part2 [lines]
  (solve first-in-each lines))


(comment
  (time (part1 (util/file->lines "dec09_sample.txt")))
  (time (part1 (util/file->lines "dec09_input.txt")))
  (time (part2 (util/file->lines "dec09_sample.txt")))
  (time (part2 (util/file->lines "dec09_input.txt"))))
