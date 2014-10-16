(ns obb-rules-api.index
  (:require [obb-rules-api.reply :as reply]))

(defn handler
  "Processes the root endpoint"
  []
  (reply/ok {:name "obb-rules-api"}))
