package com.github.aafa.vending

import com.github.aafa.vending.diode._
import org.scalajs.dom.{Node, document}

import scala.scalajs.js.JSApp
import scalatags.JsDom.all._

object Main extends JSApp {
  AppCircuit.subscribe(AppCircuit.zoom(a => a))(_ => render())

  def main(): Unit = {
    render()
  }

  private def render() = {
    document.body.innerHTML = ""
    content(document.body)
  }

  def content(targetNode: Node): Unit = {
    val insertCoin = button(`class` := "insertCoin", onclick := { () => AppCircuit(InsertCoin(CandyCoin(1))) }, ">> Insert CandyCoin here <<")
    val candies = AppCircuit.zoom(_.listOfCandies).value
    val coinsSlot = AppCircuit.zoom(_.coinsSlot).value.value
    val getTheCandy = AppCircuit.zoom(_.getTheCandySlot).value.map(_.name)
    val info = AppCircuit.zoom(_.machineInfo).value

    targetNode.appendChild(
      div(`class` := "container",
        h2("Candies for everyone!"),
        div(info),
        div(insertCoin),
        div(coinsSlot + " coins"),
        ul(`class` := "candyList",
          candies map (c => div(
            button(`class` := "candy", onclick := { () => AppCircuit(PurchaseCandy(c.candy)) },
              s"Purchase ${c.candy.name} for ${c.price.value}cc, only ${c.quantity} left")))
        ),
        div(`class` := "footer",
          button(
            `class` := "get-candy-slot", onclick := { () => AppCircuit(CandyTakenFromSlot) },
            getTheCandy
          )
        )
      ).render
    )

  }
}
