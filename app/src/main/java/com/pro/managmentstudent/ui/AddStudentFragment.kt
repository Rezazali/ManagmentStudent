package com.pro.managmentstudent.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.pro.managmentstudent.R
import com.pro.managmentstudent.database.AppDatabase
import com.pro.managmentstudent.databinding.FragmentAddStudentBinding
import com.pro.managmentstudent.models.SelectionUnit
import com.pro.managmentstudent.models.Student
import com.pro.managmentstudent.util.SessionManager

class AddStudentFragment : Fragment() {

    private val TAG = "AddStudentFragment"

    lateinit var binding: FragmentAddStudentBinding

    lateinit var appDatabase: AppDatabase
    lateinit var sessionManager: SessionManager

    var idStudent = 0
    var firstName = ""
    var lastName = ""
    var gradeTerm = ""
    var startDate = ""
    var endDate = ""
    var score = "0"
    var numberStudent = "0"
    var description = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddStudentBinding.inflate(layoutInflater)

        appDatabase = AppDatabase.getInstance(requireActivity())!!

        sessionManager = SessionManager(requireActivity())

        val receivedData: Student? = arguments?.getParcelable("data")

        if (receivedData != null) {
            setData(receivedData)
        }

        binding.firstNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                firstName = s?.replace(Regex("\\s+"), "").toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.lastNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                lastName = s?.replace(Regex("\\s+"), "").toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


        binding.gradeEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                gradeTerm = s?.replace(Regex("\\s+"), "").toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


        binding.startDateEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                startDate = s?.replace(Regex("\\s+"), "").toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


        binding.endDateEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                endDate = s?.replace(Regex("\\s+"), "").toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


        binding.scoreEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                score = s?.replace(Regex("\\s+"), "")!!.toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


        binding.numberStudentEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                numberStudent = s?.replace(Regex("\\s+"), "")!!.toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        description = binding.description.text.toString()


        binding.btnSubmit.setOnClickListener {

            when (sessionManager.fetchUpdateID()) {
                1 -> {
                    if (idStudent > 0){

                        val studentIdValue = idStudent

                        appDatabase.idao()?.updateStudent(
                            studentIdValue,
                            firstName,
                            lastName,
                            gradeTerm,
                            startDate,
                            endDate,
                            description,
                            score.toDouble(), // assuming `score` is a String that can be converted to Double
                            numberStudent.toInt()
                        )
                    }

                    backStack()
                }
                else -> {
                    val lessonId= sessionManager.fetchDbID()

                    val resultStudent = appDatabase.idao()!!.insertStudent(
                        Student(
                            firstName,lastName,gradeTerm,startDate,endDate,description,score,numberStudent
                        )
                    )

                    appDatabase.idao()!!.selectionUnit(
                        SelectionUnit(
                            resultStudent.toInt(),lessonId , score ,description
                        )
                    )

                    backStack()
                }
            }
        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (sessionManager.fetchUpdateID() == 0)
            invisibleView()
    }

    private fun setData(student: Student) {
        idStudent = student.id
        firstName = student.firstname
        lastName = student.lastname
        gradeTerm = student.gradeTerm
        startDate = student.gradeTerm
        endDate = student.endDate
        description= student.description
        score = student.score
        numberStudent = student.numberStudent
        binding.firstNameEditText.setText(student.firstname)
        binding.lastNameEditText.setText(student.lastname)
        binding.gradeEditText.setText(student.gradeTerm)
        binding.startDateEditText.setText(student.startDate)
        binding.endDateEditText.setText(student.endDate)
        binding.description.setText(student.description)
        binding.scoreEditText.setText(student.score)
        binding.numberStudentEditText.setText(student.numberStudent)
    }

    private fun NavController.safeNavigate(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
    }

    private fun backStack(){
        findNavController().safeNavigate(AddStudentFragmentDirections.actionAddStudentFragmentToStudentFragment(null))
    }

    private fun invisibleView(){
        binding.btnSubmit.visibility = View.GONE
        binding.firstNameEditText.isClickable = false
        binding.firstNameEditText.isFocusable = false
        binding.firstNameEditText.isFocusableInTouchMode = false
        binding.lastNameEditText.isClickable = false
        binding.lastNameEditText.isFocusable = false
        binding.lastNameEditText.isFocusableInTouchMode = false
        binding.gradeEditText.isClickable = false
        binding.gradeEditText.isFocusable = false
        binding.gradeEditText.isFocusableInTouchMode = false
        binding.startDateEditText.isClickable = false
        binding.startDateEditText.isFocusable = false
        binding.startDateEditText.isFocusableInTouchMode = false
        binding.endDateEditText.isClickable = false
        binding.endDateEditText.isFocusable = false
        binding.endDateEditText.isFocusableInTouchMode = false
        binding.description.isClickable = false
        binding.description.isFocusable = false
        binding.description.isFocusableInTouchMode = false
        binding.scoreEditText.isClickable = false
        binding.scoreEditText.isFocusable = false
        binding.scoreEditText.isFocusableInTouchMode = false
        binding.numberStudentEditText.isClickable = false
        binding.numberStudentEditText.isFocusable = false
        binding.numberStudentEditText.isFocusableInTouchMode = false
    }


}