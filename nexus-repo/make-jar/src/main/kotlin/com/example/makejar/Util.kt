package com.example.makejar

import org.springframework.stereotype.Component
import java.util.*

@Component
class Util {

    fun generateUuid():String{
        val uuid = UUID.randomUUID().toString()
        println("first uuid:$uuid")
        return uuid
    }
}