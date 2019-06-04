package edu.us.ischool.info448.songspot.activites

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
//import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import edu.us.ischool.info448.songspot.R

class RegisterPage : AppCompatActivity() {

    private lateinit var database: DatabaseReference
//    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        database = FirebaseDatabase.getInstance().reference
//        auth = FirebaseAuth.getInstance()

        var registerButton : Button = findViewById(R.id.register)
        var username : EditText = findViewById(R.id.username)
        var display : EditText = findViewById(R.id.displayName)
        var password : EditText = findViewById(R.id.password)

        registerButton.setOnClickListener {
            createNewUser(username.text.toString(), display.text.toString(), password.text.toString())
        }

        // createNewUser("bob", "matty", "234")
        // println("NEW USER")
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
        database.child("users").setValue(thisUser)
    }

}

// Dataclass representing user information. This will be used to update/retrieve data information
data class User(
    var username : String = "",
    var displayName : String? = "",
    var password : String = ""
)