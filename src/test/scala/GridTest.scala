package com.matthewrudy.scala.wordpuzzle.tests
 
import org.scalatest.FunSuite
import com.matthewrudy.scala.wordpuzzle.Grid
import com.matthewrudy.scala.wordpuzzle.Word
 
class GridTest extends FunSuite {
 
  test("getRow") {
    expect(0) {Grid.getRow(0)}
    expect(3) {Grid.getRow(15)}
    expect(2) {Grid.getRow(9)}
  }
  
  test("getCol") {
    expect(0) {Grid.getCol(0)}
    expect(3) {Grid.getCol(15)}
    expect(1) {Grid.getCol(9)}
  }
  
  test("workthrough") {
    
     val grid0 = Grid(
       "B", "A", "R", "B",
       "F", "A", "J", "E",
       "H", "A", "A", "A",
       "G", "N", "R", "A"
     )
     
     expect(Set()) { grid0.validWords.values.toSet }
  
     val grid1 = grid0.nextMove(1,10)
     expect(Set("BARB", "BAR", "ARB", "JAR")) {
       grid1.validWords.values.toSet
     }
  
     expect(15) {Word.score("BAR")}
     expect(32) {Word.score("BARB")}
     expect(15) {Word.score("ARB")}
     expect(30) {Word.score("JAR")}
  
     // "grid's score is BAR + BARB + ARB + JAR = 92"
     expect(92) {grid1.score}
  
     val grid2 = grid1.nextMove(0,6)
     expect(Set("FAB", "JAR", "BAR")) {
       grid2.validWords.values.toSet
     }
   
  }
}
