package com.pro.managmentstudent.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.pro.managmentstudent.R
import com.pro.managmentstudent.database.AppDatabase
import com.pro.managmentstudent.databinding.FragmentLessonBinding
import com.pro.managmentstudent.models.Lesson
import com.pro.managmentstudent.models.Student
import com.pro.managmentstudent.util.SessionManager


class LessonFragment : Fragment() {

    private val TAG = "LessonFragment"

    lateinit var binding : FragmentLessonBinding

    lateinit var appDatabase: AppDatabase

    lateinit var sessionManager: SessionManager

    var nameLesson = ""

    var code = ""

    var idLesson = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLessonBinding.inflate(layoutInflater)

        val languages = resources.getStringArray(R.array.Languages)

        sessionManager = SessionManager(requireActivity())

        appDatabase = AppDatabase.getInstance(requireActivity())!!
        val receivedData: Lesson? = arguments?.getParcelable("data")

        if (receivedData != null) {
            setData(receivedData)
        }

        val adapter = ArrayAdapter(requireActivity(),
            R.layout.dropdwon_item, languages)
        binding.autoCompleteTextView.setAdapter(adapter)


        binding.autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            nameLesson = adapter.getItem(


                0).toString()
            nameLesson = adapter.getItem(position).toString()
        }


        binding.codeEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                code = s?.replace(Regex("\\s+"), "").toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.btnSubmit.setOnClickListener {
            when (sessionManager.fetchUpdateID()) {
                1 -> {
                    //update data
                    appDatabase.idao()!!.updateLessonTitle(
                        idLesson,nameLesson
                    )
                }
                else ->{
                    appDatabase.idao()!!.insertLesson(
                        Lesson(
                            nameLesson,
                            code.toInt()
                        )
                    )
                    findNavController().safeNavigate(LessonFragmentDirections.actionLessonFragment2ToMainFragment())
                }
            }

        }

        return binding.root
    }


    fun NavController.safeNavigate(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
    }

    private fun setData(lesson: Lesson){
        idLesson = lesson.id
        nameLesson = lesson.title
    }
}