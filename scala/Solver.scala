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
		"Z" -> 10
	)

	val WORDS = List(
		"JAR",
		"BEAN",
		"BARB",
		"BAR"
	)

}

println(Scorer.WORDS)
println("score for Y is " + Scorer.LETTER_SCORES("Y"))

class Word(val letters : List[String]) {
	
	def score() : Int = {
		letters.foldLeft(0)((sum, letter) => sum + Scorer.LETTER_SCORES(letter))
    }

    def isWord() = {
	  Scorer.WORDS contains this.toString
    }

    override def toString() = letters.mkString("")

}

object Word {
	
	def apply(letters : String*) = {
		new Word(letters.toList)
	}
	
}
		

val jar = Word("J", "A", "R")

println("word is " + jar.toString())
println("score is " + jar.score())
println("jar is a word? : " + jar.isWord())

val gar = Word("G", "A", "R")
println("gar is a word? : " + gar.isWord())

class Grid(val rowList : List[List[String]]) {
	
	val colList = rowList.transpose
	
	def score() : Int = {
		var sum = 0
		rowList.foreach {
			row => row
			
			val word = new Word(row)
			sum = sum + word.score()
		}
		return sum
	}
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
	"G", "N", "A", "A"
)

println("grid row 1 is " + grid.rowList(0))
println("grid col 1 is " + grid.colList(0))
println("grid score is " + grid.score)