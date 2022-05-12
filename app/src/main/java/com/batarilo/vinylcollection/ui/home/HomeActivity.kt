package com.batarilo.vinylcollection.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batarilo.vinylcollection.R
import com.batarilo.vinylcollection.data.retrofit.RecordApiService
import com.batarilo.vinylcollection.data.retrofit.RetrofitInstance
import com.batarilo.vinylcollection.ui.home.recycle.RecordAdapter
import com.batarilo.vinylcollection.ui.singleRecord.SingleRecordActivity
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), RecordAdapter.OnRecordListener  {


    val tag = "MainActivity"
    private lateinit var recordAdapter: RecordAdapter
    val viewModel:HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setupRecyclerView()
        search("Nirvana")

        findViewById<SearchView>(R.id.sv_record).setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                search(findViewById<SearchView>(R.id.sv_record).query.toString())
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })



    }

    private fun setupRecyclerView() = findViewById<RecyclerView>(R.id.rv_record).apply {
        recordAdapter = RecordAdapter(this@HomeActivity)
        adapter = recordAdapter
        layoutManager = LinearLayoutManager(this@HomeActivity)

    }
    private fun search(term:String){
        lifecycleScope.launchWhenCreated {

            var result = try {
                RetrofitInstance.api.homeSearch(
                    RecordApiService.AUTH_KEY,
                    RecordApiService.AUTH_SECRET,
                    term
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

        }
    }

    override fun onRecordClicked(position: Int) {
        Toast.makeText(this, "BBLBLB", Toast.LENGTH_LONG)
        println(" "+ recordAdapter.records[position].toString() + " clicked record")
        val intent = Intent(this, SingleRecordActivity::class.java)
        startActivity(intent)
    }

    override fun onCollectedClicked(position: Int) {
        var clickedRecord = recordAdapter.records[position]
        viewModel.addRecord(clickedRecord)

    }
}



