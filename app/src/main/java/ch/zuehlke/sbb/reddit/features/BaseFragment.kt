package ch.zuehlke.sbb.reddit.features


import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by celineheldner on 17.11.17.
 */
open class BaseFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
    }

}