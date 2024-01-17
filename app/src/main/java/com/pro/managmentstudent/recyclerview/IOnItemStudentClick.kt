package com.pro.managmentstudent.recyclerview

interface IOnItemStudentClick {

    fun onVisibilityClick(position : Int)

    fun onEditClick(position : Int)

    fun onDeleteClick(position : Int)
}