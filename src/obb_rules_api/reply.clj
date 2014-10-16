(ns obb-rules-api.reply
  (:require [compojure.handler :as handler]
            [clojure.data.json :as json]
            [compojure.route :as route]))

(defn- make-response
  "Builds a response"
  [status-code obj]
  {:status status-code
   :headers {"Content-Type" "text/json; charset=utf-8"}
   :body (json/write-str obj)})

(defn ok
  "Returns HTTP 200 response"
  [obj]
  (make-response 200 obj))
