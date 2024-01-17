package com.pro.managmentstudent.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pro.managmentstudent.R
import com.pro.managmentstudent.models.Lesson
import com.pro.managmentstudent.models.Student

class MainAdapter( private val onViewClick: IOnViewClick) : RecyclerView.Adapter<MainViewHolder>() {

    private var listLesson : List<Lesson> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_main, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (listLesson.isEmpty()){
            0
        }else{
            listLesson.size
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        if (listLesson.isNotEmpty()){
            val currentItem = listLesson[position]
            holder.bind(currentItem)
        }


        holder.addImageView.setOnClickListener {
            onViewClick.onAddStudent(position)
        }

        holder.editImageView.setOnClickListener {
            onViewClick.onEdit(position)
        }

        holder.deleteImageView.setOnClickListener {
            onViewClick.onDelete(position)
        }
    }

    fun setLessonList(newLessonList: List<Lesson>) {
        listLesson = newLessonList
        notifyDataSetChanged()
    }

}