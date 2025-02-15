package org.greelando.composeroomlogin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.greelando.composeroomlogin.model.StudentEntity
import org.greelando.composeroomlogin.repository.Repository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {
    private val _studentDetailsList = MutableStateFlow(emptyList<StudentEntity>())
    val studentDetailsList = _studentDetailsList.asStateFlow()

    fun getStudentDetails() {
        viewModelScope.launch(IO) {
            repository.getAllStudents().collectLatest {
                _studentDetailsList.tryEmit(it)
            }
        }
    }

    fun updateStudent(studentEntity: StudentEntity) {
        viewModelScope.launch(IO) {
            repository.update(studentEntity)
        }
    }

    fun deleteStudent(studentEntity: StudentEntity) {
        viewModelScope.launch(IO) {
            repository.delete(studentEntity)
        }
    }

    fun insertStudent(studentEntity: StudentEntity) {
        viewModelScope.launch(IO) {
            repository.insert(studentEntity)
        }
    }

    private val _studentName = MutableStateFlow("")
    val studentName = _studentName.asStateFlow()
    fun setStudentName(name: String) {
        _studentName.tryEmit(name)
    }



    private val _studentRollNo = MutableStateFlow("")
    val studentRollNo = _studentRollNo.asStateFlow()
    fun setStudentRollNo(roll: String) {
        _studentRollNo.tryEmit(roll)
    }



    private val _isChecked = MutableStateFlow(false)
    val isChecked = _isChecked.asStateFlow()
    fun setChecked(bool: Boolean) {
        _isChecked.tryEmit(bool)
    }
}