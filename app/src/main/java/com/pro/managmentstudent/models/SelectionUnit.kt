package com.pro.managmentstudent.models
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "SelectionUnit",
    foreignKeys = [
        ForeignKey(
            entity = Student::class,
            parentColumns = ["id"],
            childColumns = ["student_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Lesson::class,
            parentColumns = ["id"],
            childColumns = ["lesson_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@Parcelize
data class SelectionUnit(
    val student_id: Int,
    val lesson_id: Int,
    val rate: String,
    val description: String
) : Parcelable {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
