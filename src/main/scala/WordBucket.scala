package com.matthewrudy.scala.wordpuzzle

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