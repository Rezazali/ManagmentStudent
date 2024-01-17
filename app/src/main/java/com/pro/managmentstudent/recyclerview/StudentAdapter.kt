package com.pro.managmentstudent.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pro.managmentstudent.R
import com.pro.managmentstudent.models.Student

class StudentAdapter(private val iOnItemStudentClick: IOnItemStudentClick) : RecyclerView.Adapter<StudentViewHolder>() {

    private var students: List<Student> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (students.isEmpty()){
            0
        }else{
            students.size
        }
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        if (students.isNotEmpty()){
            val currentItem = students[position]
            holder.bind(currentItem)
        }

        holder.editImageView.setOnClickListener {
            iOnItemStudentClick.onEditClick(position)
        }

        holder.visibilityImageView.setOnClickListener {
            iOnItemStudentClick.onVisibilityClick(position)
        }

        holder.deleteImageView.setOnClickListener {
            iOnItemStudentClick.onDeleteClick(position)
        }
    }

    fun setStudentList(newStudentList: List<Student>) {
        students = newStudentList
        notifyDataSetChanged()
    }
}
