package machine

// Exception Classes
internal class NotEnoughException(message: String) : Exception(message)

// CoffeeMachine Class
class CoffeeMachine(
    var water: Int,
    var milk: Int,
    var coffee: Int,
    var cup: Int,
    var money: Int
) {
    fun printState() {
        println("""
            The coffee machine has:
            $water ml of water
            $milk ml of milk
            $coffee g of coffee beans
            $cup disposable cups
            $$money of money
        """.trimIndent()
        )
    }
}

fun printState(mc: CoffeeMachine) {
    mc.printState()
    println()
}

fun fillUp(mc: CoffeeMachine, amount: Int, elem: String) {
    when (elem) {
        "water" -> mc.water += amount
        "milk" -> mc.milk += amount
        "coffee" -> mc.coffee += amount
        "cup" -> mc.cup += amount
        "money" -> mc.money += amount
    }
}

@Throws(NotEnoughException::class)
fun dispose(mc: CoffeeMachine, water: Int = 0, milk: Int = 0, coffee: Int = 0, cup: Int = 0) {
    if (water > mc.water) throw NotEnoughException(message = "Sorry, not enough water!")
    if (milk > mc.milk) throw NotEnoughException(message = "Sorry, not enough milk!")
    if (coffee > mc.coffee) throw NotEnoughException(message = "Sorry, not enough coffee!")
    if (cup > mc.cup) throw NotEnoughException(message = "Sorry, not enough cup!")
    mc.water = mc.water - water
    mc.milk = mc.milk - milk
    mc.coffee = mc.coffee - coffee
    mc.cup = mc.cup - cup
}

fun askAction(): String {
    println("Write action (buy, fill, take, remaining, exit): ")
    return readln()
}

fun buyActionFlow(mc: CoffeeMachine) {
    println()
    println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
    try {
        when (readln()) {
            "1" -> makeEspresso(mc)
            "2" -> makeLatte(mc)
            "3" -> makeCappuccino(mc)
            "back" -> println()
        }
    } catch (ex: NotEnoughException) {
        println(ex.message)
    }
    println()
}

fun makeEspresso(mc: CoffeeMachine) {
    dispose(mc, water= 250, coffee = 16, cup = 1)
    fillUp(mc, 4, "money")
    println("I have enough resources, making you a coffee!")
}

fun makeLatte(mc: CoffeeMachine) {
    dispose(mc, water= 350, milk = 75, coffee = 20, cup = 1)
    fillUp(mc, 7, "money")
    println("I have enough resources, making you a coffee!")
}

fun makeCappuccino(mc: CoffeeMachine) {
    dispose(mc, water= 200, milk = 100, coffee = 12, cup = 1)
    fillUp(mc, 6, "money")
    println("I have enough resources, making you a coffee!")
}

fun fillActionFlow(mc: CoffeeMachine) {

    println("Write how many ml of water you want to add:")
    fillUp(mc, readln().toInt(), "water")

    println("Write how many ml of milk you want to add:")
    fillUp(mc, readln().toInt(), "milk")

    println("Write how many grams of coffee beans you want to add:")
    fillUp(mc, readln().toInt(), "coffee")

    println("Write how many disposable cups you want to add:")
    fillUp(mc, readln().toInt(), "cup")

}

fun takeActionFlow(mc: CoffeeMachine) {
    println("I gave you ${mc.money}")
    mc.money = 0
}

fun main() {
    val mc = CoffeeMachine(water = 400, milk = 540, coffee = 120, cup = 9, money = 550)
    var action = askAction()
    println()

    while (action != "exit") {
        println()
        when (action) {
            "buy" -> buyActionFlow(mc)
            "fill" -> fillActionFlow(mc)
            "take" -> takeActionFlow(mc)
            "remaining" -> printState(mc)
        }
        action = askAction()
    }

}

