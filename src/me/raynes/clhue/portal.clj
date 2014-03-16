(ns me.raynes.clhue.portal
  (:require [clj-http.client :as client]))

(defn discover
  "Discover local bridges."
  []
  (-> (client/get "http://www.meethue.com/api/nupnp"
                  {:as :json
                   :content-type :json})
      (:body)
      (first)))
