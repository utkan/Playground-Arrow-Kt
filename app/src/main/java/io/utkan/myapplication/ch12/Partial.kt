package io.utkan.myapplication.ch12

object Partials {
    val strong: (String, String, String) -> String = { body, id, color ->
        "<b id=\"$id\">" +
                "<font color=\"$color\">$body" +
                "</font></b>"
    }

    val simpleStrong: (String) -> String = { body ->
        "<b>" +
                "$body" +
                "</b>"
    }
}