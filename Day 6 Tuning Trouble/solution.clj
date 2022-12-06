(ns tuning-trouble)

(defn split-in-letters
  [line]
  (clojure.string/split line #""))

(defn find-marker
  [length-distinct]
  (->>
	(slurp "input.in")
	(split-in-letters)
	(partition length-distinct 1)
	(map #(apply distinct? %))
	(take-while not)
	(count)
	(+ length-distinct)))

; part 1
; (find-marker 4)

; part 2
(find-marker 14)
