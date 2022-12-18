package com.example.languagebooster.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.languagebooster.R
import com.example.languagebooster.db.helper.WordDBHelper
import com.example.languagebooster.models.Word
import soup.neumorphism.NeumorphImageButton
import soup.neumorphism.NeumorphImageView

class RcyclerviewAdaptor(private val context: Context, private val words: ArrayList<Word> , private  val dbHelper: WordDBHelper) :
    RecyclerView.Adapter<RcyclerviewAdaptor.WordHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {

        return WordHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item_row, parent, false)
        )

    }

    override fun onBindViewHolder(holder: WordHolder, position: Int) {
        holder.keyword.text = "${words[position].keyword} : ${words[position].meaning}"
        setStarImage(holder, words[position])
        if (position == words.size - 1) {
            holder.bordr.visibility = View.GONE
        }


        holder.star.setOnClickListener { star ->
            words[position].stared = !words[position].stared
            dbHelper.updateWord(words[position])
            setStarImage(holder, words[position])
        }


        Glide
            .with(context)
            .load("https://picsum.photos/70?rand=" + System.currentTimeMillis())
            .centerCrop()
            .placeholder(R.drawable.loading_spinner)
            .into(holder.img);
    }


    private fun setStarImage(holder: WordHolder, item: Word) {
        holder.star.setImageResource(
            if (item.stared) R.drawable.ic_baseline_star_24
            else R.drawable.ic_baseline_star_border_24
        )
    }

    override fun getItemCount(): Int {
        return words.size
    }


    class WordHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var keyword: TextView = itemView.findViewById(R.id.item_text)
        var star: NeumorphImageButton = itemView.findViewById(R.id.starBtn)
        var img: NeumorphImageView = itemView.findViewById(R.id.img)
        var bordr: View = itemView.findViewById(R.id.border)
        override fun onClick(p0: View?) {

        }

    }
}