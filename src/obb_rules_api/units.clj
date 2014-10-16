(ns obb-rules-api.units
  (:require [obb-rules-api.reply :as reply]
            [obb-rules.unit :as obb-units]))

(defn handler
  "Processes the units endpoint"
  []
  (reply/ok (obb-units/get-units)))
