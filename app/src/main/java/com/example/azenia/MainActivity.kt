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
    private lateinit var eventsAdapter: EventsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        events = getEventsFromJson()
        eventsAdapter = EventsAdapter(events)
        binding.eventsRV.adapter = eventsAdapter

        binding.filterBtn.setOnClickListener {
            val city = binding.cityFilterET.text.toString()

            val filteredEvents = events.filter { event -> (event.city ?: "").contains(city, true)}
            eventsAdapter.refill(filteredEvents)
        }
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