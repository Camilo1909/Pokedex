package com.example.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.pokedex.databinding.ActivityPokemonViewBinding
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonViewActivity : AppCompatActivity() {

    private val binding: ActivityPokemonViewBinding by lazy{
        ActivityPokemonViewBinding.inflate(layoutInflater)
    }

    var pokemon: Pokemon? = null
    var user: User? = null

    var listenerDrop: OnPokemonDrop? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        pokemon = intent.extras?.get("pokemon") as Pokemon
        user = intent.extras?.get("user") as User

        binding.attackTx.text = pokemon?.attack.toString()
        binding.defenseTx.text = pokemon?.defense.toString()
        binding.speedTx.text = pokemon?.speed.toString()
        binding.lifeTx.text = pokemon?.life.toString()
        binding.nameTx.text = pokemon?.name.toString().uppercase()
        binding.typeTx.text = pokemon?.type.toString()
        Picasso.get().load(pokemon?.img.toString()).into(binding.pokeImg)

        binding.dropBtn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                Firebase.firestore.collection("users").document(user?.username.toString())
                    .collection("pokemones").document(pokemon?.uid.toString())
                    .delete()
                Firebase.firestore.collection("users").document(user?.username.toString())
                    .collection("pokemones").orderBy("dateCatch", Query.Direction.DESCENDING)
                    .get().addOnCompleteListener { task ->
                        listenerDrop?.dropPokemon(task)
                    }
            val intent = Intent(this@PokemonViewActivity, HomeActivity::class.java).apply {
                putExtra("user", user)
            }
            startActivity(intent)
            finish()
            }

        }
    }

    interface OnPokemonDrop{
        fun dropPokemon(task: Task<QuerySnapshot>)
    }
}