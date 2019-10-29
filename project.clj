(defproject satori-transformer "0.1.0-SNAPSHOT"
  :description "Transform satori exports for better anki importing"
  :license {:name "MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.csv "0.1.4"]]
  :main ^:skip-aot satori-transformer.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
