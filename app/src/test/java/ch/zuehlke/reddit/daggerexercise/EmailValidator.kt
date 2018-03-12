package ch.zuehlke.reddit.daggerexercise

/**
 * Created by celineheldner on 12.03.18.
 */
class EmailValidator{

    private val MAX_LENGTH = 500



    fun isValid(email: Email): Boolean{
        when{
            !email.receiver.contains("@") -> return false
            email.message.length>MAX_LENGTH -> return false
            else -> return true
        }
    }

}