package com.example.languagebooster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.languagebooster.adaptors.RcyclerviewAdaptor
import com.example.languagebooster.data.MockData
import com.example.languagebooster.models.Word
import soup.neumorphism.NeumorphFloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: NeumorphFloatingActionButton
    private lateinit var adaptor: RcyclerviewAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()


    }

    private fun init() {
        bindViews()
        adaptor = RcyclerviewAdaptor(this , MockData.getList())
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adaptor
        prepareItems()


    }


    private fun bindViews() {
        recyclerView = findViewById(R.id.mainList)
        fab = findViewById(R.id.neumorphFloatingActionButton)

    }


    private fun prepareItems() {
        MockData.getList().clear()
        MockData.addWord(Word("Correct", "صحیح" , false))
        MockData.addWord(Word("Mistake", "اشتباه" , false))
        MockData.addWord(Word("Love", "زید" , false))
        MockData.addWord(Word("Matrix", "جبر خطی" , false))
        MockData.addWord(Word("Ali Afroozi", "توز " , false))
        adaptor.notifyDataSetChanged()
    }
}