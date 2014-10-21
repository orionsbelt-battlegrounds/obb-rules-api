(ns obb-rules-api.turn
  (:require [obb-rules-api.reply :as reply]
            [obb-rules.turn :as turn]
            [obb-rules-api.parser :as parser]
            [obb-rules.translator :as translator]
            [obb-rules.game :as game]))

(defn- resolve-focus
  "If the focus is :p2, translate the actions"
  [focus actions]
  (translator/actions (keyword focus) actions))

(defn handler
  "Processes given actions to a game"
  [raw]
  (let [player (keyword ((raw :params) :player))
        raw-json (slurp (:body raw))
        data (parser/load-game raw-json)
        battle (data :game)
        action-focus (or (data :action-focus) :p1)
        actions (resolve-focus action-focus (data :actions))
        result (apply turn/process battle player actions)]
    (reply/result result)))
