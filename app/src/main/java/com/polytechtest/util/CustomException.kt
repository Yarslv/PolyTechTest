package com.polytechtest.util

sealed class CustomException : Throwable() {
    data object NetworkException : CustomException()
    data object RoomException : CustomException()
}