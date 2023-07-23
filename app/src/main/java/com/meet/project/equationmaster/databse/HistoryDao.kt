package com.meet.project.equationmaster.databse

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {

    @Insert
    fun insert(history: HistoryDetail)

    @Query("SELECT * FROM history_table ORDER BY id DESC")
    fun getAll(): List<HistoryDetail>
}