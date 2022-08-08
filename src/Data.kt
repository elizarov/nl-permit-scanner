import kotlinx.serialization.*

//{"status":"OK","data":[..]}
@Serializable
data class Data(
    @SerialName("data")
    val slots: List<Slot>,
    val status: String
)

// {"key":"4d583b229a147f372ed86d5394f87f69","date":"2022-10-27","startTime":"13:50","endTime":"14:00","parts":1}
@Serializable
data class Slot(
    val date: String,
    val endTime: String,
    val key: String,
    val parts: Int,
    val startTime: String
)