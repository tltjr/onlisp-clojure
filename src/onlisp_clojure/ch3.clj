;; 3.1
;; Functional Design

(defn good-reverse [coll]
  (defn rev [coll acc]
    (if (empty? coll)
      acc
      (rev (rest coll) (cons (first coll) acc))))
  (rev coll nil))
