package com.example.parcialtp3.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parcialtp3.R
import com.example.parcialtp3.entities.Dog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions


class DogAdapter(private val context: Context, private val dogList: List<Dog>) : RecyclerView.Adapter<DogAdapter.ViewHolder>(){

    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(dog: Dog)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pet, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dog = dogList[position]
        holder.bind(dog)
    }

    override fun getItemCount(): Int {
        return dogList.size
    }

    fun setDogs(dogList: List<Dog>){
        (this.dogList as ArrayList).clear()
        this.dogList.addAll(dogList)
        notifyDataSetChanged()
    }
    fun clearDogs(){
        (this.dogList as ArrayList).clear()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val petImagen: ImageView = itemView.findViewById(R.id.pet_image)
        private val petNombre: TextView = itemView.findViewById(R.id.pet_name)
        private val petRaza: TextView = itemView.findViewById(R.id.pet_raza)
        private val petSubRaza: TextView = itemView.findViewById(R.id.pet_subraza)
        private val petEdad: TextView = itemView.findViewById(R.id.pet_age)
        private val petGenero: TextView = itemView.findViewById(R.id.pet_gender)

        init {
            itemView.setOnClickListener{
                val position =adapterPosition
                if(position!= RecyclerView.NO_POSITION){
                    itemClickListener?.onItemClick(dogList[position])
                }
            }
        }
        fun bind(dog : Dog){
            petNombre.text = dog.name
            petRaza.text = dog.breed
            petSubRaza.text = dog.subBreed
            petEdad.text = dog.age.toString()
            petGenero.text = dog.gender

            val requestOptions = RequestOptions().transform()
            Glide.with(context)
                .load(dog.dogAvatarUrl)
                .apply(requestOptions)
                .into(petImagen)
        }
    }
}