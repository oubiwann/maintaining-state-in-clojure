(ns state-examples.channels
  (:require [clojure.core.async :as async]))

(defn send-name [data client]
  (async/>!! client {:result (:name data)})
  data)

(defn send-balance [data client]
  (async/>!! client {:result (:balance data)})
  data)

(defn send-apply-interest [data client]
  (let [balance (:balance data)
        interest-rate (:interest-rate data)]
    (async/>!! client {:result :ok})
    (assoc data :balance (+ balance (* balance interest-rate)))))

(defn send-withdrawl [data client amt]
  (if (<= amt (:balance data))
    (do
      (async/>!! client {:result :ok})
      (assoc data :balance (- (:balance data) amt)))
    (do
      (async/>!! client {:result :insufficient-funds})
      data)))

(defn send-deposit [data client amt]
  (async/>!! client {:result :ok})
  (assoc data :balance (+ (:balance data) amt)))

(defn new-account [name balance interest-rate]
  (let [ch (async/chan)
        data {:name name
              :balance balance
              :interest-rate interest-rate}]
    (async/go
      (loop [d data]
        (when-let [msg (async/<! ch)]
          (recur (case (:cmd msg)
                   :name (send-name d (:client msg))
                   :balance (send-balance d (:client msg))
                   :apply-interest (send-apply-interest d (:client msg))
                   :withdraw (send-withdrawl
                              d (:client msg) (:amount msg))
                   :deposit (send-deposit
                             d (:client msg) (:amount msg)))))))
    ch))

(defn make-call
  ([acc-ch cmd]
    (make-call acc-ch cmd nil))
  ([acc-ch cmd amount]
    (let [client-ch (async/chan)]
      (async/>!! acc-ch {:client client-ch :cmd cmd :amount amount})
      (when-let [msg (async/<!! client-ch)]
        (:result msg)))))

(defn get-name [acc-ch]
  (make-call acc-ch :name))

(defn get-balance [acc-ch]
  (make-call acc-ch :balance))

(defn apply-interest [acc-ch]
  (make-call acc-ch :apply-interest))

(defn withdraw [acc-ch amount]
  (make-call acc-ch :withdraw amount))

(defn deposit [acc-ch amount]
  (make-call acc-ch :deposit amount))
