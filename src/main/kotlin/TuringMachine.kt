class TuringMachine {

    var tapeLength = 350
    var tapeIndex: Int = 0
    var currentState = ""
    val tape: ArrayList<Int?> = ArrayList()
    var programm: ArrayList<String> = ArrayList()
    var programmIndex = 0

    fun initMachine(tapeLength: Int) {
        if(tapeLength != -1) {
            this.tapeLength = tapeLength
        }

        for (i in 0..<this.tapeLength){
            tape.add(null)
        }
    }
}