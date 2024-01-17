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
import com.pro.managmentstudent.databinding.FragmentMainBinding
import com.pro.managmentstudent.recyclerview.MainAdapter
import com.pro.managmentstudent.recyclerview.IOnViewClick
import com.pro.managmentstudent.util.SessionManager


class MainFragment : Fragment() , IOnViewClick {

    private  val TAG = "MainFragment"

    lateinit var binding : FragmentMainBinding

    lateinit var sessionManager: SessionManager

    lateinit var appDatabase: AppDatabase

    lateinit var adapter : MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)

        sessionManager = SessionManager(requireActivity())

        appDatabase = AppDatabase.getInstance(requireActivity())!!

        binding.flbButton.setOnClickListener {
            findNavController().safeNavigate(MainFragmentDirections.actionMainFragmentToLessonFragment2())
        }


        adapter = MainAdapter(this)
        binding.recyclerMain.adapter = adapter
        val layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
        binding.recyclerMain.layoutManager = layoutManager

        return binding.root
    }

    fun NavController.safeNavigate(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
    }

    override fun onResume() {
        super.onResume()
        if (appDatabase.idao()!!.getAllLesson().isNotEmpty()){
            adapter.setLessonList(appDatabase.idao()!!.getAllLesson())
        }
    }

    override fun onAddStudent(position: Int) {
        val listLessonId = appDatabase.idao()!!.getAllLessonIds()
        val lessonId = listLessonId?.get(position)

        sessionManager.deleteDbID()
        sessionManager.deleteTittle()

        if (lessonId != null) {
            sessionManager.saveDbID(lessonId)
            val title = appDatabase.idao()!!.getTitleWithId(lessonId)
            sessionManager.saveTitle(title)
        }
        findNavController().safeNavigate(MainFragmentDirections.actionMainFragmentToStudentFragment())
    }

    override fun onEdit(position: Int) {
        val listLessonId = appDatabase.idao()!!.getAllLessonIds()
        val lessonId = listLessonId?.get(position)
        sessionManager.deleteUpdateID()
        sessionManager.saveUpdateData(1)
        if (lessonId != null) {
            sendData(lessonId)
        }
    }

    override fun onDelete(position: Int) {
        val listId = appDatabase.idao()!!.getAllLessonIds()
        val idStudent = listId?.getOrNull(position)
        if (idStudent != null) {
            appDatabase.idao()!!.deleteStudentsForLesson(idStudent)
            appDatabase.idao()!!.deleteLessonById(idStudent)
            val updatedList = appDatabase.idao()!!.getAllLesson()
            adapter.setLessonList(updatedList)
        }
    }

    private fun sendData(lessonId : Int){
        val lesson = lessonId.let { appDatabase.idao()!!.getLessonWithID(it) }
        val bundle = bundleOf("data" to lesson)
        findNavController().navigate(R.id.action_mainFragment_to_lessonFragment2, bundle)
    }
}