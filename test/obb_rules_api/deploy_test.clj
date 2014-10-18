(ns obb-rules-api.deploy-test
  (:require [obb-rules-api.simulator-test :as sim])
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
