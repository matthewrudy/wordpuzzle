import scala.actors.Actor._

class Grid(val letters:List[String], val wordBucket:WordBucket, val highlighting:HighlightingGridBase) {
  
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
    
  val validWords = possibleWords.filter { p =>
    val position = p._1
    val word     = p._2

    Word.isWord(word) && wordBucket.valid(position, word)
  }

  val score = validWords.foldLeft(0) { (sum, p) =>
    val position = p._1
    val word     = p._2

    sum + Word.score(word)
  }
  
  def nextMove(swap1:Int, swap2:Int) = {
    val letter1 = letters(swap1)
    val letter2 = letters(swap2)
    
    val newLetters = letters.patch(swap1, List(letter2), 1)
                            .patch(swap2, List(letter1), 1)

    new Grid(newLetters, wordBucket.merge(validWords), highlighting.nextMove(swap1, swap2))
  }
  
  def bestMove() = {
    var bestest : Grid = null
    
    for(i <- 0 until 15; j <- i+1 to 15) {
      
      val next = nextMove(i, j)
      
      if (bestest == null)
        bestest = next
      else if (next.score > bestest.score)
        bestest = next
    
    }
    bestest
  }
  
  def allNextMoves() = {
    for(i <- 0 until 15; j <- i+1 to 15) {
      val next = nextMove(i, j)
      println("score : " + next.score)
    }
  }
  
  def print() {
    this.letters.grouped(4).foreach { group =>
      println(group.mkString(", "))                       
    }
  }
}

object Grid {
  
  val INDICES = List(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15)
  
  def getRow(position:Int) = position / 4
  def getCol(position:Int) = position % 4
  
  def rowMates(position:Int) = ROWS(getRow(position))
  def colMates(position:Int) = COLS(getCol(position))
  
  val ROWS = List(
    List( 0, 1, 2, 3),
    List( 4, 5, 6, 7),
    List( 8, 9,10,11),
    List(12,13,14,15)
  )
    
  val COLS = List(
    List( 0, 4, 8,12),
    List( 1, 5, 9,13),
    List( 2, 6,10,14),
    List( 3, 7,11,15)
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
  
  def indexToRowPositions(index) = {
    ROW_POSITIONS.filter { p =>
      val indices = p._2
      indices contains index
    }
  }
  
  def indexToColPositions(index) = {
    COL_POSITIONS.filter { p =>
      val indices = p._2
      indices contains index
    }
  }

  def apply(letters:String*) = {
    new Grid(letters.toList, WordBucket(), HighlightingGrid())
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
  
  def nextMove(swap1:Int, swap2:Int) = {
    this
  }
}

abstract class HighlightingGridBase {
  
  val all = false
  val disabled          : List[Int]
  val disabledPositions : List[String]
  
  def isPositionClear(position:String) : Boolean
  
  def nextMove(swap1:Int, swap2:Int) : HighlightingGridBase
}

class HighlightingGrid(val grid: Array[Boolean]) extends HighlightingGridBase {  
  
  val disabled = Grid.INDICES.filter { position => !grid(position) }
  val disabledPositions = Grid.POSITIONS.filter { p =>
    val indices = p._2
    !(this.disabled intersect indices).isEmpty
  }.keys.toList
  
  def isPositionClear(position:String) = {
    !(disabledPositions contains position)
  }
  
  def nextMove(swap1:Int, swap2:Int) = {
    if(this.disabled.length == 0) {
      new AllHighlightedGrid()
    }
    else {
      val nextGrid = this.grid.clone
    
      Array(swap1, swap2).foreach { position =>
        Grid.rowMates(position).foreach { p =>
          nextGrid(p) = true
        }
        Grid.colMates(position).foreach { p =>
          nextGrid(p) = true
        }
      }
    
      new HighlightingGrid(nextGrid)
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




  