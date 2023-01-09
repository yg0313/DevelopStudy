package com.example.makejar

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MakeJarApplication

fun main(args: Array<String>) {
    runApplication<MakeJarApplication>(*args)
}
