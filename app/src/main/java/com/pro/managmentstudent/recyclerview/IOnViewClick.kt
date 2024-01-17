package com.pro.managmentstudent.recyclerview

interface IOnViewClick {
    fun onAddStudent(position : Int)
    fun onEdit(position : Int)
    fun onDelete(position : Int)
}