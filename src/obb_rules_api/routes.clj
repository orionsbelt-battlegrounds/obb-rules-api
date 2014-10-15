(ns obb-rules-api.routes
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes api-routes
  (GET "/" [] "Hello Worldd")
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site api-routes))
