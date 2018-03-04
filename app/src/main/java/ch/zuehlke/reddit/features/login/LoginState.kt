package ch.zuehlke.reddit.features.login

/**
 * Created by chsc on 04.03.18.
 */
sealed class LoginState{

    object WrongCredentials: LoginState()

    object WrongUserName: LoginState()

    object WrongPassword: LoginState()

    object Loading: LoginState()

    data class LoggedIn(val username: String, val password: String) : LoginState()

}