package com.shenawynkov.errorhandler

enum class Errors(val code: Int) {
    Success(200),
    MobileExist(454),
    EmailExist(455),
    WrongForgetPassCode(459),
    Unnauthorized_Token(403),
    PageNotFound(404),
    WrongPassword(457),
    ContentNotFound(419),
    BlockedUser(421),
    InternalServerError(500),
    NETWORKERROR(5),

    WrongEmailForamt(460),
    WrongMobileForamt(461),

    WrongCustomerID(462)
}