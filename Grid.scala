class Grid(val letters:List[String], val wordBucket:WordBucket, val highlighting:HighlightingGridBase, val swapped:Set[Int]) {
  
  val possiblePositions = Grid.POSITIONS.filter { p => 
    val position = p._1
    highlighting.isPositionClear(position)
  }
  
  val possibleWords = possiblePositions.map { p =>
    val position = p._1
    val indices  = p._2
    val word     = indices.map(index => letters(index)).mkString("")
    
    position -> word
  }
  
  // Map("col1-4" -> "AXE", "row2-3a" -> "HEX")
  val validWords = possibleWords.filter { p =>
    val position = p._1
    val word     = p._2

    Word.isWord(word) && wordBucket.valid(position, word)
  }
  
  val words = validWords.values.toSet       // Set("AXE", "HEX")
  
  val wordPositions = validWords.keys.toSet // Set("col1-4", "row-2-3a")
  val rowWordPositions = wordPositions.intersect(Grid.ROW_POSITION_NAMES)
  val colWordPositions = wordPositions.intersect(Grid.COL_POSITION_NAMES)
  
  val rowCells = rowWordPositions.map { position =>
    Grid.POSITIONS(position)
  }.flatten
  
  val colCells = colWordPositions.map { position =>
    Grid.POSITIONS(position)
  }.flatten
  
  val intersectionCells = rowCells.intersect(colCells)
  
  val bonusWords = intersectionCells.toList.map{ index =>
    val positions = Grid.positionsForIndex(index).intersect(wordPositions)
    
    var bestScore = 0
    var bestWord : String = null
    
    positions.foreach { thisPosition =>
      val thisWord = validWords(thisPosition)
      val thisScore = Word.score(thisWord)
      
      if(thisScore > bestScore)
        bestWord = thisWord
        bestScore = thisScore
    }
    bestWord
  }

  val baseScore = validWords.foldLeft(0) { (sum, p) =>
    // val position = p._1
    val word     = p._2

    sum + Word.score(word)
  }
  
  val bonusScore = bonusWords.foldLeft(0) { (sum, word) => sum + Word.score(word) }
  
  val score = baseScore + bonusScore
  
  def nextMove(swap1:Int, swap2:Int) = {
    val letter1 = letters(swap1)
    val letter2 = letters(swap2)
    
    val newLetters = letters.patch(swap1, List(letter2), 1)
                            .patch(swap2, List(letter1), 1)

    new Grid(newLetters, wordBucket.merge(validWords), highlighting.nextMove(swap1, swap2), Set(swap1, swap2))
  }
  
  def bestMove() = {
    var bestScore = Int.MinValue
    var bestest : Grid = null
    
    for(i <- 0 until 15; j <- i+1 to 15) {
      
      val next = nextMove(i, j)
      
      if (next.score > bestScore)
        bestest = next
        bestScore = next.score
    }
    bestest
  }
  
  def best2Moves() = {
    var bestScore = Int.MinValue
    var bestest : List[Grid] = null
    
    for(i1 <- 0 until 15; j1 <- i1+1 to 15) {
      val move1 = nextMove(i1, j1)
      
      if(move1.score > Grid.MINIMUM_MOVE) { // cut out maybe 30% of cases which are 0 score
      
        for(i2 <- 0 until 15; j2 <- i2+1 to 15) {
          val move2 = move1.nextMove(i2, j2)
        
          val thisScore = move1.score + move2.score
              
          if (thisScore > bestScore) {
            bestScore = thisScore
            bestest = List(move1, move2)
          }
        }
      }  
    }
    bestest
  }
  
  def bestNMoves(n:Int) : (Int,List[Grid]) = {
    
    if (n==0) {
      (this.score, List(this))
    }
    else {
      
      var bestScore = Int.MinValue
      var bestMoves : List[Grid] = null
      
      for (i <- 0 until 15; j <- i+1 to 15) {
        
        val nextOne = nextMove(i,j)
        
        if (nextOne.score > Grid.MINIMUM_MOVE) { // cut out maybe 30% of cases
          
          val nextMoves = nextOne.bestNMoves(n-1)
          
          val thisScore = this.score + nextMoves._1
          
          if (thisScore > bestScore) {
            bestScore = thisScore
            bestMoves = this +: nextMoves._2
          }
        }
      }
      (bestScore, bestMoves)
    }
  }
  
  def best3Moves() = {
    var bestScore = Int.MinValue
    var bestest : List[Grid] = null
    
    for(i <- 0 until 15; j <- i+1 to 15) {
      val move1 = nextMove(i, j)
      
      if(move1.score > Grid.MINIMUM_MOVE) { // cut out maybe 30% of cases which are 0 score
      
        for(i <- 0 until 15; j <- i+1 to 15) {
          val move2 = move1.nextMove(i, j)
        
          if(move2.score > Grid.MINIMUM_MOVE) { // cut out maybe 30% of cases here as well
            
            for(i <- 0 until 15; j <- i+1 to 15) {
              val move3 = move2.nextMove(i, j)
              
              val thisScore = move1.score + move2.score + move3.score
              
              if (thisScore > bestScore) {
                bestScore = thisScore
                bestest = List(move1, move2, move3)
              }
            }
          }
        }
      }  
    }
    bestest
  }
  
  def print() {
    println()
    (0 to 15).grouped(4).foreach { group =>
      val line = group.map { index =>
        if(swapped.contains(index)) "("+letters(index)+")"
        else " "+letters(index)+" "
      }
      println(line.mkString(""))                 
    }
    println()
    println("words: " + words.map{ word => word+"("+Word.score(word)+")" }.mkString(", "))
    println("bonus: " + bonusWords.map{ word => word+"("+Word.score(word)+")" }.mkString(", "))
    println("score: " + baseScore + " + " + bonusScore + " = " + score)
    println("")
  }
}

