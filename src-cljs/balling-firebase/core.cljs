(ns balling-firebase.core
  (:require [domina :refer [by-id value set-text!]]
            [domina.events :refer [listen!]]))

(def messages (js/Firebase. "https://balling-firebase.firebaseio.com"))


;; // Add a callback that is triggered for each chat message.
;;   messagesRef.limit (10).on ('child_added', function (snapshot) {
;;     var message = snapshot.val ();
;;     $ ('<div/>').text (message.text).prepend ($ ('<em/>')
;;       .text (message.name+': ')).appendTo ($ ('#messagesDiv'));
;;     $ ('#messagesDiv') [0].scrollTop = $ ('#messagesDiv') [0].scrollHeight; });


(defn add-message
  [message]
  (.push messages message))

(defn init
  []
  (let [submit-button (by-id "submitMessage")
        message-input (by-id "message")
        messages-div (by-id "messages")
        messages-query (.limit messages 10)]
    (.on messages-query
         "child_added"
         (fn [snapshot]
           (.log js/console "hello")
           (.log js/console (.val snapshot))
           (set-text! messages-div (.val snapshot))))
    (listen! submit-button :click
             (fn 
               [evt] 
               (add-message (value message-input))))))

(set! (.-onload js/window) init)
