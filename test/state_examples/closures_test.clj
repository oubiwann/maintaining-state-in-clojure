(ns state-examples.closures-test
  (:require [clojure.test :refer :all]
            [state-examples.closures :as closures]))


(deftest test-account
  (let [account (closures/new-account "savings" 1000 0.05)]
    (testing "check account name"
      (is (= "savings" (closures/get-name account))))
    (testing "heck account balance"
      (is (= 1000 (closures/get-balance account))))
    (testing "check account interest"
      (is (= 1050.0 (closures/get-balance (closures/apply-interest account)))))
    (testing "check account withdraw"
      (is (= 860 (closures/get-balance (closures/withdraw account 140)))))
    (testing "check account over-withdraw"
      (is (thrown? java.lang.Exception (closures/withdraw account 1000.01))))
    (testing "check account deposit"
      (is (= 3500 (closures/get-balance (closures/deposit account 2500)))))))
