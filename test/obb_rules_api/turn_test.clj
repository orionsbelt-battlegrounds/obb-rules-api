(ns obb-rules-api.turn-test
  (:require [obb-rules-api.simulator-test :as sim]
            [obb-rules.unit :as unit]
            [obb-rules-api.parser :as parser])
  (:use clojure.test
        obb-rules-api.routes
        ring.mock.request))

(deftest test-turn
  (let [[result response]
        (sim/turn-ok :p1
                     {:game {:state :p1
                             :elements {[1 1] {:player :p1
                                               :unit (unit/fetch :kamikaze)
                                               :quantity 1
                                               :direction :south}
                                        [1 4] {:player :p2
                                               :unit (unit/fetch :rain)
                                               :quantity 1
                                               :direction :east}}}
                      :actions [[:move [1 1] [1 2] 1]]})]
    (is (not= :deploy (get-in result [:board :state])))))

