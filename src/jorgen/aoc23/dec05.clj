(ns jorgen.aoc23.dec05
  (:require
    [jorgen.aoc23.util :as util]))

(def map-headings
  ["seed-to-soil map:"
   "soil-to-fertilizer map:"
   "fertilizer-to-water map:"
   "water-to-light map:"
   "light-to-temperature map:"
   "temperature-to-humidity map:"
   "humidity-to-location map:"])


(defn parse-category
  "Given the map heading and the puzzle input parse the numbers below the map
   into a list of categories
   Ex.
         'seed-to-soil map:', <puzzle input array>
       => ({:to 50, :from 98, :length 2} {:to 52, :from 50, :length 48})
  "
  [name lines]
  (->> lines
       (drop-while #(not (re-matches (re-pattern name) %)))
       rest
       (take-while #(not (= "" %)))
       (map #(re-seq #"\d+" %))
       (clojure.walk/postwalk (fn [x]
                                (if (not (seq? x))
                                  (parse-long x)
                                  x)))
       (map #(apply assoc {} (interleave [:to :from :length] %)))))


(defn in-range?
  "Is n in range of the given category range?
   Ex.
        99, {:to 50, :from 98, :length 2} => true
        97, {:to 50, :from 98, :length 2} => false
  "
  [n {:keys [from length]}]
  (<= from n (+ from (dec length))))


(defn value-from-category
  "Lookup value in category. If n is in range in any
   of the categories ranges, map the value, otherwise
   return the value.
   Ex.
       category 'seed-to-soil map:' has the follwing category-ranges:
         ({:to 50, :from 98, :length 2} {:to 52, :from 50, :length 48})

       n=79 => 81
       n=14 => 14
       n=55 => 57
  "
  [n category-ranges]
  (let [range-match (first
                      (drop-while #(not (in-range? n %))
                                  category-ranges))]
    (if range-match
      (+ (:to range-match) (- n (:from range-match)))
      n)))


(defn location
  "Run a seed value through all the categories and find
   map seed -> location
   Ex.
        n=79 => 82
        n=43 => 43
  "
  [n categories]
  (if (empty? categories)
    n
    (recur (value-from-category n (first categories))
           (rest categories))))


(defn location-for-seeds [seeds lines]
  (let [categories (->> map-headings
                        (map #(parse-category % lines)))]
    (->> seeds
         (map #(location % categories))
         (apply min))))


(defn part1 [lines]
  (let [seeds (map parse-long (re-seq #"\d+" (first lines)))]
    (location-for-seeds seeds lines)))

(let [lines (util/file->lines "dec05_sample.txt")
      seeds (map parse-long (re-seq #"\d+" (first lines)))
      _ (prn seeds)])
  ;(location-for-seeds seeds lines))



(defn part2 [lines]
  (let [seeds (->> (map parse-long (re-seq #"\d+" (first lines)))
                   (partition 2)
                   (mapcat (fn [[start lenth]] (range start (+ start lenth)))))]
    (location-for-seeds seeds lines)))


(comment
  (part1 (util/file->lines "dec05_sample.txt"))
  (part1 (util/file->lines "dec05_input.txt"))
  (part2 (util/file->lines "dec05_sample.txt"))
  (part2 (util/file->lines "dec05_input.txt")))

