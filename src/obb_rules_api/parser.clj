(ns obb-rules-api.parser
  "Utilities for parsing/building requests"
  (:require [clojure.data.json :as json]
            [obb-rules.unit :as unit]))

(defn- clean-up-action-result
  "Removes unnecessary data from an action result"
  [[action result]]
  [action (-> (dissoc result :board))])

(defn- clean-up-action-results
  "Removes unnecessary data from action results"
  [results]
  (map clean-up-action-result results))

(defn- trim-game
  "Removes unnecessary data from the game's dump"
  [key value]
  (cond
    (= :unit key) (unit/unit-name value)
    (= :action-results key) (clean-up-action-results value)
    :else value))

(defn dump-game
  "Serializes a game to JSON"
  [game]
  (json/write-str game :value-fn trim-game))

(defn dump
  "Serialized a given object"
  [obj]
  (dump-game obj))

(defn- parse-specific-keys
  "Parses specific keys to expected format"
  [key value]
  (cond
    (= key :state) (keyword value)
    (= key :player) (keyword value)
    (= key :direction) (keyword value)
    (= key :unit) (unit/fetch value)
    (= key :action-results) nil
    :else value))

(defn- custom-keyword
  "Transforms strings in keywords"
  [key]
  (if-let [raw (re-matches #"\[(\d+) (\d+)\]" key)]
    (into [] (map read-string (rest raw)))
    (keyword key)))

(defn load-game
  "Loads a game from JSON"
  [raw-game]
  (json/read-str raw-game :key-fn custom-keyword
                          :value-fn parse-specific-keys))

(defn dump-actions
  "Serializes actions as json"
  [actions]
  (json/write-str actions))

(defn get-game-json
  "Retrieves the raw json from the given request"
  [raw]
  (if-let [body (get-in raw [:params :context])]
    body
    (slurp (:body raw))))

(defn build-data
  "Loads the data from the raw request"
  [raw]
  (-> (get-game-json raw)
      (load-game)))

(defn build-player
  "Loads the player from the raw request"
  [raw]
  (keyword ((raw :params) :player)))
