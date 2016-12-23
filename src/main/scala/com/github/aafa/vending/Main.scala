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
    val insertCoin = button(onclick := { () => AppCircuit(InsertCoin(CandyCoin(1))) }, "InsertCoin")
    val candies = AppCircuit.zoom(_.listOfCandies).value
    val coinsSlot = AppCircuit.zoom(_.coinsSlot).value.value

    def renderListings = ul(
      candies map (c => div(
        button(onclick := { () => AppCircuit(PurchaseCandy(c.candy)) },
          s"Purchase ${c.candy.name}, only ${c.quantity} left")))
    ).render

    val output = renderListings.render

    targetNode.appendChild(
      div(
        h1("Free candies!"),
        p(
          "What candy do you want?"
        ),
        div(insertCoin),
        div(coinsSlot + " coins"),
        output
      ).render
    )

  }
}
