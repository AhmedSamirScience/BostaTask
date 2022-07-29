package com.task.bosta.ui.fragments.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.bosta.databinding.AlbumItemBinding
import com.task.bosta.model.response.profile.albums.AlbumsResponse
import com.task.bosta.ui.fragments.profile.clickEvents.AlbumsClickListener
import kotlin.collections.ArrayList
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class AlbumsAdapter (
    private var myList: List<AlbumsResponse> = listOf()) :

    RecyclerView.Adapter<AlbumsAdapter.ViewHolder>() {

    var trxList: List<AlbumsResponse> = ArrayList()
    var albumsClickListener: AlbumsClickListener?= null

    private var parent: ViewGroup? = null

    init {
        this.trxList = myList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: AlbumItemBinding = AlbumItemBinding.inflate(layoutInflater, parent, false)
        this.parent = parent
        return ViewHolder(itemBinding)
    }

    fun submitMyList(myList: List<AlbumsResponse> , albumsClickListener: AlbumsClickListener) {
        this.trxList = myList
        this.albumsClickListener = albumsClickListener
    }

    override fun getItemCount(): Int {
        return trxList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.myItemTX = trxList[position]
        holder.bind(holder.myItemTX)
    }

    inner class ViewHolder(var Binding: AlbumItemBinding) :
        RecyclerView.ViewHolder(Binding.root)  , View.OnClickListener{
        var myItemTX: AlbumsResponse? = null
        init {
            Binding.containerLinear.setOnClickListener(this)
        }

        fun bind(myItem: AlbumsResponse?) {
//            myItem?.categoryIcon?.let {
//                Glide.with(Binding.root.context).load(it.replaceAfterLast("." , "svg"))
//                    .apply(RequestOptions().error(R.drawable.sharee_logo))
//                    .into(Binding.itemImage)
//            }
           Binding.myItem = myItem
        }

        override fun onClick(v: View?) {
            albumsClickListener?.onAlbumClick(Binding.root , myItemTX!!)
        }


    }
}