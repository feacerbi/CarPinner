package br.com.felipeacerbi.common.viewstate

interface ViewStateReducer <T : ViewState> {
    val updateView: T.() -> Unit
}