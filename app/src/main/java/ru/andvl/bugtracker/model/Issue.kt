package ru.andvl.bugtracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import ru.andvl.bugtracker.utils.DateConverter
import ru.andvl.bugtracker.utils.LabelConverter
import ru.andvl.bugtracker.utils.StatusConverter
import java.util.*

@Entity
//@TypeConverters(
//    DateConverter::class,
//    StatusConverter::class,
//    LabelConverter::class,
//)
data class Issue(
    @PrimaryKey
    @SerializedName("issueId")
    val id: Int,
    @SerializedName("name")
    val issueName: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("releaseVersion")
    val releaseVersion: String = "",
    @SerializedName("creationDate")
    val creationDate: Long = System.currentTimeMillis(),
    @SerializedName("deadline")
    val deadline: Long? = null,
    @SerializedName("statusId")
    val statusId: Int = Status.NEW.value,
    @SerializedName("labelId")
    val labelId: Int = Label.DOCS.value,
    @SerializedName("projectIssueNumber")
    val projectIssueNumber: Int,
    @SerializedName("projectId")
    val projectId: Int,
)
