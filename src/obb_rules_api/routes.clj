(ns obb-rules-api.routes
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [obb-rules-api.index :as index]
            [obb-rules-api.units :as units]
            [obb-rules-api.unit :as unit]
            [obb-rules-api.random-game :as random-game]
            [clojure.data.json :as json]
            [ring.adapter.jetty :as jetty]
            [obb-rules-api.reply :as reply]
            [compojure.route :as route]))

(defroutes api-routes
  (GET "/" [] (index/handler))
  (GET "/units" [] (units/handler))
  (GET "/units/:unit-name" [unit-name] (unit/handler unit-name))

  (GET "/game/random" [] (random-game/handler))

  (route/resources "/")
  (route/not-found (reply/not-found)))

(def app
  (handler/site api-routes))

(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "5000"))]
    (jetty/run-jetty api-routes {:port port})))
