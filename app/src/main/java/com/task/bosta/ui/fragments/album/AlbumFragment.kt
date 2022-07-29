package com.task.bosta.ui.fragments.album

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.bosta.R
import com.task.bosta.base.BaseFragment
import com.task.bosta.databinding.AlbumFragmentBinding
import com.task.bosta.model.response.album.PhotosResponse
import com.task.bosta.ui.MainActivity
import com.task.bosta.ui.MainActivity_GeneratedInjector
import com.task.bosta.ui.fragments.album.adapter.AlbumsPhotosAdapter
import com.task.bosta.ui.fragments.album.clickEvent.AlbumsPhotosClickListener
import com.task.bosta.utils.LiveDataResource
import com.task.bosta.utils.widgets.CustomAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.collections.ArrayList


@AndroidEntryPoint
class AlbumFragment : BaseFragment<AlbumViewModel, AlbumFragmentBinding>(),
    AlbumsPhotosClickListener {

    private val args: AlbumFragmentArgs by navArgs()
    private lateinit var myPhotosList:  MutableList <PhotosResponse>
    private var albumsPhotosAdapter: AlbumsPhotosAdapter? = null


    override fun getContentView(): Int {
        return R.layout.album_fragment
    }
    override fun initializeViewModel() {
        val viewModel: AlbumViewModel by viewModels()
        baseViewModel = viewModel
    }
    override fun subscribeLiveData() {
        super.subscribeLiveData()
        observeAlbumPhotosList()
    }
    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runner)

    }
    override fun onClick(v: View?) {
    }

    override fun initView() {
        baseViewModel?.loadPhotos(args.albumId)
        watchEgpEdt()
        baseViewBinding.swipeRefreshLayout.setColorSchemeResources(
            R.color.main_color,
            R.color.main_color,
            R.color.main_color
        )
        baseViewBinding.swipeRefreshLayout.setOnRefreshListener {
            baseViewModel?.loadPhotos(args.albumId)
            closeRefreshing()
        }
    }

    //SwipeRefresher
    private fun closeRefreshing(){
        baseViewBinding.swipeRefreshLayout.isRefreshing = false
    }

    //Searching
    val handler = Handler(Looper.getMainLooper())
    val runner = Runnable {
        initAlbumPhotosRecycler(  filter(baseViewBinding.searchEdt.text.toString(), myPhotosList), baseViewBinding.albumPhotosRecycler)
    }
    private fun watchEgpEdt() {
        baseViewBinding.searchEdt.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    handler.removeCallbacks(runner)
                    handler.postDelayed(runner, 2000)
                }
            }
        })
    }
    fun filter(text: String, itemList: MutableList<PhotosResponse>?) :MutableList<PhotosResponse>  {
        val txt = text
        val filteredNames = ArrayList<PhotosResponse>()

        itemList?.filterTo(filteredNames) {
            it.title!!.contains(txt)
        }
     return filteredNames
    }

    //Album Photos
    private fun observeAlbumPhotosList(){
        lifecycleScope.launchWhenStarted {
            baseViewModel?.photosLiveDate?.collect {
                when(it){
                    is LiveDataResource.Success -> {
                        viewShimmerAlbumPhotosVisibilityOn(false)
                        it.data?.let { it1 -> initAlbumPhotosRecycler(it1, baseViewBinding.albumPhotosRecycler) }
                        myPhotosList  =   mutableListOf()
                        myPhotosList = it.data as MutableList<PhotosResponse>
                    }
                    is LiveDataResource.ErrorResponse -> {
                        viewShimmerAlbumPhotosVisibilityOn(false)
                    }
                    is LiveDataResource.Exception -> {
                        viewShimmerAlbumPhotosVisibilityOn(false)
                    }
                    is LiveDataResource.Loading -> {
                        viewShimmerAlbumPhotosVisibilityOn(true)
                    }
                    is LiveDataResource.NoNetwork -> {
                        viewShimmerAlbumPhotosVisibilityOn(false)
                    }
                }
            }
        }
    }
    private fun viewShimmerAlbumPhotosVisibilityOn(isView: Boolean){
        if (isView){
            baseViewBinding.albumPhotosRecycler.visibility = View.GONE
            baseViewBinding.albumPhotosShimmer.visibility = View.VISIBLE
            baseViewBinding.searchEdt.isEnabled = false
        }else{
            baseViewBinding.albumPhotosRecycler.visibility = View.VISIBLE
            baseViewBinding.albumPhotosShimmer.visibility = View.GONE
            baseViewBinding.searchEdt.isEnabled = true

        }
    }
    private fun initAlbumPhotosRecycler(myList: List<PhotosResponse>, recyclerView: RecyclerView){
        recyclerView.layoutManager = GridLayoutManager(context ,  3, LinearLayoutManager.VERTICAL , false)
        albumsPhotosAdapter = AlbumsPhotosAdapter().apply {
            submitMyList(myList, this@AlbumFragment)
        }
        recyclerView.visibility = View.VISIBLE
        recyclerView.adapter = albumsPhotosAdapter
        recyclerView.startLayoutAnimation()
    }
    override fun onPhotoClick(view: View, item: PhotosResponse) {
            CustomAlertDialog.showGift(activity as MainActivity, item.url)
    }


}
