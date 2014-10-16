(ns obb-rules-api.random-game
  (:require [obb-rules-api.reply :as reply]
            [obb-rules.game :as game]))

(defn handler
  "Creates a game with a random stash"
  []
  (reply/ok (game/random)))
