package com.example.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.databinding.ActivityHomeBinding
import com.example.pokedex.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private  var username:String? = null

    private var adapter = PokemonAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        val pokemonRecycler = binding.pokemonRecycler
        pokemonRecycler.setHasFixedSize(true)
        pokemonRecycler.layoutManager = LinearLayoutManager(this)
        pokemonRecycler.adapter = adapter
        setContentView(view)

        username = intent.extras?.getString("Username")

        binding.seeBtn.setOnClickListener {
            val intent = Intent(this, PokemonViewActivity::class.java)
            startActivity(intent)
        }

        binding.catchBtn.setOnClickListener {
            val intent = Intent(this, PokemonCatchActivity::class.java)
            startActivity(intent)
        }


    }
}