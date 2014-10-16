(ns obb-rules-api.index-test
  (:use clojure.test
        obb-rules-api.routes
        ring.mock.request))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "{\"name\":\"obb-rules-api\"}"))))

 (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))
