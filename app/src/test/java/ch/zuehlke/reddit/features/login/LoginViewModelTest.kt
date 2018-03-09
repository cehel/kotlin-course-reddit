package ch.zuehlke.reddit.features.login

import ch.zuehlke.reddit.BaseTest
import org.junit.Test


/**
 * Created by celineheldner on 09.03.18.
 */
class LoginViewModelTest: BaseTest(){

    /*TODO:chapter_3_testing_exercise
        Create a mock android.arch.lifecycle.Observer

        Check the slides for guidance
     */

    override fun setup(){
        super.setup()
        /*TODO:chapter_3_testing_exercise
            let the observer observe the LoginState from the LoginViewModel
        */

    }

    @Test
    fun testWrongCredentialsLogin(){
        /*TODO:chapter_3_testing_exercise
            call login and verify the behaviour
        */
    }

    @Test
    fun testWrongPassword(){
        /*TODO:chapter_3_testing_exercise
                   call login and verify the behaviour
               */
    }

    @Test
    fun testWrongUser(){

        /*TODO:chapter_3_testing_exercise
           call login and verify the behaviour
       */
    }

    @Test
    fun correctCredentials(){
        /*TODO:chapter_3_testing_exercise
           call login and verify the behaviour
       */
    }
}