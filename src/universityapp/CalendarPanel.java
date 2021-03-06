/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universityapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.toedter.calendar.*;
import java.awt.event.*;
import java.beans.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.ScrollPaneConstants.*;

/**
 *
 * @author satranga
 */
public class CalendarPanel extends javax.swing.JPanel {

    /**
     * Creates new form CalendarPanel
     */
    public ArrayList<Event> events = new ArrayList<Event>();//ArrayList to store event objects
    DefaultListModel dm = new DefaultListModel();
    DefaultListModel dm1 = new DefaultListModel();
    SimpleDateFormat ft = new SimpleDateFormat("MMM dd, yyyy");

    public CalendarPanel() throws FileNotFoundException {
        initComponents();
        //Ensures that eventList does not scroll horizontally as scroll bar did not disappear; Program provides several ways to view full event information
        eventScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);

        //Tests to see if calendar has a different day selected in its day chooser
        collegeCalendar.getDayChooser().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                resetEventList();
            }
        });
        
        loadEventsFrom("events.json");
        if (events.size() != 0) {
            resetEventList();
        }
        
        
    }

    public void setParent(MainFrame frame) {

    }

    public void resetEventList() {
        dm.clear();
        eventList.setModel(dm);
        Date test = collegeCalendar.getDate();
        for (int i = 0; i < events.size(); i++) {
            if (!test.equals(events.get(i).getEventDate())) {
            } else {
                dm.addElement(events.get(i).getName());
            }
        }
    }
    
    public void loadEventsFrom(String fileName) throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Event[] e;
        e = gson.fromJson(new FileReader(fileName), Event[].class);
        if (e.length != 0) {
            for (int i = 0; i < e.length; i++) {
                events.add(e[i]);
            }
        }

    }

    public void saveEventTo(String fileName) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(events, writer);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addEventDialog = new javax.swing.JDialog();
        eventSelectLabel = new javax.swing.JLabel();
        addEventButton1 = new javax.swing.JButton();
        eventDescriptionScroll = new javax.swing.JScrollPane();
        eventDescriptionArea1 = new javax.swing.JTextArea();
        addEventDateChooser = new com.toedter.calendar.JDateChooser();
        eventCollegeLabel = new javax.swing.JLabel();
        calendarLabel1 = new javax.swing.JLabel();
        selectEventComboBox = new javax.swing.JComboBox<>();
        selectCollegeComboBox = new javax.swing.JComboBox<>();
        eventDescriptionLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        viewEventDialog = new javax.swing.JDialog();
        calendarLabel3 = new javax.swing.JLabel();
        eventSelectLabel1 = new javax.swing.JLabel();
        eventDescriptionScroll1 = new javax.swing.JScrollPane();
        viewDescriptionField = new javax.swing.JTextArea();
        eventDescriptionLabel1 = new javax.swing.JLabel();
        eventCollegeLabel1 = new javax.swing.JLabel();
        viewNameField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        viewDateField = new javax.swing.JTextField();
        viewEventField = new javax.swing.JTextField();
        viewCollegeField = new javax.swing.JTextField();
        errorDialog = new javax.swing.JDialog();
        errorPane = new javax.swing.JOptionPane();
        calendarLabel = new javax.swing.JLabel();
        collegeCalendar = new com.toedter.calendar.JCalendar();
        eventScrollPane = new javax.swing.JScrollPane();
        eventList = new javax.swing.JList<>();
        removeEventButton = new javax.swing.JButton();
        addEventButton = new javax.swing.JButton();
        viewEventButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        calendarBackButton = new javax.swing.JButton();

        addEventDialog.setBackground(new java.awt.Color(102, 204, 255));
        addEventDialog.setIconImage(null);
        addEventDialog.setResizable(false);

        eventSelectLabel.setText("Event:");

        addEventButton1.setText("Add Event");
        addEventButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEventButton1ActionPerformed(evt);
            }
        });

        eventDescriptionArea1.setColumns(20);
        eventDescriptionArea1.setRows(5);
        eventDescriptionScroll.setViewportView(eventDescriptionArea1);

        eventCollegeLabel.setText("College:");

        calendarLabel1.setFont(new java.awt.Font("Times New Roman", 0, 48)); // NOI18N
        calendarLabel1.setText("ADD EVENT");

        selectEventComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Event", "Application", "College Visit", "Interview", "Financial Aid", "Information Session", "Scholarship" }));

        selectCollegeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select College", "No Attached College" }));

        eventDescriptionLabel.setText("Description:");

        jLabel4.setText("Name:");

        jLabel5.setText("Date:");

        javax.swing.GroupLayout addEventDialogLayout = new javax.swing.GroupLayout(addEventDialog.getContentPane());
        addEventDialog.getContentPane().setLayout(addEventDialogLayout);
        addEventDialogLayout.setHorizontalGroup(
            addEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addEventDialogLayout.createSequentialGroup()
                .addGroup(addEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addEventDialogLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(eventDescriptionLabel)
                        .addGap(301, 301, 301))
                    .addGroup(addEventDialogLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(addEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addEventDialogLayout.createSequentialGroup()
                                .addGroup(addEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(addEventDialogLayout.createSequentialGroup()
                                        .addGap(137, 137, 137)
                                        .addComponent(addEventButton1))
                                    .addComponent(eventDescriptionScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(addEventDialogLayout.createSequentialGroup()
                                        .addGap(57, 57, 57)
                                        .addComponent(calendarLabel1))
                                    .addGroup(addEventDialogLayout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(addEventDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(addEventDialogLayout.createSequentialGroup()
                                .addGroup(addEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(eventSelectLabel)
                                    .addComponent(eventCollegeLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(addEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(selectCollegeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(selectEventComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap())
        );
        addEventDialogLayout.setVerticalGroup(
            addEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addEventDialogLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(calendarLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addEventDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(addEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectEventComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eventSelectLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eventCollegeLabel)
                    .addComponent(selectCollegeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(eventDescriptionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(eventDescriptionScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addEventButton1)
                .addGap(18, 18, 18))
        );

        viewEventDialog.setBackground(new java.awt.Color(102, 204, 255));
        viewEventDialog.setResizable(false);

        calendarLabel3.setFont(new java.awt.Font("Times New Roman", 0, 48)); // NOI18N
        calendarLabel3.setText("VIEW EVENT");

        eventSelectLabel1.setText("Event:");

        viewDescriptionField.setEditable(false);
        viewDescriptionField.setColumns(20);
        viewDescriptionField.setRows(5);
        eventDescriptionScroll1.setViewportView(viewDescriptionField);

        eventDescriptionLabel1.setText("Description:");

        eventCollegeLabel1.setText("College:");

        viewNameField.setEditable(false);

        jLabel6.setText("Name:");

        jLabel7.setText("Date:");

        viewDateField.setEditable(false);

        viewEventField.setEditable(false);

        viewCollegeField.setEditable(false);

        javax.swing.GroupLayout viewEventDialogLayout = new javax.swing.GroupLayout(viewEventDialog.getContentPane());
        viewEventDialog.getContentPane().setLayout(viewEventDialogLayout);
        viewEventDialogLayout.setHorizontalGroup(
            viewEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewEventDialogLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(calendarLabel3)
                .addGap(42, 42, 42))
            .addGroup(viewEventDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(viewEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(viewEventDialogLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(viewNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(viewDateField))
                    .addGroup(viewEventDialogLayout.createSequentialGroup()
                        .addComponent(eventSelectLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(viewEventField))
                    .addGroup(viewEventDialogLayout.createSequentialGroup()
                        .addComponent(eventCollegeLabel1)
                        .addGap(5, 5, 5)
                        .addComponent(viewCollegeField))
                    .addGroup(viewEventDialogLayout.createSequentialGroup()
                        .addGroup(viewEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(eventDescriptionScroll1, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(eventDescriptionLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        viewEventDialogLayout.setVerticalGroup(
            viewEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewEventDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(calendarLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(viewEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(viewDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(viewEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eventSelectLabel1)
                    .addComponent(viewEventField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(viewEventDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eventCollegeLabel1)
                    .addComponent(viewCollegeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(eventDescriptionLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(eventDescriptionScroll1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        errorDialog.setBackground(new java.awt.Color(102, 204, 255));

        javax.swing.GroupLayout errorDialogLayout = new javax.swing.GroupLayout(errorDialog.getContentPane());
        errorDialog.getContentPane().setLayout(errorDialogLayout);
        errorDialogLayout.setHorizontalGroup(
            errorDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(errorPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        errorDialogLayout.setVerticalGroup(
            errorDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(errorPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setBackground(new java.awt.Color(102, 204, 255));

        calendarLabel.setFont(new java.awt.Font("Times New Roman", 0, 48)); // NOI18N
        calendarLabel.setText("MY CALENDAR");

        collegeCalendar.setWeekOfYearVisible(false);

        eventScrollPane.setPreferredSize(new java.awt.Dimension(275, 305));

        eventList.setPreferredSize(new java.awt.Dimension(275, 345));
        eventScrollPane.setViewportView(eventList);

        removeEventButton.setText("Remove Event");
        removeEventButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeEventButtonActionPerformed(evt);
            }
        });

        addEventButton.setText("Add Event");
        addEventButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEventButtonActionPerformed(evt);
            }
        });

        viewEventButton.setText("View Event");
        viewEventButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewEventButtonActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Events Scheduled");
        jLabel1.setOpaque(true);

        calendarBackButton.setText("Save and Return to Main Page");
        calendarBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calendarBackButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addEventButton, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeEventButton, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(viewEventButton, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(calendarBackButton)
                                .addGap(109, 109, 109)
                                .addComponent(calendarLabel))
                            .addComponent(collegeCalendar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(eventScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(calendarLabel)
                    .addComponent(calendarBackButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(removeEventButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addEventButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewEventButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(eventScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(collegeCalendar, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addEventButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEventButton1ActionPerformed
        //Establishes the values for the objects in question
        String name;
        String description;
        String type;
        String college;
        boolean errorHandler = true;

        //Tests to see if name field only has uppercase and lowercase letters
        if (nameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(errorPane, "Please enter a valid name for your event.");
            errorHandler = false;
        } else {
            int comparer;
            for (int i = 0; i < nameField.getText().length(); i++) {
                comparer = (int) nameField.getText().charAt(i);
                if (comparer >= 65 && comparer <= 90) {

                } else if (comparer == 32) {

                } else if (comparer >= 97 && comparer <= 122) {

                } else {
                    errorHandler = false;
                }
            }

            //Checks if each of the other, forced options have errors in input
            if (errorHandler) {
                name = nameField.getText();
                description = eventDescriptionArea1.getText();//Description is an optional area that can be filled with any character the user desires
                Date eventDate = addEventDateChooser.getDate();//Date is fixed to have a date value, therefore not requiring error handling
                if ("Select Event".equals(selectEventComboBox.getSelectedItem())) {
                    JOptionPane.showMessageDialog(errorPane, "Please select the type of event.");
                } else {
                    type = selectEventComboBox.getSelectedItem().toString();
                    if ("Select College".equals(selectCollegeComboBox.getSelectedItem())) {
                        JOptionPane.showMessageDialog(errorPane, "Please indicate which college this event is related to.");
                    } else {
                        college = selectCollegeComboBox.getSelectedItem().toString();
                        Event e = new Event(name, type, description, college, eventDate);
                        events.add(e);
                        JOptionPane.showMessageDialog(errorPane, "Your event has been added.");
                    }
                }
            } else if (errorHandler == false && !nameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(errorPane, "Please enter a valid name for your event.");
            }
        }

        //Resets fields in "Add Event" dialog
        nameField.setText("");
        selectEventComboBox.setSelectedItem("Select Event");
        selectCollegeComboBox.setSelectedItem("Select College");
        eventDescriptionArea1.setText("");
        addEventDialog.setVisible(false);

        resetEventList();
    }//GEN-LAST:event_addEventButton1ActionPerformed

    private void calendarBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calendarBackButtonActionPerformed
        ((MainFrame) this.getRootPane().getParent()).showPanel(0);
        try {
            saveEventTo("events.json");
        } catch (IOException ex) {
            Logger.getLogger(CalendarPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_calendarBackButtonActionPerformed

    private void addEventButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEventButtonActionPerformed
        //Opens the "Add Event" dialog
        addEventDialog.setVisible(true);
        addEventDialog.setBounds(300, 150, 400, 350);

        //Sets date to date selected and makes it uneditable except through JDateChooser's day chooser
        addEventDateChooser.setDate(collegeCalendar.getDate());
        ((JTextFieldDateEditor) addEventDateChooser.getDateEditor()).setEditable(false);
    }//GEN-LAST:event_addEventButtonActionPerformed

    private void removeEventButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeEventButtonActionPerformed
        //Accounts for both if an item is selected, if multiple items are selected, or if no items are selected
        if (eventList.isSelectionEmpty()) {
            JOptionPane.showMessageDialog(errorPane, "Please select event(s) to remove.");
        } else {
            String deletable; //Deletes one or multiple events directly from the JList
            int count = eventList.getSelectedIndices().length;
            for (int k = 0; k < count; k++) {
                deletable = eventList.getSelectedValue();
                for (int l = 0; l < events.size(); l++) {
                    if (deletable.equals(events.get(l).getName())) {
                        events.remove(events.get(l));
                    }
                }
                dm.remove(eventList.getSelectedIndex());

            }
            JOptionPane.showMessageDialog(errorPane, "Your event(s) have been removed.");
        }

    }//GEN-LAST:event_removeEventButtonActionPerformed

    private void viewEventButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewEventButtonActionPerformed
        //Opens the "View Event" dialog
        if (eventList.isSelectionEmpty()) {
            JOptionPane.showMessageDialog(errorPane, "Please select a single event to view.");//Returns an error for no selection
        } else if (eventList.getSelectedIndices().length > 1) {
            JOptionPane.showMessageDialog(errorPane, "Please select a single event to view.");//Returns an error for multiple selections
        } else {
            String viewedEventName = eventList.getSelectedValue();
            Event viewed = null;
            for (int p = 0; p < events.size(); p++) {
                if (viewedEventName.equals(events.get(p).getName())) {
                    viewed = events.get(p);
                }
            }

            //Utilizes object oriented programming accessors to help present Event fields
            viewNameField.setText(viewed.getName());
            viewDateField.setText(ft.format(viewed.getEventDate()));
            viewEventField.setText(viewed.getType());
            viewCollegeField.setText(viewed.getCollege());
            viewDescriptionField.setText(viewed.getDescription());
            viewEventDialog.setVisible(true);
            viewEventDialog.setBounds(300, 150, 400, 350);
        }

    }//GEN-LAST:event_viewEventButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addEventButton;
    private javax.swing.JButton addEventButton1;
    private com.toedter.calendar.JDateChooser addEventDateChooser;
    private javax.swing.JDialog addEventDialog;
    private javax.swing.JButton calendarBackButton;
    private javax.swing.JLabel calendarLabel;
    private javax.swing.JLabel calendarLabel1;
    private javax.swing.JLabel calendarLabel3;
    private com.toedter.calendar.JCalendar collegeCalendar;
    private javax.swing.JDialog errorDialog;
    private javax.swing.JOptionPane errorPane;
    private javax.swing.JLabel eventCollegeLabel;
    private javax.swing.JLabel eventCollegeLabel1;
    private javax.swing.JTextArea eventDescriptionArea1;
    private javax.swing.JLabel eventDescriptionLabel;
    private javax.swing.JLabel eventDescriptionLabel1;
    private javax.swing.JScrollPane eventDescriptionScroll;
    private javax.swing.JScrollPane eventDescriptionScroll1;
    private javax.swing.JList<String> eventList;
    private javax.swing.JScrollPane eventScrollPane;
    private javax.swing.JLabel eventSelectLabel;
    private javax.swing.JLabel eventSelectLabel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField nameField;
    private javax.swing.JButton removeEventButton;
    private javax.swing.JComboBox<String> selectCollegeComboBox;
    private javax.swing.JComboBox<String> selectEventComboBox;
    private javax.swing.JTextField viewCollegeField;
    private javax.swing.JTextField viewDateField;
    private javax.swing.JTextArea viewDescriptionField;
    private javax.swing.JButton viewEventButton;
    private javax.swing.JDialog viewEventDialog;
    private javax.swing.JTextField viewEventField;
    private javax.swing.JTextField viewNameField;
    // End of variables declaration//GEN-END:variables
}
