package com.task.bosta.ui.fragments.profile

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.bosta.R
import com.task.bosta.base.BaseFragment
import com.task.bosta.databinding.ProfileFragmentBinding
import com.task.bosta.model.response.profile.albums.AlbumsResponse
import com.task.bosta.ui.fragments.profile.adapter.AlbumsAdapter
import com.task.bosta.ui.fragments.profile.clickEvents.AlbumsClickListener
import com.task.bosta.utils.LiveDataResource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProfileFragment: BaseFragment<ProfileViewModel, ProfileFragmentBinding>(), AlbumsClickListener{

    private var albumsAdapter: AlbumsAdapter? = null

    override fun subscribeLiveData() {
        super.subscribeLiveData()
        observeAlbumList()
        observeUsersList()
    }
    override fun getContentView() = R.layout.profile_fragment
    override fun initializeViewModel() {
        val viewModel: ProfileViewModel by viewModels()
        baseViewModel = viewModel
    }
    override fun onClick(v: View?) {
    }
    override fun initView() {
        baseViewBinding.swipeRefreshLayout.setColorSchemeResources(
            R.color.main_color,
            R.color.main_color,
            R.color.main_color
        )
        baseViewBinding.swipeRefreshLayout.setOnRefreshListener {
            baseViewModel?.start()
            closeRefreshing()
        }
    }
    private fun closeRefreshing(){
        baseViewBinding.swipeRefreshLayout.isRefreshing = false
    }

    //User Data
    private fun observeUsersList(){
        lifecycleScope.launchWhenStarted {
            baseViewModel?.usersLiveDate?.collect {
                when(it){
                    is LiveDataResource.Success -> {
                        viewShimmerUserDataVisibilityOn(false)

                        val listShuffeld = it.data?.shuffled()
                        baseViewBinding.name.setText(listShuffeld?.get(0)?.name.toString())

                        val addressList = listShuffeld?.get(0)?.address
                        val address = addressList?.street.toString() + ", " + addressList?.suite.toString() + ", " +
                                      addressList?.city.toString() + ", " + addressList?.zipcode.toString()
                        baseViewBinding.address.setText(address)

                        baseViewModel?.loadAlbumUser(listShuffeld?.get(0)?.id.toString())
                    }
                    is LiveDataResource.ErrorResponse -> {
                        viewShimmerUserDataVisibilityOn(false)
                    }
                    is LiveDataResource.Exception -> {
                        viewShimmerUserDataVisibilityOn(false)
                    }
                    is LiveDataResource.Loading -> {
                        viewShimmerUserDataVisibilityOn(true)
                    }
                    is LiveDataResource.NoNetwork -> {
                        viewShimmerUserDataVisibilityOn(false)
                    }
                }
            }
        }
    }
    private fun viewShimmerUserDataVisibilityOn(isView: Boolean){
        if (isView){
            baseViewBinding.profileTitle.visibility = View.GONE
            baseViewBinding.name.visibility = View.GONE
            baseViewBinding.address.visibility = View.GONE
            baseViewBinding.userDataShimmer.visibility = View.VISIBLE
        }else{
            baseViewBinding.profileTitle.visibility = View.VISIBLE
            baseViewBinding.name.visibility = View.VISIBLE
            baseViewBinding.address.visibility = View.VISIBLE
            baseViewBinding.userDataShimmer.visibility = View.GONE
        }
    }


    //User Album
    private fun observeAlbumList(){
        lifecycleScope.launchWhenStarted {
            baseViewModel?.albumsLiveDate?.collect {
                when(it){
                    is LiveDataResource.Success -> {
                        viewShimmerUserAlbumVisibilityOn(false)
                        it.data?.let { it1 -> initAlbumRecycler(it1, baseViewBinding.albumRecycler) }
                    }
                    is LiveDataResource.ErrorResponse -> {
                        viewShimmerUserAlbumVisibilityOn(false)
                    }
                    is LiveDataResource.Exception -> {
                        viewShimmerUserAlbumVisibilityOn(false)
                    }
                    is LiveDataResource.Loading -> {
                        viewShimmerUserAlbumVisibilityOn(true)
                    }
                    is LiveDataResource.NoNetwork -> {
                        viewShimmerUserAlbumVisibilityOn(false)
                    }
                }
            }
        }
    }
    private fun viewShimmerUserAlbumVisibilityOn(isView: Boolean){
        if (isView){
            baseViewBinding.albumRecycler.visibility = View.GONE
            baseViewBinding.userAlbumShimmer.visibility = View.VISIBLE
        }else{
            baseViewBinding.albumRecycler.visibility = View.VISIBLE
            baseViewBinding.userAlbumShimmer.visibility = View.GONE
        }
    }
    private fun initAlbumRecycler(myList: List<AlbumsResponse>, recyclerView: RecyclerView){
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        albumsAdapter = AlbumsAdapter().apply {
            submitMyList(myList, this@ProfileFragment)
        }
        recyclerView.visibility = View.VISIBLE
        recyclerView.adapter = albumsAdapter
        recyclerView.startLayoutAnimation()
    }
    override fun onAlbumClick(view: View, item: AlbumsResponse) {
        val directionToAlbumFragment = ProfileFragmentDirections.actionProfileFragmentToAlbumFragment( item.id.toString())
        findNavController().navigate(directionToAlbumFragment)
    }









}