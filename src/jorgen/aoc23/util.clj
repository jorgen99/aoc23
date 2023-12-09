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
