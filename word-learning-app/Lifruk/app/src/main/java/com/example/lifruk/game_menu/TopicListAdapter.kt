package com.example.lifruk.game_menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lifruk.R
import com.example.lifruk.data.Topic

class TopicListAdapter(private val listener: TopicListAdapter.TopicItemListener)
    : ListAdapter<Topic, TopicListAdapter.TopicViewHolder>(TopicsComparator()), View.OnClickListener
{

    interface TopicItemListener {
        fun onTopicSelected(topic: Topic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        return TopicViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val currentTopic = getItem(position)
        with (holder) {
            cardView.setOnClickListener(this@TopicListAdapter)
            cardView.tag = currentTopic
            topicTitle.text = currentTopic.name
        }
        holder.bind(currentTopic.name)
    }

    class TopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val topicItemView: TextView = itemView.findViewById(R.id.topicTitle)
        val cardView = itemView.findViewById<CardView>(R.id.cardView)!!
        val topicTitle = itemView.findViewById<TextView>(R.id.topicTitle)!!

        fun bind(text: String?) {
            topicItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): TopicViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_topic, parent, false)
                return TopicViewHolder(view)
            }
        }
    }

    class TopicsComparator : DiffUtil.ItemCallback<Topic>() {
        override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean {
            return oldItem.name == newItem.name
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.cardView -> listener.onTopicSelected(view.tag as Topic)
        }
    }
}
