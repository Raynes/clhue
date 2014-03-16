(ns me.raynes.clhue.request
  (:require [clj-http.client :as client]
            [clojure.string :refer [join]]))

(defn format-url [endpoint config]
  (->> (filter identity [(:user config) endpoint])
       (join "/")
       (format "http://%s/api/%s"
               (:address config))))

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
               (if (#{:post :put} method)
                 {:form-params body}
                 {:query-params body}))))))
