package io.utkan.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.widget.TextView
import io.utkan.myapplication.ch12.Composition

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val ex1 = Composition().main()
        val info = findViewById<TextView>(R.id.info)
        info.text = Html.fromHtml(ex1)
        info.text = Composition().channel()
    }
}
