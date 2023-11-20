import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Toolkit
import javax.swing.JPanel

class Draw(gui: TFrame, tMachine: TuringMachine) : JPanel() {
    private var visibleTapeCount = 10
    private var gui: TFrame
    private var tMachine: TuringMachine

    init {
        this.gui = gui
        this.tMachine = tMachine
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        val g2d = g as Graphics2D

        if(gui.controller.t.isRunning) {
            g2d.color = Color.GREEN
            g2d.fillOval(50,90,25,25)
        }
        else {
            g2d.color = Color.RED
            g2d.fillOval(50,90  ,25,25)
        }
        g2d.color = Color.BLACK

        g2d.font = Font("Ubuntu Mono", Font.BOLD, 35)
        g2d.drawString("state: ${tMachine.currentState}", 50, 200)

        g2d.drawString("delay: ${gui.controller.timerDelay}", 50,150)

        val collWidth = gui.frameSize.width / visibleTapeCount

        g2d.fillRect(collWidth * (visibleTapeCount / 2) + (collWidth - 50) / 2, 175, 50,50)
        g2d.fillRect(collWidth * (visibleTapeCount / 2) + (collWidth - 30) / 2, 210, 30,30)

        for (i in 0..<10) {
            if (tMachine.tapeIndex + i - visibleTapeCount / 2 >= 0 && tMachine.tapeIndex + i - visibleTapeCount / 2 < tMachine.tapeLength) {


                g2d.font = Font("Ubuntu Mono", Font.BOLD, 15)
                g2d.drawString("${tMachine.tapeIndex + i - visibleTapeCount / 2}", collWidth * i, 250 + collWidth + 20)

                g2d.stroke = BasicStroke(5F)
                g2d.drawRect(collWidth * i, 250, collWidth, collWidth)

                if (tMachine.tape[tMachine.tapeIndex + i - visibleTapeCount / 2] != null) {
                    g2d.font = Font("Ubuntu Mono", Font.BOLD, 35)
                    g2d.drawString(
                        "${tMachine.tape[tMachine.tapeIndex + i - visibleTapeCount / 2]}",
                        collWidth * i + (collWidth - 35) / 2,
                        250 + 35 + (collWidth - 35) / 2
                    )
                }
            }
        }
        Toolkit.getDefaultToolkit().sync()
        repaint()
    }
}