package com.example.rickandmorty.characterList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.LoadStateBinding

class LoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<com.example.rickandmorty.characterList.LoadStateAdapter.LoadStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = LoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }

    inner class LoadStateViewHolder(private val binding: LoadStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonRefresh.setOnClickListener {
                retry()
            }
        }

        fun bind(loadState: LoadState) {
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.buttonRefresh.isVisible = loadState !is LoadState.Loading
            binding.tvError.isVisible = loadState !is LoadState.Loading
        }
    }
}