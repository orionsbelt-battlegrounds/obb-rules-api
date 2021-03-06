(ns obb-rules-api.reply
  (:require [compojure.handler :as handler]
            [clojure.data.json :as json]
            [obb-rules.result :as result]
            [obb-rules-api.parser :as parser]
            [compojure.route :as route]))

(defn- make-response
  "Builds a response"
  [status-code obj]
  {:status status-code
   :headers {"Content-Type" "application/json; charset=utf-8"}
   :body (parser/dump obj)})

(defn ok
  "Returns HTTP 200 response"
  [obj]
  (make-response 200 obj))

(defn exception
  "Returns an exception"
  [exception]
  (make-response 500 {:exception (.getMessage exception)}))

(defn not-found
  "Returns HTTP 404 response"
  []
  (make-response 404 {:error "Resource not found"}))

(defn precondition-failed
  "Returns HTTP 412 response"
  [error-description]
  (make-response 412 {:error error-description}))

(defn result
  "Returns based on the given result"
  [result]
  (if (result/succeeded? result)
    (ok result)
    (make-response 412 result)))

