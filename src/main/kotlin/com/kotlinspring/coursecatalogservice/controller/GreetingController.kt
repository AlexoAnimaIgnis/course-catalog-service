package com.kotlinspring.coursecatalogservice.controller

import com.kotlinspring.coursecatalogservice.service.GreetingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/greetings")
class GreetingController(
    val greetingService: GreetingService
) {

    @GetMapping("/{name}")
    fun retrieveGreetings(@PathVariable("name") name: String) : String {
        return greetingService.retrieveGreetings(name)
    }
}