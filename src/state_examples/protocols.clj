(ns state-examples.protocols)

(defprotocol AccountAPI
  (get-name [this] "get the account name")
  (get-balance [this] "get the account balance")
  (apply-interest [this] "calculate the interest and apply it to the account")
  (withdraw [this amt] "withdraw the given amount from the account")
  (deposit [this amt] "deposit the given ammount into the account"))

(defrecord Account [name balance interest-rate]
  AccountAPI
  (get-name [this] (:name this))
  (get-balance [this] (:balance this))
  (apply-interest
    [this]
    (conj
      this
      {:balance (+ (:balance this)
                   (* (:balance this) (:interest-rate this)))}))
  (withdraw
    [this amt]
    (cond (<= amt (:balance this))
      (conj
        this
        {:balance (- (:balance this) amt)})
      :else (throw (Exception. ": Insufficient funds."))))
  (deposit
    [this amt]
    (conj
      this
      {:balance (+ (:balance this) amt)})))

(defn new-account
  [name balance interest-rate]
  (->Account name balance interest-rate))
