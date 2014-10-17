(ns obb-rules-api.routes
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [obb-rules-api.index :as index]
            [obb-rules-api.units :as units]
            [obb-rules-api.unit :as unit]
            [obb-rules-api.random-game :as random-game]
            [obb-rules-api.turn :as turn]
            [clojure.data.json :as json]
            [ring.adapter.jetty :as jetty]
            [obb-rules-api.reply :as reply]
            [ring.middleware.params :as ring-params]
            [compojure.route :as route]))

(defroutes api-routes
  (GET "/" [] (index/handler))
  (GET "/units" [] (units/handler))
  (GET "/units/:unit-name" [unit-name] (unit/handler unit-name))

  (GET "/game/random" [] (random-game/handler))
  (POST "/game/turn/:player" request (turn/handler request))

  (route/resources "/")
  (route/not-found (reply/not-found)))

#_(def api-routes-middleware (ring-params/wrap-params api-routes))
(def api-routes-middleware api-routes)

(def app
  (handler/site api-routes-middleware))

(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "5000"))]
    (jetty/run-jetty api-routes-middleware {:port port})))
