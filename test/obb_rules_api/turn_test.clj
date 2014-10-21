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
                                               :coordinate [1 4]
                                               :direction :east}}}
                      :actions [[:move [1 1] [1 2] 1]
                                [:move [1 2] [1 3] 1]
                                [:attack [1 3] [1 4]]]})]
    (is (not= :deploy (get-in result [:board :state])))))

(deftest test-focus-p2-turn
  (let [board {:game {:state :p2
                      :elements {[1 1] {:player :p1
                                        :unit (unit/fetch :kamikaze)
                                        :quantity 1
                                        :coordinate [1 1]
                                        :direction :south}
                                 [4 4] {:player :p2
                                        :unit (unit/fetch :rain)
                                        :quantity 1
                                        :coordinate [4 4]
                                        :direction :east}}}}
        direct-actions [[:move [4 4] [3 3] 1]
                        [:move [3 3] [2 2] 1]
                        [:move [2 2] [2 1] 1]
                        [:rotate [2 1] :west]
                        [:attack [2 1] [1 1]]]]
    (testing "regular :p2"
      (let [direct-board (assoc board :actions direct-actions)
            [direct result response] (sim/turn-ok :p2 direct-board)]
      ))))
