package com.github.aafa.vending.diode

import diode.{Action, _}

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Alex Afanasev
  */
object AppCircuit extends Circuit[RootModel] {
  override protected def initialModel: RootModel = RootModel()

  val coinsHandler = new ActionHandler(zoomRW(_.coinsSlot)((model: RootModel, coin: CandyCoin) => model.copy(coinsSlot = coin))) {
    override protected def handle = {
      case InsertCoin(coin) => updated(CandyCoin(value.value + coin.value))
      case CoinsSpent(coin) => updated(CandyCoin(value.value - coin.value))
    }
  }

  val listOfCandies = new ActionHandler(zoomRW(_.listOfCandies)((model: RootModel, stocks: Seq[CandyStock]) => model.copy(listOfCandies = stocks))) {
    override protected def handle = {
      case PurchaseCandy(candy, q) =>
        val coinsSlot = modelRW.root.value.coinsSlot
        val updatedValue: Seq[(CandyStock, Seq[diode.Action])] = value.map {
          case stock: CandyStock if stock.candy == candy =>
            val price = stock.price
            val canPurchase = coinsSlot.value >= price.value
            if (canPurchase) {
              (stock.buy(q), Seq(CoinsSpent(price), GetYourCandy(candy)))
            } else {
              (stock, Seq(NotEnoughCoins(coinsSlot, price)))
            }
          case s => (s, Seq(NoAction))
        }

        val candyStocks: Seq[CandyStock] = updatedValue.map(_._1)
        val actionResults = updatedValue.flatMap(_._2).map(a => Effect.action(a))
        val effectSet = new EffectSet(actionResults.head, actionResults.tail.toSet, implicitly[ExecutionContext])

        updated(candyStocks, effectSet)
    }
  }

  override protected def actionHandler = foldHandlers(
    coinsHandler,
    listOfCandies
  )

}

// model

case class RootModel(
                      listOfCandies: Seq[CandyStock] = SeedData.candies.map(c =>
                        CandyStock(Candy(c), 10, CandyCoin(1))
                      ),
                      coinsSlot: CandyCoin = CandyCoin(),
                      coinsInMyPocket: CandyCoin = CandyCoin(100)
                    )


case class Candy(name: String)

// simplify things let it be int for now
case class CandyCoin(value: Int = 0){
  override def toString: String = s"${value}cc"
}

case class CandyStock(candy: Candy, quantity: Int, price: CandyCoin) {
  def buy(i: Int): CandyStock = this.copy(candy, quantity - i, price)

  override def toString: String = s"$quantity '${candy.name}' for $price"
}


// actions

trait Action extends diode.Action

case class InsertCoin(c: CandyCoin) extends Action

case class CoinsSpent(c: CandyCoin) extends Action

case class PurchaseCandy(candy: Candy, quantity: Int = 1) extends Action

case class GetYourCandy(candy: Candy) extends Action

case class NoCandiesLeft(candy: Candy) extends Action

case class NotEnoughCoins(inMachine: CandyCoin, price: CandyCoin) extends Action

case class SupplyMoreCandies(candyStock: Seq[CandyStock]) extends Action

case object NoCoinsLeftInMyPocket extends Action

case class AddSomeCoinsToMyPocket(candyCoin: CandyCoin) extends Action

case class CandyPurchased(candyCoin: CandyStock) extends Action
