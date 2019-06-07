package edu.us.ischool.info448.songspot.activites

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
//import com.google.firebase.auth.FirebaseUser
//import com.google.firebase.auth.FirebaseAuth
import edu.us.ischool.info448.songspot.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.*


class RegisterPage : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private var registerButton : Button = findViewById(R.id.loginButton)
    private var username : EditText = findViewById(R.id.username)
    private var display : EditText = findViewById(R.id.displayName)
    private var password : EditText = findViewById(R.id.password)
//    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        database = FirebaseDatabase.getInstance().reference
//        auth = FirebaseAuth.getInstance()

//        var registerButton : Button = findViewById(R.id.loginButton)
//        var username : EditText = findViewById(R.id.username)
//        var display : EditText = findViewById(R.id.displayName)
//        var password : EditText = findViewById(R.id.password)

        registerButton.setOnClickListener {
            createNewUser(username.text.toString(), display.text.toString(), password.text.toString())
        }

    }

//    private fun createAccount(email: String, password: String) {
//        // [START create_user_with_email]
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this
//            ) { task ->
//                if (task.isSuccessful) {
//                    //User registered successfully
//                    // Need to put display name in database
//
//
//                // Will handle already existing emails
//                } else {
//                    Log.i("Response", "Failed to create user:" + task.exception!!.message)
//                }
//            }
//    }

    //  Creates a new account
    private fun createNewUser(username: String, name: String?, password: String) {
        // Context
        val thisContext = this

        if (validForm()) {
            database.child("users").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Have to hash and turn into a string because Firebase can't handle certain special characters
                    val hashedUser = username.hashCode().toString()
                    // If email doesn't eixst, create account
                    if (!dataSnapshot.child(hashedUser).exists()) {
                        val thisUser = User(username, name, password)
                        Log.i("TAG", hashedUser)
                        database.child("users").child(hashedUser).setValue(thisUser)
                        val intent = Intent(thisContext, LoginPage::class.java)
                        startActivity(intent)
                        // If email does exist in the database, tell user username already exists.
                    } else {
                        Toast.makeText(thisContext, "This username already exists!", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onCancelled(p0: DatabaseError) {
                }
            })
        }
    }

    private fun validForm() : Boolean {
        if (username.text.isEmpty()) {
            // do something
            return false
        }
        if (display.text.isEmpty()) {
            // do something
            return false
        }
        if (password.text.isEmpty()) {
            // do something
            return false
        }
        return true
    }
}

// Dataclass representing user information. This will be used to update/retrieve data information
data class User(
    var username : String = "",
    var displayName : String? = "",
    var password : String = ""
)