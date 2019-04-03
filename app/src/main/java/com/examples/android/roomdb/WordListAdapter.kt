package com.examples.android.roomdb

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class WordListAdapter : RecyclerView.Adapter<WordListAdapter.MyViewHolder>() {

    private var words = emptyList<Word>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(root)
    }

    override fun getItemCount() = words.size

    override fun onBindViewHolder(viewHolder : MyViewHolder, position : Int) {
        viewHolder.word.text = words[position].word
    }

    internal fun setWords(words : List<Word>){
        this.words = words
        notifyDataSetChanged()
    }

    class MyViewHolder(item : View) : RecyclerView.ViewHolder(item) {
        val word : TextView = item.findViewById(R.id.word)
    }
}