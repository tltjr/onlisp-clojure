;; Notes from
;; - http://thinkrelevance.com/blog/2008/12/17/on-lisp-clojure-chapter-9.html

(def w (ref []))

(defmacro gripe [warning]
  `(do 
     (dosync (alter w conj ~warning))
     nil))

(defn sample-ratio [v w]
  (let [vn (count v) wn (count w)]
    (if (or (< vn 2) (< wn 2))
      (gripe "sample < 2")
      (/ vn wn))))

(defmacro bad-gripe [warning]
  `(do
     (dosync (alter ~'w conj ~warning))
     nil))

(defn bad-sample-ratio [v w]
  (let [vn (count v) wn (count w)]
    (if (or (< vn 2) (< wn 2))
      (bad-gripe "sample < 2")
      (/ vn wn))))

;; Clojure=> (bad-sample-ratio [] [])
;; #<CompilerException java.lang.ClassCastException: clojure.lang.PersistentVector cannot be cast to clojure.lang.Ref (REPL:0)>

(defmacro foo [x y]
  `(/ (inc ~x) ~y))

;; The below call expands to (/ (inc (- 5 2)) 6)
;; Clojure=> (foo (- 5 2) 6)
;; 2/3

