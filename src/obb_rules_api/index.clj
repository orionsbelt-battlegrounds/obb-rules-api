(ns obb-rules-api.index
  (:require [compojure.handler :as handler]
            [clojure.data.json :as json]
            [compojure.route :as route]))

(defn handler
  "Processes the root endpoint"
  []
  {:status 200
   :headers {"Content-Type" "text/json; charset=utf-8"}
   :body (json/write-str {:name "obb-rules-api"})})
