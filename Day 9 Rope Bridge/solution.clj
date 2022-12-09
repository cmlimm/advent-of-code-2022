(ns rope-bridge
  (:require [clojure.string :as str]))

(def directions
  {"U" [0 1]
   "D" [0 -1]
   "R" [1 0]
   "L" [-1 0]})

(defn move
  [head direction]
  (map + head (directions direction)))

(defn sign
  [number]
  (cond
    (= number 0) 0
    (< number 0) -1
    (> number 0) 1))

(defn follow
  [tail head]
  (let [[hx hy] head
        [tx ty] tail
        dx (- hx tx)
        dy (- hy ty)]
    (if (every? #(<= % 1) [(Math/abs dx) (Math/abs dy)])
      tail
      (map + tail [(sign dx) (sign dy)]))))

(def input
  (->>
    (slurp "input.in")
    (str/split-lines)
    (map #(str/split % #" "))
    (mapcat #(repeat (Integer/parseInt (second %)) (first %)))))

; part 1
(->>
  input
  (reductions move '(0 0))
  (drop 2)
  (reductions follow '(0 0))
  (set)
  (count))

; part 2
(->>
  input
  (reductions move '(0 0))
  (drop 2)
  (iterate #(reductions follow '(0 0) %))
  (take 10)
  (last)
  (set)
  (count))