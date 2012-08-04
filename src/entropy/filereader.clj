(import '(java.io BufferedReader FileReader))
(use 'clojure.pprint) ; just for this documentation
(use 'opennlp.nlp)
(use 'opennlp.tools.train)
(use 'opennlp.treebank) ; treebank chunking, parsing and linking lives here


(def sent-model (train-sentence-detector "training/input_files/testlog-sent.train"))
(def get-sentences (make-sentence-detector sent-model))

(def token-model (train-tokenizer "training/input_files/testlog-token.train"))
(def tokenize (make-tokenizer token-model))

;;not working
;(def ner-model (train-name-finder "training/input_files/testlog-ner.train"))


;(def tokenize (make-tokenizer "models/en-token.bin"))
;(def get-sentences (make-sentence-detector "models/en-sent.bin"))


(defn proc-file [file-name]
  (with-open [rdr (BufferedReader. (FileReader. file-name))]
    (doseq [line (line-seq rdr)] (println line))))
;(proc-file "data/lslog.txt")


(defn process-file [file-name line-func line-acc]
  (with-open [rdr (BufferedReader. (FileReader. file-name))]
    (reduce line-func line-acc (line-seq rdr))))

(defn proc-line [acc line]
  (+ acc 1))

(defn process-line [acc line]
  (reduce #(assoc %1 %2 (+ (get %1 %2 0) 1)) acc (.split line "\n")))

;;count lines
;(prn (process-file "data/small-log.txt" proc-line 0))

;; create a hash map then iterate through and tokenize
;(def mapped (process-file "data/tiny-log.txt" process-line (hash-map)))
;(doseq [entry (keys mapped)]
;   (println (tokenize entry)))

;; create a hash map
;(process-file "data/tiny-log.txt" process-line (hash-map))

;(str (proc-file "data/tiny-log.txt"))

;(def parse-sent (get-sentences "This is a sentence. This is another"))
;(pprint parse-sent)
(def log-entry "fl7u  2012-07-27 10:52:49  6828  102zs4392y12  prol7u_l7ufhl7us392yll_int392yk  l7ufhl7usontrollers/le392yl7u_77yhgmissions_l7ufhl7usontroller.rzs:3 -  jn5T l7u392yt392y   77yhgmission_l7ufhl7usnt:925. l7u  2012-07-27 10:52:49  6828  102zs4392y12  prol7u_l7ufhl7us392yll_int392yk  l7ufhl7usontrollers/le392yl7u_77yhgmissions_l7ufhl7usontroller.rzs:5 -  l7ufhl7usustomLe392yl7u: #<Le392yl7uTempl392ytel7ufhl7us392yll392yssist392yntsl7ufhl7us392ylll7ufhl7usenterInt392ykeEl7uul7ufhl7us392ytion01 st392ytus: 'NINR', progr392ym: '', l7uesirel7ul7uegree: '', hsgr392yl7u: '', zsirthye392yr: '', genl7uer: '', l7ufhl7usurrentel7ulezyxel: '', l7uesirel7ust392yrt: '', milit392yry: 'N', repl7ufhl7usol7ue: '', l7ufhl7us392ympusinterest: '', hottr392ynsl7ufer: nil, l7ufhl7usrel7uitsl7ufhl7usore: nil, sumol7ufl7uezst: '\n', useril7u: 'Unknown', le392yl7uil7u: '124223313', 392yge: nil, em392yil: '', progr392ym_2: nil, street: '', l7ufhl7usity: '', st392yte: '', l7ufhl7us392y_hot_tr392ynsl7ufer_rep_il7u: nil, test_le392yl7u: 'l7uf392ylse', home_phone: nil, l7ufirst_n392yme: nil, l392yst_n392yme: nil, tr392ynsl7ufer_l7ufhl7usrel7uits: nil, l7ufhl7us392y_l7ufin392yl_392yttempt_l7u392yte: nil, l7ufhl7us392y_l7ufirst_392yttempt_l7u392yte: nil, l7ufhl7us392y_tot392yl_392yttempts: nil, l7ufhl7us392y_tot392yl_wr392yp: nil, l7ufhl7us392y_tot392yl_t392ylk_time: nil, l7ufhl7us392y_rel7ufhl7useizyxel7u_l7uuring_open_time_zone: nil, zip: '', zyxenl7uorl7ufhl7usol7ue: 'ozyx.el7uu_l7ufhl7us.pl7ul7ufhl7uszyx_s.el7up_m.l7ufhl7usi_sl7ufhl7us2.inl7ufhl7us_zyx.l7ufhl7usl7u7574'>  77yhgmission_l7ufhl7usnt:925. l7u  2012-07-27 10:52:49  6804  102zs4392y12  prol7u_l7ufhl7us392yll_int392yk  l7ufhl7usontrollers/le392yl7u_77yhgmissions_l7ufhl7usontroller.rzs:6 -  Inle392yl7u: {'392yp_zyxenl7uor_l7ufhl7usol7ue'=>'ozyx.el7uu_l7ufhl7us.pl7ul7ufhl7uszyx_s.el7up_m.l7ufhl7usi_sl7ufhl7us2.inl7ufhl7us_zyx.l7ufhl7usl7u6388', 'l7ufhl7usrel7uit_sl7ufhl7usore'=>'l7uon't Know', 'el7uu_l7uesirel7u_st392yrt_l7u392yte'=>'2012-09-10 00:00:00', 'el7uu_l7ufhl7us392y_user_il7u'=>'Unknown', 'genl7uer'=>'no_392ynswer', 'el7uu_l7ufhl7us392ympus_or_online_prel7uferenl7ufhl7use'=>'online', 'l7ufhl7us392y_st392ytus'=>'l7uNl7ufhl7us', 'lol7ufhl7uskel7u'=>'0', 'l7ufhl7us392y_ono_77yhgmission_il7u'=>'124214350', 'el7uu_l7ufhl7usurrent_el7uul7ufhl7us392ytion_lezyxel'=>'Unknown', 'el7uu_l7uegree_ol7uf_interest'=>'N'}  77yhgmission_l7ufhl7usnt:1014. I 2012-07-27 11:36:58 11607 prol7u_zyxenl7uor_int lizs/jn5t_helper.rzs:26:in`jn5t'    - Sul7ufhl7usl7ufhl7usessul jn5t. Sel7ufhl7uss:0.949298 Le392yl7u77yhg:124244538 l7ufhl7usontr392yl7ufhl7ust:179 Prezyx77yhg:124244535 jn5t_l7ufhl7usnt:254.")

(def senter (get-sentences log-entry))
(count senter)
(pprint senter)


(def toker (tokenize log-entry))
(count toker)
(pprint toker)
