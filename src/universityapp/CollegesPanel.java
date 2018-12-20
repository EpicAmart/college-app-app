/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universityapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.toedter.calendar.JTextFieldDateEditor;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import java.text.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.ScrollPaneConstants.*;
import javax.swing.table.*;

/**
 *
 * @author satranga
 */
public class CollegesPanel extends javax.swing.JPanel {

    /**
     * Creates new form CollegesPanel
     */
    DefaultListModel promptModel = new DefaultListModel();//JList model for prompts belonging to a college
    DefaultListModel progressModel = new DefaultListModel();//JList model for prompts that are in progress 
    DefaultListModel completedModel = new DefaultListModel();//JList model for prompts that are completed
    public ArrayList<College> colleges;//ArrayList to store college objects
    public ArrayList<Prompt> prompts;//ArrayList to store prompt objects
    Date d = new Date();//Retrieves current date
    SimpleDateFormat simple = new SimpleDateFormat("MMM dd, yyyy");

    public CollegesPanel() throws FileNotFoundException {
        initComponents();

        this.colleges = new ArrayList<College>();
        this.prompts = new ArrayList<Prompt>();

        collegeTableScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);//Rids of horizontal scroll bar and prevents horizontal scrolling
        ((JTextFieldDateEditor) addDeadlineChooser.getDateEditor()).setEditable(false);//Prevents errors by setting date; Only modifiable through day chooser

        addDeadlineChooser.setDate(d);//Sets date to default current date to ensure the field is not empty

        //Sets varying dimensions for table columns programatically 
        TableColumnModel cm = collegeTable.getColumnModel();
        cm.getColumn(0).setPreferredWidth(400);
        cm.getColumn(1).setPreferredWidth(120);
        cm.getColumn(2).setPreferredWidth(120);
        cm.getColumn(3).setPreferredWidth(200);
        cm.getColumn(4).setPreferredWidth(90);

        //Allows for only one college/prompt to be selected at a time
        collegeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        promptTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        loadPromptsFrom("prompt.json");
        loadCollegesFrom("college.json");
        if (colleges.size() != 0) {
            for (int i = 0; i < colleges.size(); i++) {
                updateTable(colleges.get(i).getCollegeName(), colleges.get(i).getDeadline(), colleges.get(i).getClassification(), colleges.get(i).getProgress(), colleges.get(i).getStatus());
                
            }
        }

        //Renders cell to Progress Bar format
        collegeTable.getColumn("Progress").setCellRenderer(new ProgressCellRender());
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static String readJsonFromUrl(String url) throws IOException {
        InputStream is = new URL(url).openStream();

        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);

            CharSequence a = "school.name";
            CharSequence b = "name";
            CharSequence c = "2016.cost.tuition.out_of_state";
            CharSequence d = "tuition";
            CharSequence e = "2016.admissions.admission_rate.overall";
            CharSequence f = "admissionRate";
            CharSequence g = "2016.admissions.act_scores.75th_percentile.cumulative";
            CharSequence h = "targetAct";
            CharSequence i = "2016.student.size";
            CharSequence j = "population";
            CharSequence k = "{\"metadata\":{\"total\":1,\"page\":0,\"per_page\":20},\"results\":[";
            CharSequence l = "";
            CharSequence m = "]}";
            CharSequence n = "";

            jsonText = jsonText.replace(a, b);
            jsonText = jsonText.replace(c, d);
            jsonText = jsonText.replace(e, f);
            jsonText = jsonText.replace(g, h);
            jsonText = jsonText.replace(i, j);
            jsonText = jsonText.replace(k, l);
            jsonText = jsonText.replace(m, n);

