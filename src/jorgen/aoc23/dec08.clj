(ns jorgen.aoc23.dec08
  (:require
    [jorgen.aoc23.util :as util]))


(defn parse-left-right [nodes]
  (->> nodes
       (map #(re-matches #"(\w+)\s+=\s+\((\w+),\s+(\w+)\)" %))
       (reduce (fn [acc [_ node left right]]
                 (assoc acc (keyword node) { :L (keyword left) :R (keyword right)}))
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


;; Aborted brute force after 400 000 000 steps
(defn part2-brute-force
  "Travel from the start nodes and see if all locations
   ends with a Z. If not repeat.
  "
  [lines]
  (let [directions (->> (first lines) (map str) (map keyword))
        locations (parse-left-right (drop 2 lines))
        start-locations (end-with-location \A locations)]
    (loop [current-locations start-locations
           steps 0
           remaining-directions directions]
      (when (= 0 (mod steps 1000000))
        (prn steps))
      (if (exit? current-locations)
        steps
        (let [direction (first remaining-directions)
              next-nodes (travel current-locations direction locations)
              rest-of-directions (if (empty? (rest remaining-directions))
                                   directions
                                   (rest remaining-directions))]
          (recur next-nodes (inc steps) rest-of-directions))))))


(defn travel-single-to-end
  "Travel from a start node and loop until location ends with a Z"
  [location directions locations]
  (loop [current-locations [location]
         steps 0
         remaining-directions directions]
    (if (exit? current-locations)
      steps
      (let [direction (first remaining-directions)
            next-nodes (travel current-locations direction locations)
            rest-of-directions (if (empty? (rest remaining-directions))
                                 directions
                                 (rest remaining-directions))]
        (recur next-nodes (inc steps) rest-of-directions)))))


(defn part2-with-lcm
  "Travel each start node separately and count the steps for each,
   then find the least common multiple of the steps.
  "
  [lines]
  (let [directions (->> (first lines) (map str) (map keyword))
        locations (parse-left-right (drop 2 lines))
        start-locations (end-with-location \A locations)]
    (->> start-locations
         (map #(travel-single-to-end % directions locations))
         util/lcm-multiple)))


(comment
  (time (part1 (util/file->lines "dec08_sample.txt")))
  (time (part1 (util/file->lines "dec08_input.txt")))
  (time (part2-with-lcm (util/file->lines "dec08_sample2.txt")))
  (time (part2-with-lcm (util/file->lines "dec08_input.txt"))))
