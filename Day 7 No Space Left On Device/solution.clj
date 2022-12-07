(ns file-system)

(defn cd-or-size?
  [line]
  (let [split (clojure.string/split line #" ")
        frst (first split)
        second (nth split 1)]
    (and (not= frst "dir") (not= second "ls"))))

(defn update-sizes
  [system size]
  (let [tracked (get system :tracked)]
    (reduce #(update-in %1 [:sizes %2] + size) system tracked)))

(defn add-size
  [system name]
  (if (get-in system [:sizes name]) system (assoc-in system [:sizes name] 0)))

(defn update-tracked
  [system cd-arg]
  (let [path (first (:tracked system))
        folder (clojure.string/join "/" [path cd-arg])]
    (cond
      (= cd-arg "..") (update system :tracked rest)
      :else (add-size (update system :tracked #(cons folder %)) folder))))

(def initial-system
  {:sizes {"/" 0} :tracked ["/"]})

(defn execute
  [system line]
  (let [split (clojure.string/split line #" ")
        frst (first split)
        second (nth split 1)]
    (cond
      (= second "cd") (update-tracked system (nth split 2))
      :else (update-sizes system (Integer/parseInt frst)))))

(defn sort-by-value
  [my-map]
  (into (sorted-map-by (fn [key1 key2] (<= (get my-map key1) (get my-map key2)))) my-map))

(def folder-sizes
  (->>
    (slurp "input.in")
    (clojure.string/split-lines)
    (rest)
    (filter cd-or-size?)
    (reduce execute initial-system)
    :sizes))

; part 1
(->>
  folder-sizes
  (vals)
  (filter #(<= % 100000))
  (reduce +))

(defn find-max-min
  [sizes]
  (let [overall-size (get sizes "/")
        required (- overall-size 40000000)]
    (->>
      (vals sizes)
      (filter #(>= % required))
      (apply min))))

;  part 2
(->>
  folder-sizes
  (find-max-min))