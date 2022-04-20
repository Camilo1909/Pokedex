package com.example.pokedex

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

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
        //val URIpokemon = Uri.parse(pokemon.img)
        //holder.pokemonImg.setImageURI(URIpokemon)
        holder.nameText.text = pokemon.name
        holder.dateText.text = pokemon.dateCatch
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