package com.example.pokedex


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.adapters.PokemonAdapter
import com.example.pokedex.databinding.ActivityHomeBinding
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.User
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection

class HomeActivity : AppCompatActivity(){

    private val binding: ActivityHomeBinding by lazy{
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private lateinit var user: User
    private lateinit var pokemon: Pokemon
    private var adapter = PokemonAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pokemonRecycler = binding.pokemonRecycler
        pokemonRecycler.setHasFixedSize(true)
        pokemonRecycler.layoutManager = LinearLayoutManager(this)
        pokemonRecycler.adapter = adapter
        setContentView(binding.root)

        user = intent.extras?.get("user") as User
        adapter.user = user
        var pokemon: Pokemon? = null

        Firebase.firestore.collection("users").document(user.username).collection("pokemones")
            .orderBy("dateCatch", Query.Direction.DESCENDING)
            .get().addOnCompleteListener{ task ->
          for (doc in task.result!!){
              val pk = doc.toObject(Pokemon::class.java)
              adapter.addPokemon(pk)
              adapter.notifyDataSetChanged()
          }
        }

        binding.seeBtn.setOnClickListener {
            if(binding.catchPokemonET.text.isBlank()){
                Toast.makeText(this, "Empty field or non existent pokemon", Toast.LENGTH_SHORT).show()
            }else{
                GETRequest(binding.catchPokemonET.text.toString(), true)
            }
        }

        binding.catchBtn.setOnClickListener {
            if(binding.catchPokemonET.text.isBlank()){
                Toast.makeText(this, "Empty field or non existent pokemon", Toast.LENGTH_SHORT).show()
            }else{
                GETRequest(binding.catchPokemonET.text.toString(), false)
            }
        }

        binding.searchBtn.setOnClickListener{
            searchPokemon(binding.searchEt.text.toString())
        }

    }

    fun GETRequest(namePk: String, show: Boolean){
        lifecycleScope.launch(Dispatchers.IO) {
            val url = URL("${Constants.POKE_API}/pokemon/${namePk}")
            val client = url.openConnection() as HttpsURLConnection
            client.requestMethod = "GET"
            val json = client.inputStream.bufferedReader().readText()
            val jsonObject = JSONObject(json)
            val name = jsonObject.optJSONObject("species")?.optString("name")
            val type = jsonObject.optJSONArray("types")?.getJSONObject(0)?.optJSONObject("type")
                ?.optString("name")

            val img = jsonObject.optJSONObject("sprites")?.optString("front_default")
            val stat = jsonObject.optJSONArray("stats")
            val life = stat?.getJSONObject(0)?.optInt("base_stat")
            val attack = stat?.getJSONObject(1)?.optInt("base_stat")
            val defense = stat?.getJSONObject(2)?.optInt("base_stat")
            val speed = stat?.getJSONObject(5)?.optInt("base_stat")
            //Log.e(">>>>","${name}-${type}-${life}-${speed}-${attack}-${defense}")
            pokemon = Pokemon(
            user.username,
            img!!,
            name!!,
            type!!,
            Date().time,
            "${defense!!}",
            "${attack!!}",
            "${speed!!}",
            "${life!!}")

            if(show){
                showPokemon()
            }else{
                catchPokemon()
            }

        }
    }

    private fun showPokemon(){
            val intent = Intent(this@HomeActivity, PokemonCatchActivity::class.java).apply {
                putExtra("pokemon",pokemon)
                putExtra("user",user)
            }
            startActivity(intent)
    }

    private fun catchPokemon(){
        lifecycleScope.launch(Dispatchers.IO){
            Firebase.firestore.collection("users").document(user.username).collection("pokemones")
                .document(pokemon.name).set(pokemon)
            Firebase.firestore.collection("users").document(user.username).collection("pokemones")
                .orderBy("dateCatch", Query.Direction.DESCENDING)
                .get().addOnCompleteListener{ task ->
                    adapter.deletePokemons()
                    for (doc in task.result!!){
                        val pk = doc.toObject(Pokemon::class.java)
                        adapter.addPokemon(pk)
                        adapter.notifyDataSetChanged()
                    }
                }

        }

    }

    private fun searchPokemon(namePk:String){
        lifecycleScope.launch(Dispatchers.IO){
            val query = Firebase.firestore.collection("users").document(user.username).collection("pokemones").whereEqualTo("name",namePk)
            query.get().addOnCompleteListener { task ->
                if (task.result?.size()!=0){
                    lateinit var pokemonSearch : Pokemon
                    adapter.deletePokemons()
                    for (document in task.result!!){
                        pokemonSearch = document.toObject(Pokemon::class.java)
                        adapter.addPokemon(pokemonSearch)
                        adapter.notifyDataSetChanged()
                        break
                    }
                }else{
                    Toast.makeText(this@HomeActivity,"Empty field or pokemon not catched",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}