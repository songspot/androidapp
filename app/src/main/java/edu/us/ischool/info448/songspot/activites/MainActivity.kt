package edu.us.ischool.info448.songspot.activites

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.spotify.sdk.android.authentication.AuthenticationRequest
import edu.us.ischool.info448.songspot.R
import edu.us.ischool.info448.songspot.api.App
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationResponse
import edu.us.ischool.info448.songspot.QuestionActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        val intent = Intent(this, QuestionActivity::class.java)
        startActivity(intent)
    }
}
