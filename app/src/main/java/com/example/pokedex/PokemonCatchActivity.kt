package com.example.pokedex

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokedex.databinding.ActivityPokemonCatchBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso


class PokemonCatchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonCatchBinding
    private lateinit var user: User
    private lateinit var pokemon: Pokemon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonCatchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
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
            FirebaseFirestore.getInstance().collection("users").document(user.username).collection("pokemones").document(pokemon.name).set(pokemon)
            val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra("user",user)
            }
            startActivity(intent)
        }
    }
}