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

LETTER_SCORES = {
  "A" => 1,
  "B" => 3,
  "C" => 3,
  "D" => 2,
  "E" => 1,
  "F" => 4,
  "G" => 2,
  "H" => 4,
  "I" => 1,
  "J" => 8,
  "K" => 5,
  "L" => 1,
  "M" => 3,
  "N" => 1,
  "O" => 1,
  "P" => 3,
  "Q" => 10,
  "R" => 1,
  "S" => 1,
  "T" => 1,
  "U" => 1,
  "V" => 4,
  "W" => 4,
  "X" => 8,
  "Y" => 4,
  "Z" => 10
}

WORDS = [
  "JAR",
  "BEAN",
  "BARB",
  "BAR",
]

def all_combos
  COMBOS.inject([]) do |combos, indices|
    all_4 = GRID.values_at(*indices)
    combos << all_4
    combos << all_4[0,3]
    combos << all_4[1,3]
    
    combos
  end
end

def all_words
  all_combos.select do |combo|
    is_word?(combo)
  end
end

def is_word?(combo)
  WORDS.include?(combo.join(""))
end

def score(combo)
  combo.inject(0) do |sum, letter|
    sum += LETTER_SCORES[letter]
  end
end

# all_combos.each do |combo|
#   puts combo.inspect
# end

all_words.each do |combo|
  puts combo.inspect
  puts score(combo)
end