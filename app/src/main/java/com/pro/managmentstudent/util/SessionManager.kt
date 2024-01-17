package com.pro.managmentstudent.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private var prefs: SharedPreferences = context.getSharedPreferences("token",Context.MODE_PRIVATE)
    val editor = prefs.edit()
    companion object {
        const val DB_ID = "id_db"
        const val TITTLE = "toolbar_title"
        const val UPDATE_DATA = "update_data"
    }

    /**
     * Function to save auth token
     */
    fun saveDbID(id: Int) {
        editor.putInt(DB_ID, id)
        editor.apply()
    }

    fun saveTitle(title: String) {
        editor.putString(TITTLE, title)
        editor.apply()
    }

    fun saveUpdateData(update: Int) {
        editor.putInt(UPDATE_DATA, update)
        editor.apply()
    }



    /**
     * Function to fetch auth token
     */
    fun fetchDbID(): Int {
        return prefs.getInt(DB_ID, 5)
    }

    fun fetchUpdateID(): Int {
        return prefs.getInt(UPDATE_DATA, 5)
    }


    fun fetchTitle(): String? {
        return prefs.getString(TITTLE, "درس")
    }

    fun deleteTittle(){
        editor.remove(TITTLE).apply()
    }

    fun deleteDbID(){
        editor.remove(DB_ID).apply()
    }

    fun deleteUpdateID(){
        editor.remove(UPDATE_DATA).apply()
    }


}