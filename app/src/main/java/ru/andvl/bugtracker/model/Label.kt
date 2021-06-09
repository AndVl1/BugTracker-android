package ru.andvl.bugtracker.model

enum class Label(val value: Int) {
    DB(1), // "DB"
    INTERFACE(2), // "Interface"
    DOCS(3), // "Docs"
    ERROR(1000)
}

fun Label.toInt(): Int = when(this) {
    Label.DB -> 1
    Label.INTERFACE -> 2
    Label.DOCS -> 3
    Label.ERROR -> 1000
}

fun Int.toLabel(): Label = when(this) {
    1 -> Label.DB
    2 -> Label.INTERFACE
    3 -> Label.DOCS
    else -> Label.ERROR
}
