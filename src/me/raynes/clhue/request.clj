(ns me.raynes.clhue.request
  (:require [clj-http.client :as client]
            [clojure.string :refer [join]]))

(defn format-url [endpoint config]
  (->> (filter identity [(:user config) endpoint])
       (join "/")
       (format "http://%s/api/%s"
               (:address config))))

(defn handle-weird-error
  "Some APIs return a hash map for successes, but a collection
   of only one hash when returning errors. This handles that."
  [resp]
  (let [body (:body resp)]
    (if (sequential? body)
      (first body)
      body)))

(defn req
  "Make a request to hue. Intelligently sends query params
   for GETs and a json body for posts."
  ([method endpoint config] (req method endpoint config nil))
  ([method endpoint config body]
     (let [base {:method method
                 :url (format-url endpoint config) 
                 :as :json
                 :content-type :json}]
       (client/request
        (merge base
               (if (#{:post :put :delete} method)
                 {:form-params body}
                 {:query-params body}))))))
