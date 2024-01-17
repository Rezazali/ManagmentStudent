package com.pro.managmentstudent.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pro.managmentstudent.R
import com.pro.managmentstudent.models.Student

class SearchAdapter(private val onClickSearch: IOnClickSearch) : RecyclerView.Adapter<SearchViewHolder>() {

    var listStudent : List<Student> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_search, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (listStudent.isEmpty()){
            0
        }else{
            listStudent.size
        }
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        if (listStudent.isNotEmpty()){
            val currentItem = listStudent[position]
            holder.bind(currentItem)

            holder.itemView.setOnClickListener {
                onClickSearch.onClick(position)
            }
        }
    }

    fun setStudentList(newStudentList: List<Student>) {
        listStudent = newStudentList
        notifyDataSetChanged()
    }
}