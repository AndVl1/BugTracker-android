package ru.andvl.bugtracker.model

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("commentId")
    val id: Int,
    @SerializedName("author")
    val author: User,
    @SerializedName("text")
    val text: String,
    @SerializedName("date")
    val publicationDate: Long,
)
