package gui;

import ipc1.proyect2.Administrador;
import ipc1.proyect2.AppState;
import ipc1.proyect2.Curso;
import ipc1.proyect2.Estudiantes;
import ipc1.proyect2.Profesores;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author danis
 */
public class AdministradorJFrame extends javax.swing.JFrame {
    Administrador administrador;
    LinkedList<Profesores> profesores = new LinkedList();
    LinkedList<Curso>cursos= new LinkedList();
    /**
     * Creates new form AdministradorJFrame
     */
    public AdministradorJFrame() {
        initComponents();
        
        this.setVisible(true); // Hace visible la ventana
        this.setLocationRelativeTo(null); // La coloca al centro
        this.setResizable(false); // Bloquea el redimensionamiento de la ventana
        this.setTitle("Administrador"); // Coloca un titulo a la ventana
        
        this.generargaficaprofesores();
	this.llenartablaprofesores();
	this.llenartablacursos();
        this.llenartablaalumnos();
        this.generargraficageneroalumbo();
        
        this.addWindowListener(new WindowAdapter() {
           @Override
           public void windowClosing(WindowEvent e) {
               AppState.serializar();
           }
        });
    }
    
    public void llenartablaprofesores() {
        int tamano = AppState.getProfesores().size();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setRowCount(tamano);
        // Se agregaran las columnas
        modelo.addColumn("Codigo");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Correo");
        modelo.addColumn("Genero");
        //datos
        for(int i = 0; i<tamano;i++){
            Profesores profe = AppState.getProfesores().get(i);
            modelo.setValueAt(profe.getCodigop(), i, 0);
            modelo.setValueAt(profe.getNombrep(), i, 1);
            modelo.setValueAt(profe.getApellidop(), i, 2);
            modelo.setValueAt(profe.getCorreop(), i, 3);
            modelo.setValueAt(profe.getGenerop(), i, 4);
        }
        this.profesoresjTable.setModel(modelo);
    }
    
    private String GenerartablaHTML(){
        StringBuilder sb = new StringBuilder();
        //Se crea la tabla
        sb.append("<table border=\"1\" cellspacing=\"0\">\n");
        
        sb.append("<tr>\n");
        sb.append("<th>").append("Codigo").append("</th>");
        sb.append("<th>").append("Nombre").append("</th>");
        sb.append("<th>").append("Apellido").append("</th>");
        sb.append("<th>").append("Correo").append("</th>");
        sb.append("<th>").append("Genero").append("</th>");
        sb.append("</tr>\n");
        
        for(Profesores profesor: AppState.getProfesores()){
            sb.append("<tr>");
            sb.append("<td>").append(profesor.getCodigop()).append("</td>");
            sb.append("<td>").append(profesor.getNombrep()).append("</td>");
            sb.append("<td>").append(profesor.getApellidop()).append("</td>");
            sb.append("<td>").append(profesor.getCorreop()).append("</td>");
            sb.append("<td>").append(profesor.getGenerop()).append("</td>");
            sb.append("</tr>");
        }
        
        sb.append("</table>\n");
        return sb.toString();
    }
    
    private String GenerartablacursosHTML(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("<table border=\"1\" cellspacing=\"0\">\n");
        
        sb.append("<tr>\n");
        sb.append("<th>").append("Codigo").append("</th>");
        sb.append("<th>").append("Nombre").append("</th>");
        sb.append("<th>").append("Creditos").append("</th>");
        sb.append("<th>").append("Alumnos").append("</th>");
        sb.append("<th>").append("Profesor").append("</th>");
        sb.append("</tr>\n");
        
        for(Curso curso: AppState.cursos){
            sb.append("<tr>");
            sb.append("<td>").append(curso.getCodigo()).append("</td>");
            sb.append("<td>").append(curso.getNombre()).append("</td>");
            sb.append("<td>").append(curso.getCreditos()).append("</td>");
            sb.append("<td>").append(curso.idsEstudiantes.size()).append("</td>");
            for(Profesores profesor: AppState.getProfesores()){
                if(profesor.codigop.equals(curso.codigoProfesor)){
                   sb.append("<td>").append(profesor.nombrep).append("</td>");
                }
            }
            sb.append("</tr>");
        }
        
        
        sb.append("</table>\n");
        return sb.toString();
    }
    
