(ns me.raynes.clhue.config
  (:require [me.raynes.clhue.request :refer [req]]))

(defn auth-user
  "Authorize a new user."
  [config description]
  (let [resp (req :post nil config
                  {:devicetype description})]
    (first (:body resp))))

(defn delete-user
  "Delete a user from the whitelist."
  [config user]
  (-> (req :delete (format "config/whitelist/%s" user) config)
      :body
      first))

(defn config
  "Get or set configuration."
  ([config]
     (:body (req :get "config" config)))
  ([config modify]
     (-> (req :put "config" config modify)
         :body
         first)))
