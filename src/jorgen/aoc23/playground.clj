(ns jorgen.aoc23.playground
  (:require
    [clojure.string :as str]
    [jorgen.aoc23.util :as util]))

(def lines
  (util/file->lines "dec01_sample.txt"))

(def sample
  (str/split "1721\n979\n366\n299\n675\n1456" #"\n"))

(defn part1 [lines]
  (let [numbers (->> lines
                     (map #(Integer/parseInt %))
                     sort)]
    (loop [first-no (first numbers)
           rest-of-numbers (rest numbers)]
      (let [sum-is-not-2020 #(not= 2020 (+ first-no %))
            sum-entry (->> (drop-while sum-is-not-2020 rest-of-numbers)
                           first)]
        (if sum-entry
          (* first-no sum-entry)
          (recur (first rest-of-numbers) (rest rest-of-numbers)))))))

(->> (util/file->lines "dec01_2020_input.txt")
     part1)

(defn update-grid [x y grid val]
  (update-in grid [y x] (constantly val)))

(defn parse-grid [lines]
  (let [height (count lines)
        line1 (first lines)
        width (count line1)
        grid (vec (repeat height (vec (repeat width "."))))]

    (reduce (fn [acc [i row]]
              (reduce (fn [a [j v]]
                        (let [new-val (str (get (get lines i) j))]
                          (update-grid j i a new-val)))
                      acc
                      (map-indexed vector row)))
            grid
            (map-indexed vector grid))))
