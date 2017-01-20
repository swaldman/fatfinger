package com.mchange.sc.v2

import scala.collection._

package object fatfinger {
  val AsciiPrintable = Stream.range(32, 127).map( _.toChar )

  def appendOneChar( putativePassword : String ) = for ( extra <- AsciiPrintable ) yield  putativePassword + extra

  trait Checker {
    def check( password : String ) : Boolean
  }
}

