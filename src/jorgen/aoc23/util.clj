(ns jorgen.aoc23.util
  (:require [clojure.string :as str]))


(defn file->lines [filename]
  (->
    (slurp (str "resources/" filename))
    str/split-lines))


(defn char->int [^Character c]
  (Character/digit c 10))


(defn find-first [f col]
  (first (filter f col)))


(defn digit? [c]
  (->> c
       char->int
       neg-int?
       not))


(defn first-digit [col]
  (find-first digit? col))


(defn first-and-last [col]
  ((juxt first last) col))


(defn parse-int [s]
  (Integer/parseInt s))


(defn parse-number-str [line]
  (->> line
       (re-seq #"-?\d+")))


(defn parse-ints [line]
  (->> (parse-number-str line)
       (map parse-int)))


(defn gcd
  "Calculate the greatest common divisor with the Euclidean algoritm.
   https://en.wikipedia.org/wiki/Greatest_common_divisor#Euclidean_algorithm
  "
  [a b]
  (if (zero? b)
    a
    (recur b (mod a b))))


(defn lcm
  "Calculate the least common multiple using the greatest common divisor.
   https://en.wikipedia.org/wiki/Least_common_multiple#Using_the_greatest_common_divisor
  "
  [a b]
  (* (abs a) (/ (abs b) (gcd a b))))


(defn lcm-multiple
  "The least common multiple for more than two numbers."
  [numbers]
  (reduce lcm (first numbers) (rest numbers)))


(defn parse-grid-of-chars [lines]
  (mapv #(mapv str %) lines))


(defn value-in-grid [grid pos]
  (get-in grid (reverse pos)))


(defn take-step
  "Given a direction and a position get the new position.
   Ignoring 'going out of the grid' resulting in -1
  Ex.
       :n [1 2] => [1 1]
       :e [3 8] => [4 8]
       :n [1 0] => [1 -1]
  "
  [[x y] direction]
  (case direction
    :n [x (dec y)]
    :e [(inc x) y]
    :s [x (inc y)]
    :w [(dec x) y]))


(defn points-orthogonally-adjacent [point]
  "Points n, e, w and s of a point, including invalid points"
  [point]
  (rest
    (reduce (fn [current-pos step]
              (conj current-pos (take-step step point)))
            [point]
            [:n :e :s :w])))


(defn remove-invalid-points [grid points]
  (let [height (count (first grid))
        width (count grid)]
    (->> points
         (remove #(neg? (first %)))
         (remove #(neg? (last %)))
         (remove #(<= width (first %)))
         (remove #(<= height (last %))))))


(defn surrounding-in-grid
  "Valid points in the grid surrounding the part"
  [part grid]
  (let [height (count (first grid))
        width (count grid)]
    (-> (points-orthogonally-adjacent part)
        (remove-invalid-points width height))))


