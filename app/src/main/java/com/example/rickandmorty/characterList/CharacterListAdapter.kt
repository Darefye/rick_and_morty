package com.example.rickandmorty.characterList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.CharacterItemBinding
import com.example.rickandmorty.models.Character

class CharacterListAdapter(
    private val onClick: (Character) -> Unit
) : PagingDataAdapter<Character, CharacterViewHolder>(
    DiffUtilCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {

            characterName.text = item?.name

            characterStatus.text =
                characterStatus.context.getString(R.string.status_line, item?.status, item?.species)

            characherLastLocation.text = item?.location?.name

            when (item?.status.toString().lowercase()) {
                "alive" -> {
                    ivStatus.setBackgroundResource(R.drawable.ic_alive)
                }
                "dead" -> {
                    ivStatus.setBackgroundResource(R.drawable.ic_dead)
                }
                "unknown" -> {
                    ivStatus.setBackgroundResource(R.drawable.ic_unknown)
                }
            }

            item?.let {
                Glide.with(avatar.context).load(it.image).into(avatar)
            }
        }

        holder.binding.root.setOnClickListener {
            item?.let { photo ->
                onClick(photo)
            }
        }
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(
        oldItem: Character, newItem: Character
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Character, newItem: Character
    ): Boolean = oldItem == newItem
}

class CharacterViewHolder(val binding: CharacterItemBinding) :
    RecyclerView.ViewHolder(binding.root)