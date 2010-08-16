object Solver {

  def main(args:Array[String]) {

    val grid1 = Grid(
      "B", "A", "R", "B",
      "F", "A", "J", "E",
      "H", "A", "A", "A",
      "G", "N", "R", "A"
    )
    
    println(grid1.bestMove.score)
  }
}
