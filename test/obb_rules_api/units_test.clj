(ns obb-rules-api.units-test
  (:require [clojure.data.json :as json])
  (:use clojure.test
        obb-rules-api.routes
        ring.mock.request))

(deftest test-units-handler
  (let [response (app (request :get "/units"))]
    (is (= (:status response) 200))
    (let [result (json/read-str (:body response))
          random-unit (rand-nth result)]
      (is (random-unit "name"))
      (is (random-unit "code"))
      (is (> (random-unit "attack") 0))
      (is (> (random-unit "defense") 0))
      (is (> (count result) 0)))))
