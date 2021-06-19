package ru.andvl.bugtracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

/** Represents project with its [id], [name] and [description]
 * @property id - project id in database
 * @property name - project name
 * @property description - project description
 * */
@Entity
data class Project(
    @PrimaryKey
    @field:Json(name = "projectId")
    val id: Int,
    @field:Json(name = "projectName")
    val name: String,
    @field:Json(name = "projectDescription")
    val description: String,
    @field:Json(name = "issuesCount")
    val issuesCount: Int = 0,
)
