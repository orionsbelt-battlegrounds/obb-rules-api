(ns obb-rules-api.simulator-test
  (:require [obb-rules-api.parser :as parser]
            [ring.util.codec :as codec ])
  (:use clojure.test
        obb-rules-api.routes
        ring.mock.request))

(defn- post-request
  "Creates a post request to resolve a turn"
  [player game]
  (request :post (str "/game/turn/" (name player)) game))

(defn- get-turn-request
  "Creates a get request to resolve a turn"
  [player game]
  (request :get (str "/game/turn/" (name player) "?context=" (codec/url-encode game))))

(defn- get-focus-request
  "Creates a get request to resolve a focus"
  [player game]
  (request :get (str "/game/focus/" (name player) "?context=" (codec/url-encode game))))

(defn turn
  "Simulates a turn processing call"
  [player params expected-status]
  (let [game (parser/dump-game params)
        response (app (get-turn-request player game))
        response-game (parser/load-game (:body response))]
    (is (= expected-status (response :status)))
    (when (not= expected-status (response :status))
      (println response))
    [response-game response]))

(defn turn-ok
  "Simulates a successful turn play"
  [player params]
  (turn player params 200))

(defn turn-fail
  "Simulates a successful turn play"
  [player params]
  (turn player params 412))

(defn turn-exception
  "Simulates a turn play with error"
  [player params]
  (turn player params 500))

(defn focus
  "Simulates a focus processing call"
  [player params expected-status]
  (let [game (parser/dump-game params)
        response (app (get-focus-request player game))
        response-game (parser/load-game (:body response))]
    (is (= expected-status (response :status)))
    (when (not= expected-status (response :status))
      (println response))
    [response-game response]))

(defn focus-ok
  "Simulates a successful focus view"
  [player params]
  (focus player params 200))

