package ru.andvl.bugtracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/** Represents project with its [id], [name] and [description]
 * @property id - project id in database
 * @property name - project name
 * @property description - project description
 * */
@Entity
data class Project(
    @PrimaryKey
    @SerializedName("projectId")
    val id: Int,
    @SerializedName("projectName")
    val name: String,
    @SerializedName("projectDescription")
    val description: String
)
