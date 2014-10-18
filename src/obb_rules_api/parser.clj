(ns obb-rules-api.parser
  (:require [clojure.data.json :as json]))

(defn dump-game
  "Serializes a game to JSON"
  [game]
  (json/write-str game))

(defn load-game
  "Loads a game from JSON"
  [raw-game]
  (json/read-str raw-game :key-fn keyword))

