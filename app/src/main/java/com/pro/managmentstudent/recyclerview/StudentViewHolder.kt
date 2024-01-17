package com.pro.managmentstudent.recyclerview

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.pro.managmentstudent.R
import com.pro.managmentstudent.database.AppDatabase
import com.pro.managmentstudent.models.Lesson
import com.pro.managmentstudent.models.Student

class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val firstName: AppCompatTextView = itemView.findViewById(R.id.first_name)
    private val lastName: AppCompatTextView = itemView.findViewById(R.id.last_name)
    private val score: AppCompatTextView = itemView.findViewById(R.id.score)
    private val numberStudent: AppCompatTextView = itemView.findViewById(R.id.number_student)
    private val gradeTerm: AppCompatTextView = itemView.findViewById(R.id.grade_term)
    val deleteImageView: AppCompatImageView = itemView.findViewById(R.id.img_delete)
    val editImageView: AppCompatImageView = itemView.findViewById(R.id.img_edit)
    val visibilityImageView: AppCompatImageView = itemView.findViewById(R.id.img_visibility)

    fun bind(data: Student) {
        firstName.text = data.firstname
        lastName.text = data.lastname
        score.text = data.score.toString()
        numberStudent.text = data.numberStudent.toString()
        gradeTerm.text = data.gradeTerm
        numberStudent.text = data.numberStudent.toString()
    }
}