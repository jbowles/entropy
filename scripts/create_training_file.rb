#!/usr/bin/env ruby

# Run it like this:
#ruby create_training_file.rb <FILE TO READ FROM> <TRAINING DIRECTORY> <REGULAR EXPRESSION FINGER> <TRAINING FILE NAME>
#ruby create_training_file.rb logfile.log         training             timestamp                   timestamp

REGEX_BASE = 'grep -P -o'
MODEL_REGEX = Hash[
  :loglevel => '<START:loglevel>\s\w\s<END>',
  :timestamp => '<START:timestamp>\s\d{4}-\d{2}-\d{2}\s\d{2}:\d{2}:\d{2}\s<END>',
  :processid => '<START:processid>\s\d{2,14}\s<END>',
  :commithash => '<START:commithash>\s.{5,10}\s<END>',
  :processenv => '<START:processenv>\s\w{4,}\s<END>',
  :file => '<START:file>\s\w{2,}\/\w{2,}|(\.\w{1,}:\d{1,})\s<END>',
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

def create_train
 #`#{REGEX_BASE} '#{TIMESTAMP}' #{PROMPT_PARAMS[:file_in]} > #{PROMPT_PARAMS[:train_dir]}/#{PROMPT_PARAMS[:train_file_name]}.train`
 puts "#{REGEX_BASE} '#{MODEL_REGEX[PROMPT_PARAMS[:regular_expression].to_sym]}' #{PROMPT_PARAMS[:file_in]} > #{PROMPT_PARAMS[:train_dir]}/#{PROMPT_PARAMS[:train_file_name]}.train"
end

create_train

