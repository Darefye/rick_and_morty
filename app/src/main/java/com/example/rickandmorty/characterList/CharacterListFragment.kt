package com.example.rickandmorty.characterList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.rickandmorty.databinding.FragmentCharacherListBinding
import com.example.rickandmorty.models.Character
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CharacterListFragment : Fragment() {
    private var _binding: FragmentCharacherListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterListViewModel by viewModels()

    private val pageAdapter = CharacterListAdapter { photo -> onItemClick(photo) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacherListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter =
            pageAdapter.withLoadStateFooter(LoadStateAdapter { pageAdapter.retry() })

        binding.swipeRefreshLayout.setOnRefreshListener {
            pageAdapter.refresh()
        }

        pageAdapter.loadStateFlow.onEach { state ->
            val currentState = state.refresh
            binding.swipeRefreshLayout.isRefreshing = currentState == LoadState.Loading
            when (currentState) {
                is LoadState.Error -> {
                    binding.recycler.visibility = View.GONE
                    binding.loadState.visibility = View.VISIBLE
                }
                else -> {
                    binding.recycler.visibility = View.VISIBLE
                    binding.loadState.visibility = View.GONE
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        binding.buttonRefresh.setOnClickListener { pageAdapter.refresh() }

        viewModel.pageCharacter.onEach {
            pageAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClick(item: Character) {
    }

    private companion object {
        private const val BINDING_KEY = "KEY"
    }
}