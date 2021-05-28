package ru.andvl.bugtracker.model

enum class Status(val value: Int) {
    NEW(1),
    IN_PROGRESS(2),
    REVIEW(3),
    TESTING(4),
    READY(5),
    CLOSED(6),
    ERROR(1000)
}
