package ch.zuehlke.sbb.reddit

/**
 * Created by chsc on 08.11.17.
 */

interface BaseView<in T> where T: BasePresenter {

    fun setPresenter(presenter: T)


    val isActive: Boolean
}
