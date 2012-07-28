# entropy
Using maximum entropy algorithms for doing natrual language processing of application logs. 

I'm assuming that no boilerplate training data will work across different applications. I'm using a private repository of training data I've built from application logs. However, I've provided the default OpneNLP binaries in the `models` directory.

## Dependencies
Using OpenNLP, Clojure, and clojure-opennlp, incanter.

## Create training script (sensitive data is stored in a private repository)
TODO: Following are not working or do not work good enough:

* CommitHash (produces nothing)
* MessageArg (produces nothing)
* File (if a file extension exists the line gets split -- needs to be one line)

The file created by this script are to be used as training files to build models with OpenNLP libraries.

You can run the script in one of two ways. Pass arguments through the command line or use a command line prompt. They both take the same arguments, here is what the command line option looks like:

```ssh
ruby create_training_file.rb <FILE TO READ FROM> <TRAINING DIRECTORY> <REGULAR EXPRESSION FINDER> <TRAINING FILE NAME>

ruby create_training_file.rb input_files/testlog.log ls_train timestamp timestamp
grep -P -o '<START:timestamp>\s\d{4}-\d{2}-\d{2}\s\d{2}:\d{2}:\d{2}\s<END>' input_files/testlog > ls_train/timestamp.train
```

What this does is search the logs and pull out the necessary pieces of entity tags I embedded in the production logs. We'll then use these files to build some entity extraction models, once these are built we can build some finer grained models. 
FIXME: write

## License

Copyright (C) 2012 Josh Bowles

Distributed under the Eclipse Public License, the same as Clojure.
