(use 'opennlp.nlp)
(use 'opennlp.tools.train)


(def sent-model (train-sentence-detector "training/input_files/testlog.train")

;(def tokenize (make-tokenizer "models/en-token.bin"))
;(def date-find (make-name-finder "models/en-ner-date.bin"))
;(def time-find (make-name-finder "models/en-ner-time.bin"))

;; not working
;(def token-model (train-tokenizer "training/ls_train/token-timestamp.train"))
;(def token-model (train-tokenizer "training/ls_train/tokenizer.train"))
;(def tokenize-timestamp (make-tokenizer token-model))
;(tokenize-timestamp "2007-07-30 03:50:53 Being at the polls was just like being at church")

;; not working right
;(def ner-model (train-name-finder "training/ls_train/timestamp_entities_split_files/ner-timestamp-xaa.train"))
;(def find-timestamp (make-name-finder ner-model))
;(find-timestamp (tokenize-timestamp "2007-07-30 03:50:53 Being at the polls was just like being at church"))
;; ("2007-07-30 03:50:53 Bein g a t th e poll s wa s jus t lik e bein g a t churc h"
;(find-timestamp (tokenize "2007-07-30 03:50:53 Being at the polls was just like being at church"))
;;("2007-07-30 03:50:53 Bein g a t th e poll s wa s jus t lik e bein g a t churc h")

;;; trying other ways
;(date-find (tokenize-timestamp "2007-07-30 03:50:53 Being at the polls was just like being at church"))
;(time-find (tokenize-timestamp "2007-07-30 03:50:53 Being at the polls was just like being at church"))
;(date-find (tokenize "2007-07-30 03:50:53 Being at the polls was just like being at church"))
;(time-find (tokenize "2007-07-30 03:50:53 Being at the polls was just like being at church"))

;(def namefinder-model (train-name-finder "training/ls_train/named_org.train"))
;(def name-find (make-name-finder namefinder-model))
;(write-model namefinder-model "namefinder")
;(name-find (tokenize "Who won the World Series aginst the National League? Was it Giants or Dodgers? One of them will come back next year."))

;(def ts-find (make-sentence-detector "models/en-sent.bin"))
;(def sent-vec (ts-find "New York 3:30 a.m. Sunday 11:30 midnight, noon, morning 06-06-1906 2020/12/20 on 2012-07-27 at 11:36 with prod_vendor_int controllers/lead_submissions_controller.rb:3 - POST Data  submission_cnt:367 I 2012-07-27 11:36:58 11607 prod_vendor_int lib/post_helper.rb:26:in`post'    - Successul post. Secs:0.949298 LeadSub:124244538 Contract:179 PrevSub:124244535 post_cnt:254 D 2012-07-27 11:36:58 2849  prod_vendor_int controllers/lead_submissions_controller.rb:5 - CustomLead: #<LeadTemplateActiveProspectCvVendorIntake_education01 lead_id: '053c9b6bi', fname: 'BARBARA', lname: 'SCOTT', phone1: '843-553-0033', phone2: '', street: '103 CENTRAL AVE', city: 'GOODCREEK', state: 'SC', zip: '29445', email: 'BARBARA.SCOTT2009@COMCAST.NET', age: '', area_of_study: '', lead_source: 'http://www.123freetravel.com/HBB/', ip: '67.142.163.21', timestamp: '2012-07-27 12:27:39', vendorcode: 'ov.edu_c.niccv_s.genx_r.so_m.ci', campaigncode: 'CI', gender: '', origin_vertical_id: nil> submission_cnt:367 I 2012-07-27 11:36:58 11607 prod_vendor_int lib/post_helper.rb:533:in`distribute'  - DIST: Accepted to contract 179. post_cnt:254"))
;
;(def lsatts (train-name-finder "training/ls_train/entity.train"))
;(def lsatts-find (make-name-finder lsatts))
;(lsatts-find (tokenize "D 2012-07-27 11:36:58 2849  prod_vendor_int controllers/lead_submissions_controller.rb:3 - POST Data  submission_cnt:367 I 2012-07-27 11:36:58 11607 prod_vendor_int lib/post_helper.rb:26:in`post'    - Successul post. Secs:0.949298 LeadSub:124244538 Contract:179 PrevSub:124244535 post_cnt:254 D 2012-07-27 11:36:58 2849  prod_vendor_int controllers/lead_submissions_controller.rb:5 - CustomLead: #<LeadTemplateActiveProspectCvVendorIntake_education01 lead_id: '053c9b6bi', fname: 'BARBARA', lname: 'SCOTT', phone1: '843-553-0033', phone2: '', street: '103 CENTRAL AVE', city: 'GOODCREEK', state: 'SC', zip: '29445', email: 'BARBARA.SCOTT2009@COMCAST.NET', age: '', area_of_study: '', lead_source: 'http://www.123freetravel.com/HBB/', ip: '67.142.163.21', timestamp: '2012-07-27 12:27:39', vendorcode: 'ov.edu_c.niccv_s.genx_r.so_m.ci', campaigncode: 'CI', gender: '', origin_vertical_id: nil> submission_cnt:367 I 2012-07-27 11:36:58 11607 prod_vendor_int lib/post_helper.rb:533:in`distribute'  - DIST: Accepted to contract 179. post_cnt:254"))
;
;(tokenize "Being at the polls was just like being at church.")
;(tokenize "Assigning ca_intake_vendor_contract for template type 3 Submission")
;(tokenize "D 2012-07-27 11:36:58 2849  prod_vendor_int controllers/lead_submissions_controller.rb:3 - POST Data  submission_cnt:367 I 2012-07-27 11:36:58 11607 prod_vendor_int lib/post_helper.rb:26:in`post'    - Successul post. Secs:0.949298 LeadSub:124244538 Contract:179 PrevSub:124244535 post_cnt:254 D 2012-07-27 11:36:58 2849  prod_vendor_int controllers/lead_submissions_controller.rb:5 - CustomLead: #<LeadTemplateActiveProspectCvVendorIntake_education01 lead_id: '053c9b6bi', fname: 'BARBARA', lname: 'SCOTT', phone1: '843-553-0033', phone2: '', street: '103 CENTRAL AVE', city: 'GOODCREEK', state: 'SC', zip: '29445', email: 'BARBARA.SCOTT2009@COMCAST.NET', age: '', area_of_study: '', lead_source: 'http://www.123freetravel.com/HBB/', ip: '67.142.163.21', timestamp: '2012-07-27 12:27:39', vendorcode: 'ov.edu_c.niccv_s.genx_r.so_m.ci', campaigncode: 'CI', gender: '', origin_vertical_id: nil> submission_cnt:367 I 2012-07-27 11:36:58 11607 prod_vendor_int lib/post_helper.rb:533:in`distribute'  - DIST: Accepted to contract 179. post_cnt:254")
;
