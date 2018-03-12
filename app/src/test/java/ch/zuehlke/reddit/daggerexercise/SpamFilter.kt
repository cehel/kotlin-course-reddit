package ch.zuehlke.reddit.daggerexercise

/**
 * Created by celineheldner on 12.03.18.
 */
class SpamFilter private constructor(){

    companion object {

        private var INSTANCE: SpamFilter? = null

        fun getInstance(): SpamFilter {
            if (SpamFilter.INSTANCE == null) {
                SpamFilter.INSTANCE = SpamFilter()
            }
            return SpamFilter.INSTANCE!!
        }
    }

    fun isSpam(email: Email): Boolean{
        return true
    }

}