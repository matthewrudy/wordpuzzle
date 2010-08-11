object Word {
  
  def scrabbleScore(word:String) = {
    word.foldLeft(0) {
      (sum, letter) => sum + letterScore(letter)
    }
  }
  
  /*scrabble scores*/
  val LETTER_SCORES = Map(
    "A" -> 1,
    "B" -> 3,
    "C" -> 3,
    "D" -> 2,
    "E" -> 1,
    "F" -> 4,
    "G" -> 2,
    "H" -> 4,
    "I" -> 1,
    "J" -> 8,
    "K" -> 5,
    "L" -> 1,
    "M" -> 3,
    "N" -> 1,
    "O" -> 1,
    "P" -> 3,
    "Q" -> 10,
    "R" -> 1,
    "S" -> 1,
    "T" -> 1,
    "U" -> 1,
    "V" -> 4,
    "W" -> 4,
    "X" -> 8,
    "Y" -> 4,
    "Z" -> 10
  )
  
  def letterScore(character:Char) = LETTER_SCORES(character.toString)
  
  // JAR :
  // J = 8
  // A = 1
  // R = 1
  // scrabble score = 8 + 1 + 1 = 10
  // actual score = 3 * 10 = 30
  def score(word:String) = scrabbleScore(word) * word.length
  
  val WORDS = io.Source.fromFile("3letters.dictionary").getLines.toList :::
              io.Source.fromFile("4letters.dictionary").getLines.toList
  
  def isWord(word:String) = WORDS contains word

}

{ // stick the asserts in a block so i can code fold them
  assert(Word.isWord("AAH"),  "AAH is a word")
  assert(Word.isWord("ZYME"), "ZYME is a word")

  assert(Word.score("JAR")  == 30, "JAR's score is 30")
  assert(Word.score("JARS") == 44, "JARS' score is 44")

  assert(Word.isWord("JAR"),  "JAR is a word")
  assert(Word.isWord("JARS"), "JARS is a word")

  assert(Word.isWord("JAD") == false, "JAD is not a word")
}

class Grid(val letters : List[String]) {

  val row1 = letters.slice( 0, 4)
  val row2 = letters.slice( 4, 8)
  val row3 = letters.slice( 8,12)
  val row4 = letters.slice(12,16)

  val rowList = List(row1, row2, row3, row4)
  val colList = rowList.transpose

  def calculateScore() = {
    possibleWords.foldLeft(0) { (sum, p) =>
      val position = p._1
      val word     = p._2
      
      if(Word.isWord(word))
        sum + Word.score(word)
      else
        sum
    }
  }
  
  def possibleWords = {
    Grid.POSITIONS.map { p =>
      val position = p._1
      val indices  = p._2
      val word     = indices.map(index => letters(index)).mkString("")
      
      position -> word
    }
  }

  val score = this.calculateScore()
}

object Grid {

  val POSITIONS = Map(
    "row1-4"  -> List( 0, 1, 2, 3),
    "row1-3a" -> List( 0, 1, 2   ),
    "row1-3b" -> List(    1, 2, 3),

    "row2-4"  -> List( 4, 5, 6, 7),
    "row2-3a" -> List( 4, 5, 6   ),
    "row2-3b" -> List(    5, 6, 7),

    "row3-4"  -> List( 8, 9,10,11),
    "row3-3a" -> List( 8, 9,10   ),
    "row3-3b" -> List(    9,10,11),

    "row4-4"  -> List(12,13,14,15),
    "row4-3a" -> List(12,13,14   ),
    "row4-3b" -> List(   13,14,15),

    "col1-4"  -> List( 0, 4, 8,12),
    "col1-3a" -> List( 0, 4, 8   ),
    "col1-3b" -> List(    4, 8,12),

    "col2-4"  -> List( 1, 5, 9,13),
    "col2-3a" -> List( 1, 5, 9   ),
    "col2-3b" -> List(    5, 9,13),

    "col3-4"  -> List( 2, 6,10,14),
    "col3-3a" -> List( 2, 6,10   ),
    "col3-3b" -> List(    6,10,14),

    "col4-4"  -> List( 3, 7,11,15),
    "col4-3a" -> List( 3, 7,11   ),
    "col4-3b" -> List(    7,11,15)
  )

  def apply(letters:String*) = {
    new Grid(letters.toList)
  }
}

val grid = Grid(
  "B", "A", "R", "B",
  "F", "A", "J", "E",
  "H", "A", "A", "A",
  "G", "N", "R", "A"
)

{ // stick the assertions in a block so i can code fold them
  
  assert(Word.score("BAR") == 15,      "BAR is 15")
  assert(Word.score("BARB") == 32, "BARB is 32")
  assert(Word.score("ARB") == 15,      "ARB is 15")
  assert(Word.score("JAR") == 30,      "JAR is 30")

  assert(grid.score == 92, "grid's score is BAR + BARB + ARB + JAR = 92")
}

class Move(val generation : Int, val grid : Grid, val parent : Move) {

  def parentScore() : Int = {
    if(this.parent != null) parent.score else 0
  }

  val score = parentScore() + grid.score

  def nextMoves(generations:Int) {
    if(generations > 0) {
      (1 to 120).foreach { i =>
        val nextone = new Move(this.generation+1, grid, this)
        nextone.nextMoves(generations - 1)
      }
    }
  }
}

val base = new Move(0, grid, null)
val second = new Move(1, grid, base)

//base.nextMoves(5)