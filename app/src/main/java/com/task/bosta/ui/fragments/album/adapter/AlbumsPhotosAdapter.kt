package com.task.bosta.ui.fragments.album.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.task.bosta.R
import com.task.bosta.databinding.AlbumPhotosItemBinding
import com.task.bosta.model.response.album.PhotosResponse
import com.task.bosta.ui.fragments.album.clickEvent.AlbumsPhotosClickListener

class AlbumsPhotosAdapter (
    private var myList: List<PhotosResponse> = listOf()) :

    RecyclerView.Adapter<AlbumsPhotosAdapter.ViewHolder>() {

    var trxList: List<PhotosResponse> = ArrayList()
    var albumsPhotosClickListener: AlbumsPhotosClickListener?= null

    private var parent: ViewGroup? = null

    init {
        this.trxList = myList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: AlbumPhotosItemBinding = AlbumPhotosItemBinding.inflate(layoutInflater, parent, false)
        this.parent = parent
        return ViewHolder(itemBinding)
    }

    fun submitMyList(myList: List<PhotosResponse> , albumsPhotosClickListener: AlbumsPhotosClickListener) {
        this.trxList = myList
        this.albumsPhotosClickListener = albumsPhotosClickListener
    }

    override fun getItemCount(): Int {
        return trxList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.myItemTX = trxList[position]
        holder.bind(holder.myItemTX)
    }

    inner class ViewHolder(var Binding: AlbumPhotosItemBinding) :
        RecyclerView.ViewHolder(Binding.root)  , View.OnClickListener{
        var myItemTX: PhotosResponse? = null
        init {
            Binding.mainCard.setOnClickListener(this)
        }

        fun bind(myItem: PhotosResponse?) {
            myItem?.thumbnailURL?.let {
                Glide.with(Binding.root.context).load(it+".png")
                    .apply(RequestOptions().error(R.drawable.ic_error))
                    .into(Binding.itemImage)
            }
        }

        override fun onClick(v: View?) {
            albumsPhotosClickListener?.onPhotoClick(Binding.root , myItemTX!!)
        }
    }
}