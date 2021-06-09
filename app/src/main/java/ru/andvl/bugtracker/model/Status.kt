package ru.andvl.bugtracker.model

enum class Status(val value: Int) {
    NEW(1),
    IN_PROGRESS(2),
    REVIEW(3),
    TESTING(4),
    READY(5),
    CLOSED(6),
    ERROR(1000);
}

fun Status.toInt(): Int = when(this) {
    Status.NEW -> 1
    Status.IN_PROGRESS -> 2
    Status.REVIEW -> 3
    Status.TESTING -> 4
    Status.READY -> 5
    Status.CLOSED -> 6
    Status.ERROR -> 1000
}

fun Int.toStatus(): Status = when(this) {
    1 -> Status.NEW
    2 -> Status.IN_PROGRESS
    3 -> Status.REVIEW
    4 -> Status.TESTING
    5 -> Status.READY
    6 -> Status.CLOSED
    else -> Status.ERROR
}
