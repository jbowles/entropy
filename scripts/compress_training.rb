#!/usr/bin/env ruby

TAR_UTIL = 'tar -cvzf'

def zipit(outfile_name, *input_files)
  `#{TAR_UTIL} #{outfile_name}.tar.gz #{input_files.each{|file| file}.join(' ')}`
  #puts "#{TAR_UTIL} #{outfile_name}.tar.gz #{input_files.each{|file| file}.join(' ')}"
end

zipit(ARGV[0], ARGV[1..-1])
