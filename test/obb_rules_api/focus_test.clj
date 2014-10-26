(ns obb-rules-api.focus-test
  (:require [obb-rules-api.simulator-test :as sim]
            [obb-rules.unit :as unit]
            [obb-rules.board :as board]
            [obb-rules-api.parser :as parser])
  (:use clojure.test
        obb-rules.result
        obb-rules-api.routes
        ring.mock.request))

(deftest test-focus
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
                                        :direction :east}}}}]
    (testing "regular :p2"
      (let [[result response] (sim/focus-ok :p2 board)
            game (result :board)]
        (is (not (board/get-element game [1 1])))
        (is (not (board/get-element game [4 4])))
        (is (board/get-element game [8 8]))
        (is (board/get-element game [5 5]))))))
