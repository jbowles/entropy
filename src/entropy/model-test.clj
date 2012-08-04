;; Credit: Lee Hinman (dakrone on github)
;; https://github.com/dakrone/clojure-opennlp
(ns model-test
  (:use [clojure.pprint]
           [opennlp.nlp]
           [opennlp.treebank]
           [clojure.java.io :only [input-stream]]))
;  (:import
;   (opennlp.tools.doccat DoccatModel
;                         DocumentCategorizerME)
;   (opennlp.tools.namefind NameFinderME TokenNameFinderModel)
;   (opennlp.tools.postag POSModel POSTaggerME)
;   (opennlp.tools.sentdetect SentenceDetectorME SentenceModel)
;   (opennlp.tools.tokenize DetokenizationDictionary
;                           DetokenizationDictionary$Operation
;                           Detokenizer$DetokenizationOperation
;                           DictionaryDetokenizer
;                           TokenizerME
;                           TokenizerModel)
;   (opennlp.tools.util Span)))
;
;;;;;;;;;;;;;;DOES NOT WORK;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;
;(defmulti make-time-finder
;  "Return a function for finding time-stamps from tokens based on the model file."
;  class)
;
;(defmethod make-time-finder :default
;  [modelfile]
;  (with-open [model-stream (input-stream modelfile)]
;    (make-time-finder (TokenNameFinderModel. model-stream))))
;
;(defmethod make-time-finder TokenNameFinderModel
;  [model]
;  (fn time-finder
;    [tokens & contexts]
;    {:pre [(seq tokens)
;           (every? #(= (class %) String) tokens)]}
;    (let [finder (NameFinderME. model)
;          matches (.find finder (into-array String tokens))
;          probs (seq (.probs finder))]
;      (with-meta
;        (distinct (Span/spansToStrings matches (into-array String tokens)))
;        {:probabilities probs}))))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def get-sentences (make-sentence-detector "models/en-sent.bin"))
(def tokenize (make-tokenizer "models/en-token.bin"))
(def detokenize (make-detokenizer "models/english-detokenizer.xml"))
(def pos-tag (make-pos-tagger "models/en-pos-maxent.bin"))
(def name-find (make-name-finder "models/en-ner-person.bin"))
(def time-find (make-time-finder "models/en-ner-time.bin"))
(def chunker (make-treebank-chunker "models/en-chunker.bin"))


(def sample-sentences "First sentence. Second sentence determined by periond and space. Third sentence start but can the Function deal with ... Followed by a space and capitalized letter (third sentence end)? His name is John and mine is Lee John-Smith, while his is Gabobi Al-Abdul Jihamad and hers is Leea John. Another sentence with a timestamp 2012-01-09 and then another 2010-12-25.")

(def parsed-sample-sentences (get-sentences sample-sentences))
(pprint parsed-sample-sentences)

(def tokenized-sample-sentences (tokenize sample-sentences))
(pprint tokenized-sample-sentences)

(def tagged-tokenized-sample-sentences (pos-tag tokenized-sample-sentences))
(pprint tagged-tokenized-sample-sentences)

(name-find tokenized-sample-sentences) ;does not do so well: missed Gabobi Al-Abdul Jihamad
;(time-find tokenized-sample-sentences) ;does not work: 
(detokenize tokenized-sample-sentences)

;;; Treebank chunking utilized here
(pprint (chunker tagged-tokenized-sample-sentences))
;;;just the phrases
(phrases (chunker tagged-tokenized-sample-sentences))
;;;just the strings
(phrase-strings (chunker tagged-tokenized-sample-sentences))

;;; Get the probabilities
(meta parsed-sample-sentences)
