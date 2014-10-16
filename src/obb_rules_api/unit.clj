(ns obb-rules-api.unit
  (:require [obb-rules-api.reply :as reply]
            [obb-rules.unit :as obb-units]))

(defn handler
  "Processes the units endpoint"
  [unit-name]
  (let [unit (obb-units/fetch unit-name)]
    (if unit
      (reply/ok unit)
      (reply/precondition-failed (str "UnitNotRecognized:" unit-name)))))
