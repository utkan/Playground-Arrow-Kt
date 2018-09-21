package io.utkan.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.widget.TextView
import arrow.core.Predicate
import arrow.syntax.function.*
import io.utkan.myapplication.ch12.Partials

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val info = findViewById<TextView>(R.id.info)
        //<editor-fold desc="Composition">
//        val ex1 = Composition().main()
//        info.text = Html.fromHtml(ex1)
//        info.text = Composition().channel()
        //</editor-fold>


        //<editor-fold desc="Partial application">
        // Explicit
        val newLine = "<\\br>"
        val htmlLine: (String) -> String = { it + newLine }
        val redStrong: (String, String) -> String = Partials.strong.partially3("red")
        val blueStrong: (String, String) -> String = Partials.strong.partially3("blue")
        val greenStrong: (String, String) -> String = Partials.strong.partially3("green")

        info.setHtml("Red Sonja", "movie1", redStrong)
//        info.setHtml(redStrong("Red Sonja", "movie1"))
        val bind = htmlLine.bind("<p>binded</p>")
        bind()
        info.appendHtml("<\\br>")
        info.appendHtml(blueStrong("Blue Sonja", "movie2"))
        info.appendHtml("<\\br>")
        info.appendHtml(greenStrong.reverse()("movie3", "Green Sonja"))
        info.appendHtml("<\\br>")

        val redStrongPiped: (String, String) -> String = "red" pipe3 Partials.strong.reverse()
        info.appendHtml(redStrongPiped("movie4", "Red Sonja Piped"))
        info.appendHtml("<\\br>")
        info.appendHtml("From a pipe".pipe(Partials.simpleStrong))
        info.appendHtml("<\\br>")
        //</editor-fold>

        val curriedStrong: (color: String) -> (id: String) -> (body: String) -> String = Partials.strong.reverse().curried()

        val greenCurriedStrong: (id: String) -> (body: String) -> String = curriedStrong("green")
        info.appendHtml(greenCurriedStrong("movie5")("Green Inferno"))
        info.appendHtml("<\\br>")
        info.appendHtml(Partials.strong("The Dark Knight")("movie6")("black"))
        info.appendHtml("<\\br>")

        //<editor-fold desc="logical complement">
        val evenPredicate: Predicate<Int> = { i: Int -> i % 2 == 0 }
        val oddPredicate: (Int) -> Boolean = evenPredicate.complement()

        val numbers: IntRange = 1..10
        val evenNumbers:List<Int> = numbers.filter(evenPredicate)
        val oddNumbers:List<Int> = numbers.filter(oddPredicate)

        info.append(evenNumbers.toString())
        info.appendHtml("<\\br>")
        info.append(oddNumbers.toString())
        //</editor-fold>


    }

    private fun TextView.setHtmlText(html: String) {
        text = Html.fromHtml(html)
    }

    private fun TextView.setHtml(
            p1: String,
            p2: String,
            html: (String, String) -> String) {
        setHtmlText(html(p1, p2))
    }

    private fun TextView.appendHtml(html: String) {
        append(Html.fromHtml(html))
    }
}
