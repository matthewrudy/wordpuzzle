object Solver {

  def main(args:Array[String]) {

    val ex1 = Grid(
      "E", "I", "P", "X",
      "U", "R", "O", "A",
      "A", "W", "E", "H",
      "E", "A", "I", "E"
      )
      
    ex1.print()
    println("example 1 score : "+ex1.score)
    println("words: " +ex1.validWords)
    
    val ex2 = ex1.nextMove(3,11)
    ex2.print()
    println("example 2 score : "+ex2.score)
    println("words: " +ex2.validWords)
  }
}
