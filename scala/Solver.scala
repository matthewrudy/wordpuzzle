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

assert(Word.isWord("AAH"),  "AAH is a word")
assert(Word.isWord("ZYME"), "ZYME is a word")

assert(Word.score("JAR")  == 30, "JAR's score is 30")
assert(Word.score("JARS") == 44, "JARS' score is 44")

assert(Word.isWord("JAR"),  "JAR is a word")
assert(Word.isWord("JARS"), "JARS is a word")

assert(Word.isWord("JAD") == false, "JAD is not a word")

class Grid(val rowList : List[List[String]]) {

  val colList = rowList.transpose

  def calculateScore() = {
    var sum = 0

    val addWordScores = { letterList:List[String] =>

      val combos = List( (0,4), (0,3), (1,4) )

      combos.foreach { o =>
        val word = letterList.slice( o._1, o._2 ).mkString("")
        
        if(Word.isWord(word)) {
          sum = sum + Word.score(word)
        }
      }
    }
    rowList.foreach { addWordScores }
    colList.foreach { addWordScores }
    sum
  }

  val score = this.calculateScore()
}

object Grid {

  val POSITIONS = Map(
    "row1-4"  -> ( 0, 1, 2, 3),
    "row1-3a" -> ( 0, 1, 2   ),
    "row1-3b" -> (    1, 2, 3),

    "row2-4"  -> ( 4, 5, 6, 7),
    "row2-3a" -> ( 4, 5, 6   ),
    "row2-3b" -> (    5, 6, 7),

    "row3-4"  -> ( 8, 9,10,11),
    "row3-3a" -> ( 8, 9,10   ),
    "row3-3b" -> (    9,10,11),

    "row4-4"  -> (12,13,14,15),
    "row4-3a" -> (12,13,14   ),
    "row4-3b" -> (   13,14,15),

    "col1-4"  -> ( 0, 4, 8,12),
    "col1-3a" -> ( 0, 4, 8   ),
    "col1-3b" -> (    4, 8,12),

    "col2-4"  -> ( 1, 5, 9,13),
    "col2-3a" -> ( 1, 5, 9   ),
    "col2-3b" -> (    5, 9,13),

    "col3-4"  -> ( 2, 6,10,14),
    "col3-3a" -> ( 2, 6,10   ),
    "col3-3b" -> (    6,10,14),

    "col4-4"  -> ( 3, 7,11,15),
    "col4-3a" -> ( 3, 7,11   ),
    "col4-3b" -> (    7,11,15)
    )


    def apply(letters:String*) = {
      val list = letters.toList

      val row1 = list.slice( 0, 4)
      val row2 = list.slice( 4, 8)
      val row3 = list.slice( 8,12)
      val row4 = list.slice(12,16)

      new Grid( List(row1, row2, row3, row4) )
    }
  }

  val grid = Grid(
    "B", "A", "R", "B",
    "F", "A", "J", "E",
    "H", "A", "A", "A",
    "G", "N", "R", "A")

    assert(grid.rowList(0) == List("B", "A", "R", "B"))
    assert(grid.colList(2) == List("R", "J", "A", "R"))

    assert(Word.score("BAR") == 15,      "BAR is 15")
    assert(Word.score("BARB") == 32, "BARB is 32")
    assert(Word.score("ARB") == 15,      "ARB is 15")
    assert(Word.score("JAR") == 30,      "JAR is 30")

    assert(grid.score == 92, "grid's score is BAR + BARB + ARB + JAR = 92")

    class Move(val generation : Int, val grid : Grid, val parent : Move) {

      def parentScore() : Int = {
        if(this.parent != null) {
          parent.score
        }
        else 0
      }

      val score = parentScore() + grid.score

      def nextMoves(generations:Int) {
        if(generations > 0) {
          (1 to 120).foreach { i =>
            val nextone = new Move(this.generation+1, grid, this)
            nextone.nextMoves(generations - 1)
          }
        }
        else this.score
      }
    }

    val base = new Move(0, grid, null)
    val second = new Move(1, grid, base)

    //base.nextMoves(5)