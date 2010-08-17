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
  
  val WORDS = io.Source.fromFile("dict/3letters").getLines.toSet ++
              io.Source.fromFile("dict/4letters").getLines.toSet
  
  def isWord(word:String) = WORDS contains word

}