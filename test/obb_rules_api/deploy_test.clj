(ns obb-rules-api.deploy-test
  (:require [clojure.data.json :as json])
  (:use clojure.test
        obb-rules-api.routes
        ring.mock.request))

(deftest test-deploy
  (let [response (app (request :post "/game/turn/p2" (array-map :x "y" :z "n")))]
    (is (= (:status response) 200))
    (let [game (json/read-str (:body response))]
      (is (game "stash"))
      (is (= "deploy" (game "state"))))))
