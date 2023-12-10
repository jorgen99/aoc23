(ns jorgen.aoc23.dec10
  (:require
    [clojure.string :as str]
    [jorgen.aoc23.util :as util]))


(defn find-start [lines]
  (loop [ls lines
         line-no 0]
    (let [idx (str/index-of (first ls) "S")]
      (if idx
        [idx line-no]
        (recur (rest ls) (inc line-no))))))

(def nsew [:n :e :s :w])

(def valid-pipes
  {:n #{"|" "F" "7"}
   :e #{"-" "7" "J"}
   :s #{"|" "L" "J"}
   :w #{"-" "F" "L"}})


(defn valid-exit [point came-from grid]
  (->> nsew
       (map #(take-step % point))
       (util/remove-invalid-points grid)
       set
       (#(disj % came-from))))

;I start-punkten, hitta exits :n :s
;ta en av dem :n
;gå till nästa pos
;hitta möjliga exits :e [:n :e]
;gå till nästa pos
;hitta möjliga exits :e [:n :e :e]
;gå till nästa pos
;hitta möjliga exits :e [:n :e :e]

(defn find-valid-exits
  ([position grid]
   (find-valid-exits position [] grid))
  ([position exclude grid]
   (prn "position" position)
   (prn "exclude" exclude)
   (->> nsew
        (reduce (fn [exit direction]
                  (prn "#¤%##¤#%#%#¤#¤")
                  (prn "position" position)
                  (prn "exit" exit)
                  (prn "direction" direction)
                  (let [pos-in-direction (util/take-step position direction)
                        pipe (util/value-in-grid grid pos-in-direction)
                        _ (prn "pipe" pipe)
                        valid-pipes-in-direction (direction valid-pipes)]
                    _ (prn "valid-pipes-in-direction" valid-pipes-in-direction)
                    (if (valid-pipes-in-direction pipe)
                      (conj exit direction)
                      exit)))
                [])
        set
        (#(disj % exclude))
        vec)))


(defn came-from-dir [walked-direction]
  (case walked-direction
    :n :s
    :e :w
    :s :n
    :w :e))


(let [lines (util/file->lines "dec10_sample2.txt")
      _ (prn "###########################################")
      start (find-start lines)
      _ (prn "start" start)
      grid (util/parse-grid-of-chars lines)
      possible (find-valid-exits start grid)
      _ (prn "possible" possible)
      dir' (first possible)
      _ (prn "dir'" dir')
      next-pos (util/take-step start dir')
      _ (prn "next-pos" next-pos)
      _ (prn ")))))))))))))))))))))))))))))))))999")]
  ;next-dir (first (find-valid-exits next-pos [:e] grid))
  ;_ (prn "next-dir" next-dir)
  ;ns (util/take-step next-pos next-dir)
  ;_ (prn "ns" ns)]
  (loop [dir dir'
         position start
         path []]
    (prn "))))))))))))))))))))))))))))))))))))))))))))")
    (prn "dir" dir)
    (prn "position" position)
    (prn "path" path)
    (prn "(util/value-in-grid grid position)" (util/value-in-grid grid position))
    (if (and
          (not-empty path)
          (= "S" (util/value-in-grid grid position)))
      path
      (let [came-from-dir (came-from-dir dir)
            _ (prn "came-from-dir" came-from-dir)
            exit (first (find-valid-exits position came-from-dir grid))
            _ (prn "exit" exit)
            next-position (util/take-step position exit)]
        (recur exit next-position (conj path dir))))))






(comment
  (time (part1 (util/file->lines "dec10_sample1.txt"))))
;(time (part1 (util/file->lines "dec10_input.txt")))
;(time (part2 (util/file->lines "dec10_sample1.txt")))
;(time (part2 (util/file->lines "dec10_input.txt"))))


