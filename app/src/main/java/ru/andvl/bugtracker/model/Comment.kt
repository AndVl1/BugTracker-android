package ru.andvl.bugtracker.model

import com.squareup.moshi.Json

data class Comment(
    @field:Json(name = "commentId")
    val id: Int,
    @field:Json(name = "author")
    val author: User,
    @field:Json(name = "text")
    val text: String,
    @field:Json(name = "date")
    val publicationDate: Long,
)
