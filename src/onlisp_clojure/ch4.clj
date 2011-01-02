; ch4
; 4.1
; example from http://blog.fogus.me/2008/10/08/on-lisp-clojure-chapter-4/

(defn findr [f sq]
  (when (seq sq)
    (let [value (f (first sq))]
      (if (nil? value)
        (recur f (rest sq))
        [(first sq) value]))))

(defn evenr [elem]
  (if (even? elem)
    "is even"))

; 4.3

(defn single? [sq]
  (and (seq? sq) (empty? (rest sq))))

(defn append1 [sq obj]
  (concat sq (cons obj '())))

(defn mklist [obj]
  (if (seq? obj)
    obj
    (cons obj '())))

(defn longer [x y]
   (and (not (empty? x))
	(or (empty? y)
	    (recur (rest x) (rest y)))))

;user> (longer '(1 2) '(1 2 3))
;false
;user> (longer '(1 2 3) '(1 2))
;true
;user> (longer '(1 2) '(1 2))
;false

; filter is provided in clojure core

; large section skipped due to in flight reading, return and complete (hopefully).

