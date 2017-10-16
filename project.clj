(defproject state-examples "0.2.0-SNAPSHOT"
  :description "Examples for working with state data in Clojure"
  :url "https://github.com/oubiwann/maintaining-state-in-clojure"
  :license {
    :name "Apache License, Version 2.0"
    :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [
    [org.clojure/clojure "1.8.0"]
    [org.clojure/core.async "0.3.443"]]
  :plugins [[lein-exec "0.3.6"]]
  :profiles {
    :dev {
      :source-paths ["dev-resources/src"]
      :repl-options {
        :init-ns state-examples.dev}
      :dependencies [
        [org.clojure/tools.namespace "0.2.10"]]}
    :test {
      :exclusions [org.clojure/clojure]
      :plugins [
        [jonase/eastwood "0.2.5"]
        [lein-ancient "0.6.12"]
        [lein-bikeshed "0.5.0"]
        [lein-kibit "0.1.5"]
        [lein-shell "0.5.0"]
        [venantius/yagni "0.1.4"]]}}
  :aliases {
    "check-deps" [
      "with-profile" "+test" "ancient" "check" ":all"]
    "kibit" [
      "with-profile" "+test" "do"
        ["shell" "echo" "== Kibit =="]
        ["kibit"]]
    "outlaw" [
      "with-profile" "+test"
      "eastwood" "{:namespaces [:source-paths] :source-paths [\"src\"]}"]
    "lint" [
      "with-profile" "+test" "do"
        ["check"] ["kibit"] ["outlaw"]]})
