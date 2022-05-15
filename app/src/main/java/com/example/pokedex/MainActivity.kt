package com.example.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener(::login)
    }

    private fun login(view: View){
        val user = User(UUID.randomUUID().toString(),binding.usernameET.text.toString())
        Firebase.firestore.collection("users").whereEqualTo("username",user.username)
            .get().addOnCompleteListener { task ->

                if (task.result.size() == 0){
                    Firebase.firestore.collection("users").document(user.username).set(user)
                    val intent = Intent(this@MainActivity, HomeActivity::class.java).apply {
                        putExtra("user",user)
                    }
                    startActivity(intent)
                    finish()

                }else{
                    lateinit var existingUser : User
                    for (document in task.result){
                        existingUser = document.toObject(User::class.java)
                        break
                    }
                    val intent = Intent(this@MainActivity, HomeActivity::class.java).apply {
                        putExtra("user",existingUser)
                    }
                    startActivity(intent)
                }
            }
    }
}

