; Each of the below are equivalent

(+ 1 2)

(apply + '(1 2))

(apply (fn [x y] (+ x y)) '(1 2))

(apply + 1 '(2))

; Mapping functions

(map (fn [x] (+ x 10)) '(1 2 3))
; (11 12 13)

(map + '(1 2 3) '(10 100 1000))
; (11 102 1003)

; Sort
(sort < '(1 4 2 5 6 7 3))
; (1 2 3 4 5 6 7)

; "remove-if"
(remove even? '(1 2 3 4 5 6 7))
; (1 3 5 7)

; clojure implementation of our-remove-if
; Notice the lack of funcall, which is not needed
; in clojure because the first arg is evaluated.
(defn our-remove-if [pred coll]
  (if (empty? coll) ; (if (seq coll) is idiomatic
    nil
    (if (pred (first coll))
      (our-remove-if pred (rest coll))
      (cons (first coll) (our-remove-if pred (rest coll))))))

; 2.5
; Scope
; The below example demonstrates that Clojure is not dynamically
; scoped, but lexically scoped, as is common lisp.
;Clojure=> (let [y 7]
;            (defn scope-test [x]
;              (list x y)))
;#'user/scope-test
;Clojure=> (scope-test 3)
;(3 7)
;Clojure=> (let [y 5]
;            (scope-test 3))
;(3 7)

; 2.6
; Closures
;
; "If we look closely at the function which is passed to mapcar (map)
; within list+, it’s
; actually a closure. The instance of n is free, and its binding
; comes from the
; surrounding environment. Under lexical scope, every such use of
; a mapping
; function causes the creation of a closure"
;Clojure=> (defn list+ [coll n]
;            (map (fn [x] (+ x n))
;                 coll))
;#'user/list+
;Clojure=> (list+ '(1 2 3) 10)
;(11 12 13)

; Make adder
; Note Clojure does not require setq and funcall.
; We can simply use 'def' and call the function.
(defn make-adder [n]
  (fn [x] (+ x n)))

;Clojure=> (def add2 (make-adder 2))
;#'user/add2
;Clojure=> (def add10 (make-adder 10))
;#'user/add10
;Clojure=> (add2 5)
;7
;Clojure=> (add10 3)
;13

; Make adderb implementation from Rich Hickey
; http://paste.lisp.org/display/67788
(defn make-adderb [m]
  (let [n (ref m)]
    (fn [x & change]
      (if change
        (dosync (ref-set n x))
        (+ @n x)))))

;Clojure=> (def addx (make-adderb 1))
;#'user/addx
;Clojure=> (addx 1)
;2
;Clojure=> (addx 100 true)
;100
;Clojure=> (addx 3)
;103

; 2.7
; Local Functions

(defn count-instances [obj coll]
  (defn instances-in [coll]
    (if (not (empty? coll))
      (+ (if (= (first coll) obj) 1 0)
         (instances-in (rest coll)))
      0))
  (map instances-in coll))

(defn our-find-if [pred coll]
  (if (pred (first coll))
    (first coll)
    (recur pred (rest coll))))

; Clojure=> (our-find-if even? '(1 2 3 4))
; 2

(defn our-length [coll]
  (defn rec [coll acc]
    (if (empty? coll)
      acc
      (rec (rest coll) (inc acc))))
  (rec coll 0))

;Clojure=> (our-length '(1 2 3))
;3

(defn triangle [n]
  (defn tri [c n]
    (if (zero? n)
      c
      (tri (+ n c) (dec n))))
  (tri 0 n)) 

;Clojure=> (triangle 1000)
;500500
;Clojure=> (triangle 6)
;21
