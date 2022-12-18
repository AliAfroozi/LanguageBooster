package com.example.languagebooster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.languagebooster.db.helper.WordDBHelper
import com.example.languagebooster.models.Word

class AddWordActivity : AppCompatActivity() {
    private lateinit var keywordTxt : EditText
    private lateinit var meaningTxt : EditText
    private lateinit var addBtn : Button

    private lateinit var dbHelper: WordDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
        init()
    }

    private fun init(){
        bindViews()
        dbHelper = WordDBHelper(this)
        addBtn.setOnClickListener {
            if (keywordTxt.text.isEmpty() ){
                Toast.makeText(this , " Please Enter keyword " , Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (meaningTxt.text.isEmpty() ){
                Toast.makeText(this , " Please Enter meaning " , Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val result = dbHelper.insertWord(Word(keywordTxt.text.toString() , meaningTxt.text.toString() , false))
            if (result){
                Toast.makeText(this , " New word added " , Toast.LENGTH_LONG).show()
                finish()
            }else{
                Toast.makeText(this , " Error!! try again " , Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun bindViews(){
        keywordTxt = findViewById(R.id.editText_keyword)
        meaningTxt = findViewById(R.id.editText_meaning)
        addBtn = findViewById(R.id.AddWord_btn)
    }
}