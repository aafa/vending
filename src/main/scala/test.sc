import com.github.aafa.vending.diode._

val circuit = AppCircuit

val zoom = circuit.zoom(_.listOfCandies).value

circuit.zoom(_.coinsSlot).value
circuit(InsertCoin(CandyCoin(1)))
circuit(PurchaseCandy(zoom.head.candy))

circuit.zoom(_.listOfCandies).value
circuit.zoom(_.coinsSlot).value
