(ns obb-rules-api.index
  (:require [obb-rules-api.reply :as reply]
            [obb-rules.turn :as turn]
            [obb-rules.game :as game]
            [obb-rules.actions.move :as move]))

(defn handler
  "Processes the root endpoint"
  []
  (reply/ok {:name "obb-rules-api"
             :version (System/getProperty "obb-rules-api.version")
             :rules {:version game/version
                     :max-action-points turn/max-action-points
                     :min-move-percentage move/min-move-percentage}}))
