# entropy
Using maximum entropy algorithms for doing natrual language processing of application logs. 

I'm assuming that no boilerplate training data will work across different applications. I'm using a private repository of training data I've built from application logs. However, I've provided the default OpneNLP binaries in the `models` directory.

## Dependencies
Using OpenNLP, Clojure, and clojure-opennlp, incanter.

## Training
See the training directory (a private repo) for information on how training sets were created. These training sets were used to generate the binary model files.

## License

Copyright (C) 2012 Josh Bowles

Distributed under the Eclipse Public License, the same as Clojure.
