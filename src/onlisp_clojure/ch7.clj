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

; 7.3 Defining simple macros

(defmacro our-when [test & body]
    `(if ~test
       (do
         ~@body)))
