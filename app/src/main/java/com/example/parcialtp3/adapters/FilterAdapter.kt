package com.example.parcialtp3.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.parcialtp3.entities.Filter
import com.example.parcialtp3.R
import com.example.parcialtp3.viewmodels.SharedViewModel

class FilterAdapter(private val context: Context, private val filterList: List<Filter>) : RecyclerView.Adapter<FilterAdapter.ViewHolder>(){

    private val sharedViewModel: SharedViewModel = SharedViewModel()
    private var itemClickListener: OnItemClickListener? = null
    private var selectedIndex: Int = -1

    interface OnItemClickListener {
        fun onItemClick(filter: Filter)
    }
    fun setOnItemClickListener(listener: FilterAdapter.OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pet_filter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterAdapter.ViewHolder, position: Int) {
        val filter = filterList[position]
        holder.bind(filter)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }
    fun setFilters(filters: List<Filter>){
        (this.filterList as ArrayList).clear()
        this.filterList.addAll(filters)
        notifyDataSetChanged()
    }

    fun clearFilters(){
        (this.filterList as ArrayList).clear()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val filterTitle: TextView = itemView.findViewById(R.id.filtro_title)
        private val cardView: CardView = itemView.findViewById(R.id.card_package_itemfilter)
        private val icon:ImageView = itemView.findViewById(R.id.filter_icon)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    selectedIndex = position
                    notifyDataSetChanged()
                    itemClickListener?.onItemClick(filterList[position])
                }
            }
        }

        fun bind(filter: Filter) {
            filterTitle.text = filter.title

            var isSelected = adapterPosition == selectedIndex

            if(sharedViewModel.getDarkModeState(context)){
                if(isSelected){
                    icon.setColorFilter(Color.WHITE)
                    cardView.setCardBackgroundColor(Color.parseColor("#A27035"))
                    filterTitle.setTextColor(Color.WHITE)
                }else{
                    cardView.setCardBackgroundColor(Color.BLACK)
                    filterTitle.setTextColor(Color.WHITE)
                    icon.setColorFilter(Color.WHITE)
                }
            }
            else{
                if(isSelected){
                    icon.setColorFilter(Color.WHITE)
                    cardView.setCardBackgroundColor(Color.parseColor("#A27035"))
                    filterTitle.setTextColor(Color.WHITE)
                }else{
                    cardView.setCardBackgroundColor(Color.WHITE)
                    filterTitle.setTextColor(Color.BLACK)
                    icon.setColorFilter(Color.BLACK)
                }
            }

        }

    }

}