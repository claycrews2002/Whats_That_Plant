package com.example.whatsthatplant.formatters

import kotlin.math.roundToInt


fun formatTwoItems(items: List<String>?): String {
    if (items != null) {
        var retString = items[0]
        if (items.size > 1) {
            retString += ", ${items[1]}"
        }
        return retString
    }
    return "N/A"
}

fun formatString(name: String?): String {
    if (name != null) {
        return name
    }
    return "N/A"
}

fun formatProb(prob: Double?): String {
    if (prob != null){
        val roundedValue = (prob * 10.0).roundToInt() / 10.0
        val formattedValue = String.format("%.1f", roundedValue * 100.0)
        return "$formattedValue%"
    }
    return "N/A"
}
