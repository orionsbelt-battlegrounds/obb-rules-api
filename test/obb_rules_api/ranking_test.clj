(ns obb-rules-api.ranking-test
  (:require [clojure.data.json :as json]
            [ring.util.codec :as codec ])
  (:use clojure.test
        obb-rules-api.ranking
        obb-ranking.core
        obb-rules-api.routes
        ring.mock.request))

(defn- validate-ranking
  "Validates a ranking hash"
  [result]
  (is (< 0 (result "rating")))
  (is (< 0 (result "volatility")))
  (is (< 0 (result "rd"))))

(deftest default-ranking-test
  (testing "default ranking"
    (let [response (app (request :get "/ranking/default"))]
      (is (= (:status response) 200))
      (let [result (json/read-str (:body response))]
        (validate-ranking result)))))

(deftest calculate-ranking-test
  (testing "default ranking"
    (let [winner (codec/url-encode (json/write-str (default-player)))
          other (codec/url-encode (json/write-str (default-player)))
          response (app (request :get (str "/ranking/calculate?winner=" winner "&other=" other )))]
      (is (= (:status response) 200))
      (let [result (json/read-str (:body response))
            winner (result "winner")
            other (result "other")]
        (validate-ranking winner)
        (validate-ranking other)))))

