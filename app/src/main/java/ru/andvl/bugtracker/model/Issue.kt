package ru.andvl.bugtracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import ru.andvl.bugtracker.utils.AssigneeConverter

@Entity
@TypeConverters(
//    DateConverter::class,
//    StatusConverter::class,
//    LabelConverter::class,
//    AssigneeConverter::class
)
data class Issue(
    @PrimaryKey
    @field:Json(name = "issueId")
    val id: Int,
    @field:Json(name = "name")
    val issueName: String,
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "releaseVersion")
    val releaseVersion: String = "",
    @field:Json(name = "creationDate")
    val creationDate: Long = System.currentTimeMillis(),
    @field:Json(name = "deadline")
    val deadline: Long? = null,
    @field:Json(name = "statusId")
    val statusId: Int = Status.NEW.value,
    @field:Json(name = "labelId")
    val labelId: Int = Label.DOCS.value,
    @field:Json(name = "projectIssueNumber")
    val projectIssueNumber: Int = 0,
    @field:Json(name = "projectId")
    val projectId: Int,
    @field:Json(name = "assigneeId")
    val assigneeId: Int? = null,
    @field:Json(name = "authorId")
    val authorId: Int,
) {
    override fun toString(): String {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(Issue::class.java)
        return jsonAdapter.toJson(this)
    }

}

data class AssigneeID (
    @field:Json(name = "Int32")
    val value: Int,

    @field:Json(name = "Valid")
    val valid: Boolean
)
