package edu.us.ischool.info448.songspot.activites

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*
//import com.google.firebase.auth.FirebaseAuth
import edu.us.ischool.info448.songspot.R

class RegisterPage : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    //private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        database = FirebaseDatabase.getInstance().reference
        //auth = FirebaseAuth.getInstance()

        var registerButton : Button = findViewById(R.id.loginButton)
        var username : EditText = findViewById(R.id.username)
        var display : EditText = findViewById(R.id.displayName)
        var password : EditText = findViewById(R.id.password)

        registerButton.setOnClickListener {
            if (validForm(username.text.toString(), display.text.toString(), password.text.toString())) {
                createNewUser(username.text.toString(), display.text.toString(), password.text.toString())
            } else {
                println("yes")
                Toast.makeText(applicationContext, "Incomplete Template", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun validForm(username: String, display: String, password: String) : Boolean {
        if (username.isEmpty() || display.isEmpty() || password.isEmpty()) {
            return false
        }
        var result = true
        database.child("users").addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child(username).exists()) {
                    result = false
                }
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })
        return result
    }

    // Creates a new account
    private fun createNewUser(username: String, name: String?, password: String) {
        //database.child("users").child(username).setValue(password)
        //val screenName = if (name.equals(null)) username else name
        //database.child("users").child(username).setValue(screenName)
        // check if selected username exists within database
        // if it doesn't exist in database, create it username/displayname/password combo
        // if it does, notify user that this username is already in use
        val thisUser = User(username, name, password)
        database.child("users").child(username).setValue(thisUser)
        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
    }

}

// Dataclass representing user information. This will be used to update/retrieve data information
data class User(
    var username : String = "",
    var displayName : String? = "",
    var password : String = ""
)