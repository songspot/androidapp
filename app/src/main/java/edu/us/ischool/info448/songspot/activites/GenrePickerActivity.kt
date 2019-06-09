package edu.us.ischool.info448.songspot.activites

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import edu.us.ischool.info448.songspot.R
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import edu.us.ischool.info448.songspot.api.App

/** Displays a list of quiz categories (music genres) to choose from, and a settings button. **/
class GenrePickerActivity : AppCompatActivity() {

    private val requestCode = 1337

    // List of available music genres to take quizzes from (probably move to some kind of data repository).
    private val genreList : Array<String> = arrayOf("Pop", "Rap", "Electronic", "Indie", "the 70s", "the 80s")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre_picker)

        val leaderboardButton = findViewById<ImageButton>(R.id.leaderboard_button)
        leaderboardButton.setOnClickListener {
            val intent = Intent(this, LeaderboardActivity::class.java)
            startActivity(intent)
        }

        val viewManager = GridLayoutManager(this, 2) // GridLayoutManager 2 columns wide.
        val viewAdapter = GenrePickerAdapter(genreList) // Use custom adapter.

        val recyclerView = findViewById<RecyclerView>(R.id.genre_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        recyclerView.addItemDecoration(GridSpacingItemDecoration(2, 48))

        // We need to authenicate with their spotify account
        val request = AuthenticationRequest.Builder(App.sharedInstance.clientId, AuthenticationResponse.Type.TOKEN, App.sharedInstance.redirectUri)
            .setScopes(arrayOf("user-read-private", "playlist-read", "playlist-read-private", "streaming"))
            .build()
        AuthenticationClient.openLoginActivity(this, requestCode, request)
    }

    /** For dynamically adjusting the margins/spacing between items in the RecyclerView. **/
    inner class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view) // Item position
            val column = position % spanCount // Item column

            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount

            if (position < spanCount) { outRect.top = spacing } // Top edge
            outRect.bottom = spacing // Item bottom
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        super.onActivityResult(requestCode, resultCode, intent)

        // Check if result comes from the correct activity

        val response = AuthenticationClient.getResponse(resultCode, intent)
        when (response.type) {
            // Success, response contains accessToken
            AuthenticationResponse.Type.TOKEN -> {
                App.sharedInstance.songRepository.setAccessToken(response.accessToken)
            }

            // Auth flow returned an error
            AuthenticationResponse.Type.ERROR -> {

            }

            else -> {

            }
        }
    }
}