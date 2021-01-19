package com.gubake.data.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface PemainDao {
    @Query("SELECT * FROM Pemain")
    fun getAllPemain(): List<Pemain>

    @Query("SELECT * FROM Pemain WHERE username = :username OR email = :username")
    fun getPemainByUsername(username:String): Pemain

    @Query("SELECT * FROM Pemain WHERE id = :id")
    fun getPemainById(id: Int): Pemain

    @Insert(onConflict = REPLACE)
    fun insertPemain(pemain: Pemain):Long

}