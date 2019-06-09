package edu.us.ischool.info448.songspot.activites

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.ProgressBar
import com.google.firebase.database.*
import edu.us.ischool.info448.songspot.R
import edu.us.ischool.info448.songspot.api.App

class LoginPage : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var loadingSpinner : ProgressBar
    private lateinit var loginButton : Button
    private lateinit var password : EditText
    private lateinit var username : EditText
    private lateinit var registerButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        database = FirebaseDatabase.getInstance().reference

        loginButton = findViewById(R.id.loginButton)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        registerButton = findViewById(R.id.register)
        loadingSpinner = findViewById(R.id.progressBar1)
        loadingSpinner.visibility = View.INVISIBLE

        loginButton.setOnClickListener {
            loginUser(username.text.toString(), password.text.toString())
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }
    }

    //  Logs user into their account after taking in a username and password.
    // Will check database if username/password combo exists
    private fun loginUser(usernameLogin: String, passwordLogin:String) {
        // check if user exists
        // if user exists take them to the application
        // if they do not, don't do anything
        database.child("users").addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child(usernameLogin).exists() &&
                    dataSnapshot.child(usernameLogin).child("password").getValue(true) == passwordLogin) {
                    println("USER LOGIN AUTHENTICATED")

                    // Change visibility of login UI
                    // Make loading spinner visible and
                    // other UI elements invisible
                    loadingSpinner.visibility = View.VISIBLE
                    username.visibility = View.INVISIBLE
                    password.visibility = View.INVISIBLE
                    loginButton.visibility = View.INVISIBLE
                    registerButton.visibility = View.INVISIBLE

                    val intent = Intent(applicationContext, GenrePickerActivity::class.java)
                    App.sharedInstance.username = usernameLogin

                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "Incorrect Credentials", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }
}
