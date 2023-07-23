package com.meet.project.equationmaster.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.meet.project.equationmaster.R
import com.meet.project.equationmaster.databinding.ActivityComposeBinding
import com.meet.project.equationmaster.databse.HistoryDetail

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityComposeBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        binding = ActivityComposeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.clear()
        binding.composeView.setContent {
            MainActivityScreen(
                viewModel = viewModel,
                onHistoryClick = ::onHistoryClick,
                onButtonClick = ::onButtonClick,
            )
        }
    }

    private fun onButtonClick() {
        if (viewModel.equationText.isEmpty()) {
            viewModel.errorText = getString(R.string.error_empty_field)
        } else {
            viewModel.equationsList =
                viewModel.equationText.replace(" ", "").replace("\n", "").split(",").toMutableList()
            viewModel.equationsList.removeAll(listOf(""))
            viewModel.showLoading = true
            viewModel.evaluateEquations(
                onSuccess = {
                    if (it.result.isEmpty()) {
                        viewModel.resultScreen = false
                        Toast.makeText(this, "Something Went Wrong!", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.equationsResponse = it
                        viewModel.resultScreen = true
                        viewModel.insertHistory(
                            HistoryDetail(
                                id = System.currentTimeMillis(),
                                equationsList = viewModel.convertToString(viewModel.equationsList),
                                answers = viewModel.convertToString(viewModel.equationsResponse?.result!!)
                            )
                        )
                    }
                    viewModel.showLoading = false
                },
                onFailure = {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                    viewModel.resultScreen = false
                    viewModel.showLoading = false
                }
            )
        }
    }

    private fun onHistoryClick() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }
}