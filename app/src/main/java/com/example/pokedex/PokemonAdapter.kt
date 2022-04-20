package com.example.pokedex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class PokemonAdapter: RecyclerView.Adapter<PokemonViewHolder>(){

    private var pokemons= ArrayList<Pokemon>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val row = inflater.inflate(R.layout.pokemonrow,parent,false)
        val pokemonView = PokemonViewHolder(row)
        return pokemonView
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemons[position]
        holder.nameText.text = pokemon.name
        holder.dateText.text = pokemon.dateCatch
        Picasso.get().load(pokemon.img).into(holder.pokemonImg)
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    fun deletePokemons(){
        pokemons.clear()
    }

    fun addPokemon(pokemon:Pokemon){
        pokemons.add(pokemon)
    }
}