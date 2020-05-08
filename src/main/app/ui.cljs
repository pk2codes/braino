(ns app.ui
  (:require
   [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
   [com.fulcrologic.fulcro.dom :as dom :refer [h5 h4 div p li ul]]))


(defsc Person [this {:person/keys [name age]}]
  {:query [:person/name :person/age]
   :initial-state (fn [{:keys [name age] :as params}] {:person/name name :person/age age})}
  (li
   (h5 (str name " (age " age ")"))))

(def ui-person (comp/factory Person {:keyfn :person/name}))

(defsc PersonList [this {:list/keys [label people]}]
  {
   :query [:list/label {:list/people (comp/get-query Person)}]
   :initial-state
   (fn [{:keys [label]}]
     {:list/label  label
      :list/people (if (= label "Friends")
                     [(comp/get-initial-state Person {:name "Sally" :age 32 :dosenbier "licherzzz"})
                      (comp/get-initial-state Person {:name "Joe" :age 22})]
                     [(comp/get-initial-state Person {:name "Fred" :age 11})
                      (comp/get-initial-state Person {:name "Bobby" :age 55})])})}
  (div
   (h4 label)
   (ul
    (map ui-person people))))

(def ui-person-list (comp/factory PersonList))


(defsc Root [this {:keys [friends colleagues]}]
  {:query [{:friends (comp/get-query PersonList)}
           {:enemies (comp/get-query PersonList)}]
   :initial-state (fn [params] {:friends (comp/get-initial-state PersonList {:label "Friends"})
                                :enemies (comp/get-initial-state PersonList {:label "Dosenbiere"})}) }
  (js/console.log friends)
  (js/console.log colleagues)
    (div
     (ui-person-list friends)
     (ui-person-list colleagues)))

