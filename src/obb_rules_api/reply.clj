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

(defn not-found
  "Returns HTTP 404 response"
  []
  (make-response 404 {:error "Resource not found"}))

(defn precondition-failed
  "Returns HTTP 412 response"
  [error-description]
  (make-response 412 {:error error-description}))
