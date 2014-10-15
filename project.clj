(defproject obb-rules-api "1.0.0-SNAPSHOT"
  :description "JSON/REST API for obb-rules"
  :url "https://github.com/orionsbelt-battlegrounds/obb-rules-api"

  :source-paths ["src"]
  :test-paths ["test"]

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-time "0.8.0"]
                 [compojure "1.2.0"]]

  :plugins [[lein-ring "0.8.12"]]
  :ring {:handler obb-rules-api.routes/app}
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring-mock "0.1.5"]]}})
