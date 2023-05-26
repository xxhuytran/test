package com.mobile.test.ui.screens.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.test.R
import com.mobile.test.base.BaseFragment
import com.mobile.test.databinding.CategoriesFragmentBinding
import com.mobile.test.model.response.CategoryModel
import com.mobile.test.utils.SharedPrefs

class CategoriesFragment: BaseFragment() {
    private lateinit var binding: CategoriesFragmentBinding
    private val mViewModel by lazy { viewModel as CategoriesViewModel }
    private lateinit var categoriesAdapter: CategoriesAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.categories_fragment, container, false)
        val viewModelFactory = CategoriesViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[CategoriesViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = mViewModel

        bindingView()
        bindingListener()
        observerViewModel()
        return binding.root
    }

    private fun bindingListener() {
        categoriesAdapter.listener = object : CategoryListener {
            override fun onSelectCategory(position: Int) {
                mViewModel.onSelectCategory(position)
            }
        }

        binding.tvDone.setOnClickListener {
            if (mViewModel.isEnableSubmit.value == true) {
                mViewModel.prefs.saveList(SharedPrefs.SelectedCategory, mViewModel.categories.value!!.filter { category -> category.isSelected})
                Toast.makeText(
                    requireContext(), "Save To Local Storage",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun bindingView() {
        categoriesAdapter = CategoriesAdapter()
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(requireActivity(), SPAN_COUNT)
            adapter = categoriesAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(
                    recyclerView: RecyclerView,
                    dx: Int,
                    dy: Int
                ) {
                    if (layoutManager is GridLayoutManager){
                        val layoutManager = layoutManager as GridLayoutManager
                        if (mViewModel.categories.value != null) {
                            val totalItemCount = layoutManager.itemCount
                            val lastItemVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition()

                            if (totalItemCount / layoutManager.spanCount >= layoutManager.spanCount && lastItemVisiblePosition == totalItemCount - 1) {
                                mViewModel.onLoadMore()
                            }
                        }
                    }
                }
            })
        }
    }

    private fun observerViewModel() {
        mViewModel.categories.observe(viewLifecycleOwner) {
            it?.let {
                categoriesAdapter.submitList(it)
            }
        }
    }

    companion object{
        const val SPAN_COUNT = 3
    }
}