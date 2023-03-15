package com.example.quizine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class lastPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_last_page)

        val tvName: TextView = findViewById(R.id.tv_name)
        val tvScore: TextView = findViewById(R.id.tv_score)
        val btnFinish: Button = findViewById(R.id.btn_finish)

        tvName?.text = intent.getStringExtra(Constants.USER_NAME)

        val totalQues = intent.getIntExtra(Constants.TOTAL_QUE, 0)
        val correctAns = intent.getIntExtra(Constants.CORRECT_ANS, 0)

        tvScore.text = "Your score is $correctAns out of $totalQues"

        btnFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}