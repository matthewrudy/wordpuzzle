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

COMBOS = [
  [ 0, 1, 2, 3], # row 1
  [ 4, 5, 6, 7], # row 2
  [ 8, 9,10,11], # row 3
  [12,13,14,15], # row 4
  
  [ 0, 4, 8,12], # col 1
  [ 1, 5, 9,13], # col 2
  [ 2, 6,10,14], # col 3
  [ 3, 7,11,15], # col 4
]

WORDS = [
  "JAR",
  "BEAN",
  "BARB",
  "BAR",
]

def all_combos(grid)
  COMBOS.inject([]) do |combos, indices|
    all_4 = grid.values_at(*indices)
    combos << all_4
    combos << all_4[0,3]
    combos << all_4[1,3]
    
    combos
  end
end

def all_words(grid)
  all_combos(grid).select do |combo|
    is_word?(combo)
  end
end

def is_word?(combo)
  WORDS.include?(combo.join(""))
end

# all_combos.each do |combo|
#   puts combo.inspect
# end

require 'scorer'

all_words(GRID).each do |combo|
  puts combo.inspect
  puts "score #{ Scorer.score(combo) }"
end