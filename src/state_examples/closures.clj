;; This file contains a conversion of an example in the LFE code base, that in
;; turn was a conversion of some of the Common Lisp code from the beginning of
;; Chapter 13 in Peter Norvig's "Paradigms of Artificial Intelligence
;; Programming: Case Studies in Common Lisp".
;;
;; For example usage, see the README file, in particular:
;;    https://github.com/oubiwann/maintaining-state-in-clojure#state-via-closures
;;
;; You may also view the unit tests for example usage.
(ns state-examples.closures)

(defn new-account [name balance interest-rate]
  (fn [message]
    (case message
      :name (fn [] name)
      :balance (fn [] balance)
      :interest (fn []
                   (new-account
                     name
                     (+ balance (* balance interest-rate))
                     interest-rate))
      :deposit (fn [amt]
                  (new-account
                    name
                    (+ balance amt)
                    interest-rate))
      :withdraw (fn [amt]
                  (cond (<= amt balance)
                    (new-account
                      name
                      (- balance amt)
                      interest-rate)
                    :else (throw (Exception. ": Insufficient funds.")))))))

(defn get-account-method [object command]
  (apply object [command]))

(defn get-name [object]
  (apply (get-account-method object :name) []))

(defn get-balance [object]
  (apply (get-account-method object :balance) []))

(defn apply-interest [object]
  (apply (get-account-method object :interest) []))

(defn withdraw [object amt]
  (apply (get-account-method object :withdraw) [amt]))

(defn deposit [object amt]
  (apply (get-account-method object :deposit) [amt]))
