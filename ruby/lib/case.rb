class Case
  
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
  
  def initialize(grid)
    @grid = grid
  end
  
  attr_reader :grid
  
  def all_combos
    COMBOS.inject([]) do |combos, indices|
      all_4 = grid.values_at(*indices)
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
  
  def subcases
    rtn = []
    0.upto(14) do |i|
      i.upto(15) do |j|
        subgrid = grid.dup
        subgrid[i] = grid[j]
        subgrid[j] = grid[i]
        rtn << subgrid
      end
    end
    rtn
  end
      
  
end
