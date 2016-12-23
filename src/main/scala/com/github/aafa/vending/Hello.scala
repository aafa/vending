package com.github.aafa.vending

import org.scalajs.dom
import org.scalajs.dom.document

import scala.scalajs.js.JSApp

object Hello extends JSApp {
  def main(): Unit = {
    appendPar(document.body, "I don't have any candies for you :(")
  }

  def appendPar(targetNode: dom.Node, text: String): Unit = {
    val parNode = document.createElement("p")
    val textNode = document.createTextNode(text)
    parNode.appendChild(textNode)
    targetNode.appendChild(parNode)
  }
}
