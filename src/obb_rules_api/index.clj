(ns obb-rules-api.index
  (:require [obb-rules-api.reply :as reply]
            [obb-rules.game :as game]
            [obb-rules.laws :as laws]))

(defn handler
  "Processes the root endpoint"
  []
  (reply/ok {:name "obb-rules-api"
             :version (System/getProperty "obb-rules-api.version")
             :rules {:version game/version
                     :default-board-width laws/default-board-w
                     :default-board-height laws/default-board-h
                     :max-action-points laws/max-action-points
                     :min-move-percentage laws/min-move-percentage}}))
