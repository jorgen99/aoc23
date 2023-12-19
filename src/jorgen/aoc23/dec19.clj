(ns jorgen.aoc23.dec19
  (:require
    [clojure.string :as str]
    [clojure.edn :as edn]
    [jorgen.aoc23.util :as util]))


(defn ->rule [rule-parts]
  (let [parsed-parts (map (fn [r] (re-matches #"(\w)(.)(\d+)\:(\w+)" r)) (butlast rule-parts))
        pp (map (fn [part]
                  (let [[category op value next-rule] (rest part)]
                    {:category  (keyword category)
                     :op        (ns-resolve 'clojure.core (symbol op))
                     :threshold (util/parse-int value)
                     :next-rule (keyword next-rule)}))
                parsed-parts)]
    {:rules pp :default-rule (keyword (last rule-parts))}))


(defn parse-workflow [workflow]
  (let [name (->> (str/split workflow #"\{")
                  first
                  keyword)
        between (second (re-matches #".*?\{(.*?)\}" workflow))
        rule-parts (str/split between #",")]
    [name (->rule rule-parts)]))


(defn parse-part [part-def]
  (->> (str/replace part-def #"[,=]" " ")
       edn/read-string
       (map (fn [[k v]] [(keyword k) v]))
       (into {})))


(defn one-step-in-flow [{:keys [rules default-rule]} part]
  (loop [rs rules]
    (if (empty? rs)
      default-rule
      (let [{:keys [category op threshold next-rule]} (first rs)]
        (if (op (category part) threshold)
          next-rule
          (recur (rest rs)))))))


(defn accepted? [workflow part]
  (loop [rule (:in workflow)]
    (let [next-rule (one-step-in-flow rule part)]
      (case next-rule
        (:A :R) next-rule
        (recur (next-rule workflow))))))


(defn part1 [lines]
  (let [blocks (util/parse-blocks lines)
        workflow (->> (first blocks)
                      (map parse-workflow)
                      (into {}))
        parts (->> (second blocks)
                   (map parse-part))]
    (->> parts
         (map (partial accepted? workflow))
         (map vector parts)
         (filter #(= :A (second %)))
         (map first)
         (map vals)
         flatten
         (reduce +))))


(comment
  (time (part1 (util/file->lines "dec19_sample.txt")))
  (time (part1 (util/file->lines "dec19_input.txt"))))
;(time (part2 (util/file->lines "dec19_sample.txt")))
;(time (part2 (util/file->lines "dec19_input.txt"))))


