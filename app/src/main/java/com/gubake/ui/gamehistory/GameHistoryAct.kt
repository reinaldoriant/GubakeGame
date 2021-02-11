package com.gubake.ui.gamehistory

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gubake.data.remote.ApiModule
import com.gubake.databinding.ActivityGameHistoryBinding
import com.gubake.ui.mainMenu.MainMenuAct


class GameHistoryAct : AppCompatActivity() {
    private var rvMain: RecyclerView? = null
    private lateinit var binding: ActivityGameHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = GameHistoryViewModel.Factory(ApiModule.service)

        val viewModel = ViewModelProvider(this, factory)[GameHistoryViewModel::class.java]
        viewModel.listScore()

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, MainMenuAct::class.java))
            finish()
        }

        rvMain = binding.recyclerView
        viewModel.resultScore.observe(this) {
            val adapter = GameHistoryAdapter(it.reversed(), this)
            rvMain?.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false
            )
            rvMain?.adapter = adapter
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainMenuAct::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        rvMain=null
    }
}