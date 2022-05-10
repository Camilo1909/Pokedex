package com.example.pokedex

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokedex.databinding.ActivityPokemonCatchBinding
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class PokemonCatchActivity : AppCompatActivity() {

    private val binding: ActivityPokemonCatchBinding by lazy{
        ActivityPokemonCatchBinding.inflate(layoutInflater)
    }

    private lateinit var user: User
    private lateinit var pokemon: Pokemon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        user = intent.extras?.get("user") as User
        pokemon = intent.extras?.get("pokemon") as Pokemon

        binding.attackTxt.text = pokemon.attack
        binding.defenseTxt.text = pokemon.defense
        binding.speedTxt.text = pokemon.speed
        binding.lifeTxt.text = pokemon.life
        binding.nameTxt.text = pokemon.name.uppercase()
        binding.typeTxt.text = "(${pokemon.type})"
        Picasso.get().load(pokemon.img).into(binding.pokeIg)

        binding.catchBt.setOnClickListener {
            Firebase.firestore.collection("users").document(user.username).collection("pokemones").document(pokemon.uid).set(pokemon)
            val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra("user",user)
            }
            startActivity(intent)
        }
    }
}