(ns jorgen.aoc23.dec16
  (:require
    [clojure.string :as str]
    [jorgen.aoc23.util :as util]))


(def dir-after-mirror
  {"/"  {:n :w :e :s :s :e :w :n}
   "\\" {:n :e :e :n :s :w :w :s}})

(def dir-after-splitter
  {"|" {:e [:n :s] :w [:n :s]}
   "-" {:n [:e :w] :s [:e :w]}})


(defn update-energy [energy pos]
  (util/set-value-in-grid energy pos "#"))


(defn new-direction [type previous-dir]
  (case type
    "." (vec previous-dir)
    ("|" "-") (if-let [new-dir (previous-dir
                                 (get dir-after-splitter type))]
                new-dir
                previous-dir)
    ("/" "\\") (vec (previous-dir (get dir-after-mirror type)))))


(let [lines (util/file->lines "dec16_sample.txt")
      grid (util/parse-grid-of-chars lines)]
  (loop [pos [0 0]
         previous-dir :e
         energy grid
         split-paths []]
    (prn "-------------------------------")
    (prn "split-paths" split-paths)
    (prn "(some neg? pos)" (some neg? pos))
    (if (and
          (empty? split-paths)
          (some neg? pos))
      energy
      (let [[[pos new-dir] split-paths]
            (if (some neg? pos)
              (split-at 1 split-paths)
              [[pos (first
                      (new-direction
                        (util/value-in-grid grid pos)
                        previous-dir))]
               split-paths])
            energy (update-energy energy pos)
            split-paths (if-let [split-dir (rest new-dir)]
                          (conj split-paths [pos split-dir]))
            new-pos (util/take-step pos new-dir)
            _ (clojure.pprint/pprint energy)]
        (recur new-pos new-dir energy split-paths)))))


(comment
  (time (part1 (util/file->lines "dec16_sample.txt"))))
;(time (part1 (util/file->lines "dec16_input.txt")))
;(time (part2 (util/file->lines "dec16_sample.txt")))
;(time (part2 (util/file->lines "dec16_input.txt")))v)
