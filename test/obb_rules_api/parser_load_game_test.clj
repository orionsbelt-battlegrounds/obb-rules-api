(ns obb-rules-api.parser-load-game-test
  (:require [obb-rules-api.parser :as parser])
  (:use clojure.test
        obb-rules-api.routes
        ring.mock.request))

(deftest load-deploy-game
  (let [game {:state "deploy"
              :stash {:p1 {:kamikaze 1}
                      :p2 {:kamikaze 1}}
              :width 8
              :height 8
              :elements {}}
        data (parser/dump-game game)
        loaded-game (parser/load-game data)]
    (is (= game loaded-game)))
  )