            return jsonText;
        } finally {
            is.close();
        }
    }

    public static CollegeView loadCollegeViewFrom(String json) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        CollegeView c = gson.fromJson(json, CollegeView.class);
        return c;
    }

    public void loadCollegesFrom(String fileName) throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        College[] c;
        c = gson.fromJson(new FileReader(fileName), College[].class);
        if (c.length != 0) {
            for (int i = 0; i < c.length; i++) {
                colleges.add(c[i]);
            }
        }

    }

    public void loadPromptsFrom(String fileName) throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Prompt[] p;
        p = gson.fromJson(new FileReader(fileName), Prompt[].class);
        if (p.length != 0) {
            for (int i = 0; i < p.length; i++) {
                prompts.add(p[i]);
            }
        }

    }

    public void saveCollegeTo(String fileName) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(colleges, writer);
        }

    }

    public void savePromptTo(String fileName) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(prompts, writer);
        }

    }

    public void updateTable(String collegeName, Date deadline, String classification, float progress, String status) {
        DefaultTableModel model = (DefaultTableModel) collegeTable.getModel();
        SimpleDateFormat ft1 = new SimpleDateFormat("MMM dd, yyyy");
        model.addRow(new Object[]{collegeName, ft1.format(deadline), classification, progress, status});
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        modifyCollegeDialog = new javax.swing.JDialog();
        modifyCollegeLabel = new javax.swing.JLabel();
        modifyComboBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        inProgressList = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        completedList = new javax.swing.JList<>();
        jLabel5 = new javax.swing.JLabel();
        updateModifyButton = new javax.swing.JButton();
        viewPromptButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        modifyNameTextField = new javax.swing.JTextField();
        completePromptButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        modifyDateChooser = new com.toedter.calendar.JDateChooser();
        collegeErrorDialog = new javax.swing.JDialog();
        collegeErrorPane = new javax.swing.JOptionPane();
        promptViewDialog = new javax.swing.JDialog();
        modifyCollegeLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        viewPromptField = new javax.swing.JTextField();
        viewNotesField = new javax.swing.JTextField();
        viewWordCountField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        collegeViewDialog = new javax.swing.JDialog();
        collegeViewTitleLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        viewPromptsList = new javax.swing.JList<>();
        acceptanceTextField = new javax.swing.JTextField();
        tuitionTextField = new javax.swing.JTextField();
        sizeTextField = new javax.swing.JTextField();
        actTextField = new javax.swing.JTextField();
        viewDeadlineTextField = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        updateButton = new javax.swing.JButton();
        collegesLabel = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();
        collegeListLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        collegeTableScrollPane = new javax.swing.JScrollPane();
        collegeTable = new javax.swing.JTable();
        addCollegeField = new javax.swing.JTextField();
        addCollegeLabel = new javax.swing.JLabel();
        collegeNameLabel = new javax.swing.JLabel();
        deadlineLabel = new javax.swing.JLabel();
        classificationLabel = new javax.swing.JLabel();
        modifyButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        promptTableScrollPane = new javax.swing.JScrollPane();
        promptTable = new javax.swing.JTable();
        addPromptButton = new javax.swing.JButton();
        classificationComboBox = new javax.swing.JComboBox<>();
        addDeadlineChooser = new com.toedter.calendar.JDateChooser();
        viewCollegeButton = new javax.swing.JButton();

        modifyCollegeDialog.setBackground(new java.awt.Color(102, 204, 255));

        modifyCollegeLabel.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        modifyCollegeLabel.setText("MODIFY COLLEGE");

        modifyComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Applying", "Submitted", "Accepted", "Waitlisted", "Deferred", "Rejected" }));
        modifyComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyComboBoxActionPerformed(evt);
            }
        });

        jLabel4.setText("Update Status");

        jScrollPane1.setViewportView(inProgressList);

        jScrollPane2.setViewportView(completedList);

        jLabel5.setText("Update Prompts");

        updateModifyButton.setText("Update");
        updateModifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateModifyButtonActionPerformed(evt);
            }
        });

        viewPromptButton.setText("View");
        viewPromptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewPromptButtonActionPerformed(evt);
            }
        });

        jLabel6.setText("Update Deadline");

        jLabel7.setText("Name:");

        modifyNameTextField.setEditable(false);

        completePromptButton.setText("Complete");
        completePromptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                completePromptButtonActionPerformed(evt);
            }
        });

        jLabel10.setText("In Progress");

        jLabel11.setText("Completed");

        javax.swing.GroupLayout modifyCollegeDialogLayout = new javax.swing.GroupLayout(modifyCollegeDialog.getContentPane());
        modifyCollegeDialog.getContentPane().setLayout(modifyCollegeDialogLayout);
        modifyCollegeDialogLayout.setHorizontalGroup(
            modifyCollegeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, modifyCollegeDialogLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(updateModifyButton)
                .addGap(146, 146, 146))
            .addGroup(modifyCollegeDialogLayout.createSequentialGroup()
                .addGroup(modifyCollegeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(modifyCollegeDialogLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(modifyCollegeLabel))
                    .addGroup(modifyCollegeDialogLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(modifyCollegeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(modifyCollegeDialogLayout.createSequentialGroup()
                                .addGroup(modifyCollegeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(completePromptButton)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(modifyCollegeDialogLayout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(18, 18, 18)))
                                .addGroup(modifyCollegeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(modifyCollegeDialogLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(modifyCollegeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(viewPromptButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(modifyCollegeDialogLayout.createSequentialGroup()
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(modifyCollegeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(modifyComboBox, 0, 153, Short.MAX_VALUE)
                                                    .addComponent(jLabel6)
                                                    .addComponent(modifyDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                                    .addGroup(modifyCollegeDialogLayout.createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addComponent(jLabel11)
                                        .addGap(46, 46, 46)
                                        .addComponent(jLabel4))))
                            .addGroup(modifyCollegeDialogLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(modifyNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(modifyCollegeDialogLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        modifyCollegeDialogLayout.setVerticalGroup(
            modifyCollegeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modifyCollegeDialogLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(modifyCollegeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(modifyCollegeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(modifyNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(modifyCollegeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, modifyCollegeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jLabel11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(modifyCollegeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(modifyCollegeDialogLayout.createSequentialGroup()
                        .addComponent(modifyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(modifyDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(1, 1, 1)
                .addGroup(modifyCollegeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(completePromptButton)
                    .addComponent(viewPromptButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updateModifyButton)
                .addContainerGap())
        );

        collegeErrorDialog.setBackground(new java.awt.Color(102, 204, 255));

        javax.swing.GroupLayout collegeErrorDialogLayout = new javax.swing.GroupLayout(collegeErrorDialog.getContentPane());
        collegeErrorDialog.getContentPane().setLayout(collegeErrorDialogLayout);
        collegeErrorDialogLayout.setHorizontalGroup(
            collegeErrorDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(collegeErrorPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        collegeErrorDialogLayout.setVerticalGroup(
            collegeErrorDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(collegeErrorPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        promptViewDialog.setBackground(new java.awt.Color(102, 204, 255));
        promptViewDialog.setResizable(false);

        modifyCollegeLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        modifyCollegeLabel1.setText("VIEW PROMPT");

        jLabel8.setText("Prompt:");

        jLabel9.setText("Word Count:");

        viewPromptField.setEditable(false);

        viewNotesField.setEditable(false);

        viewWordCountField.setEditable(false);

        jLabel12.setText("Notes:");

        javax.swing.GroupLayout promptViewDialogLayout = new javax.swing.GroupLayout(promptViewDialog.getContentPane());
        promptViewDialog.getContentPane().setLayout(promptViewDialogLayout);
        promptViewDialogLayout.setHorizontalGroup(
            promptViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, promptViewDialogLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(promptViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel12)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(promptViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(viewPromptField, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewNotesField, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewWordCountField, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, promptViewDialogLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(modifyCollegeLabel1)
                .addGap(112, 112, 112))
        );
        promptViewDialogLayout.setVerticalGroup(
            promptViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(promptViewDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(modifyCollegeLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(promptViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(viewPromptField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(promptViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(promptViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(viewWordCountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9))
                    .addGroup(promptViewDialogLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(promptViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(viewNotesField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        collegeViewTitleLabel.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        collegeViewTitleLabel.setText("COLLEGE NAME");

        jLabel2.setText("Information");

        jLabel3.setText("Application");

        jLabel13.setText("Acceptance:");

        jLabel14.setText("Tuition:");

        jLabel15.setText("Population:");

        jLabel16.setText("Target ACT:");

        jLabel17.setText("Deadline:");

        jLabel18.setText("Prompts:");

        jScrollPane3.setViewportView(viewPromptsList);

        acceptanceTextField.setEditable(false);

        tuitionTextField.setEditable(false);

        sizeTextField.setEditable(false);

        actTextField.setEditable(false);

        viewDeadlineTextField.setEditable(false);

        javax.swing.GroupLayout collegeViewDialogLayout = new javax.swing.GroupLayout(collegeViewDialog.getContentPane());
        collegeViewDialog.getContentPane().setLayout(collegeViewDialogLayout);
        collegeViewDialogLayout.setHorizontalGroup(
            collegeViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collegeViewDialogLayout.createSequentialGroup()
                .addGroup(collegeViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(collegeViewDialogLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(collegeViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(collegeViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(tuitionTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                            .addComponent(acceptanceTextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sizeTextField))
                        .addGroup(collegeViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, collegeViewDialogLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel16)
                                .addGap(18, 18, 18)
                                .addComponent(actTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(collegeViewDialogLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(collegeViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel17))
                                .addGap(18, 18, 18)
                                .addGroup(collegeViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(collegeViewDialogLayout.createSequentialGroup()
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(viewDeadlineTextField)))))
                    .addGroup(collegeViewDialogLayout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(collegeViewTitleLabel)))
                .addContainerGap())
            .addGroup(collegeViewDialogLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(58, 58, 58))
        );
        collegeViewDialogLayout.setVerticalGroup(
            collegeViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collegeViewDialogLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(collegeViewTitleLabel)
                .addGap(18, 18, 18)
                .addGroup(collegeViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(collegeViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel16)
                    .addComponent(acceptanceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(actTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(collegeViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewDeadlineTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel14)
                    .addComponent(tuitionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(collegeViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(collegeViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sizeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)
                        .addComponent(jLabel18))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        jPanel1.setBackground(new java.awt.Color(102, 204, 255));

        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        collegesLabel.setFont(new java.awt.Font("Times New Roman", 0, 48)); // NOI18N
        collegesLabel.setText("MY COLLEGES");

        backButton.setText("Save and Return to Main Page");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        collegeListLabel.setText("College List");

        collegeTableScrollPane.setHorizontalScrollBar(null);

        collegeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "College", "Deadline", "Classification", "Progress", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        collegeTableScrollPane.setViewportView(collegeTable);
        if (collegeTable.getColumnModel().getColumnCount() > 0) {
            collegeTable.getColumnModel().getColumn(0).setResizable(false);
            collegeTable.getColumnModel().getColumn(1).setResizable(false);
            collegeTable.getColumnModel().getColumn(2).setResizable(false);
            collegeTable.getColumnModel().getColumn(3).setResizable(false);
            collegeTable.getColumnModel().getColumn(4).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(collegeTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 959, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(collegeTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
        );

        addCollegeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCollegeFieldActionPerformed(evt);
            }
        });

        addCollegeLabel.setText("Add A College");

        collegeNameLabel.setText("University Name:");

        deadlineLabel.setText("Deadline:");

        classificationLabel.setText("Classification:");

        modifyButton.setText("Modify");
        modifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        promptTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Prompt", "Word Count", "Notes"
            }
        ));
        promptTableScrollPane.setViewportView(promptTable);

        addPromptButton.setText("+Add Prompt");
        addPromptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPromptButtonActionPerformed(evt);
            }
        });

        classificationComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Classification", "Safety", "Target", "50/50", "Reach" }));

        viewCollegeButton.setText("View College");
        viewCollegeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewCollegeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(backButton)
                        .addGap(103, 103, 103)
                        .addComponent(collegesLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(458, 458, 458)
                        .addComponent(collegeListLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(modifyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 13, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(collegeNameLabel)
                                    .addComponent(addCollegeField, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(deadlineLabel)
                                        .addGap(231, 231, 231)
                                        .addComponent(classificationLabel))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(addDeadlineChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(68, 68, 68)
                                        .addComponent(classificationComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(promptTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 667, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addPromptButton)
                            .addComponent(viewCollegeButton))))
                .addGap(28, 28, 28))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(addCollegeLabel)
                .addGap(451, 451, 451))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(backButton))
                    .addComponent(collegesLabel))
                .addGap(12, 12, 12)
                .addComponent(collegeListLabel)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(addCollegeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(collegeNameLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(deadlineLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(addCollegeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(classificationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(addDeadlineChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(classificationLabel)
                                .addGap(33, 33, 33)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(promptTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(viewCollegeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addPromptButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modifyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addCollegeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCollegeFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addCollegeFieldActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        ((MainFrame) this.getRootPane().getParent()).showPanel(0);
        //Resets objects to default
        addCollegeField.setText("");
        addDeadlineChooser.setDate(d);
        classificationComboBox.setSelectedItem("Select Classification");

        //Removes all unsaved rows in prompt table
        for (int i = 0; i < promptTable.getModel().getRowCount(); i++) {
            ((DefaultTableModel) promptTable.getModel()).removeRow(i);
        }

        //Save Colleges to JSON
        try {
            saveCollegeTo("college.json");
        } catch (IOException ex) {
            Logger.getLogger(CalendarPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            //Save Prompts to JSON
            savePromptTo("prompt.json");
        } catch (IOException ex) {
            Logger.getLogger(CollegesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_backButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        //Establishes variables
        String collegeName;
        Date deadline;
        String classification;
        boolean errorHandlerCollege = true;

        boolean errorHandlerPrompt = true;
        boolean errorHandlerWordCount = true;
        String prompt = "";
        int wordCount = 0;
        String notes = "";

        if (addCollegeField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(collegeErrorPane, "Please enter a valid university name.");//Ensures TextField has content
            errorHandlerCollege = false;
        } else {
            int comparerCollege;//Ensures that every character is a lowercase or uppercase letter
            for (int i = 0; i < addCollegeField.getText().length(); i++) {
                comparerCollege = (int) addCollegeField.getText().charAt(i);
                if (comparerCollege >= 65 && comparerCollege <= 90) {

                } else if (comparerCollege == 32) {

                } else if (comparerCollege >= 97 && comparerCollege <= 122) {

                } else {
                    errorHandlerCollege = false;
                }
            }
        }

        if (errorHandlerCollege) {
            collegeName = addCollegeField.getText();
            deadline = addDeadlineChooser.getDate();//Deadline is fixed as a date object and can therefore be stored
            if ("Select Classification".equals(classificationComboBox.getSelectedItem())) {
                JOptionPane.showMessageDialog(collegeErrorPane, "Please select a classification for this college.");
            } else {
                classification = classificationComboBox.getSelectedItem().toString();
                updateTable(collegeName, deadline, classification, 0, "Applying");
                College c = new College(collegeName, deadline, classification, 0, "Applying");
                colleges.add(c);

                JOptionPane.showMessageDialog(collegeErrorPane, "Your college has been added.");

                //Prompts depend on creation of college object
                for (int row = 0; row < promptTable.getRowCount(); row++) {
                    for (int column = 0; column <= 2; column++) {
                        if (column == 0) {
                            if ("Insert Prompt".equals(promptTable.getValueAt(row, column))) {
                                JOptionPane.showMessageDialog(collegeErrorPane, "Please enter valid prompt(s)");
                            } else if ("".equals(promptTable.getValueAt(row, column))) {
                                JOptionPane.showMessageDialog(collegeErrorPane, "Please enter valid prompt(s)");
                            } else {
                                int comparerPrompt;//Ensures prompt summary contains only lower and uppercase letters
                                for (int i = 0; i < ((String) promptTable.getValueAt(row, column)).length(); i++) {
                                    comparerPrompt = (int) ((String) promptTable.getValueAt(row, column)).charAt(i);
                                    if (comparerPrompt >= 65 && comparerPrompt <= 90) {

                                    } else if (comparerPrompt == 32) {

                                    } else if (comparerPrompt >= 97 && comparerPrompt <= 122) {

                                    } else {
                                        errorHandlerPrompt = false;
                                    }
                                }

                                if (!errorHandlerPrompt) {
                                    JOptionPane.showMessageDialog(collegeErrorPane, "Please enter valid prompt(s)");
                                } else {
                                    prompt = (String) promptTable.getValueAt(row, column);
                                }
                            }
                        } else if (column == 1) {
                            if ("Insert Word Count".equals(promptTable.getValueAt(row, column))) {
                                JOptionPane.showMessageDialog(collegeErrorPane, "Please enter valid word count(s)");
                            } else if ("".equals(promptTable.getValueAt(row, column))) {
                                JOptionPane.showMessageDialog(collegeErrorPane, "Please enter valid word count(s)");
                            } else {
                                int comparerWordCount;//Verifies that word count consists only of numerical values
                                for (int i = 0; i < ((String) promptTable.getValueAt(row, column)).length(); i++) {
                                    comparerWordCount = (int) ((promptTable.getValueAt(row, column).toString())).charAt(i);
                                    if (comparerWordCount >= 48 && comparerWordCount <= 57) {

                                    } else {
                                        errorHandlerWordCount = false;
                                    }
                                }

                                if (!errorHandlerWordCount) {
                                    JOptionPane.showMessageDialog(collegeErrorPane, "Please enter valid word counts.");
                                } else {
                                    wordCount = Integer.parseInt(promptTable.getValueAt(row, column).toString());
                                }
                            }
                        } else if (column == 2) {
                            if ("Insert Notes".equals(promptTable.getValueAt(row, column))) {
                                notes = "";
                            } else if ("".equals(promptTable.getValueAt(row, column))) {
                                notes = "";
                            } else {
                                notes = (String) promptTable.getValueAt(row, column);//Notes is optional and has no restrictions
                            }

                        }

                    }
                    if (!prompt.isEmpty() && wordCount != 0) {
                        Prompt p = new Prompt(prompt, wordCount, notes, false, colleges.get(colleges.size() - 1));//Prompt begins as incomplete and is matched to a college
                        prompts.add(p);//Added to prompt ArrayList
                    }

                    prompt = "";
                    wordCount = 0;
                    notes = "";
                    errorHandlerPrompt = true;
                    errorHandlerWordCount = true;
                }
            }
        } else if (errorHandlerCollege == false && !addCollegeField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(collegeErrorPane, "Please enter a valid university name.");
        }

        //Clear Fields After
        addCollegeField.setText("");
        addDeadlineChooser.setDate(d);
        classificationComboBox.setSelectedItem("Select Classification");

        //Remove Table Rows
        while (promptTable.getModel().getRowCount() > 0) {
            ((DefaultTableModel) promptTable.getModel()).removeRow(0);
        }


    }//GEN-LAST:event_updateButtonActionPerformed

    private void modifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyButtonActionPerformed
        //Opens "Modify" dialog and allows for completion and viewing of prompts as well as status and deadline updates
        if (collegeTable.getSelectionModel().isSelectionEmpty()) {
            JOptionPane.showMessageDialog(collegeErrorPane, "Please select a college to modify.");
        } else {
            String selectedCollege = (String) collegeTable.getModel().getValueAt(collegeTable.getSelectedRow(), 0);
            College modified = null;
            for (int p = 0; p < colleges.size(); p++) {
                if (selectedCollege.equals(colleges.get(p).getCollegeName())) {
                    modified = colleges.get(p);
                }
            }

            //Object oriented programming allows for access and viewing of college fields
            modifyNameTextField.setText(modified.getCollegeName());
            modifyDateChooser.setDate(modified.getDeadline());
            modifyComboBox.setSelectedItem(modified.getStatus());

            //Differentiates between completed prompt and prompt in-progress based on completed boolean in class
            progressModel.clear();
            completedModel.clear();
            inProgressList.setModel(progressModel);
            completedList.setModel(completedModel);

            //Adds prompt summaries to JLists on "Modify" dialog
            for (int i = 0; i < prompts.size(); i++) {
                if (!modified.getCollegeName().equals(prompts.get(i).getCollege().getCollegeName())) {
                } else {
                    if (prompts.get(i).getCompleted()) {
                        completedModel.addElement(prompts.get(i).getPrompt());
                    } else {
                        progressModel.addElement(prompts.get(i).getPrompt());
                    }
                }
            }

            modifyCollegeDialog.setVisible(true);
            modifyCollegeDialog.setBounds(300, 150, 400, 350);
        }


    }//GEN-LAST:event_modifyButtonActionPerformed

    private void modifyComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyComboBoxActionPerformed

    }//GEN-LAST:event_modifyComboBoxActionPerformed

    private void updateModifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateModifyButtonActionPerformed
        //Completes update
        College updated = null;
        SimpleDateFormat ft2 = new SimpleDateFormat("MMM dd, yyyy");
        for (int p = 0; p < colleges.size(); p++) {
            if ((modifyNameTextField.getText()).equals(colleges.get(p).getCollegeName())) {
                updated = colleges.get(p);
            }
        }

        //Updates date and status
        collegeTable.setValueAt(ft2.format(modifyDateChooser.getDate()), collegeTable.getSelectedRow(), 1);
        collegeTable.setValueAt((String) modifyComboBox.getSelectedItem(), collegeTable.getSelectedRow(), 4);
        collegeTable.setValueAt(updated.getProgress(), collegeTable.getSelectedRow(), 3);
        
        updated.setDeadline(modifyDateChooser.getDate());
        updated.setStatus((String) modifyComboBox.getSelectedItem());
        modifyCollegeDialog.setVisible(false);
    }//GEN-LAST:event_updateModifyButtonActionPerformed

    private void addPromptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPromptButtonActionPerformed
        //Adds a row/prompt to prompt table
        DefaultTableModel promptModel = (DefaultTableModel) promptTable.getModel();
        promptModel.addRow(new Object[]{"Insert Prompt", "Insert Word Count", "Insert Notes"});
    }//GEN-LAST:event_addPromptButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        //Delete functionality covers both colleges and extra prompts
        if (collegeTable.getSelectionModel().isSelectionEmpty() && promptTable.getSelectionModel().isSelectionEmpty()) {
            JOptionPane.showMessageDialog(collegeErrorPane, "Please select a college or prompt to delete.");
        } else {
            if (collegeTable.getSelectionModel().isSelectionEmpty()) {//Only prompt is selected
                String deletedPrompt = (String) promptTable.getModel().getValueAt(promptTable.getSelectedRow(), 0);
                Prompt deletePrompt = null;
                for (int p = 0; p < prompts.size(); p++) {
                    if (deletedPrompt.equals(prompts.get(p).getPrompt())) {
                        deletePrompt = prompts.get(p);
                    }

                }
                ((DefaultTableModel) promptTable.getModel()).removeRow(promptTable.getSelectedRow());
                prompts.remove(deletePrompt);
                JOptionPane.showMessageDialog(collegeErrorPane, "Your prompt has been deleted.");
            } else if (promptTable.getSelectionModel().isSelectionEmpty()) {//Only college is selected
                String deletedCollege = (String) collegeTable.getModel().getValueAt(collegeTable.getSelectedRow(), 0);
                College deleteCollege = null;
                for (int p = 0; p < colleges.size(); p++) {
                    if (deletedCollege.equals(colleges.get(p).getCollegeName())) {
                        deleteCollege = colleges.get(p);
                    }

                }
                ((DefaultTableModel) collegeTable.getModel()).removeRow(collegeTable.getSelectedRow());
                colleges.remove(deleteCollege);
                JOptionPane.showMessageDialog(collegeErrorPane, "Your college has been deleted.");
            } else {//Both are selected
                String deletedCollege = (String) collegeTable.getModel().getValueAt(collegeTable.getSelectedRow(), 0);
                College deleteCollege = null;
                for (int p = 0; p < colleges.size(); p++) {
                    if (deletedCollege.equals(colleges.get(p).getCollegeName())) {
                        deleteCollege = colleges.get(p);
                    }

                }
                ((DefaultTableModel) collegeTable.getModel()).removeRow(collegeTable.getSelectedRow());
                colleges.remove(deleteCollege);

                //Deletes designated prompts from table and ArrayList
                String deletedPrompt = (String) promptTable.getModel().getValueAt(promptTable.getSelectedRow(), 0);
                Prompt deletePrompt = null;
                for (int p = 0; p < prompts.size(); p++) {
                    if (deletedPrompt.equals(prompts.get(p).getPrompt())) {
                        deletePrompt = prompts.get(p);
                    }

                }
                ((DefaultTableModel) promptTable.getModel()).removeRow(promptTable.getSelectedRow());
                prompts.remove(deletePrompt);

                JOptionPane.showMessageDialog(collegeErrorPane, "Your college and prompt have been deleted.");
            }

        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void completePromptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completePromptButtonActionPerformed
        //Helps track progress and eliminate prompts
        if (!completedList.isSelectionEmpty()) {
            JOptionPane.showMessageDialog(collegeErrorPane, "Please select in-progress prompt(s) to complete.");
        } else {
            if (inProgressList.isSelectionEmpty()) {
                JOptionPane.showMessageDialog(collegeErrorPane, "Please select in-cell prompt(s) to complete.");
            } else {
                //Allows for multiple prompts to be deleted
                String completable = "";
                int count = inProgressList.getSelectedIndices().length;
                for (int k = 0; k < count; k++) {
                    completable = inProgressList.getSelectedValue();
                    for (int l = 0; l < prompts.size(); l++) {
                        if (completable.equals(prompts.get(l).getPrompt())) {
                            prompts.get(l).setCompleted(true);

                        }
                    }
                    //Moves selection to following item
                    inProgressList.removeSelectionInterval(inProgressList.getSelectedIndex(), inProgressList.getSelectedIndex());
                }

                //Retrieview prompt in question as opposed to simply the prompt name or status of completion
                Prompt completed = null;
                for (int p = 0; p < prompts.size(); p++) {
                    if (completable.equals(prompts.get(p).getPrompt())) {
                        completed = prompts.get(p);
                    }
                }

                //Retrieves college of designated prompt
                College appliedTo = completed.getCollege();

                //Resets JLists
                progressModel.clear();
                completedModel.clear();
                inProgressList.setModel(progressModel);
                completedList.setModel(completedModel);

                //Floats to assist in percent calculation
                float completedWordCount = 0;
                float totalWordCount = 0;

                //Totals word count for progress calculation and adds elements to JLists
                for (int i = 0; i < prompts.size(); i++) {
                    if (!appliedTo.equals(prompts.get(i).getCollege())) {
                    } else {
                        if (prompts.get(i).getCompleted()) {
                            completedModel.addElement(prompts.get(i).getPrompt());
                            completedWordCount += prompts.get(i).getWordCount();
                            totalWordCount += prompts.get(i).getWordCount();
                        } else {
                            progressModel.addElement(prompts.get(i).getPrompt());
                            totalWordCount += prompts.get(i).getWordCount();
                        }
                    }
                }
                float quotient = completedWordCount / totalWordCount;

                String name = appliedTo.getCollegeName();
                int keyRow = 0;
                //Retrieves row for designated college for each prompt
                for (int i = 0; i < collegeTable.getRowCount(); i++) {
                    if (name.equals(collegeTable.getValueAt(i, 0))) {
                        keyRow = i;
                    }
                }
                //Completes progress bar update
                appliedTo.setProgress(quotient);
            }

        }
    }//GEN-LAST:event_completePromptButtonActionPerformed

    private void viewPromptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewPromptButtonActionPerformed
        //Functionality for both inProgressList and completedList
        if (inProgressList.isSelectionEmpty() && completedList.isSelectionEmpty()) {
            JOptionPane.showMessageDialog(collegeErrorPane, "Please select a single prompt to view.");
        } else if (inProgressList.getSelectedIndices().length > 1 || completedList.getSelectedIndices().length > 1) {
            JOptionPane.showMessageDialog(collegeErrorPane, "Please select a single prompt to view.");
        } else if (!inProgressList.isSelectionEmpty() && !completedList.isSelectionEmpty()) {
            JOptionPane.showMessageDialog(collegeErrorPane, "Please select a single prompt to view.");
        } else if (!inProgressList.isSelectionEmpty() && completedList.isSelectionEmpty()) {
            String viewedPromptName = inProgressList.getSelectedValue();
            Prompt viewedPrompt = null;
            for (int p = 0; p < prompts.size(); p++) {
                if (viewedPromptName.equals(prompts.get(p).getPrompt())) {
                    viewedPrompt = prompts.get(p);
                }
            }

            //Sets fields of "View" dialog for prompts
            viewPromptField.setText(viewedPrompt.getPrompt());
            viewWordCountField.setText(viewedPrompt.getWordCount() + "");
            viewNotesField.setText(viewedPrompt.getNotes());
            promptViewDialog.setVisible(true);
            promptViewDialog.setBounds(350, 150, 350, 200);
        } else {
            String viewedPromptName = completedList.getSelectedValue();
            Prompt viewedPrompt = null;
            for (int p = 0; p < prompts.size(); p++) {
                if (viewedPromptName.equals(prompts.get(p).getPrompt())) {
                    viewedPrompt = prompts.get(p);
                }
            }

            //Sets fields of "View" dialog for prompts
            viewPromptField.setText(viewedPrompt.getPrompt());
            viewWordCountField.setText(viewedPrompt.getWordCount() + "");
            viewNotesField.setText(viewedPrompt.getNotes());
            promptViewDialog.setVisible(true);
            promptViewDialog.setBounds(350, 150, 350, 200);
        }
    }//GEN-LAST:event_viewPromptButtonActionPerformed

    private void viewCollegeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewCollegeButtonActionPerformed
        if (collegeTable.getSelectionModel().isSelectionEmpty()) {
            JOptionPane.showMessageDialog(collegeErrorPane, "Please select a college to view.");
        } else {
            try {
                String url = "https://api.data.gov/ed/collegescorecard/v1/schools.json?school.name=";
                String name = (String) collegeTable.getModel().getValueAt(collegeTable.getSelectedRow(), 0);
                String query = "&_fields=school.name,2016.admissions.admission_rate.overall,2016.admissions.act_scores.75th_percentile.cumulative,2016.student.size,2016.cost.tuition.out_of_state&api_key=xgjprcRzHvTuJcu706qiyaiBd8NEAvHsvMobdBc6";
                String json = null;

                json = readJsonFromUrl(url + name + query);

                if ("".equals(json) || json == null || "{\"metadata\":{\"total\":0,\"page\":0,\"per_page\":20},\"results\":[".equals(json)) {
                    JOptionPane.showMessageDialog(collegeErrorPane, "This college could not be searched.");
                } else {

                    CollegeView search = loadCollegeViewFrom(json);

                    collegeViewTitleLabel.setText(search.getCollegeViewName());
                    acceptanceTextField.setText(search.getAdmissionRate());
                    tuitionTextField.setText(search.getTuition());
                    sizeTextField.setText(search.getPopulation());
                    actTextField.setText(search.getTargetAct());

                    String selectedViewedCollege = (String) collegeTable.getModel().getValueAt(collegeTable.getSelectedRow(), 0);
                    College viewed = null;
                    for (int p = 0; p < colleges.size(); p++) {
                        if (selectedViewedCollege.equals(colleges.get(p).getCollegeName())) {
                            viewed = colleges.get(p);
                        }
                    }

                    promptModel.clear();
                    viewPromptsList.setModel(promptModel);

                    //Adds prompt summaries to JLists on "Modify" dialog
                    for (int i = 0; i < prompts.size(); i++) {
                        if (!viewed.equals(prompts.get(i).getCollege())) {
                        } else {
                            promptModel.addElement(prompts.get(i).getPrompt());
                        }
                    }

                    viewDeadlineTextField.setText(simple.format(viewed.getDeadline()));

                    collegeViewDialog.setVisible(true);
                    collegeViewDialog.setBounds(300, 150, 400, 350);
                }
            } catch (IOException ex) {
                Logger.getLogger(CollegesPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_viewCollegeButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField acceptanceTextField;
    private javax.swing.JTextField actTextField;
    private javax.swing.JTextField addCollegeField;
    private javax.swing.JLabel addCollegeLabel;
    private com.toedter.calendar.JDateChooser addDeadlineChooser;
    private javax.swing.JButton addPromptButton;
    private javax.swing.JButton backButton;
    private javax.swing.JComboBox<String> classificationComboBox;
    private javax.swing.JLabel classificationLabel;
    private javax.swing.JDialog collegeErrorDialog;
    private javax.swing.JOptionPane collegeErrorPane;
    private javax.swing.JLabel collegeListLabel;
    private javax.swing.JLabel collegeNameLabel;
    private javax.swing.JTable collegeTable;
    private javax.swing.JScrollPane collegeTableScrollPane;
    private javax.swing.JDialog collegeViewDialog;
    private javax.swing.JLabel collegeViewTitleLabel;
    private javax.swing.JLabel collegesLabel;
    private javax.swing.JButton completePromptButton;
    private javax.swing.JList<String> completedList;
    private javax.swing.JLabel deadlineLabel;
    private javax.swing.JButton deleteButton;
    private javax.swing.JList<String> inProgressList;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton modifyButton;
    private javax.swing.JDialog modifyCollegeDialog;
    private javax.swing.JLabel modifyCollegeLabel;
    private javax.swing.JLabel modifyCollegeLabel1;
    private javax.swing.JComboBox<String> modifyComboBox;
    private com.toedter.calendar.JDateChooser modifyDateChooser;
    private javax.swing.JTextField modifyNameTextField;
    private javax.swing.JTable promptTable;
    private javax.swing.JScrollPane promptTableScrollPane;
    private javax.swing.JDialog promptViewDialog;
    private javax.swing.JTextField sizeTextField;
    private javax.swing.JTextField tuitionTextField;
    private javax.swing.JButton updateButton;
    private javax.swing.JButton updateModifyButton;
    private javax.swing.JButton viewCollegeButton;
    private javax.swing.JTextField viewDeadlineTextField;
    private javax.swing.JTextField viewNotesField;
    private javax.swing.JButton viewPromptButton;
    private javax.swing.JTextField viewPromptField;
    private javax.swing.JList<String> viewPromptsList;
    private javax.swing.JTextField viewWordCountField;
    // End of variables declaration//GEN-END:variables
}