object Grid {
  
  val MINIMUM_MOVE = 10
  
  val INDICES = List(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15)
  
  val INDEX_TO_ROW = INDICES.map { index => (index -> index / 4) }.toMap
  val INDEX_TO_COL = INDICES.map { index => (index -> index % 4) }.toMap
  def getRow(index:Int) = INDEX_TO_ROW(index)
  def getCol(index:Int) = INDEX_TO_COL(index)
  
  def rowMates(index:Int) = ROWS(getRow(index))
  def colMates(index:Int) = COLS(getCol(index))
  
  val ROWS = List(
    Set( 0, 1, 2, 3),
    Set( 4, 5, 6, 7),
    Set( 8, 9,10,11),
    Set(12,13,14,15)
  )
    
  val COLS = List(
    Set( 0, 4, 8,12),
    Set( 1, 5, 9,13),
    Set( 2, 6,10,14),
    Set( 3, 7,11,15)
  )
  
  val ROW_POSITIONS = Map(
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
    "row4-3b" -> List(   13,14,15)
  )

  val COL_POSITIONS = Map(
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
  
  val POSITIONS = ROW_POSITIONS ++ COL_POSITIONS
  
  val ROW_POSITION_NAMES = ROW_POSITIONS.keys.toSet
  val COL_POSITION_NAMES = COL_POSITIONS.keys.toSet
  
  val POSITIONS_FOR_INDEX = INDICES.map { index =>
    val thesePositions = POSITIONS.filter { p =>
      val name = p._1
      val indices = p._2
      
      indices.contains(index)
    }.keys.toSet
    
    (index -> thesePositions)
  }.toMap
  
  def positionsForIndex(index:Int) = {
    POSITIONS_FOR_INDEX(index)
  }

  def apply(letters:String*) : Grid = {
    Grid(letters.toList)
  }
  
  def apply(letters:Array[String]) : Grid = {
    Grid(letters.toList)
  }
  
  def apply(letters:List[String]) : Grid = {
    new Grid(letters, WordBucket(), HighlightingGrid(), Set[Int]())
  }
}

object WordBucket {
  
  val EMPTY_BUCKET = Grid.POSITIONS.map { p =>
    val position = p._1
    val indices  = p._2
    
    position -> Set[String]()
  }
   
  def apply() = {
    new WordBucket(EMPTY_BUCKET)
  }
}

class WordBucket(val bucket:Map[String, Set[String]]) {
  
  // have we hit this before in this position
  def valid(position:String, word:String) = {
    !(bucket(position) contains word)
  }
  
  // at the next move
  def merge(newWords:Map[String, String]) = {
    val newBucket = bucket.map { p => 
      val position = p._1
      var words    = p._2
      
      if(newWords.isDefinedAt(position)) {
        words = words + newWords(position)
      }
      
      position -> words
    }
    new WordBucket(newBucket)
  }
      
}

class AllHighlightedGrid() extends HighlightingGridBase {
  
  override val all = true
  val disabled          = List()
  val disabledPositions = List()
  
  def isPositionClear(position:String) = true
  
  def nextMove(swap1:Int, swap2:Int) = this
}

abstract class HighlightingGridBase {
  
  val all = false
  val disabled          : List[Int]
  val disabledPositions : List[String]
  
  def isPositionClear(position:String) : Boolean
  
  def nextMove(swap1:Int, swap2:Int) : HighlightingGridBase
}

class HighlightingGrid(val grid: Array[Boolean]) extends HighlightingGridBase {  
  
  val disabled          = Grid.INDICES.filter { index => !grid(index) }
  val disabledPositions = Grid.POSITIONS.filter { p =>
    val indices = p._2
    !(this.disabled intersect indices).isEmpty
  }.keys.toList
  
  def isPositionClear(position:String) = {
    !(disabledPositions contains position)
  }
  
  def nextMove(swap1:Int, swap2:Int) = {
    val nextGrid = this.grid.clone
  
    Array(swap1, swap2).foreach { position =>
      Grid.rowMates(position).foreach { p =>
        nextGrid(p) = true
      }
      Grid.colMates(position).foreach { p =>
        nextGrid(p) = true
      }
    }
    
    if(nextGrid.contains(false)) {
      new HighlightingGrid(nextGrid)
    }
    else {
      new AllHighlightedGrid()
    }
  }
}

object HighlightingGrid {
  
  val DEFAULT_HIGHLIGHTING = Array(
    false, false, false, false,
    false, false, false, false,
    false, false, false, false,
    false, false, false, false
  )
  
  def apply() = {
    new HighlightingGrid(DEFAULT_HIGHLIGHTING)
  }
  
}




  