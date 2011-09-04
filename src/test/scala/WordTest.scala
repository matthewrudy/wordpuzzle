package com.matthewrudy.scala.wordpuzzle.tests
 
import org.scalatest.FunSuite
import com.matthewrudy.scala.wordpuzzle.Word
 
class WordTest extends FunSuite {
 
  test("AAH is a word") {
    expect(true) { Word.isWord("AAH") }
  }
 
  test("ZYME is a word") {
    expect(true) { Word.isWord("ZYME") }
  }
  
  test("JAD is not a word") {
    expect(false) { Word.isWord("JAD") }
  }
  
  test("JAR is a word") {
    expect(true) { Word.isWord("JAR") }
  }
  
  test("JARS is a word") {
    expect(true) { Word.isWord("JARS") }
  }
  
  test("JAR scores 30") {
    expect(30) { Word.score("JAR") }
  }
  
  test("JARS scores 44") {
    expect(44) { Word.score("JARS") }
  }
  
}