import java.io.Serializable

data class User(
    var uid: String ="",
    var name: String ="",
    var parent:String="",
    var money:String="0",
    var type:String="0",
    var incomes:MutableMap<String,String>,
    var expenses:MutableMap<String,String>,
    var children:MutableMap<String,String>
):Serializable{
    var incomeRecords=mutableListOf<Record>()
    var expenseRecords=mutableListOf<Record>()
    var tasks=mutableListOf<Record>()
    var applys=mutableListOf<Record>()
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
    init {
        incomes=mutableMapOf()
        expenses=mutableMapOf()
        children=mutableMapOf()
        incomeRecords=mutableListOf<Record>()
        expenseRecords=mutableListOf<Record>()
        tasks=mutableListOf<Record>()
        applys=mutableListOf<Record>()
    }
}