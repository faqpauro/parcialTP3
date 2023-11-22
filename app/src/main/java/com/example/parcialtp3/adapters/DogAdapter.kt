package com.example.parcialtp3.adapters

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.example.parcialtp3.R
import com.example.parcialtp3.entities.Dog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.parcialtp3.entities.User
import com.example.parcialtp3.entities.UserFavorite
import com.example.parcialtp3.repository.DogRepository
import com.example.parcialtp3.viewmodels.SharedViewModel


class DogAdapter(
    private val context: Context,
    private val dogList: List<Dog>,
    private val dogRepository: DogRepository,
    private val sharedViewModel: SharedViewModel
) : RecyclerView.Adapter<DogAdapter.ViewHolder>(){

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
        val dog = dogList.getOrNull(position)
        var userId: Int = -1
        val user: User? = sharedViewModel.getUserData(context)
        if (user != null) {
            userId = user.id
        }

        if (dog != null) {
            holder.bind(dog)
            val isFavorite: Boolean = dogRepository.isFavorite(userId, dog.id)
            if (!isFavorite) {
                holder.favoriteButton.setImageResource(R.drawable.favorite_mark)
            } else {
                holder.favoriteButton.setImageResource(R.drawable.favorite_mark_fill)
            }
        }
    }

    override fun getItemCount(): Int {
        return dogList.size
    }
    fun setDogs(dogList: List<Dog>){
        (this.dogList as ArrayList).clear()
        this.dogList.addAll(dogList)
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val petImagen: ImageView = itemView.findViewById(R.id.pet_image)
        private val petNombre: TextView = itemView.findViewById(R.id.pet_name)
        private val petRaza: TextView = itemView.findViewById(R.id.pet_raza)
        private val petSubRaza: TextView = itemView.findViewById(R.id.pet_subraza)
        private val petEdad: TextView = itemView.findViewById(R.id.pet_age)
        private val petGenero: TextView = itemView.findViewById(R.id.pet_gender)
        val favoriteButton: ImageView = itemView.findViewById(R.id.favorite)
        var isFavorite = false


        init {
            favoriteButton.setOnClickListener{
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    itemClickListener?.onItemClick(dogList[position])

                    val dog = dogList[position]
                    val dogId = dog.id
                    var userId: Int = -1
                    val user: User? = sharedViewModel.getUserData(context)
                    if(user != null){
                        userId = user.id
                    }
                    val isFavorite: Boolean = dogRepository.isFavorite(userId, dogId)
                    val userFavoriteaux : UserFavorite? = dogRepository.getUserFavorite(dogId, userId)

                    if(isFavorite){
                        if(userFavoriteaux != null){
                            dogRepository.deleteFavorite(userFavoriteaux)
                        }
                        favoriteButton.setImageResource(R.drawable.favorite_mark)
                    }else{
                        dogRepository.createNewFavorite(UserFavorite(0, userId, dogId))
                        favoriteButton.setImageResource(R.drawable.favorite_mark_fill)
                    }
                }
            }

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
            favoriteButton.tag = dog.id

            val requestOptions = RequestOptions().transform()
            Glide.with(context)
                .load(dog.dogAvatarUrl)
                .apply(requestOptions)
                .into(petImagen)
        }
    }
}