(ns obb-rules-api.turn
  (:require [obb-rules-api.reply :as reply]
            [obb-rules.turn :as turn]
            [obb-rules-api.parser :as parser]
            [obb-rules.game :as game]))

(defn handler
  "Processes given actions to a game"
  [raw]
  (let [player (keyword (raw :params))
        raw-json (slurp (:body raw))
        data (parser/load-game raw-json)
        battle (data :game)
        actions (data :actions)]
    (apply turn/process battle player actions)
    (reply/ok (game/random))))
