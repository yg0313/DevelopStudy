package com.example.testlib

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TestLibApplication

fun main(args: Array<String>) {
    runApplication<TestLibApplication>(*args)
}
