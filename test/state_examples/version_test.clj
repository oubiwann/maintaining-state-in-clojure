(ns state-examples.version-test
  (:require [clojure.test :refer :all]
            [state-examples.version :as version]))

(deftest test-version
  (is (= 0 (version/version :major)))
  (is (= 1 (version/version :minor)))
  (is (= 0 (version/version :patch)))
  (is (= true (version/version :snapshot))))

(deftest test-version-str
  (is (= "v0.1-dev" version/version-str)))
