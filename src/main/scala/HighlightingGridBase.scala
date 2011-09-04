package com.matthewrudy.scala.wordpuzzle

abstract class HighlightingGridBase {
  
  val all = false
  val disabled          : List[Int]
  val disabledPositions : List[String]
  
  def isPositionClear(position:String) : Boolean
  
  def nextMove(swap1:Int, swap2:Int) : HighlightingGridBase
}