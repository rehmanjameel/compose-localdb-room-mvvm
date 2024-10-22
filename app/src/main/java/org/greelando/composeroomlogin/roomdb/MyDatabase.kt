package org.greelando.composeroomlogin.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import org.greelando.composeroomlogin.model.StudentEntity
import org.greelando.composeroomlogin.roomdao.StudentDao

@Database(entities = [StudentEntity::class], version = 1)
abstract class MyDatabase: RoomDatabase() {
        abstract val dao: StudentDao
}