    private String GenerartabalalumnosHTML(){
         StringBuilder sb = new StringBuilder();
        //Se crea la tabla
        sb.append("<table border=\"1\" cellspacing=\"0\">\n");
        
        sb.append("<tr>\n");
        sb.append("<th>").append("Codigo").append("</th>");
        sb.append("<th>").append("Nombre").append("</th>");
        sb.append("<th>").append("Apellido").append("</th>");
        sb.append("<th>").append("Correo").append("</th>");
        sb.append("<th>").append("Genero").append("</th>");
        sb.append("</tr>\n");
        
        for(Estudiantes estudiantes: AppState.getEstudiantes()){
            sb.append("<tr>");
            sb.append("<td>").append(estudiantes.getCodigoe()).append("</td>");
            sb.append("<td>").append(estudiantes.getNombree()).append("</td>");
            sb.append("<td>").append(estudiantes.getApellidoe()).append("</td>");
            sb.append("<td>").append(estudiantes.getCorreoe()).append("</td>");
            sb.append("<td>").append(estudiantes.getGeneroe()).append("</td>");
            sb.append("</tr>");
        }
        
        sb.append("</table>\n");
        return sb.toString();
    }
    
    public void generargaficaprofesores(){
        int M = 0, F = 0;
        try {
            DefaultPieDataset dataset = new DefaultPieDataset();

            //Recorrido para encontrar datos
            for(Profesores profesor : AppState.getProfesores()){
                if (profesor.generop.equals("M")) {
                    M++;
                }else if (profesor.generop.equals("F")) {
                    F++;
                }
            }

            dataset.setValue("Masculino", M);
            dataset.setValue("Femenino", F);
            
            JFreeChart chart = ChartFactory.createPieChart("Genero de Profesores",dataset, true, true,false);
            
            ChartPanel chartpanel = new ChartPanel(chart);
            chartpanel.setPreferredSize(new Dimension(246,189));
            //chartpanel.setVisible(true);
            
            profejPanel7.setLayout(new BorderLayout());
            profejPanel7.add(chartpanel, BorderLayout.NORTH);
            
            pack();
            //repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,""+e);
        }
    }
    
    public void generargraficageneroalumbo(){
        int M = 0, F = 0;
        try {
            DefaultPieDataset dataset = new DefaultPieDataset();

            //Recorrido para encontrar datos
            for(Estudiantes estudiantes : AppState.getEstudiantes()){
                if (estudiantes.getGeneroe().equals("M")) {
                    M++;
                }else if (estudiantes.getGeneroe().equals("F")) {
                    F++;
                }
            }

            dataset.setValue("Masculino", M);
            dataset.setValue("Femenino", F);
            
            JFreeChart chart = ChartFactory.createPieChart("Genero de Estudiantes",dataset, true, true,false);
            
            ChartPanel chartpanel = new ChartPanel(chart);
            chartpanel.setPreferredSize(new Dimension(246,189));
            //chartpanel.setVisible(true);
            
            alumnosjPanel5.setLayout(new BorderLayout());
            alumnosjPanel5.add(chartpanel, BorderLayout.NORTH);
            
            pack();
            //repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,""+e);
        }
    }
    
