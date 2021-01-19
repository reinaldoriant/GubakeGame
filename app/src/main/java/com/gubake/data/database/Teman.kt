package com.gubake.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize
data class Teman(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "idplayer") var idplayer: Int,
    @ColumnInfo(name = "nama") var nama: String,
    @ColumnInfo(name = "email") var email: String

): Parcelable
