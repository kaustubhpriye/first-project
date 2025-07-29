package com.capsulehq

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*
import java.lang.management.ManagementFactory

@JsonIgnoreProperties(ignoreUnknown = true)
data class HealthStatus(val status: String = "ok")

@JsonIgnoreProperties(ignoreUnknown = true)
data class SystemStatus(
    val uptimeMillis: Long,
    val os: String,
    val availableProcessors: Int,
    val freeMemory: Long,
    val totalMemory: Long,
    val message: String = "System is operational"
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class AstrologyProfile(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val gender: String,
    val dateOfBirth: String, // ISO format (YYYY-MM-DD)
    val timeOfBirth: String, // HH:mm
    val locationOfBirth: String,
    val chartData: String // Placeholder for now
)

// Astro chart provider abstraction
interface AstroChartProvider {
    suspend fun getChartData(name: String, gender: String, dateOfBirth: String, timeOfBirth: String, locationOfBirth: String): String
}

// Aztro API implementation (placeholder, as Aztro does not provide full natal charts)
class AztroAstroChartProvider : AstroChartProvider {
    private val client = HttpClient(CIO)
    override suspend fun getChartData(name: String, gender: String, dateOfBirth: String, timeOfBirth: String, locationOfBirth: String): String {
        // Aztro API expects 'sign' and 'day', so we use 'aries' and 'today' as placeholders
        val response: HttpResponse = client.submitForm(
            url = "https://aztro.sameerkumar.website/",
            formParameters = Parameters.build {
                append("sign", "aries") // Placeholder
                append("day", "today")
            }
        )
        return response.bodyAsText()
    }
}

val astroChartProvider: AstroChartProvider = AztroAstroChartProvider()

// Placeholder chart generation function
fun generateChart(profile: AstrologyProfile): String {
    return "Mock chart for ${'$'}{profile.name} born on ${'$'}{profile.dateOfBirth} at ${'$'}{profile.timeOfBirth} in ${'$'}{profile.locationOfBirth}"
}

class App {
    val greeting: String
        get() = "Hello, Capsule HQ!"
}

fun main() {
    println("Starting Ktor server...")
    System.setProperty("io.ktor.development", "true")
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(CallLogging)
        install(ContentNegotiation) {
            jackson()
        }
        routing {
            get("/health") {
                call.respond(HealthStatus())
            }
            get("/system-status") {
                try {
                    val runtime = Runtime.getRuntime()
                    val uptime = System.currentTimeMillis() - ManagementFactory.getRuntimeMXBean().startTime
                    val os = System.getProperty("os.name")
                    val processors = runtime.availableProcessors()
                    val freeMem = runtime.freeMemory()
                    val totalMem = runtime.totalMemory()
                    call.respond(HttpStatusCode.OK, 
                        SystemStatus(
                            uptimeMillis = uptime,
                            os = os,
                            availableProcessors = processors,
                            freeMemory = freeMem,
                            totalMemory = totalMem
                        )
                    )
                } catch (e: Exception) {
                    println("Error in system-status endpoint: ${e.message}")
                    e.printStackTrace()
                    call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "An internal error occurred"))
                }
            }
        }
    }.start(wait = true)
}
