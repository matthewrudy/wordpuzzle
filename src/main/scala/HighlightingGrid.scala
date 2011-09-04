package com.matthewrudy.scala.wordpuzzle

class HighlightingGrid(val grid: Array[Boolean]) extends HighlightingGridBase {  
  
  val disabled          = Grid.INDICES.filter { index => !grid(index) }
  val disabledPositions = Grid.POSITIONS.filter { p =>
    val indices = p._2
    !(this.disabled intersect indices).isEmpty
  }.keys.toList
  
  def isPositionClear(position:String) = {
    !(disabledPositions contains position)
  }
  
  def nextMove(swap1:Int, swap2:Int) = {
    val nextGrid = this.grid.clone
  
    Array(swap1, swap2).foreach { position =>
      Grid.rowMates(position).foreach { p =>
        nextGrid(p) = true
      }
      Grid.colMates(position).foreach { p =>
        nextGrid(p) = true
      }
    }
    
    if(nextGrid.contains(false)) {
      new HighlightingGrid(nextGrid)
    }
    else {
      new AllHighlightedGrid()
    }
  }
}

object HighlightingGrid {
  
  val DEFAULT_HIGHLIGHTING = Array(
    false, false, false, false,
    false, false, false, false,
    false, false, false, false,
    false, false, false, false
  )
  
  def apply() = {
    new HighlightingGrid(DEFAULT_HIGHLIGHTING)
  }
  
}