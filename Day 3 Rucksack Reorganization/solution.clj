(ns rucksack-reorganization)

(def alphabet
  (let
    [alphabet-lower (map #(str (char %)) (range (int \a) (inc (int \z))))
     alphabet-upper (map #(str (char %)) (range (int \A) (inc (int \Z))))]
  (concat alphabet-lower alphabet-upper)))

(def letter-value
  (zipmap alphabet (range 1 53)))

(defn split-in-halves
  [list]
  (let [n (count list)
        half (/ n 2)]
    [(set (take half list))
     (set (take-last half list))]))

(def parse
  (->>
  (slurp "input.in")
  (clojure.string/split-lines)
  (map #(clojure.string/split % #""))))

; part 2
(->>
  parse
  (map set)
  (partition 3)
  (map #(apply clojure.set/intersection %))
  (map #(get letter-value (last (into [] %))))
  (reduce +))

; part 1
(->>
  parse
  (map split-in-halves)
  (map #(apply clojure.set/intersection %))
  (map #(get letter-value (last (into [] %))))
  (reduce +))

