package com.example.pokedex.viewholders

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.PokemonViewActivity
import com.example.pokedex.R
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.User

class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    var pokemon: Pokemon? = null
    var user: User? = null

    var pokemonImg: ImageView = itemView.findViewById(R.id.pokemonImg)
    var nameText: TextView = itemView.findViewById(R.id.nameText)
    var dateText: TextView = itemView.findViewById(R.id.dateText)
    var pokeBackBtn: Button = itemView.findViewById(R.id.pokeBackBtn)

    init{
        pokeBackBtn.setOnClickListener {
            val intent = Intent(itemView.context, PokemonViewActivity::class.java).apply {
                putExtra("pokemon", pokemon!!)
                putExtra("user", user!!)
            }
            itemView.context.startActivity(intent)
        }
    }

}