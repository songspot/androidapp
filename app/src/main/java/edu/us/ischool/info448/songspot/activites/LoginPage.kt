package edu.us.ischool.info448.songspot.activites

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*
import edu.us.ischool.info448.songspot.R

class LoginPage : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)


        database = FirebaseDatabase.getInstance().reference

        var loginButton : Button = findViewById(R.id.loginButton)
        var username : EditText = findViewById(R.id.username)
        var password : EditText = findViewById(R.id.password)
        var registerButton : Button = findViewById(R.id.register)

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
    private fun loginUser(username: String, password:String) {
        // check if user exists
        // if user exists take them to the application
        // if they do not, don't do anything
        database.child("users").addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child(username).exists() &&
                    dataSnapshot.child(username).child("password").getValue(true) == password) {
                    println("USER LOGIN AUTHENICATED")
                    val intent = Intent(applicationContext, GenrePickerActivity::class.java)
                    intent.putExtra("username", username)
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
