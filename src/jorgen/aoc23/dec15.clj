(ns jorgen.aoc23.dec15
  (:require
    [clojure.string :as str]
    [jorgen.aoc23.util :as util]))



(defn hash-step [token]
  (reduce (fn [acc c]
            (->> (int c)
                 (+ acc)
                 (* 17)
                 (#(mod % 256))))
          0
          token))


(defn tokenize-first-line [lines]
  (-> lines
      first
      (str/split #",")))


(defn part1 [lines]
  (let [steps (tokenize-first-line lines)]
    (->> steps
         (map hash-step)
         (reduce +))))


(defn parse-lens-op [step]
  (let [parts (re-matches #"(\w+)([-=])(\d?)" step)]
    {:label       (second parts)
     :focal-lenth (get parts 3)
     :lens-hash   (hash-step (second parts))
     :op          (get parts 2)}))


(defn upsert-lens-in-box
  "Split the lenses at the lens with the label we are inserting/removeing.
   If the tail is empty (no lense with that label) just insert the lens
   last in the list. If the tail has lenses, then the lens we want to update
   is first in that list. Update, join head and tail and Bob's your uncle.
   Ex.
   "
  [label focal-lenth box]
  (let [[head tail] (split-with #(not= label (:label %)) box)
        updated (if (first tail)
                  (assoc (first tail) :focal-lenth focal-lenth)
                  {:label label :focal-lenth focal-lenth})]
    (concat head (cons updated (rest tail)))))


(defn lens-operation
  "Add, uppdate or remove a lens to/from the box it belongs to."
  [{:keys [op focal-lenth label]} box]
  (if (= "-" op)
    (remove #(= label (:label %)) box)
    (upsert-lens-in-box label focal-lenth box)))


(defn perform-lens-op
  "Add or remove a lens from the boxes. Create a box if there is none.
   Ex.
       Add:
           boxes:   {}
           lens-op: {:label 'rn', :focal-lenth '1' :lens-hash 0 :op '='}
       =>  {0 [{:label 'rn', :focal-lenth '1'}]}

       Remove:
        boxes:    {0 [{:label 'rn', :focal-lenth '1'} {:label 'cm', :focal-lenth '2'}]
                   1 [{:label 'qp', :focal-lenth '3'}]}
        lens-op:  {:label 'cm', :focal-lenth '2' :lens-hash 0 :op '='}

       =>  {0 [{:label 'rn', :focal-lenth '1'} {:label 'cm', :focal-lenth '2'}]}
  "
  [boxes {:as lens-op :keys [lens-hash]}]
  (if (seq (get boxes lens-hash))
    (update boxes lens-hash (fn [box] (lens-operation lens-op box)))
    (if (= "-" (:op lens-op))
      boxes
      (assoc boxes lens-hash [(dissoc lens-op :lens-hash :op)]))))


(defn focus-power
  "Calculate the focus power of lenses in a box.
   BoxNo * SlotNo * Focal Length.
   Ex.
         [0 ({:label 'rn', :focal-lenth '1'} {:label 'cm', :focal-lenth '2'}]
      => 5
  "
  [[box-idx box]]
  (reduce (fn [acc [lens-idx lens]]
            (+ acc
               (-> (* (inc box-idx))
                   (* (inc lens-idx))
                   (* (util/parse-int (:focal-lenth lens))))))
          0
          (map-indexed vector box)))


(defn part2 [lines]
  (->> (tokenize-first-line lines)
       (map parse-lens-op)
       (reduce perform-lens-op {})
       (map focus-power)
       (reduce +)))


(comment
  (time (part1 (util/file->lines "dec15_sample.txt")))
  (time (part1 (util/file->lines "dec15_input.txt")))
  (time (part2 (util/file->lines "dec15_sample.txt")))
  (time (part2 (util/file->lines "dec15_input.txt"))))
