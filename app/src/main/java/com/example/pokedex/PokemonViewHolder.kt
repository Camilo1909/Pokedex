package com.example.pokedex

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    var pokemonImg: ImageView = itemView.findViewById(R.id.pokemonImg)
    var nameText: TextView = itemView.findViewById(R.id.nameText)
    var dateText: TextView = itemView.findViewById(R.id.dateText)
}