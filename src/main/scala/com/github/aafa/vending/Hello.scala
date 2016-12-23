package com.github.aafa.vending

import org.scalajs.dom
import org.scalajs.dom.{Node, document}
import org.scalajs.dom.html.Div

import scala.scalajs.js.JSApp
import scalatags.JsDom.all._

object Hello extends JSApp {
  def main(): Unit = {
    content(document.body)
  }

  def content(targetNode: Node): Unit = {
    val box = input(
      `type` := "text",
      placeholder := "Type in here"
    ).render

    val candies =
      ("Air Heads, Baby Ruth candy bar, Caramello, Dots, Eiffel Bon Bons, Fisher Milk Chocolate, " +
        "Good & Plenty, Hershey's Chocolate Bar, Ice Breakers gum, Jolly Ranchers, Kit Kat, Laffy Taffy, " +
        "M&M's, Nerds, Oreo Cookies, Payday candy bar, Quick Blast Sour Candy Spray, Red Vines licorice, " +
        "Skittles, Tootsie Roll, Unicorn Pop. Van Wyk Confections, Warheads, York and Zero Bars").split(", ")

    def renderListings = ul(
      for {
        c <- candies
        if c.toLowerCase.startsWith(
          box.value.toLowerCase
        )
      } yield li(c)
    ).render

    val output: Div = div(renderListings).render

    box.onkeyup = (e: dom.Event) => {
      output.innerHTML = ""
      if (renderListings.textContent.isEmpty) {
        output.appendChild(p("I don't have any candies for you :(").render)
      } else {
        output.appendChild(renderListings)
      }
    }

    targetNode.appendChild(
      div(
        h1("Free candies!"),
        p(
          "What candy do you want?"
        ),
        div(box),
        output
      ).render
    )

  }
}
