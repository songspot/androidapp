package edu.us.ischool.info448.songspot.activites

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import edu.us.ischool.info448.songspot.R

class LoginPage : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private var loginButton : Button = findViewById(R.id.loginButton)
    private var username : EditText = findViewById(R.id.username)
    private var password : EditText = findViewById(R.id.password)
    private var registerButton : Button = findViewById(R.id.register)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        database = FirebaseDatabase.getInstance().reference

        loginButton.setOnClickListener {
            loginUser(username.text.toString(), password.text.toString())
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }
    }


//      Logs user into their account after taking in a username and password.
//     Will check database if username/password combo exists
    private fun loginUser(username: String, password:String) {
        val thisContext = this
        database.child("users").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child(username).exists() && dataSnapshot.child(username).child("password").getValue(true) == password) {
                    println("USER LOGIN AUTHENICATED")
                    /**
                     *  ADD IN USERNAME AS ARG TO GIVE TO GENRE PICKING PAGE
                     *  THEN START GENRE PAGE ACTIVITY
                     */
                } else {
                    Toast.makeText(thisContext, "Login information was incorrect or accountt does not exist!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }
}
