package edu.us.ischool.info448.songspot.activites

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.spotify.sdk.android.authentication.AuthenticationRequest
import edu.us.ischool.info448.songspot.R
import edu.us.ischool.info448.songspot.api.App
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationResponse
import edu.us.ischool.info448.songspot.QuestionActivity

class MainActivity : AppCompatActivity() {

    private val requestCode = 1337
    private val clientId = "33d1e95c57e6460e806a7a9699406d17"
    private val redirectUri = "http://localhost:8888/callback/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)


        val request = AuthenticationRequest.Builder(clientId, AuthenticationResponse.Type.TOKEN, redirectUri)
            .setScopes(arrayOf("user-read-private", "playlist-read", "playlist-read-private", "streaming"))
            .build()

        AuthenticationClient.openLoginActivity(this, requestCode, request)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        super.onActivityResult(requestCode, resultCode, intent)

        // Check if result comes from the correct activity

        val response = AuthenticationClient.getResponse(resultCode, intent)
        when (response.type) {
            // Success, response contains accessToken
            AuthenticationResponse.Type.TOKEN -> {

                // TODO: PLACE THESE TWO CALLS IN THEIR PROPER LOCATION

                App.sharedInstance.songRepository.setAccessToken(response.accessToken)
                App.sharedInstance.songRepository.fetchCategorySongs("Indie") {
                    val song = it[0]

                    App.sharedInstance.spotifyRemote.playSong(song.spotifyUri)
                }
            }

            // Auth flow returned an error
            AuthenticationResponse.Type.ERROR -> {

            }

            else -> {

            }
        }

    }
}
