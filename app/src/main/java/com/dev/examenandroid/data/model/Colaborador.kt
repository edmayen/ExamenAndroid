package com.dev.examenandroid.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Colaborador(
    val name: String,
    val lat: String,
    val log: String,
    val mail: String
): Parcelable

@Entity(tableName = "colaboadores")
data class ColaboradorEntity(
    @PrimaryKey(autoGenerate = true)
    val id:  Int? = null,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "lat")
    val lat: String,
    @ColumnInfo(name = "log")
    val log: String,
    @ColumnInfo(name = "mail")
    val mail: String

)