; Tommy Ekberg, toek3476, 1/10/2023

; OBS: I added the reflection part at the end of this .clj instead of handing in safe.clj.

(defn my-max [numbers]
  ; Check if the list is empty.
  ; If it is, return nil.
  (if (empty? numbers)
    nil
    ; Check if the list has only one element.
    ; If it does, return the first element.
    (if (= 1 (count numbers))
      (first numbers)
      ; Otherwise, find the maximum value in the rest of the list
      ; and compare it to the first element.
      ; Return the maximum value.
      (let [max (my-max (rest numbers))]
        (if (> max (first numbers))
          max
          (first numbers))))))

(defn my-min [numbers]
  ; Check if the list is empty.
  ; If it is, return nil.
  (if (empty? numbers)
    nil
    ; Check if the list has only one element.
    ; If it does, return the first element.
    (if (= 1 (count numbers))
      (first numbers)
      ; Otherwise, find the minimum value in the rest of the list
      ; and compare it to the first element.
      ; Return the minimum value.
      (let [min (my-min (rest numbers))]
        (if (< min (first numbers))
          min
          (first numbers))))))

(defn my-checksum1 [numbers]
  ; Check if the input list is empty.
  ; If it is, return 0.
  (if (empty? numbers)
    0
    ; Otherwise, add the difference between the maximum and minimum values
    ; of the first list to the result of calling my-checksum1 on the rest of the input.
    (+ (if (empty? (first numbers))
         0
         (- (my-max (first numbers)) (my-min (first numbers))))
       (my-checksum1 (rest numbers)))))

(defn my-map [numbers function]
  ; If the list is empty,
  ; return an empty list.
  (if (empty? numbers)
    []
    ; Otherwise, apply the function to the first element of the list
    ; and add the result to the front of the list returned by the recursive call to my-map.
    (cons (function (first numbers)) (my-map (rest numbers) function))))

"Tried to do my-checksum2, but did not produce correct results. Does not work!"
(defn my-checksum2 [lists checksum function]
  ; If the list of lists is empty,
  ; return 0
  (if (empty? lists)
    0
    ; If there is only one list left in the list of lists,
    ; calculate its checksum
    (if (empty? (rest lists))
      (checksum (first lists))
      ; Otherwise combine the checksum of the first list with the checksum of the rest of the lists
      (function (checksum (first lists)) (my-checksum2 (rest lists) checksum function)))))

(defn my-reverse [lst]
  ; If the list is empty,
  ; return an empty list.
  (if (empty? lst)
    []
    ; Otherwise, go through my-reverse with rest. At the end concat the first element in lst, which is going to be the last
    ; element in the original list.
    (concat (my-reverse (rest lst)) (list (first lst)))))

; REFLECTION

"In general, functions are pieces of code that take input, perform some operations on that input, and return an output.
On the other hand, Macros are pieces of code that manipulate code itself, rather than just data.

One key difference between functions and macros is that macros are executed at compile-time, while functions are executed at runtime.
This means that macros can modify the code itself, while functions can only operate on the data that is passed to them.

In the context of the safe macro, it is not possible to implement it as a function.
This is because the safe macro needs to be able to manipulate code, specifically by adding in a finally block that closes
any resources that have been opened. A function, on the other hand, can only operate on data and cannot modify the
code in which it is contained.

When deciding whether to use a function or a macro in a given situation,
it is important to consider whether you need to modify code or just operate on data. If you need to modify the code itself,
a macro is likely the way to go. If you just need to operate on data, a function is likely a better choice.
It is also worth noting that macros can often be more difficult to write and debug than functions,
so it is important to carefully consider whether their use is necessary in a given situation."
