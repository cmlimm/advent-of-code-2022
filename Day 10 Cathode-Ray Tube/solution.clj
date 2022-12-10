(ns crt
  (:require [clojure.string :as str]))

(defn command->amount
  [command]
  (if (= (first command) "noop")
    [0]
    [0 (Integer/parseInt (second command))]))

(def input
  (->>
    (slurp "input.in")
    (str/split-lines)
    (map #(str/split % #" "))
    (mapcat command->amount)))

(defn interesting-signals
  [signals]
  (map #(nth signals %) (map dec [20 60 100 140 180 220])))

; part 1
(->>
  input
  (reductions + 1)
  (interesting-signals)
  (map * [20 60 100 140 180 220])
  (reduce +))

(defn draw
  [crt sprite]
  (let [before (dec sprite)
        after (inc sprite)]
    (if (some #(= crt %) [before sprite after])
      "▓"
      "░")))

; part 2
(->>
  input
  (reductions + 1)
  (map vector (map #(mod % 40) (range 240)))
  (map #(apply draw %))
  (partition 40)
  (map str/join)
  (map #(spit "output.out" (prn-str %) :append true)))