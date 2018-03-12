package ch.zuehlke.reddit.daggerexercise

/**
 * Created by celineheldner on 12.03.18.
 */
class MailBox constructor(
        private val emailValidator: EmailValidator,
        private val spamFilter: SpamFilter){

    fun send(email: Email): Boolean {
        if(emailValidator.isValid(email)){
            return true
        }
        return false;
    }

    fun receive(email: Email){
        if (spamFilter.isSpam(email)){
            //add to SpamFolder
        } else {
            //add to Ingoing
        }
    }

}