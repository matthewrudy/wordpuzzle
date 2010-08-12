{ // tests for Word
  assert(Word.isWord("AAH"),  "AAH is a word")
  assert(Word.isWord("ZYME"), "ZYME is a word")

  assert(Word.score("JAR")  == 30, "JAR's score is 30")
  assert(Word.score("JARS") == 44, "JARS' score is 44")

  assert(Word.isWord("JAR"),  "JAR is a word")
  assert(Word.isWord("JARS"), "JARS is a word")

  assert(Word.isWord("JAD") == false, "JAD is not a word")
}

val bucket = WordBucket()
println("is poo valid at 'col1-4'? " + bucket.valid("col1-4", "POO"))
println("is poo valid at 'row1-4'? " + bucket.valid("row1-4", "POO"))
println("is guff valid at 'col1-4'? " + bucket.valid("col1-4", "GUFF"))

val newWords = Map("col1-4" -> "POO", "row1-4" -> "WEE")
val newbie = bucket.merge(newWords)

println("is poo valid at 'col1-4'? " + newbie.valid("col1-4", "POO"))
println("is poo valid at 'row1-4'? " + newbie.valid("row1-4", "POO"))
println("is guff valid at 'col1-4'? " + newbie.valid("col1-4", "GUFF"))

val grid = Grid(
  "B", "A", "R", "B",
  "F", "A", "J", "E",
  "H", "A", "A", "A",
  "G", "N", "R", "A"
)

println(grid.validWords)
println(grid.score)

val grid2 = grid.nextMove(0,6)

println(grid2.validWords)
println(grid2.score)

{ // stick the assertions in a block so i can code fold them
  
  assert(Word.score("BAR") == 15,      "BAR is 15")
  assert(Word.score("BARB") == 32, "BARB is 32")
  assert(Word.score("ARB") == 15,      "ARB is 15")
  assert(Word.score("JAR") == 30,      "JAR is 30")

  assert(grid.score == 92, "grid's score is BAR + BARB + ARB + JAR = 92")
}
