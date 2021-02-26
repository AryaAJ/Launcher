package com.example.mylauncher.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mylauncher.R
import com.example.sdk.model.AppData
import kotlinx.android.synthetic.main.item_preview.view.*

class AppListAdapter : RecyclerView.Adapter<AppListAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<AppData>() {
        override fun areItemsTheSame(oldItem: AppData, newItem: AppData): Boolean {
            return oldItem.app_name == newItem.app_name
        }

        override fun areContentsTheSame(oldItem: AppData, newItem: AppData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_preview,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((AppData) -> Unit)? = null

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val app = differ.currentList[position]

        holder.itemView.apply {

            tvTitle.text = app.app_name
            tvDescription.text = app.package_name
            tvPublishedAt.text = app.version
            ivIcon.setImageDrawable(app.icon)

            setOnClickListener {
                onItemClickListener?.let { it(app) }
            }
        }

    }

    fun setOnItemClickListener(listener: (AppData) -> Unit) {
        onItemClickListener = listener
    }


}













