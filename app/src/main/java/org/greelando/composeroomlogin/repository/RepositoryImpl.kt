package org.greelando.composeroomlogin.repository

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.greelando.composeroomlogin.model.StudentEntity
import org.greelando.composeroomlogin.roomdao.StudentDao
import javax.inject.Inject


interface Repository {
    suspend fun insert(studentEntity: StudentEntity)

    suspend fun delete(studentEntity: StudentEntity)

    suspend fun update(studentEntity: StudentEntity)

    suspend fun getAllStudents(): Flow<List<StudentEntity>>
}
class RepositoryImpl @Inject constructor(
    private val studentDao: StudentDao,
): Repository {
    override suspend fun insert(studentEntity: StudentEntity) {
        withContext(IO) {
            studentDao.insert(studentEntity)
        }
    }

    override suspend fun delete(studentEntity: StudentEntity) {
        withContext(IO) {
            studentDao.delete(studentEntity)
        }
    }

    override suspend fun update(studentEntity: StudentEntity) {
        withContext(IO) {
            studentDao.update(studentEntity)
        }
    }

    override suspend fun getAllStudents(): Flow<List<StudentEntity>> {
        return withContext(IO) {
            studentDao.getAllStudents()
        }
    }

}