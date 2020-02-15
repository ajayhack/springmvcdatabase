package com.springmvcdatabase.springmvcdatabase

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class SpringmvcdatabaseApplication

fun main(args: Array<String>) {
	runApplication<SpringmvcdatabaseApplication>(*args)
}
