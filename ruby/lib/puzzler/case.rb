require 'puzzler'

module Puzzler
  
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
  
    def initialize(grid, parent, generation)
      @grid = grid
      @parent = parent
      @generation = generation
    end
  
    attr_reader :grid, :parent, :generation
    
    def each_combo
      COMBOS.each do |combo|
        [combo, combo[0,3], combo[1,3]].each do |subcombo|
          yield subcombo
          yield subcombo.reverse
        end
      end
      nil
    end
  
    def all_combos
      rtn = []
      each_combo do |combo|
        rtn << combo
      end
      rtn
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
end
