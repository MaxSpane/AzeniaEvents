package com.example.azenia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.azenia.databinding.ItemEventBinding

class EventsAdapter(
    private var dataset: List<StubhubData> = listOf()
): RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder =
        EventsViewHolder(
            ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    override fun getItemCount(): Int =
        dataset.size

    fun refill(dataset: List<StubhubData>){
        this.dataset = dataset
        notifyDataSetChanged()
    }

    inner class EventsViewHolder(private val binding: ItemEventBinding)
        : RecyclerView.ViewHolder(binding.root){

            fun bind(event: StubhubData){
                binding.apply {
                    cityTV.text = event.city ?: ""
                    artistTV.text = event.name
                    priceTV.text = "$${event.price ?: 0}"
                }
            }
        }

}