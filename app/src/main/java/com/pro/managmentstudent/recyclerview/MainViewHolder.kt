package com.pro.managmentstudent.recyclerview

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.pro.managmentstudent.R
import com.pro.managmentstudent.database.AppDatabase
import com.pro.managmentstudent.models.Lesson

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val titleTextView: AppCompatTextView = itemView.findViewById(R.id.txt_title)
    private val nameTermTextView: AppCompatTextView = itemView.findViewById(R.id.txt_name_term)
    private val codeTextView: AppCompatTextView = itemView.findViewById(R.id.code)
    val deleteImageView: AppCompatImageView = itemView.findViewById(R.id.img_delete)
    val editImageView: AppCompatImageView = itemView.findViewById(R.id.img_edit)
    val addImageView: AppCompatImageView = itemView.findViewById(R.id.img_add)

    fun bind(data: Lesson) {
        titleTextView.text = data.title
        nameTermTextView.text = data.code.toString()
        codeTextView.text = data.code.toString()
    }
}
