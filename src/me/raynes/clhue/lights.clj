(ns me.raynes.clhue.lights
  (:require [me.raynes.clhue.request :refer [req]]))

(defn percentage
  "Hue brightness goes up to 255. Would you rather use a
   percentage."
  [n]
  (quot (* n 255) 100))

(defn lights
  "Get ALL the lights."
  [config]
  (:body (req :get "lights" config)))

(defn light
  "Get a light by ID, or set a light's state by ID."
  ([config id]
     (:body (req :get (format "lights/%s" id) config)))
  ([config id settings]
     ;; For some God awful reason, hue thought it was a good idea
     ;; to have a separate endpoint for setting 'state' that does
     ;; not include renaming. Because of this, yes, this is ugly.
     (let [name (:name settings)
           responses (when name
                       (-> (req :put (format "lights/%s" id)
                                config {:name name})
                           (:body)
                           (first)))
           settings (dissoc settings :name)]
       (-> (req :put (format "lights/%s/state" id) config settings)
           (:body)
           (into responses)))))

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
