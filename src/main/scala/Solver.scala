package com.matthewrudy.scala.wordpuzzle

object Solver {

  // scala Solver E T W D I N L A M A L Y E Y E I
  
  /* val example = Grid(
      "E", "T", "W", "D",
      "I", "N", "L", "A",
      "M", "A", "L", "Y",
      "E", "Y", "E", "I"
    )
  
  */
  def main(args:Array[String]) {
    val letters = readLine("Enter a grid:\n\n")
    //val letters = "E T W D I N L A M A L Y E Y E I"
    runGame(letters.split(" "))
  }
  
  def runGame(letters:Array[String]) {
    println()
    
    println("initial:")
    val ex1 = Grid(letters)
    ex1.print()
    
    val next3 = ex1.best3Moves
    
    println("move 1:")
    val move1 = next3(0)
    move1.print
    
    println("move 2:")
    val move2 = next3(1)
    move2.print
    
    println("move 3:")
    val move3 = next3(2)
    move3.print
    
    val last2 = move3.best2Moves
    
    println("move 4:")
    val move4 = last2(0)
    move4.print
    
    println("move 5:")
    val move5 = last2(1)
    move5.print
    
    val totalScore = move1.score + move2.score + move3.score + move4.score + move5.score
    println("total: " + totalScore)
  }
}
