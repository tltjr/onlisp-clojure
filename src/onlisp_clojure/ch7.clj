; examples from http://thinkrelevance.com/blog/2008/12/12/on-lisp-clojure-chapter-7.html

(defn nil! [at]
  (swap! at (fn [_] nil)))

;user> (def a (atom 10))
;#'user/a
;user> (nil! a)
;nil
;user> @a
;nil

; 7.2 Backquote

;user> (= `(a b c) '(user/a user/b user/c))
;true
;user> (= `(a b c) (list 'user/a 'user/b 'user/c))
;true   
;user> (def b 0)
;#'user/b
;user> (def d 1)
;#'user/d
;user> (= `(a ~b c ~d) (list 'user/a b 'user/c d))
;true
;user> (def a 1)
;#'user/a
;user> (def b 2)
;#'user/b
;user> (def c 3)
;#'user/c
;user> `(a ~b c)
;(user/a 2 user/c)
;user> `(a (~b c))
;(user/a (2 user/c))
;user> `(a b ~c ('~(+ a b c)) (+ a b) 'c '((~a ~b)))
;(user/a user/b 3 ((quote 6)) (clojure.core/+ user/a user/b) (quote user/c) (quote ((1 2))))

(use '[clojure.contrib.fcase :only (case)])
 (defmacro nif [expr pos zer neg]
   `(case (Integer/signum ~expr)
	  -1 ~neg
	  0 ~zer
	  1 ~pos))

;user> (map (fn [x] (nif x 'p 'z 'n)) '(0 2.5 -8))
;(z p n)

;user> (def b '(1 2 3))
;#'user/b
;user> `(a ~b c)
;(user/a (1 2 3) user/c)
;user> `(a ~@b c)
;(user/a 1 2 3 user/c)

;7.4 Testing macroexpansion

(defmacro our-while [test & body]
    `(loop []
       (when ~test
         ~@body
         (recur))))

;Figure 7.4
;user> (macroexpand '(our-while able laugh))
;(loop* [] (clojure.core/when able laugh (recur))) 
;user> (macroexpand-1 '(our-while able laugh))
;(clojure.core/loop [] (clojure.core/when able laugh (recur)))
;nil

;Figure 7.5

(defmacro mac [expr]
  `(clojure.pprint/pprint (macroexpand-1 '~expr)))

;user> (mac (our-while able laugh))
;(clojure.core/loop [] (clojure.core/when able laugh (recur)))
;nil

; 7.5 - Destructuring in Parameter Lists

; again, from link at top
(defmacro when-bind [bindings & body]
    (let [[form tst] bindings]
      `(let [~form ~tst]
         (when ~form
     ~@body))))

;user> (when-bind [a (+ 1 2)] (println "a is:" a))
;a is: 3

; better version is included in clojure core
;user> (when-let [[a & b] [1 2 3]] (println "b is:" b))
;b is: (2 3)
;nil

; 7.10 Macros from Function

; second is already defined in clojure core
; it's worth noting that it is implemented as (first (next x))
; and not (first (rest x)) as might be expected. The difference
; between the two functions can be seen below.
;user> (rest '(1))
;()
;user> (next '(1))
;nil

(defn sec [x] (second x))

(defmacro sec [x] `(second ~x))

(defmacro noisy-second [x]
  `(do
     (println "Someone is taking a second!")
     (second ~x)))

(defmacro sum [args & rest]
  `(apply #'+ (list ~@args)))

;user> (sum (1 2 3 4))
;10
;user> (sum (1 1))
;2
;user> (sum ())
;0

(defmacro foo [x y z]
  `(list ~x (list ~y ~z)))

(use 'clojure.contrib.macro-utils)
(symbol-macrolet [hi (do (println "Howdy") 1)] (+ hi 2))

;user> (symbol-macrolet [hi (do (println "Howdy") 1)] (+ hi 2))
; 
;Howdy
;3
