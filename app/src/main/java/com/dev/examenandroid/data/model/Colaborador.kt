package com.dev.examenandroid.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Colaborador(
    val name: String,
    val lat: String,
    val log: String,
    val mail: String
): Parcelable