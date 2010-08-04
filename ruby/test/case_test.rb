require 'test_helper'

class CaseTest < Test::Unit::TestCase
  
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
  
  def test_all_combos
    c = Puzzler::Case.new(GRID, nil, 0)
    combos = c.all_combos
    # all uniq
    assert_equal combos, combos.uniq
    
    assert_equal 8*3*2, combos.length
  end
  
end