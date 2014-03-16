(ns me.raynes.clhue.lights
  (:require [me.raynes.clhue.request :refer [req]]))

(defn lights
  "Get ALL the lights."
  [config]
  (:body (req :get "lights" config)))

(defn light
  "Get ONE light."
  [config id]
  (:body (req :get (format "lights/%s" id) config)))

(defn scan
  "Scan for new lights."
  [config]
  (-> (req :post "lights" config)
      :body
      first))

(defn new-lights
  "Get lights found on last scan."
  [config]
  (:body (req :get "lights/new" config)))
