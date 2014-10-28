(defproject obb-rules-api "1.1.0"
  :description "JSON/REST API for obb-rules"
  :url "https://github.com/orionsbelt-battlegrounds/obb-rules-api"

  :source-paths ["src"]
  :test-paths ["test"]

  :scm {:name "git"
        :url "git@github.com:orionsbelt-battlegrounds/obb-rules-api.git"}

  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-time "0.8.0"]
                 [org.clojure/data.json "0.2.5"]
                 [javax.servlet/servlet-api "2.5"]
                 [ring/ring-jetty-adapter "1.2.2"]
                 [environ "0.5.0"]
                 [obb-rules "1.0.0"]
                 [obb-ranking "1.0.0"]
                 [compojure "1.2.0"]]

  :plugins [[environ/environ.lein "0.2.1"]
            [lein-ring "0.8.12"]]
  :main obb-rules-api.routes/-main
  :ring {:handler obb-rules-api.routes/app}
  :hooks [environ.leiningen.hooks]
  :uberjar-name "obb-api-rules-standalone.jar"
  :profiles {:dev {:dependencies [[ring-mock "0.1.5"]]
                   :plugins [[com.jakemccrary/lein-test-refresh "0.5.2"]]}
             :production {:env {:production true}}})
