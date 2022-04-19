package com.example.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.databinding.ActivityHomeBinding
import com.example.pokedex.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class HomeActivity : AppCompatActivity() {

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

        binding.seeBtn.setOnClickListener {
            GETRequest(binding.catchPokemonET.text.toString())
        }

        binding.catchBtn.setOnClickListener {
            val intent = Intent(this, PokemonCatchActivity::class.java)
            startActivity(intent)
        }
    }

    fun GETRequest(namePk: String){
        lifecycleScope.launch(Dispatchers.IO) {
            val url = URL("${Constants.POKE_API}/pokemon/${namePk}")
            val client = url.openConnection() as HttpsURLConnection
            client.requestMethod = "GET"
            val json = client.inputStream.bufferedReader().readText()
            Log.e(">>>>", json)
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
                    "fecha",
                    "${defense!!}",
                    "${attack!!}",
                    "${speed!!}",
                    "${life!!}"
                )
                val intent = Intent(this@HomeActivity, PokemonCatchActivity::class.java).apply {
                    putExtra("pokemon",pokemon).
                    putExtra("user",user)
                }
                startActivity(intent)
            }else{

            }
        }
    }
}



