(ns balling-firebase.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [hiccup.core :refer :all]
            [hiccup.page :refer :all]
            [hiccup.form :refer :all]))

(defn home-page
  []
  (html5 
   [:html
    [:head
     (include-js "https://cdn.firebase.com/v0/firebase.js")
     (include-js "/js/main.js")
     ;; STEP ONE - Installing Firebase
     ]
    [:body
     [:center
      (text-field {:id "message" :placeholder "Message"} "message")
      (submit-button {:id "submitMessage"} "Submit Message")
      [:div {:id "messages"}]]]]))

(defroutes app-routes
  (GET "/" [] (home-page))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
