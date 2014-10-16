(ns obb-rules-api.random-game-test
  (:require [clojure.data.json :as json])
  (:use clojure.test
        obb-rules-api.routes
        ring.mock.request))

(deftest test-random-game-handler
  (let [response (app (request :get "/game/random"))]
    (is (= (:status response) 200))
    (let [game (json/read-str (:body response))]
      (is (game "stash"))
      (is (= "deploy" (game "state"))))))
