package com.matthewrudy.scala.wordpuzzle.tests
 
import org.scalatest.FunSuite
import com.matthewrudy.scala.wordpuzzle.HighlightingGrid
 
class HighlightingGridTest extends FunSuite {
 
  test("highlighting") {
    val g1 = HighlightingGrid()
    expect(16)    {g1.disabled.length}
    expect(false) {g1.isPositionClear("row1-4")}
    expect(false) {g1.isPositionClear("col3-3a")}

    val g2 = g1.nextMove(0,1)
    expect(6)     {g2.disabled.length}
    expect(true)  {g2.isPositionClear("row1-4")}
    expect(false) {g2.isPositionClear("col3-3a")}

    val g3 = g2.nextMove(14,11)
    expect(0)     {g3.disabled.length}
    expect(true)  {g3.isPositionClear("row1-4")}
    expect(true)  {g3.isPositionClear("col3-3a")}

    val g4 = g3.nextMove(13,12)
    expect(true)  {g4.all}
    expect(true)  {g4.isPositionClear("row1-4")}
    expect(true)  {g4.isPositionClear("col3-3a")}
  }

}