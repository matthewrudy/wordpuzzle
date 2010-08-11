object Scorer {

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
		"Z" -> 10)

	val WORDS = io.Source.fromFile("3letters.dictionary").getLines.toList :::
	io.Source.fromFile("4letters.dictionary").getLines.toList

}

println(Scorer.WORDS.head + "..." + Scorer.WORDS.last)
println("score for Y is " + Scorer.LETTER_SCORES("Y"))

class Word(val letters : List[String]) {

    val isWord = Scorer.WORDS contains this.toString

    // JAR :
    // J = 8
    // A = 1
    // R = 1
    // scrabble score = 8 + 1 + 1 = 10
    // actual score = 3 * 10 = 30
	def score() : Int = {
		val scrabbleScore = letters.foldLeft(0)((sum, letter) => sum + Scorer.LETTER_SCORES(letter))
		scrabbleScore * letters.length
	}

	override def toString() = letters.mkString("")

}

object Word {

	def apply(letters : String*) = {
		new Word(letters.toList)
	}

}

val jar = Word("J", "A", "R")
assert(jar.isWord)
assert(jar.score() == 30)
assert(jar.toString == "JAR")

val jars = Word("J", "A", "R", "S")
assert(jars.isWord)
assert(jars.score() == 44)

val gar = Word("G", "A", "R")
//assert(!gar.isWord)
println("score for gars is " + gar.score())

class Grid(val rowList : List[List[String]]) {

	val colList = rowList.transpose
	
	def calculateScore() = {
		var sum = 0
		
		val addWordScores = { letterList:List[String] =>
			
			val combos = List( (0,4), (0,3), (1,4) )
			
			combos.foreach { o =>
				val letters = letterList.slice( o._1, o._2 )
				val word = new Word(letters)
				
				if(word.isWord) {
					sum = sum + word.score()
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
	"G", "N", "A", "A")

assert(grid.rowList(0) == List("B", "A", "R", "B"))
assert(grid.colList(2) == List("R", "J", "A", "A"))

println("grid score is " + grid.score)
	
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

println("base's parent is " + base.parent + " score: " + base.score)
println("second's parent is " + second.parent + " score:" + second.score)

//base.nextMoves(5)