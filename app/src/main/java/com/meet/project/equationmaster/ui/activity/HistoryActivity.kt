package com.meet.project.equationmaster.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.meet.project.equationmaster.databinding.ActivityComposeBinding
import com.meet.project.equationmaster.network.models.History

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityComposeBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        binding = ActivityComposeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initData()

        binding.composeView.setContent {
            HistoryActivityScreen(
                viewModel = viewModel,
                onBackClick = ::onBackClick,
            )
        }
    }

    private fun initData() {
        if (viewModel.getHistory().isEmpty()) {
            viewModel.showEmptyScreen = true
        } else {
            viewModel.getHistory().forEachIndexed { i, item ->
                if (item.answers.isNotEmpty()) {
                    viewModel.history.add(
                        History(
                            id = item.id,
                            equationsList = item.equationsList.split(","),
                            answers = item.answers.split(",")
                        )
                    )
                }
            }
        }
    }

    private fun onBackClick() {
        finish()
    }
}