    public void llenartablacursos(){
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setRowCount(AppState.cursos.size());
        //se agregan las columnas
        modelo.addColumn("Codigo");
        modelo.addColumn("Nombre");
        modelo.addColumn("Creditos");
        modelo.addColumn("Alumnos");
        modelo.addColumn("Profesor");
        //datos
        for(int i = 0; i < AppState.cursos.size(); i++){
            Curso curso = AppState.cursos.get(i);
            modelo.setValueAt(curso.getCodigo(), i, 0);
            modelo.setValueAt(curso.getNombre(), i, 1);
            modelo.setValueAt(curso.getCreditos(), i, 2);
            modelo.setValueAt(curso.idsEstudiantes.size(), i, 3);
            for (Profesores profesor : AppState.getProfesores()) {
                if (profesor.codigop.equals(curso.codigoProfesor)) {
                    modelo.setValueAt(profesor.nombrep, i, 4);
                }
            }
            //modelo.setValueAt(cur.getEstudiantes(), i, NORMAL);
            //modelo.setValueAt(cur.getProfesor(), i, 4);
        }
        this.cursosjTable.setModel(modelo);
    }
    
    public void llenartablaalumnos(){
			 int tamano = AppState.getEstudiantes().size();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setRowCount(tamano);
        // Se agregaran las columnas
        modelo.addColumn("Codigo");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Correo");
        modelo.addColumn("Genero");
        //datos
        for(int i = 0; i<tamano;i++){
            Estudiantes estudiantes = AppState.getEstudiantes().get(i);
            modelo.setValueAt(estudiantes.getCodigoe(), i, 0);
            modelo.setValueAt(estudiantes.getNombree(), i, 1);
            modelo.setValueAt(estudiantes.getApellidoe(), i, 2);
            modelo.setValueAt(estudiantes.getCorreoe(), i, 3);
            modelo.setValueAt(estudiantes.getGeneroe(), i, 4);
        }
        this.EstudiantesjTable1.setModel(modelo);
        this.generargraficageneroalumbo();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        profesoresjTable = new javax.swing.JTable();
        CrearprofeButton11 = new javax.swing.JButton();
        CargamasivajButton12 = new javax.swing.JButton();
        actualizarprofejButton13 = new javax.swing.JButton();
        EliminarprofejButton14 = new javax.swing.JButton();
        CerrarsesionjButton15 = new javax.swing.JButton();
        profejPanel7 = new javax.swing.JPanel();
        ExportarprofejButton16 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        cursosjTable = new javax.swing.JTable();
        crarcursojButton = new javax.swing.JButton();
        CargaMasivacursojButton7 = new javax.swing.JButton();
        ActualizarcursojButton8 = new javax.swing.JButton();
        EliminarCrusojButton9 = new javax.swing.JButton();
        CerrarSesionjButton10 = new javax.swing.JButton();
        ProfesorjPanel = new javax.swing.JPanel();
        ExportarCrusojButton6 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        EstudiantesjTable1 = new javax.swing.JTable();
        txtcargamasivaalumno = new javax.swing.JButton();
        txtexportaralumno = new javax.swing.JButton();
        txtcerrarsesionalumno = new javax.swing.JButton();
        alumnosjPanel5 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 153, 0));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Listado Oficial");

        profesoresjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre", "Apellido", "Correo", "Genero"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(profesoresjTable);

        CrearprofeButton11.setText("Crear");
        CrearprofeButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CrearprofeButton11ActionPerformed(evt);
            }
        });

        CargamasivajButton12.setText("Carga Masiva");
        CargamasivajButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CargamasivajButton12ActionPerformed(evt);
            }
        });

        actualizarprofejButton13.setText("Actualizar");
        actualizarprofejButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarprofejButton13ActionPerformed(evt);
            }
        });

        EliminarprofejButton14.setText("Eliminar");
        EliminarprofejButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarprofejButton14ActionPerformed(evt);
            }
        });

        CerrarsesionjButton15.setText("Cerrar Sesión");
        CerrarsesionjButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CerrarsesionjButton15ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout profejPanel7Layout = new javax.swing.GroupLayout(profejPanel7);
        profejPanel7.setLayout(profejPanel7Layout);
        profejPanel7Layout.setHorizontalGroup(
            profejPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        profejPanel7Layout.setVerticalGroup(
            profejPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 192, Short.MAX_VALUE)
        );

        ExportarprofejButton16.setText("Exportar");
        ExportarprofejButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportarprofejButton16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(CerrarsesionjButton15))
                            .addComponent(profejPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(actualizarprofejButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(CrearprofeButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(CargamasivajButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(EliminarprofejButton14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(ExportarprofejButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CrearprofeButton11)
                            .addComponent(CargamasivajButton12))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(actualizarprofejButton13)
                            .addComponent(EliminarprofejButton14))
                        .addGap(29, 29, 29)
                        .addComponent(ExportarprofejButton16)
                        .addGap(18, 18, 18)
                        .addComponent(profejPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(CerrarsesionjButton15))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Profesores", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 153, 0));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Listado Oficial");

        cursosjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre", "Creditos", "Alumnos", "Profesor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(cursosjTable);

        crarcursojButton.setText("Crear");
        crarcursojButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crarcursojButtonActionPerformed(evt);
            }
        });

        CargaMasivacursojButton7.setText("Carga Masiva");
        CargaMasivacursojButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CargaMasivacursojButton7ActionPerformed(evt);
            }
        });

        ActualizarcursojButton8.setText("Actualizar");
        ActualizarcursojButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarcursojButton8ActionPerformed(evt);
            }
        });

        EliminarCrusojButton9.setText("Eliminar");
        EliminarCrusojButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarCrusojButton9ActionPerformed(evt);
            }
        });

        CerrarSesionjButton10.setText("Cerrar Sesión");
        CerrarSesionjButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CerrarSesionjButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ProfesorjPanelLayout = new javax.swing.GroupLayout(ProfesorjPanel);
        ProfesorjPanel.setLayout(ProfesorjPanelLayout);
        ProfesorjPanelLayout.setHorizontalGroup(
            ProfesorjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        ProfesorjPanelLayout.setVerticalGroup(
            ProfesorjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 189, Short.MAX_VALUE)
        );

        ExportarCrusojButton6.setText("Exportar Listado");
        ExportarCrusojButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportarCrusojButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(CerrarSesionjButton10))
                            .addComponent(ProfesorjPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ActualizarcursojButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(crarcursojButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(CargaMasivacursojButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(EliminarCrusojButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(ExportarCrusojButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(crarcursojButton)
                            .addComponent(CargaMasivacursojButton7))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ActualizarcursojButton8)
                            .addComponent(EliminarCrusojButton9))
                        .addGap(18, 18, 18)
                        .addComponent(ExportarCrusojButton6)
                        .addGap(25, 25, 25)
                        .addComponent(ProfesorjPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(CerrarSesionjButton10))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Cursos", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 153, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Listado Oficial");

        EstudiantesjTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre", "Apellido", "Correo", "Genero"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(EstudiantesjTable1);

        txtcargamasivaalumno.setText("Carga Masiva");
        txtcargamasivaalumno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcargamasivaalumnoActionPerformed(evt);
            }
        });

        txtexportaralumno.setText("Exportar listado");
        txtexportaralumno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtexportaralumnoActionPerformed(evt);
            }
        });

        txtcerrarsesionalumno.setText("Cerrar Sesión");
        txtcerrarsesionalumno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcerrarsesionalumnoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout alumnosjPanel5Layout = new javax.swing.GroupLayout(alumnosjPanel5);
        alumnosjPanel5.setLayout(alumnosjPanel5Layout);
        alumnosjPanel5Layout.setHorizontalGroup(
            alumnosjPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        alumnosjPanel5Layout.setVerticalGroup(
            alumnosjPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 192, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(alumnosjPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txtcerrarsesionalumno))
                            .addComponent(txtcargamasivaalumno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtexportaralumno, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtcargamasivaalumno)
                        .addGap(32, 32, 32)
                        .addComponent(txtexportaralumno)
                        .addGap(34, 34, 34)
                        .addComponent(alumnosjPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(txtcerrarsesionalumno))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Alumnos", jPanel4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CrearprofeButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CrearprofeButton11ActionPerformed
        CrearprofesorJFrame crearprofesorJFrame = new CrearprofesorJFrame();
        crearprofesorJFrame.setFmrLista(this);
        crearprofesorJFrame.setProfesores(profesores);
        crearprofesorJFrame.setVisible(true);
    }//GEN-LAST:event_CrearprofeButton11ActionPerformed

    private void CargamasivajButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CargamasivajButton12ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("Archivos CSV", "csv");
        fileChooser.setFileFilter(csvFilter);
        
        int fileChooserState = fileChooser.showOpenDialog(this);
        if(fileChooserState == JFileChooser.APPROVE_OPTION){
            File[] archivos = fileChooser.getSelectedFiles();
            
            for(File file: archivos){
                try{
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String linea;
                    
                    while((linea = reader.readLine()) !=null){
                        String[] datos = linea.split(",");
                        String codigoprof = datos[0];
                        String nombreprof = datos[1];
                        String apellidoprof = datos[2];
                        String correoprof = datos[3];
                        String generoprof = datos[4];
                        //Se agrega a profesores
                        Profesores profe = new Profesores(codigoprof, nombreprof, apellidoprof, 
                                correoprof, "1234", generoprof, codigoprof, 
                                "1234");
                        AppState.usuarios.add(profe);
                        /*this.profesores.add(nuevo);
                        Profesores estudiantes= new Profesores("", "", "", "", "", "", 
                            codigoestu, "1234");
                        AppState.usuarios.add(estudiantes);*/
                    }
                }catch (IOException e){
                    e.printStackTrace();
                    System.out.println("Error al Cargar Archivo");
                }
            }
        }
        this.llenartablaprofesores();
        this.generargaficaprofesores();
    }//GEN-LAST:event_CargamasivajButton12ActionPerformed

    private void actualizarprofejButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarprofejButton13ActionPerformed
        int row = profesoresjTable.getSelectedRow();
        String Codigo = (String) profesoresjTable.getValueAt(row, 0);
        if(row !=-1){
            ActualizarprofesorJFrame actuframe = new ActualizarprofesorJFrame();
            actuframe.setFmrLista(this);
            //actuframe.setProfesores(profesores);
            actuframe.setCodigo(Codigo);
            actuframe.setVisible(true);
        }
    }//GEN-LAST:event_actualizarprofejButton13ActionPerformed

    private void EliminarprofejButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarprofejButton14ActionPerformed
        DefaultTableModel tablaprofe = (DefaultTableModel) profesoresjTable.getModel();
        int eliminar = profesoresjTable.getSelectedRow();
        
        if(eliminar>=0){
            tablaprofe.removeRow(eliminar);
            AppState.usuarios.remove(eliminar);
        }
    }//GEN-LAST:event_EliminarprofejButton14ActionPerformed

    private void CerrarsesionjButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CerrarsesionjButton15ActionPerformed
        LoginJFrame cerrar = new LoginJFrame();
        cerrar.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_CerrarsesionjButton15ActionPerformed

    private void ExportarprofejButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportarprofejButton16ActionPerformed
        //Crear Carpeta si no existe
        File carpeta = new File("./reportes");
        if(!carpeta.exists()){
            carpeta.mkdir();
        }
        
        String ContenidoHTML = this.GenerartablaHTML();
        
        try{
            FileWriter fileWriter;
            fileWriter = new FileWriter("./reportes/Profesores.html");
            fileWriter.write(ContenidoHTML);
            fileWriter.close();
            
            String mensajeExito = "Reporte generado en la carpeta \"Reportes\" dentro del Proyecto.";
            JOptionPane.showMessageDialog(this, mensajeExito, "Alerta", JOptionPane.INFORMATION_MESSAGE);
        }catch(IOException e){
            System.out.println("No se pudo generar el documento.");
        }    
    }//GEN-LAST:event_ExportarprofejButton16ActionPerformed

    private void crarcursojButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crarcursojButtonActionPerformed
	int nProfesores = AppState.getProfesores().size();
	System.out.println("El numero de profesores es: " + nProfesores);
	if(nProfesores < 1) {
        JOptionPane.showMessageDialog(this, "No has creado ningún profesor!", "Alerta", JOptionPane.INFORMATION_MESSAGE);
	return;
	}
        CrearcursoJfrime crearcursoJfrime = new CrearcursoJfrime();
        crearcursoJfrime.setFmrLista(this);
        crearcursoJfrime.setCursos(cursos);
        crearcursoJfrime.setVisible(true);
    }//GEN-LAST:event_crarcursojButtonActionPerformed

    private void CargaMasivacursojButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CargaMasivacursojButton7ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("Archivos CSV", "csv");
        fileChooser.setFileFilter(csvFilter);
        
        int fileChooserState = fileChooser.showOpenDialog(this);
        if(fileChooserState == JFileChooser.APPROVE_OPTION){
            File[] archivos = fileChooser.getSelectedFiles();
            
            for(File file: archivos){
                try{
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String linea;
                    
                    while((linea = reader.readLine()) !=null){
                        String[] datos = linea.split(",");
                        String codigocurso = datos[0];
                        String nombrecurso = datos[1];
                        String creditos = datos[2];
                        //String estudiantes = datos[3];
                        String codigoProfesor = datos[3];

                        //Se agrega a profesores
                        Curso cur = new Curso(codigocurso, nombrecurso, creditos, codigoProfesor);
												// curso.codigoProfesor = null;
                        AppState.cursos.add(cur);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                    System.out.println("Error al Cargar Archivo");
                }
            }
        }
        this.llenartablacursos();
    }//GEN-LAST:event_CargaMasivacursojButton7ActionPerformed

    private void EliminarCrusojButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarCrusojButton9ActionPerformed
       DefaultTableModel tablacurso = (DefaultTableModel) cursosjTable.getModel();
        int eliminar = cursosjTable.getSelectedRow();
        
        if(eliminar>=0){
            tablacurso.removeRow(eliminar);
            AppState.cursos.remove(eliminar);
        }
    }//GEN-LAST:event_EliminarCrusojButton9ActionPerformed

    private void ActualizarcursojButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarcursojButton8ActionPerformed
        int row = cursosjTable.getSelectedRow();
        String Codigo = (String) cursosjTable.getValueAt(row, 0);
        if(row !=-1){
            ActualizarCursoJFrime actucur = new ActualizarCursoJFrime();
            actucur.setFmrLista(this);
            actucur.setCodigo(Codigo);
            actucur.setVisible(true);
        }
    }//GEN-LAST:event_ActualizarcursojButton8ActionPerformed

    private void ExportarCrusojButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportarCrusojButton6ActionPerformed
         //Crear Carpeta si no existe
        File carpeta = new File("./reportes");
        if(!carpeta.exists()){
            carpeta.mkdir();
        }
        
        String ContenidoHTML = this.GenerartablacursosHTML();
        
        try{
            FileWriter fileWriter;
            fileWriter = new FileWriter("./reportes/Cursos.html");
            fileWriter.write(ContenidoHTML);
            fileWriter.close();
            
            String mensajeExito = "Reporte generado en la carpeta \"Reportes\" dentro del Proyecto.";
            JOptionPane.showMessageDialog(this, mensajeExito, "Alerta", JOptionPane.INFORMATION_MESSAGE);
        }catch(IOException e){
            System.out.println("No se pudo generar el documento.");
        }    
    }//GEN-LAST:event_ExportarCrusojButton6ActionPerformed

    private void CerrarSesionjButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CerrarSesionjButton10ActionPerformed
        LoginJFrame cerrar = new LoginJFrame();
        cerrar.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_CerrarSesionjButton10ActionPerformed

    private void txtcargamasivaalumnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcargamasivaalumnoActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("Archivos CSV", "csv");
        fileChooser.setFileFilter(csvFilter);
        
        int fileChooserState = fileChooser.showOpenDialog(this);
        if(fileChooserState == JFileChooser.APPROVE_OPTION){
            File[] archivos = fileChooser.getSelectedFiles();
            
            for(File file: archivos){
                try{
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String linea;
                    
                    while((linea = reader.readLine()) !=null){
                        String[] datos = linea.split(",");
                        String codigoestu = datos[0];
                        String nombreestu = datos[1];
                        String apellidoestu = datos[2];
                        String correoestu = datos[3];
                        String generoestu = datos[4];
                        //Se agrega a profesores
                        Estudiantes estudienate = new Estudiantes(codigoestu, nombreestu, apellidoestu, 
                                correoestu, "1234", generoestu, codigoestu, 
                                "1234");
                        AppState.usuarios.add(estudienate);
                        //Curso.idsEstudiantes.add(estudienate.toString());
                    }
                }catch (IOException e){
                    e.printStackTrace();
                    System.out.println("Error al Cargar Archivo");
                }
            }
        }
        this.llenartablaalumnos();
        //this.generargaficaprofesores();
    }//GEN-LAST:event_txtcargamasivaalumnoActionPerformed

    private void txtexportaralumnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtexportaralumnoActionPerformed
     //Crear Carpeta si no existe
        File carpeta = new File("./reportes");
        if(!carpeta.exists()){
            carpeta.mkdir();
        }
        
        String ContenidoHTML = this.GenerartabalalumnosHTML();
        
        try{
            FileWriter fileWriter;
            fileWriter = new FileWriter("./reportes/Estudiantes.html");
            fileWriter.write(ContenidoHTML);
            fileWriter.close();
            
            String mensajeExito = "Reporte generado en la carpeta \"Reportes\" dentro del Proyecto.";
            JOptionPane.showMessageDialog(this, mensajeExito, "Alerta", JOptionPane.INFORMATION_MESSAGE);
        }catch(IOException e){
            System.out.println("No se pudo generar el documento.");
        }
    }//GEN-LAST:event_txtexportaralumnoActionPerformed

    private void txtcerrarsesionalumnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcerrarsesionalumnoActionPerformed
        LoginJFrame cerrar = new LoginJFrame();
        cerrar.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_txtcerrarsesionalumnoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdministradorJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdministradorJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdministradorJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdministradorJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdministradorJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ActualizarcursojButton8;
    private javax.swing.JButton CargaMasivacursojButton7;
    private javax.swing.JButton CargamasivajButton12;
    private javax.swing.JButton CerrarSesionjButton10;
    private javax.swing.JButton CerrarsesionjButton15;
    private javax.swing.JButton CrearprofeButton11;
    private javax.swing.JButton EliminarCrusojButton9;
    private javax.swing.JButton EliminarprofejButton14;
    private javax.swing.JTable EstudiantesjTable1;
    private javax.swing.JButton ExportarCrusojButton6;
    private javax.swing.JButton ExportarprofejButton16;
    private javax.swing.JPanel ProfesorjPanel;
    private javax.swing.JButton actualizarprofejButton13;
    private javax.swing.JPanel alumnosjPanel5;
    private javax.swing.JButton crarcursojButton;
    private javax.swing.JTable cursosjTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel profejPanel7;
    private javax.swing.JTable profesoresjTable;
    private javax.swing.JButton txtcargamasivaalumno;
    private javax.swing.JButton txtcerrarsesionalumno;
    private javax.swing.JButton txtexportaralumno;
    // End of variables declaration//GEN-END:variables
}
