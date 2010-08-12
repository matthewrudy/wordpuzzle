object Tests {
  
  def main(args:Array[String]) {
    
    { // Word tests
      assert(Word.isWord("AAH"),  "AAH is a word")
      assert(Word.isWord("ZYME"), "ZYME is a word")

      assert(Word.score("JAR")  == 30, "JAR's score is 30")
      assert(Word.score("JARS") == 44, "JARS' score is 44")

      assert(Word.isWord("JAR"),  "JAR is a word")
      assert(Word.isWord("JARS"), "JARS is a word")

      assert(Word.isWord("JAD") == false, "JAD is not a word")
    }
    
    { // WordBucket tests
      val bucket = WordBucket()
      assert(bucket.valid("col1-4", "POO"), "poo is not valid at col1-4")
      assert(bucket.valid("row1-4", "POO"), "poo is not valid at row1-4")
      assert(bucket.valid("col1-4", "GUFF"), "guff is not valid at col1-4")

      val newWords = Map("col1-4" -> "POO", "row1-4" -> "WEE")
      val newbie = bucket.merge(newWords)

      assert(!newbie.valid("col1-4", "POO"), "poo is still valid at col1-4")
      assert(newbie.valid("row1-4", "POO"),  "poo is not still valid at row1-4")
      assert(newbie.valid("col1-4", "GUFF"), "guff is not still valid at col1-4")
    }

    { // Grid tests
  
      val grid1 = Grid(
        "B", "A", "R", "B",
        "F", "A", "J", "E",
        "H", "A", "A", "A",
        "G", "N", "R", "A"
      )
      
      val grid1Words = grid1.validWords.values.toSet
      assert(Set("BARB", "BAR", "ARB", "JAR") == grid1Words, "words not as expected " + grid1Words)
  
      assert(Word.score("BAR") == 15,      "BAR is 15")
      assert(Word.score("BARB") == 32,     "BARB is 32")
      assert(Word.score("ARB") == 15,      "ARB is 15")
      assert(Word.score("JAR") == 30,      "JAR is 30")
      
      assert(grid1.score == 92, "grid's score is BAR + BARB + ARB + JAR = 92")

      val grid2 = grid1.nextMove(0,6)
      val grid2Words = grid2.validWords.values.toSet
      assert(Set("FAB", "JAR", "BAR") == grid2Words, "words not as expected " + grid2Words)
    }

    println("all the tests seem to pass")
  }
}
