package com.example.pixabay44.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pixabay44.databinding.ItemImageRvBinding
import com.example.pixabay44.model.ImageModel

class ImageAdapter(private val list: ArrayList<ImageModel> = arrayListOf()):RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {


    inner class ImageViewHolder(private val binding: ItemImageRvBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(model:ImageModel){
            binding.pixabayimage.load(model.largeImageURL)
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun addList(model: ArrayList<ImageModel>){
        list.addAll(model)
        notifyDataSetChanged()
    }
    var isLoading = false

    fun clear(){
        list.clear()
        notifyDataSetChanged()
    }

    fun startLoading() {
        isLoading = true
    }




}