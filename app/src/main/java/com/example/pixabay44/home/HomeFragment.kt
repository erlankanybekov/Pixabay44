package com.example.pixabay44.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

    private  lateinit var binding: FragmentHomeBinding
    private var page:Int = 1
    private var imageAdapter = ImageAdapter(arrayListOf())
    private lateinit var list: ArrayList<ImageModel>
    private lateinit var layoutManager:GridLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.INVISIBLE
        layoutManager = GridLayoutManager(requireContext(),3)
        initClickers()


    }

    private fun initClickers() {

         binding.requestBtn.setOnClickListener {
             binding.progressBar.visibility = View.VISIBLE
                doRequest(page++)
            }
           binding.changePageBtn.setOnClickListener {
               Handler(Looper.myLooper()!!).postDelayed({
                   binding.progressBar.visibility = View.GONE
               },2500)
                imageAdapter.clear()
            }
        binding.recyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)){
                    try {
                        doRequest(page++)

                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
           }
        })

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
                            imageAdapter.addList(list)
                        }

                        binding.recyclerView.adapter = imageAdapter

                        Log.e("ololo", "onResponse:${page} ${response.body()?.hits}")
                    }

                    override fun onFailure(call: Call<PixabayModel>, t: Throwable) {
                        Log.e("ololo", "onFailure: ${t.message}")
                    }

                })


    }


}