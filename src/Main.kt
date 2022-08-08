import kotlinx.serialization.json.*
import kotlinx.serialization.*
import java.net.*
import kotlin.random.*

val desks = listOf("AM", /*"DH", "ZW", "DB" */)

val SLOTS_URL = "https://oap.ind.nl/oap/api/desks/%/slots/?productKey=BIO&persons=1"
val PREFIX = ")]}',\n"
val BELL = Char(7)
val START_DATE = "2022-08-18"
val END_DATE = "2022-08-26"

fun main() {
    println("STARTING$BELL")
    while (true) {
        println("---")
        for (desk in desks) {
            val data = loadDeskData(desk) ?: continue
            val first = data.slots.minByOrNull { it.date } ?: continue
            println("$desk: ${first.date} at ${first.startTime}")
            val candidate = data.slots.filter { it.date in START_DATE..END_DATE }.firstOrNull() ?: continue
            println("FOUND!!! $desk: ${candidate.date} at ${first.startTime}$BELL")
        }
        Thread.sleep(Random.nextLong(1500L, 2500L))
    }
}

fun loadDeskData(desk: String): Data? {
    val url = URL(SLOTS_URL.replace("%", desk))
    val result = runCatching {
        val str = url.openConnection().getInputStream().use { it.readAllBytes() }.decodeToString().removePrefix(PREFIX)
        Json.decodeFromString<Data>(str)
    }
    result.exceptionOrNull()?.let { println("$desk ($url): $it") }
    return result.getOrNull()
}
