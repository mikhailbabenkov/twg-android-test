package nz.co.warehouseandroidtest

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.io.IOException
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import java.util.logging.LogManager

object MockServerHelper {
    private var started: Boolean = false

    var server: MockWebServer? = null
        get() {
            if (field == null) {
                try {
                    val server = MockWebServer()
                    server.start(21000)
                    LogManager.getLogManager().getLogger(MockWebServer::class.java.name).level = Level.OFF
                    field = server
                } catch (e: IOException) {
                    throw RuntimeException(e)
                }
            }
            return field
        }

    fun start() {
        if (started) {
            throw IllegalStateException("Have you called after???")
        }
        started = true
    }

    fun stop() {
        started = false
        try {
            if (server != null) {
                server?.shutdown()
                server = null
            }
        } catch (ignore: IOException) {
        }
    }

    fun mockAPIResponse(response: FakeResponse = FakeResponse.Empty, responseCode: Int = 200, vararg values: Pair<String, String>) {
        var responseStr = response.toString()
        for(value in values) {
            responseStr = responseStr.replace("{${value.first}}", value.second)
        }

        val mockResponse = MockResponse()
            .setResponseCode(responseCode)
            .setBody(responseStr)
        server?.enqueue(mockResponse)
    }

    fun mockAPIResponse(response: FakeResponse = FakeResponse.Empty, responseCode: Int = 200, delay: Long, vararg values: Pair<String, String>) {
        generateResponseObject(response, responseCode, *values)
            .setBodyDelay(delay, TimeUnit.SECONDS)
            .setHeadersDelay(delay, TimeUnit.SECONDS).let {
                server?.enqueue(it)
            }
    }

    fun generateResponseObject(response: FakeResponse = FakeResponse.Empty, responseCode: Int = 200, vararg values: Pair<String, String>): MockResponse {
        var responseStr = response.toString()
        for(value in values) {
            responseStr = responseStr.replace("{${value.first}}", value.second)
        }

        return MockResponse()
            .setResponseCode(responseCode)
            .setBody(responseStr)
    }

    fun setDispather(dispatcher: Dispatcher) {
        server?.setDispatcher(dispatcher)
    }

    fun restart(){
        stop()
        start()
    }
}