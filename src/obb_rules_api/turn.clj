(ns obb-rules-api.turn
  (:require [obb-rules-api.reply :as reply]
            [obb-rules.turn :as turn]
            [obb-rules.result :as result]
            [obb-rules-api.parser :as parser]
            [obb-rules.translator :as translator]
            [obb-rules.game :as game]))

(defn- resolve-focus
  "If the focus is :p2, translate the actions"
  [focus actions]
  (translator/actions (keyword focus) actions))

(defn- include-p2-focused-board
  "Checks if it's necessary to include a p2 focused board"
  [result data]
  (if (= true (data :p2-focused-board))
    (assoc result :p2-focused-board (translator/board :p2 (result/result-board result)))
    result))

(defn get-raw-json
  "Retrieves the raw json from the given request"
  [raw]
  (println raw)
  (if-let [body (get-in raw [:params :context])]
    body
    (slurp (:body raw))))

(defn handler
  "Processes given actions to a game"
  [raw]
  (let [player (keyword ((raw :params) :player))
        raw-json (get-raw-json raw)
        data (parser/load-game raw-json)
        battle (data :game)
        action-focus (or (data :action-focus) :p1)
        actions (resolve-focus action-focus (data :actions))
        result (-> (apply turn/process battle player actions)
                   (include-p2-focused-board data))]
    (reply/result result)))
