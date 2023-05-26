package com.mobile.test.ui.screens.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.test.databinding.CategoryItemBinding
import com.mobile.test.model.response.CategoryModel

class CategoriesAdapter: RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
    private var categories: List<CategoryModel> = mutableListOf()
    var listener: CategoryListener? = null

    fun submitList(categories: List<CategoryModel>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = categories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position], position, listener)
    }

    class CategoryViewHolder(private val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoryModel, position: Int, listener: CategoryListener?) {
            binding.category = category
            binding.root.setOnClickListener {
                listener?.onSelectCategory(position)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CategoryViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CategoryItemBinding.inflate(layoutInflater, parent, false)
                return CategoryViewHolder(binding)
            }
        }
    }
}

interface CategoryListener {
    fun onSelectCategory(position: Int)
}