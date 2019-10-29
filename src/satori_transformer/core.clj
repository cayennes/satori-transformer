(ns satori-transformer.core
  (:require [clojure.string :as string]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io])
  (:gen-class))


;; things that theoretically should be arguments

(def in-filename "exported.csv")
(def out-filename "exported-for-anki.csv")

;; The following synthetic fields are added:
;; * All context related fields get a ContextLast version which has the last added field
;; * All expression fields have number-suffix versions which will together
;;   include all words that have cards with thhe ContextLast context sentence

(def out-fields [:ContextLast
                 :ContextLast-ReadingsInline
                 :ContextLast-Translation
                 :Expression-0
                 :Expression-ReadingsInline-0
                 :English-0
                 :Expression-1
                 :Expression-ReadingsInline-1
                 :English-1
                 :Expression-2
                 :Expression-ReadingsInline-2
                 :English-2
                 :SatoriTag])

;; functions

(defn add-reading-spaces
  [expression]
  ;; the range is the unicode codeblock kanji is found in
  (string/replace expression #"([^\u4E00-\u9FBF])([\u4E00-\u9FBF]*\[.*?\])" "$1 $2"))

(defn add-last-context
  [item]
  (into item
        (for [field [""
                     "-ReadingsOnly"
                     "-ReadingsInline"
                     "-PerYourPreferences"
                     "-PerYourPreferencesReadingsInline"
                     "-Translation"]]
          [(keyword (str "ContextLast" field))
           (some #(not-empty (get item (keyword (str % field))))
                 ["Context3" "Context2" "Context1"])]))) 

(defn add-satori-tag
  [item]
  (assoc item :SatoriTag "satori"))

(defn add-indexed-item-fields
  [i item]
  (into item
        (for [field [:Expression
                     :Expression-ReadingsInline
                     :Expression-ReadingsOnly
                     :Expression-PerYourPreferences
                     :Expression-PerYourPreferencesReadingsInline
                     :English]]
          [(keyword (str (name field) "-" i))
           (field item)])))

(defn merge-items
  [items]
  (->> items
       (group-by :ContextLast)
       (vals)
       (map #(map-indexed add-indexed-item-fields %))
       (map #(apply merge %))))

(defn -main
  []
  (let [[columns & lines] (with-open [reader (io/reader in-filename)]
                            (doall
                             (csv/read-csv reader)))
        everything (->> (map zipmap
                             (repeat (map keyword columns))
                             (map #(map add-reading-spaces %) lines))
                        (map add-last-context)
                        (merge-items)
                        (map add-satori-tag))
        for-anki (map #(map % out-fields) everything)]
    (with-open [writer (io/writer out-filename)]
      (csv/write-csv writer for-anki))
    ;; return value for debugging purposes
    {:everything everything
     :for-anki for-anki}))
