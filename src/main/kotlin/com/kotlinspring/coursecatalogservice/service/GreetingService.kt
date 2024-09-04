package com.kotlinspring.coursecatalogservice.service

import org.springframework.stereotype.Service

@Service
class GreetingService {

    fun retrieveGreetings(name: String) = "Hello $name"
}