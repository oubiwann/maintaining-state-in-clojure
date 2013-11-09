(defproject state-examples "0.1.0-SNAPSHOT"
  :description "Examples for working with state data in Clojure"
  :url "https://github.com/oubiwann/maintaining-state-in-clojure"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :profiles {
    :dev {
      :dependencies [[org.clojure/tools.namespace "0.2.3"]
                     [org.clojure/java.classpath "0.2.0"]]}
    :testing {
      :dependencies [[leiningen "2.3.3"]]}})
