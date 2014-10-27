(ns obb-rules-api.ranking-test
  (:require [clojure.data.json :as json])
  (:use clojure.test
        obb-rules-api.ranking
        obb-rules-api.routes
        ring.mock.request))

(deftest default-ranking-test
  (testing "default ranking"
    (let [response (app (request :get "/ranking/default"))]
      (is (= (:status response) 200))
      (let [result (json/read-str (:body response))]
        (is (< 0 (result "rating")))
        (is (< 0 (result "volatility")))
        (is (< 0 (result "rd")))))))

