package edu.us.ischool.info448.songspot.activites

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.widget.Button
import android.widget.TextView
import edu.us.ischool.info448.songspot.R
import edu.us.ischool.info448.songspot.api.App

import kotlinx.android.synthetic.main.activity_results.*

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val genreLabel = findViewById<TextView>(R.id.genreTitleLabel)
        genreLabel.text = App.sharedInstance.selectedGenre
        genreLabel.setTextColor(resources.getColor(android.R.color.white))

        val scoreLabel = findViewById<TextView>(R.id.scoreLabel)
        val points = intent.getIntExtra("SCORE", 0)
        val text = if (points > 0) " points! Congrats!" else " points :( Try again!"
        scoreLabel.text = "You finished with ".plus(points).plus(text)
        scoreLabel.setTextColor(resources.getColor(android.R.color.white))

        val leaderboardAdvice = findViewById<TextView>(R.id.leaderboardAdvice)
        leaderboardAdvice.text = "Go check out the leaderboard back on the home page " +
                "to see where you stack up against everyone else!"
        leaderboardAdvice.setTextColor(resources.getColor(android.R.color.white))

        val finishButton = findViewById<Button>(R.id.finishQuizButton)

        finishButton.setOnClickListener {
            val intent = Intent(this, GenrePickerActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        val rootView = genreLabel.rootView
        rootView.setBackgroundColor(resources.getColor(android.R.color.background_dark))
    }

    override fun onDestroy() {
        super.onDestroy()
        App.sharedInstance.spotifyRemote.disconnect()
    }
}
