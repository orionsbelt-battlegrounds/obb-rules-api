(ns obb-rules-api.simulator-test
  (:require [obb-rules-api.parser :as parser])
  (:use clojure.test
        obb-rules-api.routes
        ring.mock.request))

(defn turn
  "Simulates a turn processing call"
  [player params expected-status]
  (let [game (parser/dump-game (params :game))
        response (app (request :post (str "/game/turn/" player) game))
        response-game (parser/load-game (:body response))]
    (is (= expected-status (response :status)))
    [response-game response]))

(defn turn-ok
  "Simulates a successful turn play"
  [player params]
  (turn player params 200))
