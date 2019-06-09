package edu.us.ischool.info448.songspot.activites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.*
import edu.us.ischool.info448.songspot.R

class LeaderboardActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference

    // Hard-coded for now.
    val genreList : Array<String> = arrayOf(
        "Today's Pop Hits",
        "Rap Caviar",
        "Hot Country",
        "Ultimate Indie",
        "Rock Classics",
        "All Out 70s")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        database = FirebaseDatabase.getInstance().reference
        val leaderboardListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.getValue()
                // Sort users by score here.
            }

            override fun onCancelled(p0: DatabaseError) {}
        }
        database.addValueEventListener(leaderboardListener)

        val backButton = findViewById<ImageButton>(R.id.leaderboard_back_button)
        backButton.setOnClickListener {
            val intent = Intent(this, GenrePickerActivity::class.java)
            startActivity(intent)
        }

        val scoresList = findViewById<ListView>(R.id.scores_list)

        val listAdapter = ScoresAdapter(this, genreList, arrayOf(20, 110, 70, 47, 83, 92))
        scoresList.adapter = listAdapter
    }

    class ScoresAdapter(context: Context, private val genreList: Array<String>, private val scoreList: Array<Int>) : BaseAdapter() {
        private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int { return genreList.size }

        override fun getItem(p0: Int): Any { return genreList[p0] }

        override fun getItemId(p0: Int): Long { return p0.toLong() }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val rowView = inflater.inflate(R.layout.leaderboard_list_item, p2, false)
            val usernameView = rowView.findViewById<TextView>(R.id.leaderboard_item_username)
            val genreView = rowView.findViewById<TextView>(R.id.leaderboard_item_genre)
            val scoreView = rowView.findViewById<TextView>(R.id.leaderboard_item_score)

            usernameView.text = (p0 + 1).toString() + ". username"
            genreView.text = genreList[p0]
            scoreView.text = scoreList[p0].toString()

            return rowView
        }
    }
}
