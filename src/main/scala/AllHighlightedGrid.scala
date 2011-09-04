package com.matthewrudy.scala.wordpuzzle

class AllHighlightedGrid() extends HighlightingGridBase {
  
  override val all = true
  val disabled          = List()
  val disabledPositions = List()
  
  def isPositionClear(position:String) = true
  
  def nextMove(swap1:Int, swap2:Int) = this
}