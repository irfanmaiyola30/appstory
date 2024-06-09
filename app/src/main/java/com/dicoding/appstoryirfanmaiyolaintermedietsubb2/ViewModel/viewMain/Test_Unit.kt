package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.ViewModel.viewMain

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Response.ListStoryItem
import com.dicoding.AppStory.databinding.StoryItemBinding
import com.bumptech.glide.Glide

class Test_Unit : PagingDataAdapter<ListStoryItem, Test_Unit.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            StoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        if (user != null) {
            holder.bind(user)
        }
    }

    inner class MyViewHolder(private val binding: StoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemName: ListStoryItem) {
            binding.tvTitleRow.text = itemName.name
            Glide.with(binding.root)
                .load(itemName.photo)
                .into(binding.ivStoryRow)
            binding.root.setOnClickListener {
                val intentDetail = Intent(binding.root.context, DetailActivity::class.java)
                intentDetail.putExtra(DetailActivity.ID, itemName.id)
                intentDetail.putExtra(DetailActivity.NAME, itemName.name)
                intentDetail.putExtra(DetailActivity.DESCRIPTION, itemName.description)
                intentDetail.putExtra(DetailActivity.PICTURE, itemName.photo)
                binding.root.context.startActivity(intentDetail)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}