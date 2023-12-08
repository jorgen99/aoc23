(ns jorgen.aoc23.dec08
  (:require
    [clojure.string :as str]
    [jorgen.aoc23.util :as util]))


(defn parse-left-right [nodes]
  (->> nodes
       (map #(re-matches #"(\w+)\s+=\s+\((\w+),\s+(\w+)\)" %))
       (reduce (fn [acc [_ node left right]]
                 (assoc acc (keyword node) (assoc {} :L (keyword left)
                                                     :R (keyword right))))
               {})))


(defn part1 [lines]
  (let [directions (->> (first lines) (map str) (map keyword))
        locations (parse-left-right (drop 2 lines))]
    (loop [current-location :AAA
           steps 0
           remaining-directions directions]
      (assert (not (nil? current-location)))
      (if (= current-location :ZZZ)
        steps
        (let [direction (first remaining-directions)
              next-node (get-in locations [current-location direction])
              rest-of-directions (if (empty? (rest remaining-directions))
                                   directions
                                   (rest remaining-directions))]
          (recur next-node (inc steps) rest-of-directions))))))


(defn end-with-location [char locations]
  (reduce (fn [acc loc]
            (if (= char (last (name (key loc))))
              (conj acc (key loc))
              acc))
          []
          locations))


(defn exit? [locations]
  (->> locations
       (map name)
       (map last)
       (map #(= \Z %))
       (filter true?)
       count
       (= (count locations))))


(defn travel [current-locations direction locations]
  (->> current-locations
       (map #(get-in locations [% direction]))))


(let [lines (util/file->lines "dec08_sample2.txt")
      directions (->> (first lines) (map str) (map keyword))
      locations (parse-left-right (drop 2 lines))
      start-locations (end-with-location \A locations)]
  (prn "directions" directions)
  (clojure.pprint/pprint locations)
  (prn "start-locations" start-locations)
  (prn "(count directions)" (count directions))


  (loop [current-locations start-locations
         steps 0
         remaining-directions directions]
    (prn "#€#€#€#€#€#€##€#€#€##€#€#")
    (prn "current-locations" current-locations)
    (prn "steps" steps)
    (prn "count remaining-directions" (count remaining-directions))
    (when (= 0 (mod steps 1000000))
      (prn steps))
    (if (exit? current-locations)
      steps
      (let [direction (first remaining-directions)
            ;_ (prn "direction" direction)
            next-nodes (travel current-locations direction locations)
            rest-of-directions (if (empty? (rest remaining-directions))
                                 directions
                                 (rest remaining-directions))]
        (recur next-nodes (inc steps) rest-of-directions)))))




(comment
  (time (part1 (util/file->lines "dec08_sample.txt")))
  (time (part1 (util/file->lines "dec08_input.txt")))
  ;(time (part2 (util/file->lines "dec08_sample2.txt")))
  (time (part2 (util/file->lines "dec08_input.txt"))))

