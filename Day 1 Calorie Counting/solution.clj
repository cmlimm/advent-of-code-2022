(ns calorie-counting)

(defn to-int
  [string]
  (if (= "" string) 0 (Integer/parseInt string)))

(def parse
  (->> (slurp "input.in")
    	 (clojure.string/split-lines)
    	 (map to-int)
    	 (partition-by #(= 0 %))))

(def part1
  (->> parse
    	 (map #(reduce + %))
    	 (apply max)))

part1

(def part2
  (->> parse
    	 (map #(reduce + %))
    	 (sort >)
    	 (take 3)
       (reduce +)))

part2