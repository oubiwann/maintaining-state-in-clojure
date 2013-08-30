(ns state-examples.fake-objects)

(defn new-account [name balance interest-rate]
  (fn [message]
    (case message
      "name" (fn [] name)
      "balance" (fn [] balance)
      "interest" (fn []
                   (new-account
                     name
                     (+ balance (* balance interest-rate))
                     interest-rate))
      "deposit" (fn [amt]
                  (new-account
                    name
                    (+ balance amt)
                    interest-rate))
      "withdraw" (fn [amt]
                  (cond (<= amt balance)
                    (new-account
                      name
                      (- balance amt)
                      interest-rate)
                    :else (throw (Exception. "Insufficient funds.")))))))

(defn get-account-method [object command]
  (apply object [command]))

(defn get-name [object]
  (apply (get-account-method object "name") []))

(defn get-balance [object]
  (apply (get-account-method object "balance") []))

(defn apply-interest [object]
  (apply (get-account-method object "interest") []))

(defn withdraw [object amt]
  (apply (get-account-method object "withdraw") [amt]))

(defn deposit [object amt]
  (apply (get-account-method object "deposit") [amt]))