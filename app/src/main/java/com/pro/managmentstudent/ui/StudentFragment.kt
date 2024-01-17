package com.pro.managmentstudent.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pro.managmentstudent.R
import com.pro.managmentstudent.database.AppDatabase
import com.pro.managmentstudent.databinding.FragmentStudentBinding
import com.pro.managmentstudent.recyclerview.IOnItemStudentClick
import com.pro.managmentstudent.recyclerview.StudentAdapter
import com.pro.managmentstudent.util.SessionManager


class StudentFragment : Fragment(), IOnItemStudentClick {

    lateinit var binding: FragmentStudentBinding
    lateinit var appDatabase: AppDatabase
    lateinit var sessionManager: SessionManager
    lateinit var adapter : StudentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentStudentBinding.inflate(layoutInflater)

        appDatabase = AppDatabase.getInstance(requireActivity())!!
        sessionManager = SessionManager(requireActivity())

        binding.flbButton.setOnClickListener {
            sessionManager.deleteUpdateID()
            findNavController().safeNavigate(StudentFragmentDirections.actionStudentFragmentToAddStudentFragment())
        }
        binding.ivSearchIcon.setOnClickListener {
            findNavController().safeNavigate(StudentFragmentDirections.actionStudentFragmentToSearchFragment())
        }

        adapter = StudentAdapter(this)
        val layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        binding.recyclerStudent.layoutManager = layoutManager
        binding.recyclerStudent.adapter = adapter

        binding.txtTitle.text = sessionManager.fetchTitle()

        return binding.root
    }


    private fun NavController.safeNavigate(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
    }



    override fun onResume() {
        super.onResume()

        if (appDatabase.idao()!!.getStudentWithSelectionUnit(sessionManager.fetchDbID()).isNotEmpty()){
            val listStudent= appDatabase.idao()!!.getStudentWithSelectionUnit(sessionManager.fetchDbID())
            adapter.setStudentList(listStudent)
        }

    }

    override fun onVisibilityClick(position: Int) {
        sessionManager.deleteUpdateID()
        sessionManager.saveUpdateData(0)
        sendData(position)
    }

    override fun onEditClick(position: Int) {
        sessionManager.deleteUpdateID()
        sessionManager.saveUpdateData(1)
        sendData(position)
    }

    override fun onDeleteClick(position: Int) {
        val listId = appDatabase.idao()!!.getStudentWithSelectionUnit(sessionManager.fetchDbID()).map { it.id }
        val idStudent = listId.getOrNull(position)
        if (idStudent != null) {
            appDatabase.idao()!!.deleteStudents(idStudent)
            val updatedList = appDatabase.idao()!!.getStudentWithSelectionUnit(sessionManager.fetchDbID())
            adapter.setStudentList(updatedList)
        }
    }

    private fun sendData(position: Int){
        val myData = appDatabase.idao()!!.getStudentWithSelectionUnit(sessionManager.fetchDbID())
        val myObjectData = myData[position]
        val bundle = bundleOf("data" to myObjectData)
        findNavController().navigate(R.id.action_studentFragment_to_addStudentFragment, bundle)
    }

}
