(ns obb-rules-api.routes
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [obb-rules-api.index :as index]
            [clojure.data.json :as json]
            [ring.adapter.jetty :as jetty]
            [compojure.route :as route]))

(defroutes api-routes
  (GET "/" [] (index/handler))

  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site api-routes))

(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "5000"))]
    (jetty/run-jetty api-routes {:port port})))
