class Case
  attr_accessor :grid
  
  def score
    Scorer.score(self.grid)
  end
  
end
