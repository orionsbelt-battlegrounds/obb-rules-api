(ns obb-rules-api.parser
  (:require [clojure.data.json :as json]))

(defn dump-game
  "Serializes a game to JSON"
  [game]
  (json/write-str game))

(defn- parse-specific-keys
  "Parses specific keys to expected format"
  [key value]
  (cond
    (= key :state) (keyword value)
    :else value))

(defn load-game
  "Loads a game from JSON"
  [raw-game]
  (json/read-str raw-game :key-fn keyword
                          :value-fn parse-specific-keys))

(defn dump-actions
  "Serializes actions as json"
  [actions]
  (json/write-str actions))

