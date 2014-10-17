(ns obb-rules-api.turn
  (:require [obb-rules-api.reply :as reply]
            [obb-rules.game :as game]))

(defn handler
  "Processes given actions to a game"
  [player game actions]
  (reply/ok (game/random)))
