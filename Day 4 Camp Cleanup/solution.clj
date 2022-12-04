(ns camp-cleanup)

(defn parse-line
  [line]
  (->>
    (re-find #"(\d+)-(\d+),(\d+)-(\d+)" line)
    (rest)
    (map #(Integer/parseInt %))))

(defn fully-contains?
  [list]
  (let [[a b c d] list]
    (cond
    (and (>= c a) (<= d b)) 1
	(and (>= a c) (<= b d)) 1
    :else 0)))

(defn overlap?
  [list]
  (let [[a b c d] list]
    (cond
    (and (>= c a) (<= c b)) 1
	(and (>= a c) (<= a d)) 1
    (and (>= b c) (<= b d)) 1
	(and (>= d a) (<= d b)) 1
    :else 0)))

; part 2
(->>
  (slurp "input.in")
  (clojure.string/split-lines)
  (map (comp overlap? parse-line))
  (reduce +))

; part 1
(->>
  (slurp "input.in")
  (clojure.string/split-lines)
  (map (comp fully-contains? parse-line))
  (reduce +))