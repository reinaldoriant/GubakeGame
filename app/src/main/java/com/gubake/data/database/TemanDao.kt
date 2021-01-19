package com.gubake.data.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface TemanDao {

    @Query("SELECT * FROM Teman WHERE idplayer=:idplayer")
    fun getAllbyId(idplayer:Int): List<Teman>
    @Insert(onConflict = REPLACE)
    fun insertTeman(teman: Teman):Long

    @Update
    fun updateTeman(teman: Teman):Int

    @Delete
    fun deleteTeman(teman: Teman):Int

}