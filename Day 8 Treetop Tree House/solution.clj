(ns treetop-tree-house
  (:require [clojure.string :as str]))

(defn strings-to-int
  [line]
  (map #(Integer/parseInt %) line))

(defn get-column
  [matrix column]
  (map #(nth % column) matrix))

(def parse-input
  (->>
    (slurp "input.in")
    (str/split-lines)
    (map #(str/split % #""))
    (map strings-to-int)))

(defn visible-in-row?
  [row idx]
  (let [element (nth row idx)
        before (take idx row)
        after (nthrest row (inc idx))]
    (or
      (every? #(> element %) before)
      (every? #(> element %) after))))

(defn visible?
  [matrix x y]
  (let [row (nth matrix x)
        column (get-column matrix y)]
    (or
      (visible-in-row? row y)
      (visible-in-row? column x))))

; part 1
(reduce +
  (for [[x row] (map-indexed vector parse-input)
        [y val] (map-indexed vector row)]
   (if (visible? parse-input x y) 1 0)))


; part 2

; https://stackoverflow.com/a/30928487
(defn take-while+
  [pred coll]
  (lazy-seq
    (when-let [[f & r] (seq coll)]
      (if (pred f)
        (cons f (take-while+ pred r))
        [f]))))

(defn seen-count
  [list element]
  (count (take-while+ #(> element %) list)))

(defn visibility-score-in-row
  [row idx]
  (let [element (nth row idx)
        before (take idx row)
        after (nthrest row (inc idx))]
    (*
      (seen-count (reverse before) element)
      (seen-count after element))))

(defn visibility-score
  [matrix x y]
  (let [row (nth matrix x)
        column (get-column matrix y)]
    (*
      (visibility-score-in-row row y)
      (visibility-score-in-row column x))))

(apply max
  (let [row-number (count parse-input)
        column-number (count (nth parse-input 0))]
    (for [[x row] (map-indexed vector parse-input)
          [y val] (map-indexed vector row)
          :when (and
                  (not= 0 x) (not= 0 y)
                  (not= (dec row-number) x) (not= (dec column-number) y))]
      (visibility-score parse-input x y))))
