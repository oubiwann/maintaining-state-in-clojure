(ns state-examples.protocols-test
  (:require [clojure.test :refer :all]
            [state-examples.protocols :as protocols])
  (:import [state_examples.protocols Account]))


(deftest test-account
  (let [account (protocols/Account. "savings" 1000 0.05)]
    (testing "check account name"
      (is (= "savings" (protocols/get-name account))))
    (testing "check account balance"
      (is (= 1000 (protocols/get-balance account))))
    (testing "check account interest"
      (is (= 1050.0 (protocols/get-balance
                      (protocols/apply-interest account)))))
    (testing "check account withdraw"
      (is (= 860 (protocols/get-balance
                   (protocols/withdraw account 140)))))
    (testing "check account over-withdraw"
      (is (thrown-with-msg?
            java.lang.Exception
            #"Insufficient funds"
            (protocols/get-balance
              (protocols/withdraw account 1000.01)))))
    (testing "check account deposit"
      (is (= 3500 (protocols/get-balance
                    (protocols/deposit account 2500)))))))
