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



class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    var page = 1
    var imageAdapter = ImageAdapter(arrayListOf())
    lateinit var list: ArrayList<ImageModel>
    lateinit var layoutManager: GridLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     //   binding.swipeToRefresh.setOnRefreshListener(this)
        initClickers()




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
            App.api.getImagesByWord(keyWord = binding.keywordEd.text.toString().trim(), page = page)
                .enqueue(object : Callback<PixabayModel> {
                    override fun onResponse(
                        call: Call<PixabayModel>,
                        response: Response<PixabayModel>,
                    ) {
                        response.body()?.hits?.let {
                            list = it as ArrayList<ImageModel>
                            imageAdapter = ImageAdapter(list)
                            binding.recyclerView.adapter = imageAdapter
                        }

                        // binding.swipeToRefresh.isRefreshing = false
                        Log.e("ololo", "onResponse:${page} ${response.body()?.hits}")
                    }

                    override fun onFailure(call: Call<PixabayModel>, t: Throwable) {
                        Log.e("ololo", "onFailure: ${t.message}")
                    }

                })


    }


}