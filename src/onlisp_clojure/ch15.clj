;; Chapter 15

;; Ch. 15.2

defn our-every [f lst]
  (if (empty? lst)
    true
    (and (f (first lst))
	 (our-every f (rest lst)))))

;user> (our-every odd? '(1 2 3))
;false
;user> (our-every odd? '(1 3 5))
;true




