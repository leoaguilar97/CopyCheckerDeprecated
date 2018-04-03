package ui;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import proyectdata.Proyect;
import proyectdata.analyzer;

/**
 * @author Leonel Aguilar
 */
public class ServerMainWindow extends javax.swing.JFrame {
        
    private void CreateGUI(){
        initComponents();   
        
        TextAreaNumber lanCode1 = new TextAreaNumber(console);
        LineNumberComponent lncCode1 = new LineNumberComponent(lanCode1);
        
        SPConsole.setRowHeaderView(lncCode1);     
        
        lncCode1.setAlignment(LineNumberComponent.CENTER_ALIGNMENT);
        
        console.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                lncCode1.adjustWidth();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                lncCode1.adjustWidth();   
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                lncCode1.adjustWidth();
            }        
        });
        
        TextAreaNumber lanCode2 = new TextAreaNumber(console);
        LineNumberComponent lncCode2 = new LineNumberComponent(lanCode2);
        
        SPCodeEditor1.setRowHeaderView(lncCode2);     
        
        lncCode2.setAlignment(LineNumberComponent.CENTER_ALIGNMENT);
        
        codeEditor1.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                lncCode2.adjustWidth();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                lncCode2.adjustWidth();   
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                lncCode2.adjustWidth();
            }        
        });
        
        TextAreaNumber lanCode3 = new TextAreaNumber(console);
        LineNumberComponent lncCode3 = new LineNumberComponent(lanCode3);
        
        SPCodeEditor2.setRowHeaderView(lncCode3);     
        
        lncCode3.setAlignment(LineNumberComponent.CENTER_ALIGNMENT);
        
        codeEditor2.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                lncCode3.adjustWidth();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                lncCode3.adjustWidth();   
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                lncCode3.adjustWidth();
            }        
        });
        
        lncCode1.adjustWidth();
        lncCode2.adjustWidth();
        lncCode3.adjustWidth();   
    }
    
    /**
     * Creates new form MainWindow
     */
    public ServerMainWindow() {       
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerMainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        CreateGUI();    
    }

    public JTextArea getConsole(){
        return console;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        editorTabPane = new javax.swing.JTabbedPane();
        PanelContainer = new javax.swing.JPanel();
        SPConsole = new javax.swing.JScrollPane();
        console = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        SPCodeEditor1 = new javax.swing.JScrollPane();
        codeEditor1 = new javax.swing.JTextArea();
        SPCodeEditor2 = new javax.swing.JScrollPane();
        codeEditor2 = new javax.swing.JTextArea();
        colRowNumber = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        console.setBackground(new java.awt.Color(248, 247, 250));
        console.setColumns(20);
        console.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        console.setForeground(new java.awt.Color(0, 102, 102));
        console.setRows(5);
        console.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                consoleCaretUpdate(evt);
            }
        });
        SPConsole.setViewportView(console);

        javax.swing.GroupLayout PanelContainerLayout = new javax.swing.GroupLayout(PanelContainer);
        PanelContainer.setLayout(PanelContainerLayout);
        PanelContainerLayout.setHorizontalGroup(
            PanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SPConsole, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                .addContainerGap())
        );
        PanelContainerLayout.setVerticalGroup(
            PanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SPConsole, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                .addContainerGap())
        );

        editorTabPane.addTab("Java Analysis", PanelContainer);

        jButton1.setText("COMPARAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        codeEditor1.setColumns(20);
        codeEditor1.setRows(5);
        SPCodeEditor1.setViewportView(codeEditor1);

        codeEditor2.setColumns(20);
        codeEditor2.setRows(5);
        SPCodeEditor2.setViewportView(codeEditor2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(SPCodeEditor1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(SPCodeEditor2, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SPCodeEditor1, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                    .addComponent(SPCodeEditor2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        editorTabPane.addTab("Java Analysis Testing", jPanel1);

        colRowNumber.setFont(new java.awt.Font("Consolas", 0, 11)); // NOI18N
        colRowNumber.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(colRowNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editorTabPane, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(editorTabPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(colRowNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void consoleCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_consoleCaretUpdate
        int linenum = 1;
        int columnnum = 1;
        try {
            int caretpos = console.getCaretPosition();
            linenum = console.getLineOfOffset(caretpos);

            columnnum = caretpos - console.getLineStartOffset(linenum);
            
            linenum += 1;
            
        }
        catch(BadLocationException ex) {
            System.err.println(ex.getMessage());
        }

        colRowNumber.setText("Lin: " + linenum + " Col: " + (++columnnum ));
    }//GEN-LAST:event_consoleCaretUpdate

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String java_code1 = codeEditor1.getText();
        String java_code2 = codeEditor2.getText();
        Proyect p1 = javacompiler.compiler.Analyze(java_code1);
        Proyect p2 = javacompiler.compiler.Analyze(java_code2);
        
        String json = analyzer.getRESULTString(p1, p2);
        
        Reporter.log(json);
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelContainer;
    private javax.swing.JScrollPane SPCodeEditor1;
    private javax.swing.JScrollPane SPCodeEditor2;
    private javax.swing.JScrollPane SPConsole;
    private javax.swing.JTextArea codeEditor1;
    private javax.swing.JTextArea codeEditor2;
    private javax.swing.JLabel colRowNumber;
    private javax.swing.JTextArea console;
    private javax.swing.JTabbedPane editorTabPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
