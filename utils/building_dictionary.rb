require 'rubygems'
require 'open-uri'
require 'hpricot'

url = ARGV[0]
html = open(url).read
h = Hpricot.parse(html)
h./("#canvas_inner .word").each do |word|
  puts word.to_plain_text
end
