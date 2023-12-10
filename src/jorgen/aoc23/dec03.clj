(ns jorgen.aoc23.dec03
  (:require
    [clojure.string :as str]
    [clojure.set :refer [intersection]]
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
                  (conj (first temp) (second temp))         ;; if number on last position
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


(defn surrounding-points
  "All points surrounding a part, including invalid points"
  [part]
  (rest
    (reduce (fn [current-pos step]
              (conj current-pos (util/take-step step (last current-pos))))
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
  [part grid]
  (let [height (count (first grid))
        width (count grid)]
    (-> (surrounding-points part)
        (remove-invalid-points width height))))


(defn is-symbol? [c]
  (not
    (or (re-matches #"\d" c)
        (= c "."))))


(defn part?
  "Walk around the part checking for a symbol"
  [part grid]
  (let [part? (->> (surrounding-in-grid part grid)
                   (some #(is-symbol? (util/value-in-grid grid %))))]
    (when part?
      (:number part))))


(defn part1 [lines]
  (let [grid (parse-grid lines)]
    (->> grid
         possible-parts-in-grid
         (map #(part? % grid))
         (remove nil?)
         (reduce +))))


(defn gear-indicies
  "Find the index of all gears
  Ex.
      .234.*.#.321*.  => (5, 12)
  "
  [line]
  (reduce (fn [acc [i s]]
            (if (= s \*)
              (conj acc i)
              acc))
          []
          (map-indexed vector line)))


(defn gear-positions [lines]
  (->> lines
       (map-indexed vector)
       (mapcat (fn [[i line]]
                 (map (fn [j] [i j]) (gear-indicies line))))
       (remove empty?)
       (map reverse)))


(defn connected-parts
  "Given a gear position return a list of all parts that are connected"
  [gear-pos parts]
  (reduce (fn [acc part]
            (let [inter (intersection (:surrounding part) (set [gear-pos]))]
              (if (empty? inter)
                acc
                (conj acc (:number part)))))
          []
          parts))


(defn parts-with-surronding
  "Parts with surrounding positions"
  [lines]
  (let [grid (parse-grid lines)]
    (->> grid
         possible-parts-in-grid
         (map (fn [part]
                (assoc part :surrounding (set (surrounding-in-grid part grid))))))))


(defn part2 [lines]
  (let [parts (parts-with-surronding lines)]
    (->> (gear-positions lines)
         (map #(connected-parts % parts))
         (filter #(<= 2 (count %)))
         (map #(apply * %))
         (reduce +))))


(comment
  (part1 (util/file->lines "dec03_sample.txt"))
  (part1 (util/file->lines "dec03_input.txt"))
  (part2 (util/file->lines "dec03_sample.txt"))
  (part2 (util/file->lines "dec03_input.txt")))
