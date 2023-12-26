(ns jorgen.aoc23.dec14
  (:require
    [clojure.string :as str]
    [jorgen.aoc23.util :as util]))


(defn find-first [pred coll]
  (->> (map-indexed vector coll)
       (drop-while #(not (pred (second %))))
       first


(let [lines (util/file->lines "dec14_sample.txt")
      cols (util/transpose lines)]
  (->> (first cols)
         (partition 2 1)
         (find-first (fn [t] (if (= t [])))))))

(defn find-first [pred coll]
  (->> (map-indexed vector coll)
       (drop-while #(not (pred (second %))))
       first



(comment
  (time (part1 (util/file->lines "dec14_sample.txt"))))
;(time (part1 (util/file->lines "dec14_input.txt")))
;(time (part2 (util/file->lines "dec14_sample.txt")))
;(time (part2 (util/file->lines "dec14_input.txt"))))


