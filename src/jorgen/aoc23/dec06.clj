(ns jorgen.aoc23.dec06
  (:require
    [clojure.string :as str]
    [jorgen.aoc23.util :as util]))


(defn record-times-for-race [[t r]]
  (loop [times (range 1 (inc t))
         records (int 0)]
    (if (empty? times)
      records
      (let [hold (first times)
            distance (* (- t hold) hold)
            records (if (> distance r)
                      (inc records)
                      records)]
        (recur (rest times) records)))))


(defn record-times [races]
  (map record-times-for-race races))


(defn part1 [lines]
  (let [times (util/parse-ints (first lines))
        records (util/parse-ints (second lines))
        races (map vector times records)]
    (->> races
         record-times
         (reduce *))))


(defn parse-number [line]
  (->> line
       util/parse-number-str
       str/join
       parse-long))


(defn part2 [lines]
  (let [time' (parse-number (first lines))
        record (parse-number (second lines))
        race [[time' record]]]
    (->> race
         record-times
         first)))


(comment
  (time (part1 (util/file->lines "dec06_sample.txt")))
  (time (part1 (util/file->lines "dec06_input.txt")))
  (time (part2 (util/file->lines "dec06_sample.txt")))
  (time (part2 (util/file->lines "dec06_input.txt"))))

