(ns obb-rules-api.routes
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [obb-rules-api.index :as index]
            [clojure.data.json :as json]
            [compojure.route :as route]))

(defroutes api-routes
  (GET "/" [] (index/handler))

  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site api-routes))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'api-routes) {:port port :join? false})))
