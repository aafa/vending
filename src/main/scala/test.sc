import com.github.aafa.vending.diode._
import diode.ModelRO

val circuit = AppCircuit

AppCircuit.subscribe(AppCircuit.zoom(a => a))(a => render(a))
AppCircuit.subscribe(AppCircuit.zoom(_.coinsSlot))(a =>
  println("inserted coins " + a.value)
)

def render(rootModel: ModelRO[RootModel]) = {
  println(rootModel.value)
}

val zoom = circuit.zoom(_.listOfCandies).value

circuit.zoom(_.coinsSlot).value
circuit(InsertCoin(CandyCoin(1)))
circuit(InsertCoin(CandyCoin(1)))

circuit(CoinsSpent(CandyCoin(1)))

circuit(PurchaseCandy(zoom.head.candy))



