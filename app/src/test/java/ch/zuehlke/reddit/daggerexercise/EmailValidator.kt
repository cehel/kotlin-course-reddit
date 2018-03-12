package ch.zuehlke.reddit.daggerexercise

/**
 * Created by celineheldner on 12.03.18.
 */
class EmailValidator private constructor(){

    private val MAX_LENGTH = 500

    //Singleton
    companion object {

        private var INSTANCE: EmailValidator? = null

        fun getInstance(): EmailValidator {
            if (EmailValidator.INSTANCE == null) {
                EmailValidator.INSTANCE = EmailValidator()
            }
            return EmailValidator.INSTANCE!!
        }
    }


    fun isValid(email: Email): Boolean{
        when{
            !email.receiver.contains("@") -> return false
            email.message.length>MAX_LENGTH -> return false
            else -> return true
        }
    }

}