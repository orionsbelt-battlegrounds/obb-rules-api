(ns obb-rules-api.unit-test
  (:require [clojure.data.json :as json])
  (:use clojure.test
        obb-rules-api.routes
        ring.mock.request))

(deftest test-unit-handler
  (let [response (app (request :get "/units/rain"))]
    (is (= (:status response) 200))
    (let [unit (json/read-str (:body response))]
      (is (unit "name"))
      (is (unit "code"))
      (is (> (unit "attack") 0))
      (is (> (unit "defense") 0)))))

(deftest test-invalid-unit-handler
  (let [response (app (request :get "/units/invalid"))]
    (is (= (:status response) 412))))
