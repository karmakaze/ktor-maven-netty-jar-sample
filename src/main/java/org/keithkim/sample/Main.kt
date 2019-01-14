package org.keithkim.sample

import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.text.DateFormat
import java.time.Instant

fun main(args: Array<String>) {
    val server = embeddedServer(
            Netty,
            watchPaths = listOf("target/classes"),
            port = 8080,
            module = Application::mymodule
    )
    server.start(wait = true)
}

fun Application.mymodule() {
    install(DefaultHeaders)
    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }

    routing {
        get("/") {
            log.info("Entered GET /")
            call.respond(hashMapOf(
                    "message" to "Hello, new world.",
                    "now" to Instant.now().toString()))
            log.info("exiting GET /")
        }
    }
}
