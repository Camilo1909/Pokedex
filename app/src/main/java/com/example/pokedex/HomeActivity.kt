package com.example.pokedex

import android.content.Intent
import android.icu.text.DateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.databinding.ActivityHomeBinding
import com.example.pokedex.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection

class HomeActivity : AppCompatActivity(){

    private lateinit var binding: ActivityHomeBinding

    private lateinit var user: User
    private lateinit var pokemon: Pokemon
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

        user = intent.extras?.get("user") as User

        FirebaseFirestore.getInstance().collection("users").document(user.username).collection("pokemones").get().addOnCompleteListener{ task ->
          for (doc in task.result!!){
              val pk = doc.toObject(Pokemon::class.java)
              adapter.addPokemon(pk)
              adapter.notifyDataSetChanged()
          }
        }

        binding.seeBtn.setOnClickListener {
            GETRequest(binding.catchPokemonET.text.toString(),true)
        }

        binding.catchBtn.setOnClickListener {
            GETRequest(binding.catchPokemonET.text.toString(),false)
        }
    }

    fun GETRequest(namePk: String, show:Boolean){
        lifecycleScope.launch(Dispatchers.IO) {
            val url = URL("${Constants.POKE_API}/pokemon/${namePk}")
            val client = url.openConnection() as HttpsURLConnection
            client.requestMethod = "GET"
            val json = client.inputStream.bufferedReader().readText()
            //val pokemon = Gson().fromJson(json,Pokemon::class.java)
            if(json.isEmpty() == false){
                val jsonObject = JSONObject(json)
                val name = jsonObject.optJSONObject("species")?.optString("name")
                val type = jsonObject.optJSONArray("types")?.getJSONObject(0)?.optJSONObject("type")
                    ?.optString("name")
                //val img = jsonObject.optJSONObject("sprites")?.optJSONObject("other")?.optJSONObject("dream_world")?.optString("front_default")
                val img = jsonObject.optJSONObject("sprites")?.optString("front_default")
                val stat = jsonObject.optJSONArray("stats")
                val life = stat?.getJSONObject(0)?.optInt("base_stat")
                val attack = stat?.getJSONObject(1)?.optInt("base_stat")
                val defense = stat?.getJSONObject(2)?.optInt("base_stat")
                val speed = stat?.getJSONObject(5)?.optInt("base_stat")
                //Log.e(">>>>","${name}-${type}-${hp}-${speed}-${attack}-${defense}")
                pokemon = Pokemon(
                    user.username,
                    img!!,
                    name!!,
                    type!!,
                    DateFormat.getDateInstance().format(Calendar.getInstance().time),
                    "${defense!!}",
                    "${attack!!}",
                    "${speed!!}",
                    "${life!!}"
                )

                if (show){
                    val intent = Intent(this@HomeActivity, PokemonCatchActivity::class.java).apply {
                        putExtra("pokemon",pokemon).
                        putExtra("user",user)
                    }
                    startActivity(intent)
                    finish()
                }else{
                    FirebaseFirestore.getInstance().collection("users").document(user.username).collection("pokemones").document(pokemon.name).set(pokemon)
                    FirebaseFirestore.getInstance().collection("users").document(user.username).collection("pokemones").get().addOnCompleteListener{ task ->
                        adapter.deletePokemons()
                        for (doc in task.result!!){
                            val pk = doc.toObject(Pokemon::class.java)
                            adapter.addPokemon(pk)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }

            }else{

            }
        }
    }
}



