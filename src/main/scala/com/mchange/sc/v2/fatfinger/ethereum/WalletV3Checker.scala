package com.mchange.sc.v2.fatfinger.ethereum

import com.mchange.sc.v2.fatfinger.Checker
import com.mchange.sc.v1.consuela.ethereum.wallet

class WalletV3Checker( w : wallet.V3 ) extends Checker {
  def check( password : String ) : Boolean = {
    try {
      wallet.V3.decodePrivateKey( w, password )
      true
    } catch {
      case e : wallet.V3.BadDecodeException => false
      case t : Throwable => {
        t.printStackTrace
        throw t
      }
    }
  }
}
