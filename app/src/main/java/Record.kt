import java.io.Serializable
import java.util.*

data class Record(
    var id: String = "",
    var uid: String = "",
    var date: String = "",
    var name: String = "",
    var content: String = "",
    var money: String = "",
    var type:String="",
    var state:String=""
):Serializable