package com.matthewrudy.scala.wordpuzzle.tests
 
import org.scalatest.FunSuite
import com.matthewrudy.scala.wordpuzzle.WordBucket
 
class WordBucketTest extends FunSuite {
 
  test("empty bucket") {
    val bucket = WordBucket()
    expect(true) { bucket.valid("col1-4", "POO") }
    expect(true) { bucket.valid("row1-4", "POO") }
    expect(true) { bucket.valid("col1-4", "GUFF") }
  }
 
  test("with words") {
    val empty     = WordBucket()
    val newWords  = Map("col1-4" -> "POO", "row1-4" -> "WEE")
    val bucket = empty.merge(newWords)
    
    expect(false) { bucket.valid("col1-4", "POO") }
    expect(true) { bucket.valid("row1-4", "POO") }
    expect(true) { bucket.valid("col1-4", "GUFF") }
  }
}