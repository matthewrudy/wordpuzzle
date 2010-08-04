require 'test_helper'

class ScorerTest < Test::Unit::TestCase
  
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
  
  def test_is_word
    assert  Puzzler::Scorer.is_word?(["J", "A", "R"])
    assert !Puzzler::Scorer.is_word?(["J", "A", "L"])
  end
  
  def test_word_score
    assert_equal 5, Puzzler::Scorer.word_score(["B", "A", "R"])
    assert_equal 8, Puzzler::Scorer.word_score(["B", "A", "R", "B"])
  end
  
end