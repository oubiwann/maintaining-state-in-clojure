(ns state-examples.data-test
  (:require [clojure.test :refer :all]
            [state-examples.data :as data]))


(deftest test-account
  (let [account (data/new-account "savings" 1000 0.05)]
    (testing "check account name"
      (is (= "savings" (data/get-name account))))
    (testing "check account balance"
      (is (= 1000 (data/get-balance account))))
    (testing "check account interest rate"
      (is (= 0.05 (data/get-interest-rate account))))
    (testing "check account interest"
      (is (= 1050.0 (data/get-balance (data/apply-interest account)))))
    (testing "check account withdraw"
      (is (= 860 (data/get-balance (data/withdraw account 140)))))
    (testing "check account over-withdraw"
      (is (thrown-with-msg?
            java.lang.Exception
            #"Insufficient funds"
            (data/withdraw account 1000.01))))
    (testing "check account deposit"
      (is (= 3500 (data/get-balance (data/deposit account 2500)))))))



