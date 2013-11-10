(ns balling-firebase.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [hiccup.core :refer :all]
            [hiccup.page :refer :all]
            [hiccup.form :refer :all]))

(defn home-page
  []
  (html5 
   [:html
    [:head
     (include-js "https://cdn.firebase.com/v0/firebase.js" 
                 "https://cdn.firebase.com/v0/firebase-simple-login.js"
                 "/js/main.js")
     (include-css "https://www.firebase.com/css/example.css")]
    [:body
     [:button {:id "login"} "Login"]
     [:div {:id "messages"}]
     [:div {:id "entry"}
      (text-field {:id "nameInput" :placeholder "Name"} "nameInput")
      (text-field {:id "messageInput" :placeholder "Message..."} "messageInput")]
     ]]))

(defroutes app-routes
  (GET "/" [] (home-page))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app (handler/site app-routes))
