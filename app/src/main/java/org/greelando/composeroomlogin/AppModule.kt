package org.greelando.composeroomlogin

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.greelando.composeroomlogin.repository.Repository
import org.greelando.composeroomlogin.repository.RepositoryImpl
import org.greelando.composeroomlogin.roomdb.MyDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideMyDataBase(app: Application) :MyDatabase{
        return Room.databaseBuilder(
            app,
            MyDatabase::class.java,
            "MyDataBase"
        )
//            .addMigrations()
            .build()
    }

    @Provides
    @Singleton
    fun provideMyRepository(myDatabase: MyDatabase): Repository {
        return RepositoryImpl(myDatabase.dao)
    }
}