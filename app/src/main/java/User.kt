import java.io.Serializable

data class User(
    var uid: String = "",
    var name: String = "",
    var parent: String = "",
    var money: String = "0",
    var type: String = "0",
    var incomes: MutableMap<String, String>,
    var expenses: MutableMap<String, String>,
    var children: MutableMap<String, String>
) : Serializable {
    var incomeRecords:MutableList<Record>
    var expenseRecords:MutableList<Record>
    var tasks:MutableList<Record>
    var applys:MutableList<Record>
    init {
        incomeRecords = mutableListOf<Record>()
        expenseRecords = mutableListOf<Record>()
        tasks = mutableListOf<Record>()
        applys = mutableListOf<Record>()
    }



    constructor(
        uid: String = "",
        name: String = "",
        parent: String = "",
        money: String = "0",
        type: String = "0"
    ) : this(uid, name, parent, money, type, mutableMapOf(), mutableMapOf(), mutableMapOf()) {

    }

    constructor(
    ) : this("", "", "", "", "", mutableMapOf(), mutableMapOf(), mutableMapOf()) {

    }
}