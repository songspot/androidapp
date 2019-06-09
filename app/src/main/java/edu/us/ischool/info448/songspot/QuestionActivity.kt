package edu.us.ischool.info448.songspot

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import edu.us.ischool.info448.songspot.activites.MainActivity

class QuestionActivity : AppCompatActivity(), QuestionFragment.OnNextQuestionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        // by default starts Question Fragment
        val questionFragment = QuestionFragment.newInstance(1,2, 0)
        supportFragmentManager.beginTransaction().run {
            add(R.id.fragment_container, questionFragment, "QUESTION_FRAGMENT")
            commit()
        }
    }

    override fun onNextQuestion(questionNumber: Int, questionsCount:Int, points: Int) {
        if (questionNumber < (questionsCount + 1)) {
            Log.d("debugging", "Reached redirect!")
            val questionFragment = QuestionFragment.newInstance(questionNumber, questionsCount, points)
            supportFragmentManager.beginTransaction().run {
                replace(R.id.fragment_container, questionFragment, "QUESTION_FRAGMENT")
                commit()
            }
        } else {
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
