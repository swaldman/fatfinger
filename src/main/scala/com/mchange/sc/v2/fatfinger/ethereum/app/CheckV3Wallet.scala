package com.mchange.sc.v2.fatfinger.ethereum.app

import com.mchange.sc.v2.fatfinger._
import com.mchange.sc.v2.fatfinger.ethereum.WalletV3Checker

import com.mchange.sc.v1.consuela.ethereum.wallet
import com.mchange.sc.v2.lang.borrow
import com.mchange.sc.v2.util.Platform


import java.io.File
import scala.io.Source


object CheckV3Wallet {

  val (green, red, yellow, white, reset) = {
    if ( Platform.Current == Some( Platform.Windows ) ) {
      ("", "", "", "", "")
    } else {
      ( Console.GREEN, Console.RED, Console.YELLOW, Console.WHITE, Console.RESET )
    }
  }

  def main( argv : Array[String] ) : Unit = {
    if ( argv.length != 2 ) {
      Console.err.println( s"${yellow}java com.mchange.sc.v2.fatfinger.ethereum.app.CheckV3Wallet <wallet-json-file> <password-text-file>${reset}" )
    } else {
      val w = wallet.V3( new File( argv(0) ) )
      val checker = new WalletV3Checker( w )
      var done = false
      borrow( Source.fromFile( new File( argv(1) ) ) )( _.close ) { source =>
        source.getLines.map( _.trim ).foreach { password =>
          if (! done ) {
            if ( checker.check( password ) ) {
              Console.out.println( s"${white}Checking password '${password}'...${reset} ${green}Yes!${reset}" )
              done = true
            } else {
              Console.out.println( s"${white}Checking password '${password}'...${reset} ${red}No.${reset}" )
            }
          }
        }
      }
      if (! done) {
        Console.out.println( s"${red}The wallet in file '${argv(0)}' could not be decoded with any of the passwords provided in ${argv(1)}${reset}" )
      } else {
        Console.out.println( s"${green}Password search successful.${reset}" )
      }
    }
  }
}
