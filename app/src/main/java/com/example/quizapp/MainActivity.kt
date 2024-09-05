package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        with(binding){
            btnStart.setOnClickListener {
                if (loginName.text.toString().isEmpty()){
                    Toast.makeText(this@MainActivity,"Please give a name",Toast.LENGTH_LONG).show()
                }
                else{
                    val intent = Intent(this@MainActivity, QuizQuestionsActivity::class.java)
                    startActivity(intent)

                }

            }
        }
    }
}