(ns obb-rules-api.index-test
  (:require [clojure.data.json :as json])
  (:use clojure.test
        obb-rules-api.routes
        ring.mock.request))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))
      (let [result (json/read-str (:body response))]
        (is (= "obb-rules-api" (result "name")))
        (is (= "1.0.0" (result "version"))))))

 (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))
