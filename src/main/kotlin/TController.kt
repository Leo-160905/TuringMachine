import java.lang.Exception
import javax.swing.Timer
import kotlin.system.exitProcess

class TController {
    val tMachine = TuringMachine()
    private lateinit var commandList: ArrayList<String>
    var timerDelay = 500
    lateinit var t: Timer

    fun startTuring(programm: String) {

        val programm1 = programm.replace(" ", "").replace("\n", "").lowercase()
        try {
            commandList = programm1.split(';') as ArrayList<String>
        } catch (e: Exception) {
            println("There is a problem wir your written programm (no semicolon \";\")")
            exitProcess(0)
        }
        commandList.reversed().forEach { if (it == "") commandList.remove(it) }
        if(commandList[0].startsWith("length")){
            val command = commandList[0].removePrefix("length")
            tMachine.initMachine(command.toInt())
        }
        else {
            tMachine.initMachine(-1)
        }

        tMachine.programm = commandList
        turingTimer()
    }

    private fun turingTimer() {
        t = Timer(timerDelay) {
            var command = tMachine.programm[tMachine.programmIndex]
            println(command)
            if (command.startsWith("length")) {
                tMachine.programmIndex++
            }
            else if (command.startsWith("amov")) {
                command = command.removePrefix("amov")
                tMachine.tapeIndex = command.toInt()
                tMachine.programmIndex ++
            }
            else if (command.startsWith("rmov")) {
                command = command.removePrefix("rmov")
                tMachine.tapeIndex += command.toInt()
                tMachine.programmIndex ++

            }
            else if (command.startsWith("ifst")) {
                command = command.removePrefix("ifst")
                if(command == tMachine.currentState) tMachine.programmIndex++
                else tMachine.programmIndex += 2
            }
            else if (command.startsWith("ifi")) {
                command = command.removePrefix("ifi")
                if(command.startsWith("<")){
                    command = command.removePrefix("<")
                    if(command.toInt() > tMachine.tapeIndex){
                        tMachine.programmIndex++
                    }
                    else {
                        tMachine.programmIndex += 2
                    }
                }
                else if(command.startsWith(">")){
                    command = command.removePrefix(">")
                    if(command.toInt() < tMachine.tapeIndex){
                        tMachine.programmIndex++
                    }
                    else {
                        tMachine.programmIndex += 2
                    }
                }
                else if(command.toInt() == tMachine.tapeIndex){
                    tMachine.programmIndex++
                }
                else {
                    tMachine.programmIndex += 2
                }
            }
            else if (command.startsWith("ifnn")) {
                if (tMachine.tape[tMachine.tapeIndex] != null) tMachine.programmIndex++
                else tMachine.programmIndex += 2
            }
            else if (command.startsWith("if")) {
                command = command.removePrefix("if")
                if (tMachine.tape[tMachine.tapeIndex] == command.toInt()) tMachine.programmIndex++
                else tMachine.programmIndex += 2
            }
            else if (command.startsWith("stat")) {
                command = command.removePrefix("stat")
                tMachine.currentState = command
                tMachine.programmIndex++
            }
            else if (command.startsWith("jmpi")) {
                command = command.removePrefix("jmpi")
                tMachine.programmIndex = command.toInt() - 1
            }
            else if (command.startsWith("set")) {
                command = command.removePrefix("set")
                if(command.toInt() == 0 || command.toInt() == 1) tMachine.tape[tMachine.tapeIndex] = command.toInt()
                tMachine.programmIndex++
            }
            else if (command.startsWith("_")) {
                tMachine.programmIndex++
            }
            else if (command.startsWith("rjmp")) {
                command = command.removePrefix("rjmp")
                tMachine.programmIndex += command.toInt()
            }
            else if (command.startsWith("init")){
                command = command.removePrefix("init")
                for (i in command.indices){
                    tMachine.tape[tMachine.tapeIndex + i] = command.get(i).toString().toInt()
                }
                tMachine.programmIndex++
            }
            else if(command.startsWith("end")){
                stopTimer()
            }
            else {
                println("false command in: $command")
            }
        }
        t.start()
    }
    private fun stopTimer () {
        println("timer has stopped")
        t.stop()
    }

    fun increaseTimerDelay(){
        timerDelay += 50
        t.delay = timerDelay
    }
    fun decreaseTimerDelay(){
        timerDelay -= 50
        t.delay = timerDelay
    }
}