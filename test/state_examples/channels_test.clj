(ns state-examples.channels-test
  (:require [clojure.test :refer :all]
            [state-examples.channels :as channels]))

(deftest test-account
  (let [account (channels/new-account "savings" 1000 0.05)]
    (testing "check account name"
      (is (= "savings" (channels/get-name account))))
    (testing "check account balance"
      (is (= 1000 (channels/get-balance account))))
    (testing "check account interest"
      (is (= :ok (channels/apply-interest account)))
      (is (= 1050.0 (channels/get-balance account))))
    (testing "check account withdraw"
      (is (= :ok (channels/withdraw account 140)))
      (is (= 910.0 (channels/get-balance account))))
    (testing "check account over-withdraw"
      (is (= :insufficient-funds (channels/withdraw account 1000.01)))
      (is (= 910.0 (channels/get-balance account))))
    (testing "check account deposit"
      (is (= :ok (channels/deposit account 2500)))
      (is (= 3410.0 (channels/get-balance account))))))
