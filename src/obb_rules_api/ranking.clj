(ns obb-rules-api.ranking
  (:require [obb-rules-api.reply :as reply]
            [obb-ranking.core :as ranking]))

(defn default-handler
  "Returns the default ranking"
  [raw]
  (reply/ok (ranking/default-player)))
