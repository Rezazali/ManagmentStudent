package com.pro.managmentstudent.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pro.managmentstudent.R
import com.pro.managmentstudent.database.AppDatabase
import com.pro.managmentstudent.databinding.FragmentSearchBinding
import com.pro.managmentstudent.models.Student
import com.pro.managmentstudent.recyclerview.IOnClickSearch
import com.pro.managmentstudent.recyclerview.SearchAdapter
import com.pro.managmentstudent.recyclerview.StudentAdapter
import com.pro.managmentstudent.util.SessionManager

class SearchFragment : Fragment(), IOnClickSearch {

    lateinit var binding : FragmentSearchBinding

    var textSearch = ""

    lateinit var adapter : SearchAdapter

    lateinit var appDatabase: AppDatabase

    lateinit var sessionManager: SessionManager

     lateinit var students : List<Student>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)

        appDatabase = AppDatabase.getInstance(requireActivity())!!
        sessionManager = SessionManager(requireActivity())

        adapter = SearchAdapter(this)
        binding.recyclerSearch.adapter = adapter
        val layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerSearch.layoutManager = layoutManager

        // Set focus to the EditText
        binding.etSearchContacts.requestFocus()

        binding.etSearchContacts.postDelayed({
            binding.etSearchContacts.performClick()
            binding.etSearchContacts.requestFocus()
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput( binding.etSearchContacts, InputMethodManager.SHOW_IMPLICIT)
        }, 200)

        binding.etSearchContacts.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                textSearch = s.toString()
                performSearch(textSearch)
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (appDatabase.idao()!!.getStudentsByLessonAndName(sessionManager.fetchDbID(),textSearch,textSearch).isNotEmpty()){
            var listStudent = appDatabase.idao()!!. getStudentsByLessonAndName(
                sessionManager.fetchDbID(),textSearch,textSearch
            )
            adapter.setStudentList(listStudent)
        }
    }

    private fun performSearch(query: String) {
         students = appDatabase.idao()?.getStudentsByLessonAndName(
             sessionManager.fetchDbID(),
             query,
             query
         )!!

        if (!query.isBlank() && students != null && students.isNotEmpty()) {
            adapter.setStudentList(students)
        } else {
            // Handle the case when no students match the query or query is empty
            // You may want to clear the list or show a message in the UI
        }
    }

    override fun onClick(position: Int) {
        if (students.isNotEmpty()){
            sessionManager.deleteUpdateID()
            sessionManager.saveUpdateData(0)
            val myObjectData = students[position]
            val bundle = bundleOf("data" to myObjectData)
            findNavController().navigate(R.id.action_searchFragment_to_addStudentFragment, bundle)
        }

    }
}