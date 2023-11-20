import java.awt.*
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.text.Element

class TFrame : JFrame() {
    val frameSize = Dimension(700, 500)
    lateinit var controller: TController
    private val contentPanel = JPanel()
    private val programmField = JTextArea()


    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        location = Point((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2)


        contentPanel.preferredSize = frameSize

        contentPanel.add(getOptionPanel())



        val cp = contentPane
        cp.add(contentPanel)
        pack()
        isVisible = true
    }

    private fun getOptionPanel(): JPanel {
        val optionPanel = JPanel()
        optionPanel.preferredSize = frameSize
        optionPanel.layout = null

        val captionLabel = JLabel("Turing Machine")
        val programmBtn = JButton("Programm Machine")
        val startBtn = JButton("Start Machine")
        val scrollPane = JScrollPane()


        captionLabel.setBounds(50, 25, 600, 100)
        captionLabel.font = Font("Arial", Font.BOLD, 40)
        captionLabel.horizontalAlignment = SwingConstants.CENTER
        optionPanel.add(captionLabel)

        programmBtn.setBounds(50, 150, 300, 50)
        programmBtn.isFocusPainted = false
        programmBtn.font = Font("Arial", Font.BOLD, 20)
        programmBtn.addActionListener {
            if (programmField.isEditable) {
                programmBtn.text = "Programm Maching"
                programmField.isEditable = false
            } else {
                programmBtn.text = "Save Programm"
                programmField.isEditable = true
            }
        }
        optionPanel.add(programmBtn)

        startBtn.setBounds(50, 250, 300, 50)
        startBtn.isFocusPainted = false
        startBtn.font = Font("Arial", Font.BOLD, 20)
        startBtn.addActionListener { startActionPerformed() }
        optionPanel.add(startBtn)

        val lines = JTextArea("1")
        lines.background = Color.LIGHT_GRAY
        lines.isEditable = false
        lines.font = Font("Ubuntu Mono", Font.PLAIN, 20)

        programmField.isEditable = false
        programmField.font = Font("Ubuntu Mono", Font.PLAIN, 20)
        programmField.document.addDocumentListener(object : DocumentListener {
            val text: String
                get() {
                    val caretPosition: Int = programmField.document.length
                    val root: Element = programmField.document.defaultRootElement
                    var text = "1" + System.getProperty("line.separator")
                    for (i in 2..<root.getElementIndex(caretPosition) + 2) {
                        text += i.toString() + System.getProperty("line.separator")
                    }
                    return text
                }

            override fun changedUpdate(de: DocumentEvent?) {
                lines.text = text
            }

            override fun insertUpdate(de: DocumentEvent?) {
                lines.text = text
            }

            override fun removeUpdate(de: DocumentEvent?) {
                lines.text = text
            }
        })


        scrollPane.viewport.add(programmField)
        scrollPane.setRowHeaderView(lines)
        scrollPane.setBounds(400, 150, 250, 300)
        scrollPane.border = BorderFactory.createLineBorder(Color.black, 2)
        optionPanel.add(scrollPane)

        return optionPanel
    }

    private fun startActionPerformed() {
        if (programmField.text != "" && programmField.text.contains("end")) {
            contentPanel.removeAll()

            controller = TController()
            controller.startTuring(programmField.text)

            val turingPanel = Draw(this, controller.tMachine)
            turingPanel.preferredSize = frameSize
            contentPanel.add(turingPanel)

            turingPanel.requestFocus()
            turingPanel.addKeyListener(object : KeyAdapter() {
                override fun keyReleased(e: KeyEvent?) {
                    super.keyReleased(e)

                    if(e?.keyChar == '-'){
                        controller.increaseTimerDelay()
                    }
                    if(e?.keyChar == '+'){
                        controller.decreaseTimerDelay()
                    }
                }
            })

            contentPanel.revalidate()
            contentPanel.repaint()
        }
    }
}