package com.example.azenia

import android.content.res.AssetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.azenia.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var events: ArrayList<StubhubData>
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        events = getEventsFromJson()
        binding.testTV.text = "Found ${events.size} Events"
    }

    private fun getEventsFromJson(): ArrayList<StubhubData> {
        val stubhubDataJsonString = assets.readAssetsFile("StubhubData.json")
        val gson = Gson()
        val stubhubData = gson.fromJson(stubhubDataJsonString, StubhubData::class.java)
        return getEventsFromStubhubData(stubhubData)
    }

    private fun getEventsFromStubhubData(stubhubData: StubhubData): ArrayList<StubhubData>{
        val events = arrayListOf<StubhubData>()
        if (stubhubData.children.isNotEmpty()){
            for (data in stubhubData.children){
                events.addAll(getEventsFromStubhubData(data))
            }
        }else if(stubhubData.events.isNotEmpty()){
            events.addAll(stubhubData.events)
        }
        return events
    }

    private fun AssetManager.readAssetsFile(fileName : String): String = open(fileName).bufferedReader().use{it.readText()}

}