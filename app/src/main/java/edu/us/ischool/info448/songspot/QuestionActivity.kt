package edu.us.ischool.info448.songspot

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class QuestionActivity : AppCompatActivity(), QuestionFragment.OnNextQuestionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        // by default starts Question Fragment
        val questionFragment = QuestionFragment.newInstance("1")
        supportFragmentManager.beginTransaction().run {
            add(R.id.fragment_container, questionFragment, "QUESTION_FRAGMENT")
            commit()
        }
    }

    override fun onNextQuestion(questionNumber: String) {

    }
}
