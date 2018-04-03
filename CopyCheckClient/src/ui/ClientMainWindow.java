package ui;

import FileSocket.FileSocket;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.BadLocationException;
import json.JsonResult;
import json.JsonVisualizer;

/**
 * @author Leonel Aguilar
 */
public class ClientMainWindow extends javax.swing.JFrame {
    
    private int currentIndex = 0;
    
    private JsonResult currentJsonResult = new JsonResult();
    
    private final ArrayList<Editor> editors;
    
    private void alert(String message, boolean isError){
        if (isError) {
            JOptionPane.showConfirmDialog(null, message, " Error ",  JOptionPane.ERROR_MESSAGE, JOptionPane.OK_OPTION);
        }
        else {
            JOptionPane.showConfirmDialog(null, message, " Mensaje ",JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_OPTION );
        }
    }
    
    private void alert(String message){
        alert(message, true);
    }
    
    private Editor getCurrentEditor(){
        if (currentIndex >= 0 && currentIndex < editors.size()){
            return editors.get(currentIndex);
        }
        return null;
    }
    
    private File selectFile(boolean isHtml){
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = fileChooser.showOpenDialog(null);
              
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getName();
            
            if ((isHtml && fileName.endsWith(".html")) || fileName.endsWith(".cp")) {
                return selectedFile;  
            }     
        }
        return null;
    }
    
    private File selectFile(){
        return selectFile(false);
    }
    
    private File selectFolder(){
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());        
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);        
        int returnValue = fileChooser.showOpenDialog(null);
              
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            if (selectedFile.isDirectory()) {
                return selectedFile;  
            }     
        }
        return null;
    }
            
    private String sendProyects() throws IOException{
            
        File folder1 = selectFolder();
        
        if (folder1 == null) {
            alert("Por favor seleccione una carpeta con sus documentos java.");
            return "";
        }
        
        File folder2 = selectFolder();
        
        if (folder2 == null) {
            alert("Por favor seleccione una carpeta con sus documentos java.");
            return "";
        }
                
        return FileSocket.sendFiles(folder1.getAbsolutePath(), folder2.getAbsolutePath());
        
    }
        
    private void addNewTab() {
        String newName = "CReport" + (editors.size()) + ".cp";
        
        String path = new JFileChooser().getFileSystemView().getDefaultDirectory().getAbsolutePath();
        
        Editor newEditor = new Editor(path + "\\" + newName, newName);
               
        editorTabPane.addTab(newName, null);
        
        editorTabPane.setSelectedIndex(editorTabPane.getTabCount() - 1);
        
        codeEditor.setText("");
                
        newEditor.setCode(codeEditor.getText());
        
        editors.add(newEditor);
    }
    
    private void closeFile(boolean confirmation){
        
        if (editors.size() <= 1){
            alert("No se permite cerrar la ultima pestaña.");
            return;
        }
        
        if (confirmation) {  
            
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog ( 
                null, 
                "¿Desea guardar su archivo antes de cerrarlo?", 
                "Advertencia", 
                dialogButton
            );

            if(dialogResult == JOptionPane.YES_OPTION){    
                saveFileAs();
            }   
        }   
        
        Editor editor = getCurrentEditor();
        
        editorTabPane.removeTabAt(currentIndex); 
        
        editors.remove(editor);       
                
        codeEditor.setText(getCurrentEditor().getCode());
    }
        
    private void openFile(){
        try { 
            Editor editor = getCurrentEditor();  
            File file = selectFile();
            
            codeEditor.setText("");

            codeEditor.read( new FileReader( file.getAbsolutePath() ), null );
            codeEditor.requestFocus();

            editor.setCode(codeEditor.getText());
            editor.setPath(file.getAbsolutePath());
            editor.setName(file.getName());

            editorTabPane.setTitleAt(currentIndex, editor.getName());                
                   
        } catch (IOException ex) {
            alert("Error al cargar archivo. " + ex.getMessage());
        } catch (HeadlessException ex){
            alert("Error al abrir archivo. " + ex.getMessage());
        } catch (NullPointerException ex) {
            alert("El tipo o nombre dle archivo es inválido. " + ex.getMessage());
        }
    }
    
    private void saveFile(String fileName) {
        Editor editor = getCurrentEditor();            
        PrintWriter printer;
        try {
            editor.setCode(codeEditor.getText());
            
            printer = new PrintWriter(fileName);
            
            BufferedWriter wr = new BufferedWriter(printer);
            
            codeEditor.write(wr);
            
            wr.close();
            
            editor.setPath(fileName);
        } catch (FileNotFoundException ex) {
            alert("Error al guardar archivo. " + ex.getMessage());
        } catch (IOException ex) {
            alert("Error al abrir o guardar archivo. " + ex.getMessage());
        } catch (NullPointerException ex) {
            alert("El tipo o nombre dle archivo es inválido. " + ex.getMessage());
        }
    }
    
    private void saveFile(){     
        saveFile(getCurrentEditor().getPath());        
    }
    
    private void saveFileAs(){
        try {
            File file = selectFile();
            saveFile(file.getAbsolutePath());
            editorTabPane.setTitleAt(currentIndex, file.getName()); 
        }
        catch (HeadlessException ex){
            alert("Error al guardar archivo: " + ex.getMessage());
        }
    }
    
    private void deleteFile(){
        Editor editor = getCurrentEditor();
        File file = new File(editor.getPath());
        if (file.delete()){
            alert("Archivo eliminado.", false);
            closeFile(false);
        }     
        else {
            alert("Archivo no eliminado.");
        }
    }
        
    private void CreateGUI(){
        
        initComponents();
        
        editorTabPane.addChangeListener((ChangeEvent e) -> {
            if (e.getSource() instanceof JTabbedPane) {
                
                Editor editor = getCurrentEditor();
                
                if (editor != null) {
                    editor.setCode(codeEditor.getText());
                }
                
                currentIndex = editorTabPane.getSelectedIndex();
                
                editor = getCurrentEditor();
                
                if (editor != null){
                    codeEditor.setText(editor.getCode());
                }
            }
        });
        
        TextAreaNumber lanCode = new TextAreaNumber(codeEditor);
        LineNumberComponent lncCode = new LineNumberComponent(lanCode);
        
        editorScrollPane.setRowHeaderView(lncCode);     
        
        lncCode.setAlignment(LineNumberComponent.CENTER_ALIGNMENT);
        
        codeEditor.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                lncCode.adjustWidth();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                lncCode.adjustWidth();   
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                lncCode.adjustWidth();
            }        
        });
        String path = new JFileChooser().getFileSystemView().getDefaultDirectory().getAbsolutePath();
        Editor firstEditor = new Editor(path + "\\creport.cp", "creport.cp");
        editors.add(firstEditor); 
        
        lncCode.adjustWidth();   
        
        TextAreaNumber lanCode2 = new TextAreaNumber(console);
        LineNumberComponent lncCode2 = new LineNumberComponent(lanCode2);
        
        consoleScrollPane.setRowHeaderView(lncCode2);     
        
        lncCode2.setAlignment(LineNumberComponent.CENTER_ALIGNMENT);
        
        console.getDocument().addDocumentListener(new DocumentListener(){
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
        
        String path2 = new JFileChooser().getFileSystemView().getDefaultDirectory().getAbsolutePath();        
        lncCode2.adjustWidth();        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        editorTabPane = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        consoleScrollPane = new javax.swing.JScrollPane();
        console = new javax.swing.JTextArea();
        colRowNumber = new javax.swing.JLabel();
        editorScrollPane = new javax.swing.JScrollPane();
        codeEditor = new javax.swing.JTextArea();
        resultTabs = new javax.swing.JTabbedPane();
        menu = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenu = new javax.swing.JMenuItem();
        newMenu = new javax.swing.JMenuItem();
        saveMenu = new javax.swing.JMenuItem();
        saveasMenu = new javax.swing.JMenuItem();
        closeMenu = new javax.swing.JMenuItem();
        deleteMenu = new javax.swing.JMenuItem();
        analysisMenu = new javax.swing.JMenu();
        loadProyectsMenu = new javax.swing.JMenuItem();
        creportMenu = new javax.swing.JMenu();
        createReportMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 737, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        editorTabPane.addTab("CReport.cp", jPanel1);

        console.setColumns(20);
        console.setRows(5);
        consoleScrollPane.setViewportView(console);

        colRowNumber.setFont(new java.awt.Font("Consolas", 0, 11)); // NOI18N
        colRowNumber.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        codeEditor.setBackground(new java.awt.Color(248, 247, 250));
        codeEditor.setColumns(20);
        codeEditor.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        codeEditor.setForeground(new java.awt.Color(0, 102, 102));
        codeEditor.setRows(5);
        codeEditor.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                codeEditorCaretUpdate(evt);
            }
        });
        editorScrollPane.setViewportView(codeEditor);

        fileMenu.setText("Archivo");

        openMenu.setText("Abrir");
        openMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuActionPerformed(evt);
            }
        });
        fileMenu.add(openMenu);

        newMenu.setText("Nuevo");
        newMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuActionPerformed(evt);
            }
        });
        fileMenu.add(newMenu);

        saveMenu.setText("Guardar");
        saveMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenu);

        saveasMenu.setText("Guardar como");
        saveasMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveasMenuActionPerformed(evt);
            }
        });
        fileMenu.add(saveasMenu);

        closeMenu.setText("Cerrar");
        closeMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeMenuActionPerformed(evt);
            }
        });
        fileMenu.add(closeMenu);

        deleteMenu.setText("Eliminar");
        deleteMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMenuActionPerformed(evt);
            }
        });
        fileMenu.add(deleteMenu);

        menu.add(fileMenu);

        analysisMenu.setText("Análisis");

        loadProyectsMenu.setText("Analizar Proyectos");
        loadProyectsMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadProyectsMenuActionPerformed(evt);
            }
        });
        analysisMenu.add(loadProyectsMenu);

        menu.add(analysisMenu);

        creportMenu.setText("CReport");

        createReportMenu.setText("Crear reporte");
        createReportMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createReportMenuActionPerformed(evt);
            }
        });
        creportMenu.add(createReportMenu);

        menu.add(creportMenu);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(consoleScrollPane, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(colRowNumber, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(editorScrollPane)
                            .addComponent(editorTabPane))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resultTabs, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(editorTabPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(resultTabs)
                    .addComponent(editorScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(colRowNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(consoleScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createReportMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createReportMenuActionPerformed
        Reporter.Reporter.reset();
                    
        try {                
            File file = selectFile(true);       
            String ap = file.getAbsolutePath();       
            BufferedWriter out = new BufferedWriter(new FileWriter(ap));

            String code = codeEditor.getText();
            String html = cp.html.compiler.Analyze(code, currentJsonResult); 
            
            if (Reporter.Reporter.hasErrors()){
                alert("No se pudo generar el reporte, hay al menos un error.");
                html = Reporter.Reporter.generateAsHtml();
            }                

            out.write(html);
            out.flush();
            
            Desktop.getDesktop().browse(file.toURI());
            
        } catch (FileNotFoundException ex) {
            alert("Error al guardar archivo. " + ex.getMessage());
        } catch (IOException ex) {
            alert("Error al abrir o guardar archivo. " + ex.getMessage());
        } catch (NullPointerException ex) {
            alert("El tipo o nombre de archivo es inválido. " + ex.getMessage());
        } catch (HeadlessException ex){
            alert("Error al guardar archivo: " + ex.getMessage());
        } catch (Exception ex) {
            alert("Error al generar reporte de errores: " + ex.getMessage());
        }        
    }//GEN-LAST:event_createReportMenuActionPerformed

    private void openMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuActionPerformed
        openFile();
    }//GEN-LAST:event_openMenuActionPerformed

    private void newMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuActionPerformed
        addNewTab();
    }//GEN-LAST:event_newMenuActionPerformed

    private void saveMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuActionPerformed
        saveFile();
    }//GEN-LAST:event_saveMenuActionPerformed

    private void saveasMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveasMenuActionPerformed
        saveFileAs();
    }//GEN-LAST:event_saveasMenuActionPerformed

    private void closeMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeMenuActionPerformed
        closeFile(true);
    }//GEN-LAST:event_closeMenuActionPerformed

    private void codeEditorCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_codeEditorCaretUpdate
        int linenum = 1;
        int columnnum = 1;
        try {
            int caretpos = codeEditor.getCaretPosition();
            linenum = codeEditor.getLineOfOffset(caretpos);

            columnnum = caretpos - codeEditor.getLineStartOffset(linenum);
            
            linenum += 1;
            
        }
        catch(BadLocationException ex) {
            System.err.println(ex.getMessage());
        }

        colRowNumber.setText("Lin: " + linenum + " Col: " + (++columnnum ));
    }//GEN-LAST:event_codeEditorCaretUpdate

    private void deleteMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteMenuActionPerformed
        deleteFile();
    }//GEN-LAST:event_deleteMenuActionPerformed

    private void loadProyectsMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadProyectsMenuActionPerformed
        try {
            String json = sendProyects();
            
            JsonVisualizer jv = new JsonVisualizer();
            
            resultTabs.addTab("Análisis " + (resultTabs.getTabCount() + 1), jv.createJsonView(json));
            
            currentJsonResult = jv.getJsonResult();
            
            Reporter.Reporter.log(json, false);
        }
        catch (IOException ex) {
            alert("No se pudo realizar el envio de proyectos. " + ex.getMessage());
        }
    }//GEN-LAST:event_loadProyectsMenuActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientMainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            new ClientMainWindow().setVisible(true);
        });
    }

    /**
     * Obtiene la consola del cliente
     * @return
     */
    public JTextArea getConsole(){
        return console;
    }
    
    /**
     * Creates new form MainWindow
     */
    public ClientMainWindow() {        
        this.editors = new ArrayList<>();
        CreateGUI();              
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu analysisMenu;
    private javax.swing.JMenuItem closeMenu;
    private javax.swing.JTextArea codeEditor;
    private javax.swing.JLabel colRowNumber;
    private javax.swing.JTextArea console;
    private javax.swing.JScrollPane consoleScrollPane;
    private javax.swing.JMenuItem createReportMenu;
    private javax.swing.JMenu creportMenu;
    private javax.swing.JMenuItem deleteMenu;
    private javax.swing.JScrollPane editorScrollPane;
    private javax.swing.JTabbedPane editorTabPane;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenuItem loadProyectsMenu;
    private javax.swing.JMenuBar menu;
    private javax.swing.JMenuItem newMenu;
    private javax.swing.JMenuItem openMenu;
    private javax.swing.JTabbedPane resultTabs;
    private javax.swing.JMenuItem saveMenu;
    private javax.swing.JMenuItem saveasMenu;
    // End of variables declaration//GEN-END:variables
}
