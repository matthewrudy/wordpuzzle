#!/usr/bin/env ruby

# use:
#
#  utils/build_dictionary.rb http://scrabutility.com/3_SOWPODS.php > dict/3letters
require 'rubygems'
require 'open-uri'
require 'hpricot'

url = ARGV[0]
html = open(url).read
h = Hpricot.parse(html)
h./("#canvas_inner .word").each do |word|
  puts word.to_plain_text
end
