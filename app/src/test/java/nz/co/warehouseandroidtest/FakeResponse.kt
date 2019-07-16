package nz.co.warehouseandroidtest

import java.io.File
import java.nio.file.Paths

enum class FakeResponse {
    Empty,
    NewUser,
    Price,
    Search;
    override fun toString(): String {
        return File("../app/src/test/api-responses/$name.json").readText()
    }
}