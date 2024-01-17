package com.pro.managmentstudent.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
@kotlinx.parcelize.Parcelize
data class Student(
    val firstname: String,
    val lastname: String,
    val gradeTerm: String,
    val startDate: String,
    val endDate: String,
    val description: String,
    val score: String,
    val numberStudent: String,

) : Parcelable {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
