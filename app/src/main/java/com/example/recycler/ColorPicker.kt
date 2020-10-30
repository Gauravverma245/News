package com.example.recycler

object ColorPicker{
    val colors = arrayOf("#FFFACD", "#FFFACD", "#FFD700", "#FFE4C4", "#FFA07A", "#CD5C5C", "#FFF0F5")
    var colorIndex = 1;

    fun getColor():String{
        return colors[colorIndex++ % colors.size]
    }

}