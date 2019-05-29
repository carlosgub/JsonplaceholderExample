package com.carlosgub.jsonexample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.carlosgub.jsonexample.R
import com.carlosgub.jsonexample.model.Post

class PostAdapter(private var posts:List<Post>, private var callback: onClick): RecyclerView.Adapter<PostAdapter.MViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_post, parent, false)
        return MViewHolder(view)
    }

    override fun onBindViewHolder(vh: MViewHolder, position: Int) {
        val post= posts[position]

        //render
        vh.textViewName.text= post.title
        vh.cv.setOnClickListener { callback.click(post.id) }
        vh.cv.setOnLongClickListener {
            callback.onLongClick(post)
            true
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class MViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textViewName: TextView = view.findViewById(R.id.tvTitle)
        val cv: CardView = view.findViewById(R.id.cv)
    }

    interface onClick{
        fun click(id:Int)
        fun onLongClick(post:Post)
    }
}