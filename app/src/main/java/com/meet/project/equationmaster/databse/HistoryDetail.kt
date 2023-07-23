package com.meet.project.equationmaster.databse

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class HistoryDetail(
    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "equations_list")
    val equationsList: String = "",

    @ColumnInfo(name = "answers")
    val answers: String = "",
)