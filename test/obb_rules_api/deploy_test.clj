(ns obb-rules-api.deploy-test
  (:require [obb-rules-api.simulator-test :as sim]
            [obb-rules-api.parser :as parser])
  (:use clojure.test
        obb-rules-api.routes
        ring.mock.request))

(deftest test-deploy
  (let [[result response]
        (sim/turn-ok :p1
                     {:game {:state "deploy"
                             :stash {:p1 {:kamikaze 1}
                                     :p2 {:kamikaze 1}}
                             :width 8
                             :height 8
                             :elements {}}
                      :actions [[:deploy 1 :kamikaze [8 8]]]})]
    (is (= :deploy (get-in result [:board :state])))))

(deftest test-finish-deploy
  (let [[result response]
        (sim/turn-ok :p1
                     {:game {:state "deploy"
                             :stash {:p1 {:kamikaze 1}
                                     :p2 {}}
                             :width 8
                             :height 8
                             :elements {}}
                      :actions [[:deploy 1 :kamikaze [8 8]]]})]
    (is (not= :deploy (get-in result [:board :state])))))

(deftest test-invalid-deploy
  (let [[result response]
        (sim/turn-fail :p2
                     {:game {:state "deploy"
                             :stash {:p1 {:kamikaze 1}
                                     :p2 {:kamikaze 1}}
                             :width 8
                             :height 8
                             :elements {}}
                      :actions [[:deploy 1 :kamikaze [8 8]]]})]
    (is (= "ActionFailed" (get-in result [:message])))
    (is (= :deploy (get-in result [:board :state])))))

#_(deftest test-p2-deploy
  (let [response (app (request :get "/game/turn/p1?context=%7B%22p2-focused-board%…y%22%3A%7B%22username%22%3A%22Pyro%22%2C%22player-code%22%3A%22p2%22%7D%7D"))]
    (is (= 200 (response :status)))))
