#!/usr/bin/env ruby

PROMPTER = '>> '
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

def command_prompt
  puts "Running #{$0} script on #{Time.now}... pleas use underscore to separate names (EX: input_file)"
  puts "What is the name of the input file you want to build your training from? (EX: logfile.log -- add file extension please)"
  print PROMPTER
  PROMPT_PARAMS[:file_in] = STDIN.gets.chomp()
  puts "What is the directory path you want the training file to be stored in? (EX training -- just the name)"
  print PROMPTER
  PROMPT_PARAMS[:train_dir] = STDIN.gets.chomp()
  puts "Write one of the models in the prompt (no colons please) #{MODEL_REGEX.keys}"
  print PROMPTER
  PROMPT_PARAMS[:regular_expression] = STDIN.gets.chomp()
  puts "Write the name of the training file (EX: timestamp -- I suggest the name of the model you selected):"
  print PROMPTER
  PROMPT_PARAMS[:train_file_name] = STDIN.gets.chomp()
  puts "Write OUTPUT if you want to see the command (but not run it), othwerwise hit return to run it:"
  print PROMPTER
  PROMPT_PARAMS[:runit] = STDIN.gets.chomp()
  #confirm = <<-MESSAGE
  #I will create the training file #{PROMPT_PARAMS[:train_file_name]} 
  #in #{PROMPT_PARAMS[:train_dir]} 
  #from #{PROMPT_PARAMS[:file_in]}
  #using the regex model #{PROMPT_PARAMS[:regular_expression]}
  #MESSAGE
  #puts confirm
  #puts PROMPT_PARAMS
end

def create_train(test=true)
  if test
    puts "#{REGEX_BASE} '#{MODEL_REGEX[PROMPT_PARAMS[:regular_expression].to_sym]}' #{PROMPT_PARAMS[:file_in]} > #{PROMPT_PARAMS[:train_dir]}/#{PROMPT_PARAMS[:train_file_name]}.train"
  else
    `#{REGEX_BASE} '#{MODEL_REGEX[PROMPT_PARAMS[:regular_expression].to_sym]}' #{PROMPT_PARAMS[:file_in]} > #{PROMPT_PARAMS[:train_dir]}/#{PROMPT_PARAMS[:train_file_name]}.train`
  end
end

command_prompt
!PROMPT_PARAMS[:runit].empty? ? create_train : create_train(test=false)

