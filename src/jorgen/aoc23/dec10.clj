(ns jorgen.aoc23.dec10
  (:require
    [clojure.string :as str]
    [jorgen.aoc23.util :as util]))


(def nsew [:n :e :s :w])

(def valid-pipes
  {:n #{"|" "F" "7"}
   :e #{"-" "7" "J"}
   :s #{"|" "L" "J"}
   :w #{"-" "F" "L"}})

(def pipe-directions
  {"|" #{:n :s}
   "-" #{:e :w}
   "L" #{:n :e}
   "J" #{:n :w}
   "7" #{:s :w}
   "F" #{:s :e}})

(def came-from-dir
  {:n :s
   :e :w
   :s :n
   :w :e})


(defn find-start
  "Return the position of 'S'
  "
  [lines]
  (loop [ls lines
         line-no 0]
    (let [idx (str/index-of (first ls) "S")]
      (if idx
        [idx line-no]
        (recur (rest ls) (inc line-no))))))


(defn valid-exit-standing-on-pipe
  "Given that you are coming from one direction, find the
   exit direction on the pipe you are standing on.
   Ex.
       'F', :s => :e
       '-', :e => :w
   "
  [pipe coming-from]
  (-> pipe-directions
      (get pipe)
      (disj coming-from)
      first))


(defn find-valid-exits-from-start
  "Look in all directions from 'S', and given the pipes
   find all valid exit directions."
  [position grid]
  (->> nsew
       (reduce (fn [exit direction]
                 (let [pos-in-direction (util/take-step position direction)
                       pipe (util/value-in-grid grid pos-in-direction)
                       valid-pipes-in-direction (direction valid-pipes)]
                   (if (valid-pipes-in-direction pipe)
                     (conj exit direction)
                     exit)))
               [])))


(defn walk-pipes
  "Walk the pipe path and return all the directions"
  [start-pos direction grid]
  (loop [dir direction
         position start-pos
         path []]
    (let [standing-on-pipe (util/value-in-grid grid position)]
      (if (and
            (not-empty path)
            (= standing-on-pipe "S"))
        path
        (let [next-position (util/take-step position dir)
              next-pipe (util/value-in-grid grid next-position)
              came-from-dir (came-from-dir dir)
              next-exit (valid-exit-standing-on-pipe next-pipe came-from-dir)]
          (recur next-exit next-position (conj path dir)))))))


(defn part1 [lines]
  (let [start (find-start lines)
        grid (util/parse-grid-of-chars lines)
        dir-from-start (first (find-valid-exits-from-start start grid))
        path (walk-pipes start dir-from-start grid)]
    (/ (count path) 2)))


(comment
  (time (part1 (util/file->lines "dec10_sample1.txt")))
  (time (part1 (util/file->lines "dec10_sample2.txt")))
  (time (part1 (util/file->lines "dec10_input.txt"))))
;(time (part2 (util/file->lines "dec10_sample1.txt")))
;(time (part2 (util/file->lines "dec10_input.txt"))))


