package com.pro.managmentstudent.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lesson")
@kotlinx.parcelize.Parcelize
data class Lesson(
    val title: String,
    val code: Int
) : Parcelable {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
