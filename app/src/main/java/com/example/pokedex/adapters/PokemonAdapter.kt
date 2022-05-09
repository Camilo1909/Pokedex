package com.example.pokedex.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.PokemonViewActivity
import com.example.pokedex.R
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.User
import com.example.pokedex.viewholders.PokemonViewHolder
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.local.QueryResult
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PokemonAdapter: RecyclerView.Adapter<PokemonViewHolder>(), PokemonViewActivity.OnPokemonDrop{

    private var pokemons= ArrayList<Pokemon>()
    lateinit var user: User

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val row = inflater.inflate(R.layout.pokemonrow,parent,false)
        val pokemonView = PokemonViewHolder(row)
        return pokemonView
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemons[position]
        holder.pokemon = pokemon
        holder.user = user
        holder.nameText.text = pokemon.name
        holder.dateText.text = SimpleDateFormat("MMM dd, yy 'at' HH:mm").format(Date(pokemon.dateCatch))
        Picasso.get().load(pokemon.img).into(holder.pokemonImg)
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    fun deletePokemons(){
        pokemons.clear()
    }

    fun addPokemon(pokemon: Pokemon){
        pokemons.add(pokemon)
    }

    fun removePokemon(pokemon: Pokemon){
        pokemons.remove(pokemon)
        notifyDataSetChanged()
    }

    override fun dropPokemon(task: Task<QuerySnapshot>) {
        for(doc in task.result!!){
            val pk = doc.toObject(Pokemon::class.java)
            removePokemon(pk)
            notifyDataSetChanged()
        }
    }

}