(ns jorgen.aoc23.dec02
  (:require
    [clojure.string :as str]
    [jorgen.aoc23.util :as util]))


(def no-of-cubes
  {:red 12 :green 13 :blue 14})


(defn parse-grab [grab]
  (let [parsed-grab (re-seq #"(\d+) (\w+)" grab)]
    (reduce (fn [acc [_ no color]]
              (assoc acc (keyword color) (util/parse-int no)))
            {}
            parsed-grab)))


(defn parse-line [line]
  (let [[game cubes] (str/split line #":")
        game-no (->> (re-matches #"Game (\d+)" game)
                     second
                     util/parse-int)
        grabs (str/split (str/trim cubes) #"; ")
        parsed-grabs (reduce (fn [acc grab]
                               (conj acc (parse-grab grab)))
                             []
                             grabs)]
    [game-no parsed-grabs]))


(defn ok-no-of-cubes? [[color cubes]]
  (<= cubes (get no-of-cubes color)))


(defn good-game? [game]
  (->> game
       (mapcat (fn [m]
                 (map ok-no-of-cubes? m)))
       (filter false?)
       empty?))


(defn good-game-no [[game-no game]]
  (when (good-game? game)
    game-no))


(defn good-games [games]
  (->> games
       (map good-game-no)
       (remove nil?)))


(defn parse-games [lines]
  (reduce (fn [acc line]
            (conj acc (parse-line line)))
          []
          lines))


(defn part1 [lines]
  (->> lines
       parse-games
       good-games
       (reduce +)))


(defn max-cubes-in-game [game]
  (reduce (fn [max-cubes grab]
            (reduce-kv (fn [acc color cubes]
                         (update acc color #(max % cubes)))
                       max-cubes
                       grab))
          {:blue 0 :red 0 :green 0}
          game))


(defn part2 [lines]
  (->> lines
       parse-games
       (map second)
       (map max-cubes-in-game)
       (map vals)
       (map #(reduce * %))
       (reduce +)))
