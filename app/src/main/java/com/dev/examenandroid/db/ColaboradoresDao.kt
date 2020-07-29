package com.dev.examenandroid.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dev.examenandroid.data.model.ColaboradorEntity

@Dao
interface ColaboradoresDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertColaborador(colaborador: ColaboradorEntity)

    @Query("SELECT * FROM colaboadores")
    suspend fun getAllColaboradores(): List<ColaboradorEntity>
}