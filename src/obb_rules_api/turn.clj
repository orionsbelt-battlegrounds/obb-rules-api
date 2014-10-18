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
        body (parser/load-game raw-json)
        battle (game/random)
        actions []]
    #_(turn/process battle player actions)
    (reply/ok (game/random))))
