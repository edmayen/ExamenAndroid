package com.dev.examenandroid.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dev.examenandroid.data.model.ColaboradorEntity

@Database(
    entities = [ColaboradorEntity::class],
    version = 1
)
abstract class ColaboradoresDatabase: RoomDatabase() {
    abstract fun getColaboradorDao(): ColaboradoresDao

    companion object{

        @Volatile
        private var instance: ColaboradoresDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ColaboradoresDatabase::class.java,
                "colaboradores_db.db"
            ).build()
    }
}