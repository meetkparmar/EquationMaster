package com.meet.project.equationmaster.databse

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryDetail::class], version = 2)
abstract class HistoryDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

}
