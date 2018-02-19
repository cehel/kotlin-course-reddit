package ch.zuehlke.sbb.reddit.features

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by celineheldner on 17.11.17.
 */
open class BaseActivity: AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}