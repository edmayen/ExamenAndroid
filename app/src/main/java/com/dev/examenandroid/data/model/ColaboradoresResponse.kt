package com.dev.examenandroid.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ColaboradoresResponse(
    @SerializedName("file")
    val file: String = ""
): Parcelable

data class DataList(
    @SerializedName("data")
    val data: List<Archivo>,
    @SerializedName("code")
    val code: Int,
    @SerializedName("success")
    val success: Boolean
)