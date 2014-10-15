(defproject obb-rules-api "1.0.0-SNAPSHOT"
  :description "JSON/REST API for obb-rules"
  :url "https://github.com/orionsbelt-battlegrounds/obb-rules-api"

  :source-paths ["src"]
  :test-paths ["test"]

  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-time "0.8.0"]
                 [org.clojure/data.json "0.2.5"]
                 [javax.servlet/servlet-api "2.5"]
                 [compojure "1.2.0"]]

  :plugins [[lein-ring "0.8.12"]]
  :ring {:handler obb-rules-api.routes/app}
  :profiles {:dev {:dependencies [[ring-mock "0.1.5"]]}})
