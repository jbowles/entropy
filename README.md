# entropy
Using maximum entropy algorithms for doing natrual language processing of application logs. 

I'm assuming that no boilerplate training data will work across different applications. I'm using a private repository of training data I've built from application logs. However, I've provided the default OpneNLP binaries in the `models` directory.

## Dependencies
Using OpenNLP, Clojure, and clojure-opennlp, incanter.

## Training
See the training directory (a private repo) for information on how training sets were created. These training sets were used to generate the binary model files.

## Examples
Finally getting the correct models working. The goal here is to take unstructred log data and parse it. The models are being trained so as not to anticipate log formatting that conforms with the parsing logic. The examples below provide lists that consume strings; this is only for exhibition. In reality we would be streaming text files either real-time or after the fact and persisting portions of the parsed text to a queryabe cache (soon I'll be saving parsed log data to PostGres and expect to provide Mongo ro Riak support soon after).

### Sentences
Parsing senteces on unstructured log data; I added a period to each line end to facilitate parsing.
Here is an example of parsing production logs into sentences:

Assume some portion of streamed log data:

```clj
(def log-entry "fl7u  2012-07-27 10:52:49  6828  102zs4392y12  prol7u_l7ufhl7us392yll_int392yk  l7ufhl7usontrollers/le392yl7u_77yhgmissions_l7ufhl7usontroller.rzs:3 -  jn5T l7u392yt392y   77yhgmission_l7ufhl7usnt:925. l7u  2012-07-27 10:52:49  6828  102zs4392y12  prol7u_l7ufhl7us392yll_int392yk  l7ufhl7usontrollers/le392yl7u_77yhgmissions_l7ufhl7usontroller.rzs:5 -  l7ufhl7usustomLe392yl7u: #<Le392yl7uTempl392ytel7ufhl7us392yll392yssist392yntsl7ufhl7us392ylll7ufhl7usenterInt392ykeEl7uul7ufhl7us392ytion01 st392ytus: 'NINR', progr392ym: '', l7uesirel7ul7uegree: '', hsgr392yl7u: '', zsirthye392yr: '', genl7uer: '', l7ufhl7usurrentel7ulezyxel: '', l7uesirel7ust392yrt: '', milit392yry: 'N', repl7ufhl7usol7ue: '', l7ufhl7us392ympusinterest: '', hottr392ynsl7ufer: nil, l7ufhl7usrel7uitsl7ufhl7usore: nil, sumol7ufl7uezst: '\n', useril7u: 'Unknown', le392yl7uil7u: '124223313', 392yge: nil, em392yil: '', progr392ym_2: nil, street: '', l7ufhl7usity: '', st392yte: '', l7ufhl7us392y_hot_tr392ynsl7ufer_rep_il7u: nil, test_le392yl7u: 'l7uf392ylse', home_phone: nil, l7ufirst_n392yme: nil, l392yst_n392yme: nil, tr392ynsl7ufer_l7ufhl7usrel7uits: nil, l7ufhl7us392y_l7ufin392yl_392yttempt_l7u392yte: nil, l7ufhl7us392y_l7ufirst_392yttempt_l7u392yte: nil, l7ufhl7us392y_tot392yl_392yttempts: nil, l7ufhl7us392y_tot392yl_wr392yp: nil, l7ufhl7us392y_tot392yl_t392ylk_time: nil, l7ufhl7us392y_rel7ufhl7useizyxel7u_l7uuring_open_time_zone: nil, zip: '', zyxenl7uorl7ufhl7usol7ue: 'ozyx.el7uu_l7ufhl7us.pl7ul7ufhl7uszyx_s.el7up_m.l7ufhl7usi_sl7ufhl7us2.inl7ufhl7us_zyx.l7ufhl7usl7u7574'>  77yhgmission_l7ufhl7usnt:925. l7u  2012-07-27 10:52:49  6804  102zs4392y12  prol7u_l7ufhl7us392yll_int392yk  l7ufhl7usontrollers/le392yl7u_77yhgmissions_l7ufhl7usontroller.rzs:6 -  Inle392yl7u: {'392yp_zyxenl7uor_l7ufhl7usol7ue'=>'ozyx.el7uu_l7ufhl7us.pl7ul7ufhl7uszyx_s.el7up_m.l7ufhl7usi_sl7ufhl7us2.inl7ufhl7us_zyx.l7ufhl7usl7u6388', 'l7ufhl7usrel7uit_sl7ufhl7usore'=>'l7uon't Know', 'el7uu_l7uesirel7u_st392yrt_l7u392yte'=>'2012-09-10 00:00:00', 'el7uu_l7ufhl7us392y_user_il7u'=>'Unknown', 'genl7uer'=>'no_392ynswer', 'el7uu_l7ufhl7us392ympus_or_online_prel7uferenl7ufhl7use'=>'online', 'l7ufhl7us392y_st392ytus'=>'l7uNl7ufhl7us', 'lol7ufhl7uskel7u'=>'0', 'l7ufhl7us392y_ono_77yhgmission_il7u'=>'124214350', 'el7uu_l7ufhl7usurrent_el7uul7ufhl7us392ytion_lezyxel'=>'Unknown', 'el7uu_l7uegree_ol7uf_interest'=>'N'}  77yhgmission_l7ufhl7usnt:1014. I 2012-07-27 11:36:58 11607 prol7u_zyxenl7uor_int lizs/jn5t_helper.rzs:26:in`jn5t'    - Sul7ufhl7usl7ufhl7usessul jn5t. Sel7ufhl7uss:0.949298 Le392yl7u77yhg:124244538 l7ufhl7usontr392yl7ufhl7ust:179 Prezyx77yhg:124244535 jn5t_l7ufhl7usnt:254.")
```

```clj
(def senter (get sentences log-entry))
(count senter) ; => 4
(pprint senter) ; I've separated sentences for readability
[

      "fl7u  2012-07-27 10:52:49  6828  102zs4392y12  prol7u_l7ufhl7us392yll_int392yk  l7ufhl7usontrollers/le392yl7u_77yhgmissions_l7ufhl7usontroller.rzs:3 -  jn5T l7u392yt392y   77yhgmission_l7ufhl7usnt:925."

      "l7u  2012-07-27 10:52:49  6828  102zs4392y12  prol7u_l7ufhl7us392yll_int392yk  l7ufhl7usontrollers/le392yl7u_77yhgmissions_l7ufhl7usontroller.rzs:5 -  l7ufhl7usustomLe392yl7u: #<Le392yl7uTempl392ytel7ufhl7us392yll392yssist392yntsl7ufhl7us392ylll7ufhl7usenterInt392ykeEl7uul7ufhl7us392ytion01 st392ytus: 'NINR', progr392ym: '', l7uesirel7ul7uegree: '', hsgr392yl7u: '', zsirthye392yr: '', genl7uer: '', l7ufhl7usurrentel7ulezyxel: '', l7uesirel7ust392yrt: '', milit392yry: 'N', repl7ufhl7usol7ue: '', l7ufhl7us392ympusinterest: '', hottr392ynsl7ufer: nil, l7ufhl7usrel7uitsl7ufhl7usore: nil, sumol7ufl7uezst: '\n', useril7u: 'Unknown', le392yl7uil7u: '124223313', 392yge: nil, em392yil: '', progr392ym_2: nil, street: '', l7ufhl7usity: '', st392yte: '', l7ufhl7us392y_hot_tr392ynsl7ufer_rep_il7u: nil, test_le392yl7u: 'l7uf392ylse', home_phone: nil, l7ufirst_n392yme: nil, l392yst_n392yme: nil, tr392ynsl7ufer_l7ufhl7usrel7uits: nil, l7ufhl7us392y_l7ufin392yl_392yttempt_l7u392yte: nil, l7ufhl7us392y_l7ufirst_392yttempt_l7u392yte: nil, l7ufhl7us392y_tot392yl_392yttempts: nil, l7ufhl7us392y_tot392yl_wr392yp: nil, l7ufhl7us392y_tot392yl_t392ylk_time: nil, l7ufhl7us392y_rel7ufhl7useizyxel7u_l7uuring_open_time_zone: nil, zip: '', zyxenl7uorl7ufhl7usol7ue: 'ozyx.el7uu_l7ufhl7us.pl7ul7ufhl7uszyx_s.el7up_m.l7ufhl7usi_sl7ufhl7us2.inl7ufhl7us_zyx.l7ufhl7usl7u7574'>  77yhgmission_l7ufhl7usnt:925."

      "l7u  2012-07-27 10:52:49  6804  102zs4392y12  prol7u_l7ufhl7us392yll_int392yk  l7ufhl7usontrollers/le392yl7u_77yhgmissions_l7ufhl7usontroller.rzs:6 -  Inle392yl7u: {'392yp_zyxenl7uor_l7ufhl7usol7ue'=>'ozyx.el7uu_l7ufhl7us.pl7ul7ufhl7uszyx_s.el7up_m.l7ufhl7usi_sl7ufhl7us2.inl7ufhl7us_zyx.l7ufhl7usl7u6388', 'l7ufhl7usrel7uit_sl7ufhl7usore'=>'l7uon't Know', 'el7uu_l7uesirel7u_st392yrt_l7u392yte'=>'2012-09-10 00:00:00', 'el7uu_l7ufhl7us392y_user_il7u'=>'Unknown', 'genl7uer'=>'no_392ynswer', 'el7uu_l7ufhl7us392ympus_or_online_prel7uferenl7ufhl7use'=>'online', 'l7ufhl7us392y_st392ytus'=>'l7uNl7ufhl7us', 'lol7ufhl7uskel7u'=>'0', 'l7ufhl7us392y_ono_77yhgmission_il7u'=>'124214350', 'el7uu_l7ufhl7usurrent_el7uul7ufhl7us392ytion_lezyxel'=>'Unknown', 'el7uu_l7uegree_ol7uf_interest'=>'N'}  77yhgmission_l7ufhl7usnt:1014."

      "I 2012-07-27 11:36:58 11607 prol7u_zyxenl7uor_int lizs/jn5t_helper.rzs:26:in`jn5t'    - Sul7ufhl7usl7ufhl7usessul jn5t. Sel7ufhl7uss:0.949298 Le392yl7u77yhg:124244538 l7ufhl7usontr392yl7ufhl7ust:179 Prezyx77yhg:124244535 jn5t_l7ufhl7usnt:254."

]
```

### Tokens
Also got tokenization working on unstructured log data.

```clj
(def toker (tokenize "..."))
(count toker) ;=> 132
(pprint toker) ; Only a potion of tokens presented here
["fl7u"
 "2012-07-27"
 "10:52:49"
 "6828"
 "102zs4392y12"
 "prol7u_l7ufhl7us392yll_int392yk"
 "l7ufhl7usontrollers/le392yl7u_77yhgmissions_l7ufhl7usontroller.rzs:3"
 "-"
 "jn5T"
 "l7u392yt392y"
 "77yhgmission_l7ufhl7usnt:925."
 "l7u"
 "2012-07-27"
 "10:52:49"
 "6828"
 "progr392ym:"
 "'',"
 "l7uesirel7ul7uegree:"
 "'',"
 "hsgr392yl7u:"
 "'',"
 "zsirthye392yr:"
 "'',"
 "genl7uer:"
 "'',"
 "l7ufhl7usurrentel7ulezyxel:"
 "'',"
 "l7uesirel7ust392yrt:"
 "nil,"
 "l7ufhl7us392y_l7ufin392yl_392yttempt_l7u392yte:"
 "nil,"
 "zip:"
 "'',"
 "'ozyx.el7uu_l7ufhl7us.pl7ul7ufhl7uszyx_s.el7up_m.l7ufhl7usi_sl7ufhl7us2.inl7ufhl7us_zyx.l7ufhl7usl7u7574'>"
 "77yhgmission_l7ufhl7usnt:925."
 "l7u"
 "2012-07-27"
 "10:52:49"
 "6804"
 "102zs4392y12"
 "prol7u_l7ufhl7us392yll_int392yk"
 "l7ufhl7usontrollers/le392yl7u_77yhgmissions_l7ufhl7usontroller.rzs:6"
 "-"
 "Inle392yl7u:"
 "{'392yp_zyxenl7uor_l7ufhl7usol7ue'=>'ozyx.el7uu_l7ufhl7us.pl7ul7ufhl7uszyx_s.el7up_m.l7ufhl7usi_sl7ufhl7us2.inl7ufhl7us_zyx.l7ufhl7usl7u6388',"
 "'el7uu_l7uesirel7u_st392yrt_l7u392yte'=>'2012-09-10"
 "00:00:00',"
 "'el7uu_l7ufhl7us392y_user_il7u'=>'Unknown',"
 "'genl7uer'=>'no_392ynswer',"
 "'el7uu_l7ufhl7us392ympus_or_online_prel7uferenl7ufhl7use'=>'online',"
 "'l7ufhl7us392y_st392ytus'=>'l7uNl7ufhl7us',"
 "I"
 "2012-07-27"
 "11:36:58"
 "11607"
 "prol7u_zyxenl7uor_int"
 "lizs/jn5t_helper.rzs:26:in`jn5t'"
 "Sul7ufhl7usl7ufhl7usessul"
 "Sel7ufhl7uss:0.949298"
 "l7ufhl7usontr392yl7ufhl7ust:179"
 "Prezyx77yhg:124244535"
 "jn5t_l7ufhl7usnt:254."]
```


## License

Copyright (C) 2012 Josh Bowles

Distributed under the Eclipse Public License, the same as Clojure.
