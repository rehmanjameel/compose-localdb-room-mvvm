package org.greelando.composeroomlogin.roomdao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import org.greelando.composeroomlogin.model.StudentEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(studentEntity: StudentEntity)

    @Delete
    suspend fun delete(studentEntity: StudentEntity)

    @Update
    suspend fun update(studentEntity: StudentEntity)

    @Query("Select * from StudentEntity")
    fun getAllStudents(): Flow<List<StudentEntity>>
}