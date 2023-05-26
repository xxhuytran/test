package com.mobile.test.utils

object ResponseCode {
    const val Success = 200
}

enum class BoardingPagePosition(val position: Int) {
    BEFORE_LAST(3),
    LAST(4)
}

enum class PasswordLevel(val rawValue: Int) {
    SHORT(-2),
    LONG(-1),
    EMPTY(0),
    WEEK(1),
    FAIR(2),
    GOOD(3),
    STRONG(4)
}
