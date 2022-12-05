(ns supply-stacks)

(def initial-stacks
  {
    1 ["D" "T" "R" "B" "J" "L" "W" "G"]
    2 ["S" "W" "C"]
    3 ["R" "Z" "T" "M"]
    4 ["D" "T" "C" "H" "S" "P" "V"]
    5 ["G" "P" "T" "L" "D" "Z"]
    6 ["F" "B" "R" "Z" "J" "Q" "C" "D"]
    7 ["S" "B" "D" "J" "M" "F" "T" "R"]
    8 ["L" "H" "R" "B" "T" "V" "M"]
    9 ["Q" "P" "D" "S" "V"]
  })

(def test-stacks
  {
    1 ["Z" "N"]
    2 ["M" "C" "D"]
    3 ["P"]
  })

(defn update-stacks
  [stacks instructions reverse?]
  (let [[amount from to] instructions
        selected ((if reverse? reverse identity) (take-last amount (stacks from)))
        from-stack (drop-last amount (stacks from))
        to-stack (concat (stacks to) selected)]
    (assoc stacks from from-stack to to-stack)))

(defn parse-line
  [line]
  (->>
    (re-find #"move (\d+) from (\d+) to (\d+)" line)
    (rest)
    (map #(Integer/parseInt %))))

(defn move-stacks
  [reverse?]
  (->>
    (slurp "input.in")
    (clojure.string/split-lines)
    (map parse-line)
    (reduce #(update-stacks %1 %2 reverse?) initial-stacks)
    (into (sorted-map))
    (vals)
    (map last)
    (clojure.string/join "")))

; part 1
; (move-stacks true)

; part 2
(move-stacks false)


