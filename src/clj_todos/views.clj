(ns clj-todos.views
  (:require [clj-todos.db :as db]
            [clojure.string :as str]
            [hiccup.page :as hic-p]))

(defn gen-page-head
  [title]
  [:head
   [:title (str "TODOs: " title)]
   (hic-p/include-css "/css/styles.css")])

(def header-links
  [:div#header-links
   "[ "
   [:a {:href "/"} "Home"]
   " | "
   [:a {:href "/add-todo"} "Add a TODOs"]
   " | "
   [:a {:href "/todos"} "View All TODOs"]
   " ]"])

(defn home-page
  []
  (hic-p/html5
   (gen-page-head "Home")
   header-links
   [:h1 "Home"]
   [:p "Webapp to store and display some TODOs."]))

(defn add-todo-page
  []
  (hic-p/html5
   (gen-page-head "Add a TODO")
   header-links
   [:h1 "Add a TODO"]
   [:form {:action "/add-todo" :method "POST"}
    [:p "Description: " [:input {:type "text" :name "description" :required "required"}]]
    [:p "Numeric priority: " [:input {:type "text" :name "priority" :required "required"}]]
    [:p [:button "Create"]]]))

(defn add-todo-results-page
  [{:keys [description priority]}]
  (let [id (db/add-todo-to-db description priority)]
    (hic-p/html5
     (gen-page-head "Added a TODO")
     header-links
     [:h1 "Added a TODO"]
     [:p "Added [" description ", " priority "] (id: " id ") to the db. "
      [:a {:href (str "/todos/" id)} "See for yourself"]
      "."])))

(defn todo-page
  [todo-id]
  (let [{description :description priority :priority} (db/get-todo todo-id)]
    (hic-p/html5
     (gen-page-head (str "TODO " todo-id))
     header-links
     [:h1 "A Single TODO"]
     [:p "ID: " todo-id]
     [:p "Description: " description]
     [:p "Priority: " priority])))

(defn all-todos-page
  []
  (let [all-todos (db/get-all-todos)]
    (hic-p/html5
     (gen-page-head "All TODOs in the db")
     header-links
     [:h1 "All TODOs"]
     [:table
      [:tr [:th "id"] [:th "Description"] [:th "Priority"]]
      (for [todo all-todos]
        [:tr
         [:td [:a {:href (str "/todos/" (:id todo))} (:id todo)]]
         [:td (:description todo)]
         [:td (:priority todo)]])])))
