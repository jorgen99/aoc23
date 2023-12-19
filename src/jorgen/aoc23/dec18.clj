(ns jorgen.aoc23.dec18
  (:require
    [clojure.string :as str]
    [jorgen.aoc23.util :as util]))


(defn flood-fill
  "Recursive flood fill algorithm."
  [grid x y target replacement]
  (if (or (< x 0) (>= x (count grid))
          (< y 0) (>= y (count (grid 0)))
          (not= (get-in grid [x y]) target))
    grid
    (let [grid-updated (assoc-in grid [x y] replacement)]
      (-> grid-updated
          (flood-fill (inc x) y target replacement)
          (flood-fill (dec x) y target replacement)
          (flood-fill x (inc y) target replacement)
          (flood-fill x (dec y) target replacement)))))

(defn find-positions-inside-loop
  "Identify positions inside the loop in a grid."
  [grid-string]
  (let [grid (map vec (clojure.string/split-lines grid-string))]
    (let [filled-grid (flood-fill grid 0 0 \. \o)]
      (for [x (range (count filled-grid))
            y (range (count (first filled-grid)))
            :when (= (get-in filled-grid [x y]) \.)]
        [x y]))))

; Define the grid
(def grid-string
  "#######\n#.....#\n###...#\n..#...#\n..#...#\n###.###\n#...#..\n##..###\n.#....#\n.######")

; Find positions inside the loop
(def inside-positions (find-positions-inside-loop grid-string))



(let [lines (util/file->lines "dec18_sample.txt")]
  lines)


(comment
  (time (part1 (util/file->lines "dec18_sample.txt"))))
;(time (part1 (util/file->lines "dec18_input.txt")))
;(time (part2 (util/file->lines "dec18_sample.txt")))
;(time (part2 (util/file->lines "dec18_input.txt"))))


