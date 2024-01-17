package com.pro.managmentstudent.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.pro.managmentstudent.models.Lesson
import com.pro.managmentstudent.models.SelectionUnit
import com.pro.managmentstudent.models.Student

@Dao
interface Dao {

    @Insert
    fun insertLesson(lesson: Lesson)

    @Insert
    fun insertStudent(student: Student) : Long

    @Insert
    fun selectionUnit(selectionUnit: SelectionUnit)

    @Insert
    fun insertSelectionUnit(selectionUnit: SelectionUnit)

    // get information query
    @Query("SELECT * FROM lesson")
    fun getAllLesson(): List<Lesson>

    @Query("SELECT id FROM lesson")
    fun getAllLessonIds(): List<Int?>?

    @Query("SELECT id FROM student")
    fun getAllStudentIds(): List<Int?>?

    @Query("SELECT * FROM lesson WHERE id = :id")
    fun getLessonWithID(id : Int) : Lesson

    @Query("SELECT title FROM lesson WHERE id = :id")
    fun getTitleWithId(id: Int):String

    @Query("SELECT  student.id AS id ,student.firstname AS firstname, student.lastname AS lastname," +
            " student.gradeTerm AS gradeTerm," +
            " student.startDate AS startDate, " +
            " student.endDate AS endDate, " +
            " student.description AS description, " +
            " student.score AS score, " +
            " student.numberStudent AS numberStudent " +
            "FROM selectionunit " +
            "INNER JOIN student ON selectionunit.student_id = student.id where lesson_id = :id")
    fun getStudentWithSelectionUnit(id : Int): List<Student>

    @Query("SELECT student.id AS id, student.firstname AS firstname, student.lastname AS lastname, student.gradeTerm AS gradeTerm, student.startDate AS startDate, student.endDate AS endDate, student.description AS description, student.score AS score, student.numberStudent AS numberStudent " +
            "FROM selectionunit " +
            "INNER JOIN student ON selectionunit.student_id = student.id " +
            "WHERE (lesson_id = :lessonId AND student.firstname = :firstName) " +
            "OR (lesson_id = :lessonId AND student.lastname = :lastName)")
    fun getStudentsByLessonAndName(lessonId: Int, firstName: String, lastName: String): List<Student>


    // delete query
    @Transaction
    @Query("DELETE FROM student WHERE id = :lessonId")
    fun deleteStudents(lessonId: Int)

    @Query("DELETE FROM student WHERE id IN (SELECT student_id FROM selectionunit WHERE lesson_id = :lessonId)")
    fun deleteStudentsForLesson(lessonId: Int)

    @Query("DELETE FROM lesson WHERE id = :id")
    fun deleteLessonById(id: Int)

    // update query
    @Query("UPDATE lesson SET title = :newTitle WHERE id = :lessonId")
    fun updateLessonTitle(lessonId: Int, newTitle: String)

    @Query("UPDATE student SET firstname = :newFirstName, lastname = :newLastName, gradeTerm = :newGradeTerm, startDate = :newStartDate, endDate = :newEndDate, description = :newDescription, score = :newScore, numberStudent = :newNumberStudent WHERE id = :studentId")
    fun updateStudent(
        studentId: Int,
        newFirstName: String,
        newLastName: String,
        newGradeTerm: String,
        newStartDate: String,
        newEndDate: String,
        newDescription: String,
        newScore: Double,
        newNumberStudent: Int
    )


}