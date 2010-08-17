object Solver {

  def main(args:Array[String]) {

    val ex1 = Grid(
      "E", "I", "P", "X",
      "U", "R", "O", "A",
      "A", "W", "E", "H",
      "E", "A", "I", "E"
      )
      
    ex1.print()
    
    val next2 = ex1.best2Moves
    val move1 = next2(0)
    move1.print
    val move2 = next2(1)
    move2.print
    
    val last2 = move2.best2Moves
    val move3 = last2(0)
    move3.print
    val move4 = last2(1)
    move4.print
    
    val move5 = move2.bestMove
    move5.print
    
   /* val move1 = ex1.bestMove
       move1.print
       val move2 = move1.bestMove
       move2.print
       val move3 = move2.bestMove
       move3.print
       val move4 = move3.bestMove
       move4.print
       val move5 = move4.bestMove
       move5.print*/
  }
}
