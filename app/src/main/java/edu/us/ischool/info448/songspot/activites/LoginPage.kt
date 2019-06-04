package edu.us.ischool.info448.songspot.activites

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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

        loginButton.setOnClickListener {
            loginUser(username.text.toString(), password.text.toString())
        }


//        createNewUser("bob", "matty", "234")
        println("NEW USER")
    }

    // Creates a new account
//    private fun createNewUser(username: String, name: String?, password: String) {
//        database.child("users").child(username).setValue(password)
//        //val screenName = if (name.equals(null)) username else name
//        //database.child("users").child(username).setValue(screenName)
//    }

    //  Logs user into their account after taking in a username and password.
    // Will check database if username/password combo exists
    private fun loginUser(username: String, password:String) {
        // check if user exists
        // if user exists take them to the application
        // if they do not, don't do anything
    }
}
