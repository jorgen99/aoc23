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

