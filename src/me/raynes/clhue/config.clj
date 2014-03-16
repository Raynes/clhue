(ns me.raynes.clhue.config
  (:require [me.raynes.clhue.request :refer [req]]))

(defn auth-user
  "Authorize a new user."
  [config description]
  (let [resp (req :post nil config
                  {:devicetype description})]
    (first (:body resp))))

(defn config
  "Get or set configuration."
  ([config]
     (:body (req :get "config" config)))
  ([config modify]
     (-> (req :put "config" config modify)
         :body
         first)))
