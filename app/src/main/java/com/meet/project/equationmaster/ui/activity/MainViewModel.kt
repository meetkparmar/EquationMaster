package com.meet.project.equationmaster.ui.activity

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.meet.project.equationmaster.databse.HistoryDatabase
import com.meet.project.equationmaster.databse.HistoryDetail
import com.meet.project.equationmaster.network.MainService
import com.meet.project.equationmaster.network.RetrofitClient
import com.meet.project.equationmaster.network.models.EquationsRequest
import com.meet.project.equationmaster.network.models.EquationsResponse
import com.meet.project.equationmaster.network.models.History
import kotlinx.coroutines.launch

class MainViewModel(context: Application) : AndroidViewModel(context) {

    private val retrofit = RetrofitClient.getInstance()
    private val apiInterface = retrofit.create(MainService::class.java)

    var showLoading by mutableStateOf(value = false)
    var resultScreen by mutableStateOf(value = false)
    var showEmptyScreen by mutableStateOf(value = false)
    var history = mutableStateListOf<History>()
    var equationText by mutableStateOf(value = "")
    var errorText by mutableStateOf(value = "")
    var equationsList = mutableListOf<String>()
    var equationsResponse by mutableStateOf<EquationsResponse?>(value = null)

    private val db = Room.databaseBuilder(
        context,
        HistoryDatabase::class.java, "history_database"
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

    private val historyDao = db.historyDao()

    fun insertHistory(history: HistoryDetail) {
        historyDao.insert(history)
    }

    fun getHistory(): List<HistoryDetail> {
        return historyDao.getAll()
    }

    fun clear() {
        showLoading = false
        resultScreen = false
        equationText = ""
        errorText = ""
        equationsList.clear()
        equationsResponse = null
    }

    fun onEquationTextChange(newText: String) {
        errorText = ""
        equationText = newText
    }

    fun convertToString(list: List<String>): String {
        var str = list[0]
        list.forEachIndexed { i, s ->
            if (i != 0) str = "$str,$s"
        }
        return str
    }

    fun evaluateEquations(
        onSuccess: (EquationsResponse) -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = apiInterface.evaluateEquations(
                    request = EquationsRequest(
                        expr = equationsList
                    )
                )
                if (response.isSuccessful) {
                    onSuccess(response.body()!!)
                } else {
                    onFailure(response.message() ?: "Something Went Wrong!")
                }
            } catch (e: Exception) {
                onFailure("Something Went Wrong!")
                Log.e("evaluateEquations", e.localizedMessage)
            }
        }
    }
}