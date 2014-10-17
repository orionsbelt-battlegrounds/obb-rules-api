(ns obb-rules-api.turn
  (:require [obb-rules-api.reply :as reply]
            [obb-rules.turn :as turn]
            [clojure.data.json :as json]
            [obb-rules.game :as game]))

(defn handler
  "Processes given actions to a game"
  [raw]
  (let [player (keyword (raw :params))
        raw-json (slurp (:body raw))
        body (json/read-str raw-json :key-fn keyword)
        battle (game/random)
        actions []]
    (println body)
    #_(turn/process battle player actions)
    (reply/ok (game/random))))
