package edu.us.ischool.info448.songspot.api

import android.content.Context
import android.util.Log
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.Track

class SpotifyRemote {
    private val clientId = "33d1e95c57e6460e806a7a9699406d17"
    private val redirectUri = "http://localhost:8888/callback/"
    private var spotifyAppRemote: SpotifyAppRemote? = null

    private var context: Context

    constructor(context: Context) {
        this.context = context
    }

    fun playSong(uri: String) {
        spotifyAppRemote?.let {
            val playlistURI = "spotify:playlist:37i9dQZF1DXcBWIGoYBM5M"
            it.playerApi.play(playlistURI)

            // Subscribe to PlayerState
            it.playerApi.subscribeToPlayerState().setEventCallback {
                val track: Track = it.track
                Log.d("MainActivity", track.name + " by " + track.artist.name)
            }
        }
    }

    fun pauseSong() {
        spotifyAppRemote?.let {
            it.playerApi.pause()
        }
    }

    fun connect(onSuccess: () -> Unit) {
        val connectionParams = ConnectionParams.Builder(clientId)
            .setRedirectUri(redirectUri)
            .showAuthView(true)
            .build()

        SpotifyAppRemote.connect(this.context, connectionParams, object : Connector.ConnectionListener {
            override fun onConnected(appRemote: SpotifyAppRemote) {
                spotifyAppRemote = appRemote
                Log.d("MainActivity", "Connected! Yay!")
                // Now you can start interacting with App Remote
                onSuccess()
            }

            override fun onFailure(throwable: Throwable) {
                Log.e("MainActivity", throwable.message, throwable)
                // Something went wrong when attempting to connect! Handle errors here
            }
        })
    }

    fun disconnect() {
        spotifyAppRemote?.let {
            it.playerApi.pause()
            SpotifyAppRemote.disconnect(it)
        }
    }
}
