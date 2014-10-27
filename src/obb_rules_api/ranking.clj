(ns obb-rules-api.ranking
  (:require [obb-rules-api.reply :as reply]
            [clojure.data.json :as json]
            [obb-ranking.core :as ranking]))

(defn default-handler
  "Returns the default ranking"
  [raw]
  (reply/ok (ranking/default-player)))

(defn- load-player
  "Loads the player hash from json"
  [raw selector]
  (-> (get-in raw [:params selector])
      (json/read-str :key-fn keyword)))

(defn calculate-handler
  "Calculates the new rakings of players"
  [raw]
  (let [winner (load-player raw :winner)
        other (load-player raw :other)
        result (ranking/calculate winner other)]
    (reply/ok {:winner (first result)
               :other (last result)})))
