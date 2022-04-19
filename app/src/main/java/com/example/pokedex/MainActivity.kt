package com.example.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.pokedex.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /*binding.loginBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }*/
        binding.loginBtn.setOnClickListener(::login)
    }

    private fun login(view: View){
        val user = User(UUID.randomUUID().toString(),binding.usernameET.text.toString())
        val query = FirebaseFirestore.getInstance().collection("users").whereEqualTo("username",user.username)
        query.get().addOnCompleteListener{ task ->

            if (task.result?.size() == 0){
                FirebaseFirestore.getInstance().collection("users").document(user.username).set(user)
                val intent = Intent(this@MainActivity, HomeActivity::class.java).apply {
                    putExtra("user",user)
                }
                startActivity(intent)
                finish()
            }else{
                lateinit var existingUser : User
                for (document in task.result!!){
                    existingUser = document.toObject(User::class.java)
                    break
                }
                val intent = Intent(this@MainActivity, HomeActivity::class.java).apply {
                    putExtra("user",existingUser)
                }
                startActivity(intent)
                finish()
            }

        }

        /*val json = Gson().toJson(user)
        lifecycleScope.launch(Dispatchers.IO){
            HTTPSWebUtil().PUTRequest("${Constans.BASE_URL}/users/${user.username}.json",json)
        }*/
    }
}