package edu.us.ischool.info448.songspot

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.Track

class MainActivity : AppCompatActivity() {

    /* UNCOMMENT THIS AND THE BOTTOM TO EXECUTE THIS CODE
    /**
     *  SAMPLE CODE BELOW FOR OPENING APP THEN HARDCODED PLAYLIST WILL START PLAYING
     */
    private val clientId = "33d1e95c57e6460e806a7a9699406d17"
    //private val redirectUri = "http://com.yourdomain.yourapp/callback";
    // redirect: "https://com.spotify.android.spotifysdkkotlindemo/callback"
    private val redirectUri = "http://localhost:8888/callback/"
    private var spotifyAppRemote: SpotifyAppRemote? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        val connectionParams = ConnectionParams.Builder(clientId)
            .setRedirectUri(redirectUri)
            .showAuthView(true)
            .build()

        println("LETS CONNECT PLEASE")
        SpotifyAppRemote.connect(this, connectionParams, object : Connector.ConnectionListener {
            override fun onConnected(appRemote: SpotifyAppRemote) {
                spotifyAppRemote = appRemote
                Log.d("MainActivity", "Connected! Yay!")
                // Now you can start interacting with App Remote
                println("CONNECTING")
                connected()
                println("CONNECTED")
            }

            override fun onFailure(throwable: Throwable) {
                Log.e("MainActivity", throwable.message, throwable)
                // Something went wrong when attempting to connect! Handle errors here
            }
        })
    }

    private fun connected() {
        spotifyAppRemote?.let {
            // Play a playlist - playlist URI taken from the spotify app
            //val playlistURI = "spotify:playlist:37i9dQZF1DX2sUQwD7tbmL"
            val playlistURI = "spotify:user:spotify:playlist:37i9dQZF1DXcBWIGoYBM5M"
            it.playerApi.play(playlistURI)
            // Subscribe to PlayerState
            it.playerApi.subscribeToPlayerState().setEventCallback {
                val track: Track = it.track
                Log.d("MainActivity", track.name + " by " + track.artist.name)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        spotifyAppRemote?.let {
            SpotifyAppRemote.disconnect(it)
        }
    }
    */
}
 