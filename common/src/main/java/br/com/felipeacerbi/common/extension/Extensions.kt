package br.com.felipeacerbi.common.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.espresso.idling.CountingIdlingResource
import br.com.felipeacerbi.common.viewstate.ViewState
import br.com.felipeacerbi.common.viewstate.ViewStateReducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

infix fun ViewGroup.inflate(layoutResId: Int): View =
    LayoutInflater.from(context).inflate(layoutResId, this, false)

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit): LiveData<T> =
    liveData.apply { observe(this@observe, Observer { observable -> observable?.let { action(it) } }) }

fun CoroutineScope.launchSafely(
    idlingResource: CountingIdlingResource? = null,
    error: (java.lang.Exception) -> Unit = {},
    block: suspend CoroutineScope.() -> Unit
) {
    launch {
        try {
            idlingResource?.increment()
            block.invoke(this)
        } catch (e: Exception) {
            error.invoke(e)
        } finally {
            idlingResource?.decrement()
        }
    }
}

fun <T : ViewState> MutableLiveData<T>.update(reducer: ViewStateReducer<T>) {
    value = value?.apply {
        reducer.updateView.invoke(this)
    }
}