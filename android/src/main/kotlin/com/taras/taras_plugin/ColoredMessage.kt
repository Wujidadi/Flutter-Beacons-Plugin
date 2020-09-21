package com.taras.taras_plugin

fun coloredMessage(message: String, color: String = "bright green"): String {

    val colorString: String = color.toLowerCase()

    val head: String = when (colorString) {

        /*
        |--------------------------------------------------
        | ANSI 基本 8 色
        |--------------------------------------------------
        */

        /* 黑色 */
        "black" -> {
            "\u001B[30m"
        }
        /* 紅色 */
        "red",
        "dark red",
        "darker red" -> {
            "\u001B[31m"
        }
        /* 綠色 */
        "green" -> {
            "\u001B[32m"
        }
        /* 黃色 */
        "yellow",
        "dark yellow",
        "darker yellow" -> {
            "\u001B[33m"
        }
        /* 藍色 */
        "blue",
        "dark blue",
        "darker blue" -> {
            "\u001B[34m"
        }
        /* 品紅色 */
        "magenta",
        "dark magenta",
        "darker magenta",
        "purple" -> {
            "\u001B[35m"
        }
        /* 青色 */
        "cyan",
        "dark cyan",
        "darker cyan" -> {
            "\u001B[36m"
        }
        /* 白色 */
        "bright gray",
        "light gray",
        "lighter gray",
        "bright grey",
        "light grey",
        "lighter grey",
        "dark white",
        "darker white" -> {
            "\u001B[37m"
        }

        /*
        |--------------------------------------------------
        | ANSI 基本 8 色 - 亮色（粗體）
        |--------------------------------------------------
        */

        /* 亮黑色（灰色） */
        "gray",
        "dark gray",
        "darker gray",
        "grey",
        "dark grey",
        "darker grey",
        "bright black",
        "light black",
        "lighter black" -> {
            "\u001B[90m"
        }
        /* 亮紅色 */
        "bright red",
        "light red",
        "lighter red" -> {
            "\u001B[91m"
        }
        /* 亮綠色 */
        "bright green",
        "light green",
        "lighter green" -> {
            "\u001B[92m"
        }
        /* 亮黃色 */
        "bright yellow",
        "light yellow",
        "lighter yellow" -> {
            "\u001B[93m"
        }
        /* 亮藍色 */
        "bright blue",
        "light blue",
        "lighter blue" -> {
            "\u001B[94m"
        }
        /* 亮品紅色 */
        "bright magenta",
        "light magenta",
        "lighter magenta",
        "pink" -> {
            "\u001B[95m"
        }
        /* 亮青色 */
        "bright cyan",
        "light cyan",
        "lighter cyan" -> {
            "\u001B[96m"
        }
        /* 亮白色 */
        "white",
        "bright white",
        "light white",
        "lighter white" -> {
            "\u001B[97m"
        }

        /*
        |--------------------------------------------------
        | ANSI 256 色
        |--------------------------------------------------
        */

        /* 深綠色 */
        "dark green",
        "darker green" -> {
            "\u001B[38;5;34m"
        }
        /* 深青色 */
        "teal" -> {
            "\u001B[38;5;30m"
        }
        /* 粉紅色 */
        "bright pink",
        "light pink",
        "lighter pink" -> {
            "\u001B[38;5;219m"
        }
        /* 金色 */
        "gold" -> {
            "\u001B[38;5;220m"
        }

        /*
        |--------------------------------------------------
        | 預設顏色
        |--------------------------------------------------
        */

        else -> {
            "\u001B[93m"
        }
    }

    val tail: String = "\u001B[0m"

    val finalMessage: String = head + message + tail

    return finalMessage
}
