#!/usr/bin/env ruby

# Run it like this:
#ruby create_training_file.rb <FILE TO READ FROM> <TRAINING DIRECTORY> <REGULAR EXPRESSION FINDER> <TRAINING FILE NAME>
#ruby create_training_file.rb logfile.log         training             timestamp                   timestamp

# Actual usage
# ruby create_training_file.rb input_files/testlog.log ls_train timestamp timestamp

# Script will execute something like this:
#grep -P -o '<START:timestamp>\s\d{4}-\d{2}-\d{2}\s\d{2}:\d{2}:\d{2}\s<END>' input_files/testlog > ls_train/timestamp.train

#stdin arguments to k-v hash
#params = {}
#ARGV.each.map{|i| params[i.to_sym] = i}

REGEX_BASE = 'grep -P -o'
FIND_ALL = '(.+?)'
MODEL_REGEX = Hash[
  # This is iffy becuase it is just a few Capital Letters
  :loglevel => "<START:loglevel>#{FIND_ALL}<END>",
  # use OpenNLP's ner for timestamps, or roll my own, but can't create them
  # from log training data
  #:timestamp => "<START:timestamp>#{FIND_ALL}<END>",
  # This is problematic because its just sequences of integers
  :processid => "<START:processid>#{FIND_ALL}<END>",
  # This is problematic becuase its just sequences of characters
  :commithash => "<START:commithash>#{FIND_ALL}<END>",
  :processenv => "<START:processenv>#{FIND_ALL}<END>",
  :file => "<START:file>#{FIND_ALL}<END>",
  ## message are will return this (<START:messagearg> POST Data  <END>, <END>, ) ## Need to clean this again
  :messagearg => "<START:messagearg>#{FIND_ALL}<END>",
  :attributes => "<START:attributes>#{FIND_ALL}<END>",
  :deploy_path => "<START:deploypath>#{FIND_ALL}<END>",
  :exception_class => "<START:exceptionclassmessage>#{FIND_ALL}<END>"
]

PROMPT_PARAMS = {}
PROMPT_PARAMS[:file_in] = ARGV[0]
PROMPT_PARAMS[:train_dir] = ARGV[1]
PROMPT_PARAMS[:regular_expression] = ARGV[2]
PROMPT_PARAMS[:train_file_name] = ARGV[3]
#stdin arguments to k-v hash

def create_train(test=true)
  if test
    puts "#{REGEX_BASE} '#{MODEL_REGEX[PROMPT_PARAMS[:regular_expression].to_sym]}' #{PROMPT_PARAMS[:file_in]} > #{PROMPT_PARAMS[:train_dir]}/#{PROMPT_PARAMS[:train_file_name]}.train"
  else
    `#{REGEX_BASE} '#{MODEL_REGEX[PROMPT_PARAMS[:regular_expression].to_sym]}' #{PROMPT_PARAMS[:file_in]} > #{PROMPT_PARAMS[:train_dir]}/#{PROMPT_PARAMS[:train_file_name]}.train`
  end
end


ARGV[4].nil? ? create_train : create_train(test=false)

