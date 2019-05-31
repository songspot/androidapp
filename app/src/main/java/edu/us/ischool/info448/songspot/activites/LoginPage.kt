package edu.us.ischool.info448.songspot.activites

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import edu.us.ischool.info448.songspot.R

class LoginPage : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        database = FirebaseDatabase.getInstance().reference
        createNewUser("bob", "matty", "234")
        println("NEW USER")
    }

    private fun createNewUser(username: String, name: String?, password: String) {
        database.child("users").child(username).setValue(password)
        //val screenName = if (name.equals(null)) username else name
        //database.child("users").child(username).setValue(screenName)
    }
}
