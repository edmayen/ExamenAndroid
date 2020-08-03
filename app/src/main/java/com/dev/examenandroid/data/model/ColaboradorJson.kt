package com.dev.examenandroid.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.google.gson.annotations.SerializedName

data class Employee(
    val id: String,
    val name: String,
    val location: Location,
    val mail: String
)

data class ColaboradorJson(
    val data: Data
)

data class Data(
    val employees: List<Employee>
)
