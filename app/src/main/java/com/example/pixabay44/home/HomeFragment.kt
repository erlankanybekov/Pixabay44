package com.example.pixabay44.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout

import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pixabay44.App
import com.example.pixabay44.adapter.ImageAdapter
import com.example.pixabay44.databinding.FragmentHomeBinding
import com.example.pixabay44.model.ImageModel
import com.example.pixabay44.model.PixabayModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    lateinit var binding: FragmentHomeBinding
    var page = 1
    var imageAdapter = ImageAdapter(arrayListOf())
    var isLoading = false
    lateinit var list: ArrayList<ImageModel>
    private var totalPage = 1
    lateinit var layoutManager: GridLayoutManager
    private var isOnRefresh = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = GridLayoutManager(requireContext(),3)
     //   binding.swipeToRefresh.setOnRefreshListener(this)

        initClickers()

        binding.recyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = imageAdapter.itemCount
                if (!isLoading&&page<totalPage){
                    if (visibleItemCount+pastVisibleItem>=total){
                     doRequest(page++)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })



    }

    private fun initClickers() {

         binding.requestBtn.setOnClickListener {
                doRequest(page++)
            }
           binding.changePageBtn.setOnClickListener {

                doRequest(page++)
            }

    }


    private fun doRequest(page:Int) {

        Handler().postDelayed({
            App.api.getImagesByWord(keyWord = binding.keywordEd.text.toString().trim(), page = page)
                .enqueue(object : Callback<PixabayModel> {
                    override fun onResponse(
                        call: Call<PixabayModel>,
                        response: Response<PixabayModel>,
                    ) {
                        totalPage = response.body()?.total!!
                        response.body()?.hits?.let {
                            binding.recyclerView.setHasFixedSize(true)
                            binding.recyclerView.layoutManager = layoutManager
                            list = it as ArrayList<ImageModel>
                            imageAdapter = ImageAdapter(list)
                            binding.recyclerView.adapter = imageAdapter
                        }
                        if (page-1 !=0 ){
                            imageAdapter.addList(list)
                        }
                        if (page==totalPage){
                            binding.progressBar.visibility = View.GONE
                        }else{
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        binding.progressBar.visibility = View.VISIBLE
                        isLoading=false
                        // binding.swipeToRefresh.isRefreshing = false
                        Log.e("ololo", "onResponse:${page} ${response.body()?.hits}")
                    }

                    override fun onFailure(call: Call<PixabayModel>, t: Throwable) {
                        Log.e("ololo", "onFailure: ${t.message}")
                    }

                })

        },1000)

    }

    override fun onRefresh() {
        imageAdapter.clear()
        page =1
        doRequest(page)
    }


}