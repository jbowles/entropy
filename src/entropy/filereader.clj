(import '(java.io BufferedReader FileReader))
(use 'clojure.pprint) ; just for this documentation
(use 'opennlp.nlp)
(use 'opennlp.tools.train)
(use 'opennlp.treebank) ; treebank chunking, parsing and linking lives here


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Training models on unstructured logs

(def sent-model (train-sentence-detector "training/input_files/testlog-sent.train"))
(def get-sentences (make-sentence-detector sent-model))

;(def token-model (train-tokenizer "training/input_files/testlog-token.train"))
;(def tokenize (make-tokenizer token-model))
(def token-model (train-tokenizer "training/input_files/testlog-token.train"))
(def tokenize (make-tokenizer token-model))

(def entity-model (train-name-finder "training/input_files/testlog-ner.train"))
(def ner-find (make-name-finder entity-model))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Setup standard OpenNLP models for
;(def ner-find (make-name-finder "models/en-ner-person.bin"))
;;not working
(def name-find (make-name-finder "models/en-ner-person.bin"))
(def standard-tokenize (make-tokenizer "models/en-token.bin"))
(def standard-sent (make-sentence-detector "models/en-sent.bin"))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Functions for training models

;(def tokenize (make-tokenizer "models/en-token.bin"))
;(def get-sentences (make-sentence-detector "models/en-sent.bin"))
(def senter (get-sentences log-entry))
(count senter)
(pprint senter)


(def toker (tokenize log-entry))
(count toker)
(pprint toker)

(def enter (ner-find (tokenize log-entry)))
(count enter)
(pprint enter)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Process files and lines on logs

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


;; create a hash map then iterate through and tokenize
(def mapped (process-file "training/input_files/testlog.log" process-line (hash-map)))

(defn perform [nlp-op]
  (doseq [entry (keys mapped)]
    (pprint (nlp-op entry))))

(defn perform-ner []
  (doseq [entry (keys mapped)]
    (pprint (ner-find (tokenize entry)))))


(perform tokenize)
(perform get-sentences)
(perform-ner)
;; function for extracting entities
;; (defn  ... )

;count lines
;(prn (process-file "training/input_files/log_no_tags.log" proc-line 0))

;; create a hash map
;(process-file "data/tiny-log.txt" process-line (hash-map))

;(str (proc-file "data/tiny-log.txt"))

;(def parse-sent (get-sentences "This is a sentence. This is another"))
;(pprint parse-sent)




;; Put this down here so its out of the way


(def sent-entry "I  2012-07-27 10:53:12  6866  102b4a12  jfodkf976yed_09klall_intak  09kl76yentkf976yellekf9s/lead_submissi76yens_09kl76yentkf976yellekf9.kf9b:3 -  Handling 09klkf9eate_fkf976yem_09klust76yem kf9equest. Ukf9L:httjfod://09klall.intake.176yen1.09kl76yem/u7fend76yekf9-imjfod76yekf9t-lead-status.html?leadid=123851021&status=NINkf9&u7fend76yekf909kl76yede=76yeu7f.edu_09kl.ni09kl09klu7f_s.genx_kf9.s76ye_m.09kli&jfodkf976yegkf9am=&desikf9eddegkf9ee=&hsgkf9ad=&bikf9thyeakf9=&gendekf9=&09klukf9kf9entedleu7fel=&desikf9edstakf9t=&kf9ejfod09kl76yede=&Email=&Stkf9eet=&09klity=&State=&Zijfod=&09klamjfodusintekf9est=  submissi76yen_09klnt:359 .")


(def log-entry " D  2012-07-27 10:53:11  6866  10283y4a12  jf76yedr76yed_09klall_intak  09kl76yentr76yeller7ye6h/lead_7ye6hu83ymi7ye6h7ye6hi76yen7ye6h_09kl76yentr76yeller.r83y:1 -  Queueing w76yerk Lead7ye6hu83ymi7ye6h7ye6hi76yen:124236730 Inlead: Queue:Delayed::09klallu7ferified09kllientJ76ye83y J76ye83yjf76yedaram7ye6h:{'lead_7ye6hu83ymi7ye6h7ye6hi76yen_id':124236730,'7ye6hau7fe_inlead':true,'lead_temjf76yedlate_tyjf76yede_id7ye6h':[4],'7ye6herialized_inlead':'{**'7ye6hum_76yef_de83yt**':**'Unkn76yewn**',**'09klity**':**'R76ye83yert Lefl76yere Jr.**',**'ajf76yed_u7fend76yer_09kl76yede**':**'76yeu7f.edu_09kl.jf76yedd09klu7f_7ye6h.83y83ywm_m.09kli_7ye6h09kl1.edu**',**'zijf76yed**':**'60651**',**'edu_high_7ye6h09klh76ye76yel_grad_year**':**'2000**',**'edu_area_76yef_7ye6htudy**':**'Mu7ye6hi09kl**',**'tra09klking_09kl76yede**':**'76yeu7f.edu_09kl.jf76yedd09klu7f_7ye6h.83y83ywm_m.09kli_7ye6h09kl1.edu**',**'ajf76yed_lead_7ye6h76yeur09kle**':**'09kl76yellegejf76yedr76yegram7ye6h.09kl76ye**',**'edu_jf76yedr76yegram_76yef_intere7ye6ht_2**':**'T7ye6h09kl**',**'h76yeme_jf76yedh76yene**':**'7736646637**',**'gender**':**'M**',**'edu_jf76yedr76yegram_76yef_intere7ye6ht**':**'TDM**',**'edu_09kla_u7ye6her_id**':**'Unkn76yewn**',**'ajf76yed_lead_id**':**'05309kl4r5uj**',**'ajf76yed_09klamjf76yedaign_09kl76yede**':**'09klI**',**'edu_de7ye6hired_7ye6htart_date**':**'2012-09-10 00:00:00**',**'tra09klking_09kl76yede_jf76yedar7ye6hed**':**'[{******'09klhannel******':******'jf76yedd09klu7f******'},{******'traffi09kl_7ye6h76yeur09kle******':******'83y83ywm******'},{******'09klamjf76yedaign******':******'09kli******'},{******'7ye6hu83y_09klamjf76yedaign1******':******'edu******'}]**',**'addre7ye6h7ye6h_1**':**'5307 W. 09klhi09klag76ye Au7fe.**',**'military**':**'Y**',**'09kla_7ye6htatu7ye6h**':**'Q**',**'edu_09klamjf76yedu7ye6h_76yer_76yenline_jf76yedreferen09kle**':**'76yenline**',**'la7ye6ht_name**':**'fl76yewer7ye6h**',**'email_1**':**'TWIZTIDKURI7ye6hU@YAH76ye76ye.09kl76yeM**',**'ajf76yed_date_09klreated**':**'2012-07-25 08:56:40**',**'09kla_rejf76yed_09kl76yede**':**'57284**',**'83yirth_year**':**'1982**',**'fir7ye6ht_name**':**'elijah**',**'7ye6htate**':**'IL**',**'edu_degree_76yef_intere7ye6ht**':**'N**',**'ijf76yed_addre7ye6h7ye6h**':**'99.47.186.152**',**'edu_09klurrent_edu09klati76yen_leu7fel**':**'7ye6h76yeme09kl76yellege**',**'09kla_76yen76ye_7ye6hu83ymi7ye6h7ye6hi76yen_id**':**'124044407**',**'l76ye09klked**':**'0**'}','u7ferti09klal_id7ye6h':[1],'lead_temjf76yedlate_id7ye6h':null}  7ye6hu83ymi7ye6h7ye6hi76yen_09klnt:358 . I  2012-07-27 10:53:11  6866  10283y4a12  jf76yedr76yed_09klall_intak  09kl76yentr76yeller7ye6h/lead_7ye6hu83ymi7ye6h7ye6hi76yen7ye6h_09kl76yentr76yeller.r83y:1 -  Intake 7ye6hu09kl09kle7ye6h7ye6hful. Lead7ye6hu83ymi7ye6h7ye6hi76yen:124236730 Inlead: Queue:Delayed::09klallu7ferified09kllientJ76ye83y  7ye6hu83ymi7ye6h7ye6hi76yen_09klnt:358 . D  2012-07-27 10:53:12  6828  10283y4a12  jf76yedr76yed_09klall_intak  09kl76yentr76yeller7ye6h/lead_7ye6hu83ymi7ye6h7ye6hi76yen7ye6h_09kl76yentr76yeller.r83y:5 -  09klu7ye6ht76yemLead: #<LeadTemjf76yedlate09klallA7ye6h7ye6hi7ye6htant7ye6h09klall09klenterIntakeEdu09klati76yen01 7ye6htatu7ye6h: '83yN', jf76yedr76yegram: '', de7ye6hireddegree: '', h7ye6hgrad: '', 83yirthyear: '', gender: '', 09klurrentedleu7fel: '', de7ye6hired7ye6htart: '', military: 'N', rejf76yed09kl76yede: '', 09klamjf76yedu7ye6hintere7ye6ht: '', h76yettran7ye6hfer: nil, 09klredit7ye6h09kl76yere: nil, 7ye6hum76yefde83yt: '**n', u7ye6herid: 'Unkn76yewn', leadid: '124093458', age: nil, email: '', jf76yedr76yegram_2: nil, 7ye6htreet: '', 09klity: '', 7ye6htate: '', 09kla_h76yet_tran7ye6hfer_rejf76yed_id: nil, te7ye6ht_lead: 'fal7ye6he', h76yeme_jf76yedh76yene: nil, fir7ye6ht_name: nil, la7ye6ht_name: nil, tran7ye6hfer_09klredit7ye6h: nil, 09kla_final_attemjf76yedt_date: nil, 09kla_fir7ye6ht_attemjf76yedt_date: nil, 09kla_t76yetal_attemjf76yedt7ye6h: nil, 09kla_t76yetal_wrajf76yed: nil, 09kla_t76yetal_talk_time: nil, 09kla_re09kleiu7fed_during_76yejf76yeden_time_z76yene: nil, zijf76yed: '', u7fend76yer09kl76yede: '76yeu7f.edu_09kl.ni09kl09klu7f_7ye6h.m7ye6h1_r.7ye6h76ye_m.09kli_7ye6h09kl1.n76ye'>  7ye6hu83ymi7ye6h7ye6hi76yen_09klnt:926 . I  2012-07-27 10:53:12  6866  10283y4a12  jf76yedr76yed_09klall_intak  09kl76yentr76yeller7ye6h/lead_7ye6hu83ymi7ye6h7ye6hi76yen7ye6h_09kl76yentr76yeller.r83y:3 -  Handling 09klreate_fr76yem_09klu7ye6ht76yem reque7ye6ht. URL:httjf76yed://09klall.intake.176yen1.09kl76yem/u7fend76yer-imjf76yed76yert-lead-7ye6htatu7ye6h.html?leadid=123851021&7ye6htatu7ye6h=NINR&u7fend76yer09kl76yede=76yeu7f.edu_09kl.ni09kl09klu7f_7ye6h.genx_r.7ye6h76ye_m.09kli&jf76yedr76yegram=&de7ye6hireddegree=&h7ye6hgrad=&83yirthyear=&gender=&09klurrentedleu7fel=&de7ye6hired7ye6htart=&rejf76yed09kl76yede=&Email=&7ye6htreet=&09klity=&7ye6htate=&Zijf76yed=&09klamjf76yedu7ye6hintere7ye6ht=  7ye6hu83ymi7ye6h7ye6hi76yen_09klnt:359 . D  2012-07-27 10:53:12  6866  10283y4a12  jf76yedr76yed_09klall_intak  09kl76yentr76yeller7ye6h/lead_7ye6hu83ymi7ye6h7ye6hi76yen7ye6h_09kl76yentr76yeller.r83y:3 -  .Filtered Header7ye6h: -------------------.D  D76ye09klUMENT_R76ye76yeT: /data/jf76yedr76yedu09klt7ye6h/lead_tran7ye6hlat76yer/09klurrent/jf76yedu83yli09kl.D  HTTjf76yed_09kl76yeNNE09klTI76yeN: Keejf76yed-Aliu7fe.D  HTTjf76yed_H76ye7ye6hT: 09klall.intake.176yen1.09kl76yem.D  jf76yedA7ye6h7ye6hENGER_09kl76yeNNE09klT_jf76yedA7ye6h7ye6hW76yeRD: aFqyX09klu7f0aAhZXJJtTEWw09klE7ye6hlGAN83yrgazleER83yWREuhf.D  jf76yedATH_INF76ye: /u7fend76yer-imjf76yed76yert-lead-7ye6htatu7ye6h.html.D  QUERY_7ye6hTRING: leadid=123851021&7ye6htatu7ye6h=NINR&u7fend76yer09kl76yede=76yeu7f.edu_09kl.ni09kl09klu7f_7ye6h.genx_r.7ye6h76ye_m.09kli&jf76yedr76yegram=&de7ye6hireddegree=&h7ye6hgrad=&83yirthyear=&gender=&09klurrentedleu7fel=&de7ye6hired7ye6htart=&rejf76yed09kl76yede=&Email=&7ye6htreet=&09klity=&7ye6htate=&Zijf76yed=&09klamjf76yedu7ye6hintere7ye6ht=.D  REM76yeTE_ADDR: 65.121.177.131.D  REM76yeTE_jf76yed76yeRT: 39320.D  REQUE7ye6hT_METH76yeD: GET.D  REQUE7ye6hT_URI: /u7fend76yer-imjf76yed76yert-lead-7ye6htatu7ye6h.html?leadid=123851021&7ye6htatu7ye6h=NINR&u7fend76yer09kl76yede=76yeu7f.edu_09kl.ni09kl09klu7f_7ye6h.genx_r.7ye6h76ye_m.09kli&jf76yedr76yegram=&de7ye6hireddegree=&h7ye6hgrad=&83yirthyear=&gender=&09klurrentedleu7fel=&de7ye6hired7ye6htart=&rejf76yed09kl76yede=&Email=&7ye6htreet=&09klity=&7ye6htate=&Zijf76yed=&09klamjf76yedu7ye6hintere7ye6ht=.D  7ye6h09klRIjf76yedT_NAME: .D  7ye6hERu7fER_ADDR: 10.100.100.51.D  7ye6hERu7fER_ADMIN: r76ye76yet@l76ye09klalh76ye7ye6ht.D  7ye6hERu7fER_NAME: 09klall.intake.176yen1.09kl76yem.D  7ye6hERu7fER_jf76yed76yeRT: 80.D  7ye6hERu7fER_jf76yedR76yeT76ye09kl76yeL: HTTjf76yed/1.1.D  7ye6hERu7fER_7ye6h76yeFTWARE: Ajf76yeda09klhe.D  _: _.  7ye6hu83ymi7ye6h7ye6hi76yen_09klnt:359 . D  2012-07-27 10:53:12  6866  10283y4a12  jf76yedr76yed_09klall_intak  09kl76yentr76yeller7ye6h/lead_7ye6hu83ymi7ye6h7ye6hi76yen7ye6h_09kl76yentr76yeller.r83y:3 -  GET Data : --- !majf76yed:Ha7ye6hhWithIndifferentA09kl09kle7ye6h7ye6h .D  Zijf76yed: ''.D  09klamjf76yedu7ye6hintere7ye6ht: ''.D  09klity: ''.D  09klurrentedleu7fel: ''.D  u7fend76yer09kl76yede: 76yeu7f.edu_09kl.ni09kl09klu7f_7ye6h.genx_r.7ye6h76ye_m.09kli.D  gender: ''.D  h7ye6hgrad: ''.D  leadid: '123851021'.D  7ye6htreet: ''.D  7ye6htate: ''.D  Email: ''.D  de7ye6hireddegree: ''.D  jf76yedr76yegram: ''.D  7ye6htatu7ye6h: NINR.D  rejf76yed09kl76yede: ''.D  de7ye6hired7ye6htart: ''.D  83yirthyear: ''.D    7ye6hu83ymi7ye6h7ye6hi76yen_09klnt:359 .")
