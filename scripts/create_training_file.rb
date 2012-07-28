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
MODEL_REGEX = Hash[
  :loglevel => '<START:loglevel>\s\w\s<END>',
  :timestamp => '<START:timestamp>\s\d{4}-\d{2}-\d{2}\s\d{2}:\d{2}:\d{2}\s<END>',
  :processid => '<START:processid>\s\d{2,14}\s<END>',
  :commithash => '<START:commithash>\s.{5,10}\s<END>',
  :processenv => '<START:processenv>\s\w{4,}\s<END>',
  :file => '<START:file>\s\w{2,}\/\w{2,}(\.\w{1,}:\d{1,})*\s<END>',
  ## message are will return this (<START:messagearg> POST Data  <END>, <END>, ) ## Need to clean this again
  :messagearg => '<START:messagearg>.+<END>,\s',
  :attributes => '<START:attributes>\s.+<END>'
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

