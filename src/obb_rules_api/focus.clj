(ns obb-rules-api.focus
  "Allows the manipulation of the board by player focus"
  (:require [obb-rules-api.reply :as reply]
            [obb-rules-api.turn :as turn]
            [obb-rules.result :as result]
            [obb-rules-api.parser :as parser]
            [obb-rules.translator :as translator]
            [obb-rules.game :as game]))

(defn handler
  "Returns a representation of the game, with the focus on a given player"
  [raw]
  (let [player (parser/build-player raw)
        data (parser/build-data raw)
        battle (->> (data :game)
                    (translator/board player))]
    (reply/result (result/action-success battle "OK"))))
