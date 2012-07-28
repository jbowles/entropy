#!/usr/bin/env ruby

REGEX_BASE = 'grep -P -o'
PROMPTER = '>> '
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
