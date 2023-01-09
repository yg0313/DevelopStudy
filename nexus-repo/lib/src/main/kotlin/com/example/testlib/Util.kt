package com.example.testlib

import org.springframework.stereotype.Component
import java.util.*

@Component
class Util {

    fun generateUuid() : String{
        return UUID.randomUUID().toString()
    }
}