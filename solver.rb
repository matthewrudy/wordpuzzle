 # B A R B
 # F A J E
 # H A A A
 # G N A A

GRID = [
  "B", "A", "R", "B",
  "F", "A", "J", "E",
  "H", "A", "A", "A",
  "G", "N", "A", "A",
]

WORDS = [
  "JAR",
  "BEAN",
  "BARB",
  "BAR",
]

def is_word?(combo)
  WORDS.include?(combo.join(""))
end

# all_combos.each do |combo|
#   puts combo.inspect
# end

require 'scorer'
require 'case'

base_case = Case.new(GRID)

base_case.all_words.each do |combo|
  puts combo.inspect
  puts "score #{ Scorer.word_score(combo) }"
end