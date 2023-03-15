package com.example.quizine

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btn : Button = findViewById(R.id.btn_start)
        val name : EditText = findViewById(R.id.et_name)
        btn.setOnClickListener{


            if(name.text.isEmpty()){
                Toast.makeText(this,"please enter ur name", Toast.LENGTH_LONG).show()
            } else{
                val intent = Intent(this,questionsActivity::class.java)
                intent.putExtra(Constants.USER_NAME,name.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }
}