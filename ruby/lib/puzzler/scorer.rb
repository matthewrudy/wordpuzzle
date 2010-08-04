require 'puzzler'

module Puzzler
  
  class Scorer
  
    # scrabble scores
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
    
    class << self
      
      def word_score(combo)
        combo.inject(0) do |sum, letter|
          sum += LETTER_SCORES[letter]
        end
      end

      def is_word?(combo)
        WORDS.include?(combo.join(""))
      end
  
    end
    
  end
  
end
