(ns jorgen.aoc23.dec03
  (:require
    [clojure.string :as str]
    [jorgen.aoc23.util :as util]))


(defn parse-grid [lines]
  (mapv #(mapv str %) lines))


(defn parse-line
  "Parse one line into a list of possible parts
  Ex
     467..114.. => [{:x 0, :y 0, :number 467, :length 3}
                    {:x 5, :y 0, :number 114, :length 3}]
  "
  [line line-part]
  (let [temp (reduce (fn [[acc part] [i v]]
                       (if (re-matches #"\d" v)
                         (let [n (if-not (:number part)
                                   (assoc part :x i :y line-part)
                                   part)
                               end (if (:x n)
                                     (+ 1 (count (:number n)))
                                     1)
                               n (-> (update n :number (fn [s] (str/join [s v])))
                                     (assoc :length end))]
                           [acc n])
                         (if (empty? part)
                           [acc]
                           [(conj acc part)])))
                     [[]]
                     (map-indexed vector line))
        ;; Hack because I uesd reduce instead of recursion.
        ;; If the last character is a number we'll get
        ;; the last part in its own vector
        numbers (if (= 2 (count temp))
                  (conj (first temp) (second temp))                     ;; if number on last position
                  (first temp))]
    (map #(update % :number util/parse-int) numbers)))


(defn possible-parts-in-grid
  "Create a list of all possible parts in the grid"
  [grid]
  (mapcat (fn [[i line]]
            (parse-line line i))
          (map-indexed vector grid)))


(defn path-around
  "List of directions around a part in the grid, not
  Ex.
         {:x 0, :y 0, :number 467, :length 3}
      => (:w :n :e :e :e :e :s :s :w :w :w :w)
  "
  [part]
  (let [width (inc (:length part))]
    (-> []
        (concat [:w :n])
        (concat (repeat width :e))
        (concat [:s :s])
        (concat (repeat width :w)))))


(defn take-step
  "Given a direction and a position get the new position.
   Ignoring 'going out of the grid' resulting in -1
  Ex.
       :n [1 2] => [1 1]
       :e [3 8] => [4 8]
       :n [1 0] => [1 -1]
  "
  [direction [x y]]
  (case direction
    :n [x (dec y)]
    :e [(inc x) y]
    :s [x (inc y)]
    :w [(dec x) y]))


(defn surrounding-points
  "All points surrounding a part, including invalid points"
  [part]
  (rest
    (reduce (fn [current-pos step]
              (conj current-pos (take-step step (last current-pos))))
            [[(:x part) (:y part)]]
            (path-around part))))


(defn remove-invalid-points [points width height]
  (->> points
       (remove #(neg? (first %)))
       (remove #(neg? (last %)))
       (remove #(<= width (first %)))
       (remove #(<= height (last %)))))


(defn surrounding-in-grid
  "Valid points in the grid surrounding the part"
  [part width height]
  (-> (surrounding-points part)
      (remove-invalid-points width height)))


(defn is-symbol? [c]
  (not
    (or (re-matches #"\d" c)
        (= c "."))))


(defn value-in-grid [grid pos]
  (get-in grid (reverse pos)))


(defn part?
  "Walk around the part checking for a symbol"
  [part grid]
  (let [height (count (first grid))
        width (count grid)
        part? (->> (surrounding-in-grid part height width)
                   (some #(is-symbol? (value-in-grid grid %))))]
    (when part?
      (:number part))))


(defn part1 [lines]
  (let [grid (parse-grid lines)]
    (->> grid
         possible-parts-in-grid
         (map #(part? % grid))
         (remove nil?)
         (reduce +))))


(comment
  (part1 (util/file->lines "dec03_sample.txt"))
  (part1 (util/file->lines "dec03_input.txt")))
