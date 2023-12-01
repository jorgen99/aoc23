(ns jorgen.aoc23.dec01
  (:require
    [clojure.string :as str]
    [jorgen.aoc23.util :as util]))


(defn part1 [lines]
  (let [firsts (map util/first-digit lines)
        seconds (map (fn [s] (->> s str/reverse util/first-digit)) lines)]
    (->> (map vector firsts seconds)
         (map str/join)
         (map #(Integer/parseInt %))
         (reduce +))))


(def text-digits
  (into {} (map vector
                (map name [:zero :one :two :three :four
                           :five :six :seven :eight :nine])
                (map str (range 10)))))


(def number-digits
  (->> (range 10)
       (map (fn [s] [(str s) (str s)]))
       (into {})))


(defn underscore-first
  "Replace first occurance of value with underscores
   Ex.
      ['9169nine6kvninecklgmn', 'nine'] => '9169____6kvninecklgmn'
      ['9169nine6kvninecklgmn', '6'] => '91_9nine6kvninecklgmn'
  "
  [s value]
  (str/replace-first s value (str/join (repeat (count value) "_"))))


(defn index-of-all
  "Returns a list of index value tuples for all indexes of value in s
  Ex.
      ['9169nine6kvcklgmn', '9'] => [[0 '9'] [3 '9']]
  "
  [s value]
  (first
    (reduce (fn [[acc rest-of-string] c]
              (let [i (str/index-of rest-of-string value)]
                (if i
                  [(conj acc [i value]) (underscore-first rest-of-string value)]
                  [acc ""])))
            [[] s]
            s)))


(defn digit-positions
  "Position and digit tuples from a line
   Ex.
      [text-digits 9169nine6kfourvninecklgmn] => ([1 'nine'] [15 'nine'] [10 'four'])
      [number-digits 9169nine6kfourvninecklgmn] => ([0 '9'] [3 '9'] [2 '6'] [8 '6'] [1 '1'])
  "
  [digits line]
  (->> digits
       (reduce (fn [acc [k v]]
                 (concat acc (index-of-all line k)))
               [])))


(defn positions-and-digits-tuples
  "Position and digit tuples from a line
   Ex.
      zoneight234 => [1 '1'] [3 '8'] [8 '2'] [9 '3'] [10 '4']
  "
  [line]
  (let [td (->> (digit-positions text-digits line)
                (map (fn [[k v]] [k (get text-digits v)])))
        dd (digit-positions number-digits line)]
    (concat td dd)))


(defn part2 [lines]
  (->> lines
       (map positions-and-digits-tuples)
       (map #(sort-by first %))
       (map util/first-and-last)
       (map (fn [[[_ v1] [_ v2]]] [v1 v2]))
       (map str/join)
       (map #(Integer/parseInt %))
       (reduce +)))

