package com.sosorevgm.profanityfilterexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sosorevgm.profanityfilter.ProfanityFilter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val filter = ProfanityFilter(this)
//        filter.setNewProfanityList("assets_profanity_list")
//        filter.setNewProfanityList(R.raw.raw_profanity_list)
        val filteredString = filter.getFilteredString("I want to filter these string")

        main_text_view.text = filteredString
    }
}