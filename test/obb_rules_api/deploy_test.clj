(ns obb-rules-api.deploy-test
  (:require [clojure.data.json :as json])
  (:use clojure.test
        obb-rules-api.routes
        ring.mock.request))

(deftest test-deploy
  (let [data (json/write-str {:game {:state "deploy"
                                     :stash {:p1 {:kamikaze 1}
                                              :p2 {:kamikaze 1}}
                                     :width 8
                                     :height 8
                                     :elements {}}
                              :actions [[:deploy 1 :kamikaze [8 8]]]})
        response (app (request :post "/game/turn/p1" data))]
    (is (= (:status response) 200))
    (let [game (json/read-str (:body response))]
      (is (game "stash"))
      (is (= "deploy" (game "state"))))))
