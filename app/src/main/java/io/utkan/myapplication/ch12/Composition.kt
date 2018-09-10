package io.utkan.myapplication.ch12

import arrow.syntax.function.andThen
import arrow.syntax.function.compose
import arrow.syntax.function.forwardCompose
import java.util.*

class Composition {

    val strong: (String) -> String = { body -> "<strong>$body</strong>" }

    val p: (String) -> String = { body -> "<p>$body</p>" }

    val span: (String) -> String = { body -> "<span>$body</span>" }

    val div: (String) -> String = { body -> "<div>$body</div>" }

    val randomNames: () -> String = {
        if (Random().nextInt() % 2 == 0) {
            "foo"
        } else {
            "bar"
        }
    }

    data class Quote(val value: Double, val client: String, val item: String, val quantity: Int)

    data class Bill(val value: Double, val client: String)

    data class PickingOrder(val item: String, val quantity: Int)

    fun calculatePrice(quote: Quote): Pair<Bill, PickingOrder> {
        return Bill(quote.value * quote.quantity, quote.client) to PickingOrder(quote.item, quote.quantity)
    }

    fun filterBills(billAndOrder: Pair<Bill, PickingOrder>): Pair<Bill, PickingOrder>? {
        val (bill, _) = billAndOrder
        return if (bill.value >= 100) {
            billAndOrder
        } else {
            null
        }
    }

    fun warehouse(order: PickingOrder): String {
        return "Processing order = $order"
    }

    fun accounting(bill: Bill): String {
        return "processing = $bill"
    }

    fun splitter(billAndOrder: Pair<Bill, PickingOrder>?): String {
        if (billAndOrder != null) {
            return warehouse(billAndOrder.second) + "\n" + accounting(billAndOrder.first)
        }
        return ""
    }

    fun channel(): String {
        val salesSystem: (quote: Quote) -> String = {
            (::calculatePrice andThen ::filterBills forwardCompose ::splitter).invoke(it)
        }
//        return salesSystem(Quote(20.0, "Foo", "Shoes", 1))
        return salesSystem(Quote(2000.0, "Foo", "Motorbike", 1))
    }

    fun main(): String {
        val divStrong: (String) -> String = div compose strong
        val spanP: (String) -> String = p forwardCompose span
        val randomStrong: () -> String = randomNames andThen strong

//        return divStrong("Hello composition world!")
//        return spanP("Hello composition world!")
        return randomStrong()
    }

}