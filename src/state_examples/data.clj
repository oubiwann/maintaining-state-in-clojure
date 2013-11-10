(ns state-examples.data)

(defn new-account [name balance interest-rate]
  {:name name
   :balance balance
   :interest-rate interest-rate})

(defn get-name [data]
  (data :name))

(defn get-balance [data]
  (data :balance))

(defn get-interest-rate [data]
  (data :interest-rate))

(defn apply-interest [data]
  (let [balance (get-balance data)
        interest-rate (get-interest-rate data)]
    (new-account
      (get-name data)
      (+ balance (* balance interest-rate))
      interest-rate)))

(defn withdraw [data amt]
  (let [balance (get-balance data)]
  (cond (<= amt balance)
    (new-account
      (get-name data)
      (- (get-balance data) amt)
      (get-interest-rate data))
    :else (throw (Exception. ": Insufficient funds.")))))

(defn deposit [data amt]
  (new-account
    (get-name data)
    (+ (get-balance data) amt)
    (get-interest-rate data)))
