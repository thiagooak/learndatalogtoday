(ns learndatalogtoday.views
  (:require [clojure.java.io :as io]
            [datomic-query-helpers.core :refer [pretty-query-string]]
            [fipp.edn :as fipp]
            [hiccup.element :refer [javascript-tag]]
            [hiccup.page :refer [html5 include-js include-css]]
            [markdown.core :as md]))

(defn footer []
  [:footer {:class "bg-white"}
   [:div {:class "mx-auto max-w-7xl px-6 py-12 md:flex md:items-center md:justify-between lg:px-8"}
    [:div {:class "flex justify-center space-x-6 md:order-2"}
     [:a {:href "https://github.com/thiagooak/learndatalogtoday" :class "text-gray-400 hover:text-gray-500"}
      [:span {:class "sr-only"} "GitHub"]
      [:svg {:class "h-6 w-6" :fill "currentColor" :viewBox "0 0 24 24" :aria-hidden "true"}
       [:path {:fill-rule "evenodd" :d "M12 2C6.477 2 2 6.484 2 12.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.531 1.032 1.531 1.032.892 1.53 2.341 1.088 2.91.832.092-.647.35-1.088.636-1.338-2.22-.253-4.555-1.113-4.555-4.951 0-1.093.39-1.988 1.029-2.688-.103-.253-.446-1.272.098-2.65 0 0 .84-.27 2.75 1.026A9.564 9.564 0 0112 6.844c.85.004 1.705.115 2.504.337 1.909-1.296 2.747-1.027 2.747-1.027.546 1.379.202 2.398.1 2.651.64.7 1.028 1.595 1.028 2.688 0 3.848-2.339 4.695-4.566 4.943.359.309.678.92.678 1.855 0 1.338-.012 2.419-.012 2.747 0 .268.18.58.688.482A10.019 10.019 0 0022 12.017C22 6.484 17.522 2 12 2z" :clip-rule "evenodd"}]]]]
    [:div {:class "mt-8 md:order-1 md:mt-0"}
     [:p {:class "text-center text-xs leading-5 text-gray-500"} "A fork of Jonas Enlund's Learn Datalog Today"]]]])

(defn row [& content]
  [:div.row
   [:div.offset2
    content]])

(defn base [chapter text exercises ecount]
  (list
   [:head
    (include-css "/third-party/bootstrap/css/bootstrap.css")
    (include-css "/third-party/codemirror-3.15/lib/codemirror.css")
    (include-css "/style.css")
    [:title "Learn Datalog Today!"]]
   [:body
    [:div {:class "flex min-h-full flex-col"}
     [:header {:class "shrink-0 border-b border-gray-200 bg-white"}
      [:div {:class "mx-auto flex h-16 max-w-7xl items-center justify-between px-4 sm:px-6 lg:px-8"}
       [:a {:href "/"} [:img {:class "h-8 w-auto" :src "/logo.png" :alt "Learn Datalog Today"}]]
       [:div {:class "flex items-center gap-x-8"} [:a {:href "/"} "Learn Datalog Today"]]]]
     [:div {:class "mx-auto flex w-full max-w-7xl items-start gap-x-8 px-4 py-10 sm:px-6 lg:px-8"}
      [:main {:class "flex-1"}
       [:div.container
        [:div text]
        [:div {:class "mt-4"}
         (when (> chapter 0)
           [:a {:href (str "/chapter/" (dec chapter))}
            [:button {:type "button" :class "rounded bg-indigo-50 px-2 py-1 text-sm font-semibold text-indigo-600 shadow-sm hover:bg-indigo-100"}
             "Previous chapter"]])
         (when (< chapter 8)
           [:a.pull-right {:href (str "/chapter/" (inc chapter))}
            [:button {:type "button" :class "rounded bg-indigo-50 px-2 py-1 text-sm font-semibold text-indigo-600 shadow-sm hover:bg-indigo-100"}
             "Next chapter"]])]]]
      [:aside {:class "sticky top-8 w-1/2 shrink-0 xl:block"}
       [:div.exercises {:style "margin-top: 14px"} exercises]]]
     [:div (footer)]]




    (include-js "/third-party/jquery/jquery-1.10.1.min.js")
    (include-js "/third-party/codemirror-3.15/lib/codemirror.js")
    (include-js "/third-party/codemirror-3.15/mode/clojure/clojure.js")
    (include-js "/third-party/bootstrap/js/bootstrap.js")
    (include-js "https://cdn.tailwindcss.com?plugins=typography")
    (include-js "/app.js")
    (javascript-tag (format "learndatalogtoday.core.init(%s, %s);" chapter ecount))]))

(defn build-input [tab-n input-n input]
  (let [label (condp = (:type input)
                :query "Query:"
                :rule "Rules:"
                :value (str "Input #" input-n ":"))
        input-str (condp = (:type input)
                    :query (pretty-query-string (:value input))
                    :rule (with-out-str (fipp/pprint (:value input)))
                    :value (with-out-str (fipp/pprint (:value input))))]
    [:div
     [:div
      [:div [:p [:small [:strong label]
                 (when (= :query (:type input))
                   [:span.pull-right [:a {:href "#" :class (str "show-ans-" tab-n)} "show solution"]])]]]]
     [:div
      [:div [:textarea {:class (str "input-" tab-n)} input-str]]]]))

(defn build-inputs [tab-n inputs]
  (map-indexed (partial build-input tab-n) inputs))

(defn build-exercise [tab-n exercise]
  (list [:div {:class (if (zero? tab-n) "tab-pane active" "tab-pane")
               :id (str "tab" tab-n)}

         (md/md-to-html-string (:question exercise))
         [:div.inputs
          (build-inputs tab-n (:inputs exercise))]
         [:div
          [:div
           [:button {:id (str "run-query-" tab-n)
                     :data-tab tab-n
                     :type "button"
                     :class "rounded bg-indigo-50 px-2 py-1 text-sm font-semibold text-indigo-600 shadow-sm hover:bg-indigo-100"}
            "Run Query"]]]
         [:div
          [:div
           [:div.alerts]
           [:table.table.table-striped.resultset
            [:thead]
            [:tbody]]]]]))

(defn build-exercises [exercises]
  (list [:div.tabbable
         [:ul.nav.nav-tabs
          (for [n (range (count exercises))]
            [:li (when (zero? n) {:class "active"})
             [:a {:href (str "#tab" n)
                  :data-toggle "tab"}
              [:span.label n]]])]
         [:div.tab-content
          (map-indexed build-exercise exercises)]]))

(defn read-chapter [file]
  (with-open [r (io/reader (io/resource file))]
    [:div.prose (md/md-to-html-string (slurp r))]))

(defn chapter-response [chapter-data]
  (let [text (-> chapter-data :text-file read-chapter)
        exercises (build-exercises (:exercises chapter-data))
        ecount (count (:exercises chapter-data))
        chapter (:chapter chapter-data)]
    (html5 (base chapter text exercises ecount))))

(defn toc []
  (html5
   [:head
    (include-css "/third-party/bootstrap/css/bootstrap.css")
    (include-css "/style.css")
    [:title "Learn Datalog Today!"]]
   [:body
    [:div.container
     (row [:div.textcontent (md/md-to-html-string
                             (with-open [r (io/reader (io/resource "toc.md"))]
                               (slurp r)))])
     (row (footer))]
    (include-js "/third-party/jquery/jquery-1.10.1.min.js")
    (include-js "/third-party/bootstrap/js/bootstrap.js")]))
