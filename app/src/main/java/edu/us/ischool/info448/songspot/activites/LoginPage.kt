package edu.us.ischool.info448.songspot.activites

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
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
//        auth = FirebaseAuth.getInstance()
//
//        var loginButton : Button = findViewById(R.id.loginButton)
//        var username : EditText = findViewById(R.id.username)
//        var password : EditText = findViewById(R.id.password)
//        var registerButton : Button = findViewById(R.id.register)

        loginButton.setOnClickListener {
            loginUser(username.text.toString(), password.text.toString())
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }
    }


//    private fun signIn(email: String, password: String) {
//        // [START sign_in_with_email]
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.w("TAG", "signInWithEmail:success")
//                    // Take to new activity
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w("TAG", "signInWithEmail:failure", task.exception)
//                }
//            }
//    }

//      Logs user into their account after taking in a username and password.
//     Will check database if username/password combo exists
    private fun loginUser(username: String, password:String) {
        if (validForm()) {
            database.child("users").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.child(username).exists() && dataSnapshot.child(username).child("password").getValue(
                            true
                        ) == password
                    ) {
                        println("USER LOGIN AUTHENICATED")
                        /**
                         *  ADD IN USERNAME AS ARG TO GIVE TO GENRE PICKING PAGE
                         *  THEN START GENRE PAGE ACTIVITY
                         */
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                }
            })
        }
    }

    // Checks whether form is properly filled
    private fun validForm() : Boolean {
        if (username.text.isEmpty()) {
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
