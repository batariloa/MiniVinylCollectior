package com.batarilo.vinylcollection.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batarilo.vinylcollection.R
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.retrofit.RecordApiService
import com.batarilo.vinylcollection.data.retrofit.RetrofitInstance
import com.batarilo.vinylcollection.ui.recycle.RecordAdapter
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    val tag = "MainActivity"
    private lateinit var recordAdapter: RecordAdapter
    val viewModel:HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        setupRecyclerView()

        lifecycleScope.launchWhenCreated {

            var result = try {
                RetrofitInstance.api.homeSearch(
                    RecordApiService.AUTH_KEY,
                    RecordApiService.AUTH_SECRET,
                    "Nirvana"
                )
            }
            catch (e:IOException){
                Log.d(tag, "EXCEPTION $e. You might not have an internet connection.")
                return@launchWhenCreated
            }
            catch (e: HttpException){
                Log.d(tag, "EXCEPTIOn $e")
                return@launchWhenCreated
            }

            if(result.isSuccessful && result.body()!=null){
                println("results")
                recordAdapter.records = result.body()!!.results
            }

        }}

    private fun setupRecyclerView() = findViewById<RecyclerView>(R.id.rv_record).apply {
        recordAdapter = RecordAdapter()
        adapter = recordAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)

    }
}



