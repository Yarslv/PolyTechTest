package com.polytechtest.util
sealed interface AppAction {
    sealed interface Navigate : AppAction {
        class ToBookList(val encodedName: String) : Navigate
        class ToBuyBook(val url: String) : Navigate
    }

    class Message(val e: Throwable) : AppAction
}

