/*
 * Authors      Nsid    ID
 * Max Gooding, mag501, 11087688
 * Brady Smith, bas453, 11135973
 */
package personDatabase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

//Graphical User Interface. this class contains all graphical options.
public class PersonDatabaseGUI {

    Controller controller;
    private Component pnlMain;

    //constructor
    public PersonDatabaseGUI(Controller c) {
        controller = c;
        mainMenu();
    }

    /* method: mainMenu
     * Pre: none
     * Post: the main menu GUI is opened. from here you can choose to signup a new user
     * search for a user in database, or choose to display all users
     */
    public void mainMenu() {

        //JFrame
        final JFrame mainMenuFrame = new JFrame();
        //JPanel
        JPanel pnlMain = new JPanel();
        //Buttons
        JButton btnSignUp = new JButton("Sign Up");
        JButton btnSearch = new JButton("Search");
        JButton btnSearchAll = new JButton("Display All Users");

        //JButton btnAdvSearch = new JButton("Advanced Search");
        //Labels
        JLabel lblSignUp = new JLabel("New User?");
        JLabel lblSearch = new JLabel("Search For a User");
        //text fields
        final JTextField txtfSearch = new JTextField();

        //separators
        JSeparator sptrMainOne = new JSeparator();

        //JButton bounds
        btnSignUp.setBounds(300, 30, 220, 30);
        btnSearch.setBounds(300, 200, 220, 30);
        btnSearchAll.setBounds(300, 240, 220, 30);

        //JPanel bounds
        pnlMain.setBounds(1, 1, 600, 600);

        pnlMain.setLayout(null);
        //JLabel bounds
        lblSignUp.setBounds(20, 30, 220, 30);
        lblSearch.setBounds(20, 130, 220, 30);

        //JTextField bounds
        txtfSearch.setBounds(20, 200, 220, 30);

        //JSeparator bounds
        sptrMainOne.setBounds(0, 90, 600, 1);

        //Adding to JFrame
        pnlMain.add(btnSignUp);
        pnlMain.add(btnSearch);
        pnlMain.add(btnSearchAll);
        pnlMain.add(lblSignUp);
        pnlMain.add(lblSearch);
        pnlMain.add(txtfSearch);

        //pnlMain.add(btnAdvSearch);
        pnlMain.add(sptrMainOne);
        mainMenuFrame.add(pnlMain);

        // JFrame properties
        mainMenuFrame.setSize(600, 400);
        mainMenuFrame.setTitle("Main Menu");
        mainMenuFrame.setLocationRelativeTo(null);
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuFrame.setVisible(true);

        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                UserEntry user = new UserEntry();
                user.setDataNum(controller.file.getNextNum());
                try {
                    editMenu(user);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                LinkedList searchResults = new LinkedList();
                try {
                    if (txtfSearch.getText().compareTo("") == 0) {
                        showError("Please enter a name to search for");
                    } else {
                        controller.searchDataList((String) txtfSearch.getText(), searchResults);
                        searchList(searchResults);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnSearchAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                LinkedList searchResults = new LinkedList();
                try {
                    controller.getAllUsers(searchResults);
                    searchList(searchResults);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //mainMenuFrame.dispose();
            }
        });
    }

    /* method: searchList
     * Pre: a linkedList
     * Post: the search List GUI is opened. and the linkedList is displays all
     * of the UserEntrys that are in the LinkedList and you can choose one fo them to lookup
     * or go back to the mainMenu without doing anything else
     *       
     * Note: Search List does not use null layout, rather it uses the default layout, be careful when modifying this section
     * It needs to be done this way as scroll bars do not function well in null layout
     */
    public void searchList(final LinkedList resultsList) {
        final JFrame searchFrame = new JFrame();

        JPanel pnlMain = new JPanel();
        JButton btnBack = new JButton("Back To Main Menu");
        final JButton btnGo = new JButton("Look Up");

        //Table
        final JTable tblSearchResults = new JTable();
        @SuppressWarnings("serial")
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Name"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblSearchResults.setModel(model);
        LinkedList displayList = new LinkedList();
        displayList = resultsList;
        while (displayList.isEmpty() != true) {
            model.addRow(new Object[]{displayList.user.getName()});
            displayList = displayList.nextList;
        }

        ListSelectionModel listMod = tblSearchResults.getSelectionModel();
        listMod.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        searchFrame.add(new JScrollPane(tblSearchResults));
        JScrollPane scroll = new JScrollPane(tblSearchResults);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setViewportView(tblSearchResults);
        searchFrame.add(scroll);
        tblSearchResults.setVisible(true);

        //Labels
        JLabel lblExample = new JLabel("The names below match your search, select a name then click the look up button to edit them.");

        //Adding to JFrame
        pnlMain.add(lblExample);
        pnlMain.add(new JScrollPane(tblSearchResults), BorderLayout.CENTER);
        pnlMain.add(btnBack, BorderLayout.LINE_END);
        pnlMain.add(btnGo, BorderLayout.LINE_END);

        searchFrame.add(pnlMain);

        // JFrame properties
        searchFrame.setSize(600, 600);
        searchFrame.setTitle("Search Results");
        searchFrame.setLocationRelativeTo(null);
        searchFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        searchFrame.setVisible(true);

        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                searchFrame.dispose();
            }
        });

        btnGo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // Add user to the list of people who cannot be edited
                try {
                    if (tblSearchResults.getRowCount() == 0) {
                        btnGo.setEnabled(false);
                    } else {
                        controller.tryAgainstLock(controller.extractUserFromList(tblSearchResults.getSelectedRow(), resultsList));
                        searchFrame.dispose();
                    }
                } catch (ParseException e) {
                }
            }
        });
    }

    /* method: editMenu
     * Pre: a UserEntry for editting
     * Post: the edit Menu GUI is opened. and all of the userEntrys information
     * fields are displayed. they are all editbale. and from here you can choose
     * to save changes, delete the user, or do nothing and go back to mainMenu
     */
    public void editMenu(final UserEntry user) throws ParseException {
        LettersOnlyDocumentFilter lettersOnlyFilter = new LettersOnlyDocumentFilter();
        NumbersOnlyDocumentFilter numbersOnlyFilter = new NumbersOnlyDocumentFilter();
        SimpleDocumentFilter simpleFilter = new SimpleDocumentFilter();

        final JFrame editFrame = new JFrame();
        final JPanel pnlMain = new JPanel();
        final JLabel lblFirstName = new JLabel("First Name");
        final JLabel lblLastName = new JLabel("Last Name");
        final JLabel lblMidInitial = new JLabel("Middle Initial");
        final JLabel lblAddress = new JLabel("Street Address");
        final JLabel lblCity = new JLabel("City");
        final JLabel lblCountry = new JLabel("Country");
        final JLabel lblZip = new JLabel("Zip/Postal Code");
        final JLabel lblHomePhone = new JLabel("Home Phone Number");
        final JLabel lblWorkPhone = new JLabel("Work Phone Number");
        final JLabel lblEmail = new JLabel("Email Address");
        final JLabel lblYearReg = new JLabel("Year Registered");
        final JLabel lblGPA = new JLabel("Current GPA");
        final JLabel lblTotalCred = new JLabel("Total Credits");
        final JLabel lblThesisSupervisor = new JLabel("Thesis Supervisor");
        final JLabel lblThesisArea = new JLabel("Thesis Area - Give a brief description:");
        final JLabel lblThesisTitle = new JLabel("Thesis Title");
        final JLabel lblScholarshipAmount = new JLabel("Scholarship Amount");
        final JLabel lblDataNum = new JLabel("Database Number:");

        //JButton
        final JButton btnSave = new JButton("Save Changes");
        final JButton btnBack = new JButton("Back");
        final JButton btnDelete = new JButton("Delete");

        //text fields
        final JTextField txtfFirstName = new JTextField(user.getFirstName());
        ((AbstractDocument) txtfFirstName.getDocument()).setDocumentFilter(lettersOnlyFilter);
        final JTextField txtfLastName = new JTextField(user.getLastName());
        ((AbstractDocument) txtfLastName.getDocument()).setDocumentFilter(lettersOnlyFilter);

        // Create a textfield that only accepts letters and only accepts one length strings
        final JTextField txtfMidName = new JTextField(1);
        txtfMidName.setText(user.getMidName());
        ((AbstractDocument) txtfMidName.getDocument()).setDocumentFilter(lettersOnlyFilter);
        txtfMidName.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                JTextField txtfMidName = (JTextField) input;
                if (txtfMidName.getText().length() > 1) {
                    txtfMidName.setText(txtfMidName.getText().substring(0, 1));
                }
                return true;
            }
        });

        final JTextField txtfAddress = new JTextField(user.getAddress());
        final JTextField txtfCity = new JTextField(user.getCity());
        final JTextField txtfCountry = new JTextField(user.getCountry());
        ((AbstractDocument) txtfCountry.getDocument()).setDocumentFilter(lettersOnlyFilter);
        final JTextField txtfZip = new JTextField(user.getZip());
        txtfZip.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                JTextField txtfZip = (JTextField) input;
                txtfZip.setText(txtfZip.getText().toUpperCase());
                return true;
            }
        });

        final JTextField txtfHomePhone = new JTextField();
        ((AbstractDocument) txtfHomePhone.getDocument()).setDocumentFilter(numbersOnlyFilter);
        txtfHomePhone.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                JTextField txtfHomePhone = (JTextField) input;
                if (txtfHomePhone.getText().length() < 10) {
                    txtfHomePhone.setText("----------");
                } else if (txtfHomePhone.getText().contains("-")) {
                    txtfHomePhone.setText(txtfHomePhone.getText());
                } else {
                    txtfHomePhone.setText(txtfHomePhone.getText().substring(0, 3) + "-" + txtfHomePhone.getText().substring(3, 6) + "-" + txtfHomePhone.getText().substring(6, 10));
                }
                return true;
            }
        });
        txtfHomePhone.setText(user.getPhoneHome());

        final JTextField txtfWorkPhone = new JTextField();
        ((AbstractDocument) txtfWorkPhone.getDocument()).setDocumentFilter(numbersOnlyFilter);
        txtfWorkPhone.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                JTextField txtfWorkPhone = (JTextField) input;
                if (txtfWorkPhone.getText().length() < 10) {
                    txtfWorkPhone.setText("----------");
                } else if (txtfWorkPhone.getText().contains("-")) {
                    txtfWorkPhone.setText(txtfHomePhone.getText());
                } else {
                    txtfWorkPhone.setText(txtfWorkPhone.getText().substring(0, 3) + "-" + txtfWorkPhone.getText().substring(3, 6) + "-" + txtfWorkPhone.getText().substring(6, 10));
                }
                return true;
            }
        });
        txtfHomePhone.setText(user.getPhoneHome());
        txtfWorkPhone.setText(user.getPhoneWork());
        final JTextField txtfEmail = new JTextField(user.getEmail());
        txtfEmail.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                JTextField txtfEmail = (JTextField) input;
                if (!(txtfEmail.getText().contains("@") && txtfEmail.getText().contains("."))) {
                    txtfEmail.setText("");
                }
                return true;
            }
        });
        final JTextField txtfYearReg = new JTextField(user.getYearReg());
        ((AbstractDocument) txtfYearReg.getDocument()).setDocumentFilter(numbersOnlyFilter);
        final JTextField txtfGPA = new JTextField(user.getGPA());
        ((AbstractDocument) txtfGPA.getDocument()).setDocumentFilter(numbersOnlyFilter);
        final JTextField txtfTotalCreds = new JTextField(user.getTotalCredit());
        ((AbstractDocument) txtfTotalCreds.getDocument()).setDocumentFilter(numbersOnlyFilter);
        final JTextField txtfThesisSupervisor = new JTextField(user.getThesisSupervisor());
        final JTextField txtfScolarshipAmount = new JTextField(user.getScholarshipAmount());
        ((AbstractDocument) txtfScolarshipAmount.getDocument()).setDocumentFilter(numbersOnlyFilter);
        final JTextField txtfDataNum = new JTextField("" + user.getDataNum());
        final JTextField txtfThesisTitle = new JTextField(user.getThesisTitle());
        final JTextArea txtaThesisArea = new JTextArea(user.getThesisArea());

        // Thesis Area - options
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        txtaThesisArea.setLineWrap(true);
        txtaThesisArea.setWrapStyleWord(true);
        txtaThesisArea.setBorder(border);
        ((AbstractDocument) txtaThesisArea.getDocument()).setDocumentFilter(simpleFilter);

        //separators
        final JSeparator sptrSignUpOne = new JSeparator();
        final JSeparator sptrSignUpTwo = new JSeparator();

        //Combo boxes
        String[] strPersonTypeArr = {"Which User Type?", "Person", "Undergrad Student", "Grad Student"};
        final JComboBox<?> cmbPersonType = new JComboBox<Object>(strPersonTypeArr);
        String[] strDegreeProg = {"Which Degree Program?", "Arts & Science", "Medicine", "Vet Med", "Engineering",
            "Education", "Business", "Law"};
        final JComboBox<?> cmbDegreeProg = new JComboBox<Object>(strDegreeProg);
        String[] strYear = {"What Year Are You In?", "Year 1", "Year 2", "Year 3", "Year 4", "Year 5"};
        final JComboBox<?> cmbYear = new JComboBox<Object>(strYear);
        String[] strDegreeType = {"Degree Type", "Masters", "PhD", "MBA", "LawDoc", "MedDoc", "VetMedDoc"};
        final JComboBox<?> cmbDegreeType = new JComboBox<Object>(strDegreeType);

        //Sets all the bounds of all the componenets and adds them to the main panel
        setBoundsAndAdd(pnlMain, lblFirstName, lblLastName, lblMidInitial, lblAddress, lblCity, lblCountry, lblZip, lblHomePhone, lblWorkPhone,
                lblEmail, lblYearReg, lblGPA, lblTotalCred, lblThesisSupervisor, lblScholarshipAmount, lblThesisArea, lblThesisTitle,
                lblDataNum, btnSave, btnDelete, btnBack, txtfFirstName, txtfLastName, txtfMidName, txtfAddress, txtfCity, txtfCountry, txtfZip, txtfHomePhone,
                txtfWorkPhone, txtfEmail, txtfYearReg, txtfGPA, txtfTotalCreds, txtfThesisSupervisor,
                txtfScolarshipAmount, txtfThesisTitle, txtaThesisArea, txtfDataNum, sptrSignUpOne, sptrSignUpTwo, cmbPersonType, cmbDegreeProg,
                cmbYear, cmbDegreeType, editFrame);


        // Sets the default options based upon which person type is being brought in
        if (user.getUserType().contains("Person")) {
            cmbPersonType.setSelectedIndex(1);
            (txtfFirstName).setEnabled(true);
            (txtfLastName).setEnabled(true);
            (txtfMidName).setEnabled(true);
            (txtfAddress).setEnabled(true);
            (txtfCity).setEnabled(true);
            (txtfCountry).setEnabled(true);
            (txtfZip).setEnabled(true);
            (txtfHomePhone).setEnabled(true);
            (txtfWorkPhone).setEnabled(true);
            (txtfEmail).setEnabled(true);
            (txtfYearReg).setEnabled(false);
            (txtfGPA).setEnabled(false);
            (txtfTotalCreds).setEnabled(false);
            (txtfScolarshipAmount).setEnabled(false);
            (txtfThesisSupervisor).setEnabled(false);
            //Adding text areas
            (txtfThesisTitle).setEnabled(false);
            (txtaThesisArea).setEnabled(false);
            //Adding ComboBoxes                                 
            (cmbDegreeProg).setEnabled(false);
            (cmbYear).setEnabled(false);
            (cmbDegreeType).setEnabled(false);
        }
        if (user.getUserType().compareTo("Undergrad Student") == 0) {
            cmbPersonType.setSelectedIndex(2);
            (txtfFirstName).setEnabled(true);
            (txtfLastName).setEnabled(true);
            (txtfMidName).setEnabled(true);
            (txtfAddress).setEnabled(true);
            (txtfCity).setEnabled(true);
            (txtfCountry).setEnabled(true);
            (txtfZip).setEnabled(true);
            (txtfHomePhone).setEnabled(true);
            (txtfWorkPhone).setEnabled(true);
            (txtfEmail).setEnabled(true);
            (txtfYearReg).setEnabled(true);
            (txtfGPA).setEnabled(true);
            (txtfTotalCreds).setEnabled(true);
            (txtfScolarshipAmount).setEnabled(false);
            (txtfThesisSupervisor).setEnabled(false);
            //Adding text areas
            (txtfThesisTitle).setEnabled(false);
            (txtaThesisArea).setEnabled(false);
            //Adding ComboBoxes                                 
            (cmbDegreeProg).setEnabled(true);
            (cmbYear).setEnabled(true);
            (cmbDegreeType).setEnabled(false);
        }
        if (user.getUserType().compareTo("Grad Student") == 0) {
            cmbPersonType.setSelectedIndex(3);
            (txtfFirstName).setEnabled(true);
            (txtfLastName).setEnabled(true);
            (txtfMidName).setEnabled(true);
            (txtfAddress).setEnabled(true);
            (txtfCity).setEnabled(true);
            (txtfCountry).setEnabled(true);
            (txtfZip).setEnabled(true);
            (txtfHomePhone).setEnabled(true);
            (txtfWorkPhone).setEnabled(true);
            (txtfEmail).setEnabled(true);
            (txtfYearReg).setEnabled(true);
            (txtfGPA).setEnabled(true);
            (txtfTotalCreds).setEnabled(true);
            (txtfScolarshipAmount).setEnabled(true);
            (txtfThesisSupervisor).setEnabled(true);
            //Adding text areas
            (txtfThesisTitle).setEnabled(true);
            (txtaThesisArea).setEnabled(true);
            //Adding ComboBoxes                                 
            (cmbDegreeProg).setEnabled(true);
            (cmbYear).setEnabled(true);
            (cmbDegreeType).setEnabled(true);
        }

        if (user.getDegreeProgram().compareTo("Arts & Science") == 0) {
            cmbDegreeProg.setSelectedIndex(1);
        }
        if (user.getDegreeProgram().compareTo("Medicine") == 0) {
            cmbDegreeProg.setSelectedIndex(2);
        }
        if (user.getDegreeProgram().compareTo("Vet Med") == 0) {
            cmbDegreeProg.setSelectedIndex(3);
        }
        if (user.getDegreeProgram().compareTo("Engineering") == 0) {
            cmbDegreeProg.setSelectedIndex(4);
        }
        if (user.getDegreeProgram().compareTo("Education") == 0) {
            cmbDegreeProg.setSelectedIndex(5);
        }
        if (user.getDegreeProgram().compareTo("Buisness") == 0) {
            cmbDegreeProg.setSelectedIndex(6);
        }
        if (user.getDegreeProgram().compareTo("Law") == 0) {
            cmbDegreeProg.setSelectedIndex(7);
        }

        if (user.getYearInProgram().compareTo("Year 1") == 0) {
            cmbYear.setSelectedIndex(1);
        }
        if (user.getYearInProgram().compareTo("Year 2") == 0) {
            cmbYear.setSelectedIndex(2);
        }
        if (user.getYearInProgram().compareTo("Year 3") == 0) {
            cmbYear.setSelectedIndex(3);
        }
        if (user.getYearInProgram().compareTo("Year 4") == 0) {
            cmbYear.setSelectedIndex(4);
        }
        if (user.getYearInProgram().compareTo("Year 5") == 0) {
            cmbYear.setSelectedIndex(5);
        }

        if (user.getDegreeType().compareTo("Masters") == 0) {
            cmbDegreeType.setSelectedIndex(1);
        }
        if (user.getDegreeType().compareTo("PhD") == 0) {
            cmbDegreeType.setSelectedIndex(2);
        }
        if (user.getDegreeType().compareTo("MBA") == 0) {
            cmbDegreeType.setSelectedIndex(3);
        }
        if (user.getDegreeType().compareTo("LawDoc") == 0) {
            cmbDegreeType.setSelectedIndex(4);
        }
        if (user.getDegreeType().compareTo("MedDoc") == 0) {
            cmbDegreeType.setSelectedIndex(5);
        }
        if (user.getDegreeType().compareTo("VetMedDoc") == 0) {
            cmbDegreeType.setSelectedIndex(6);
        }

        cmbPersonType.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                setVisable();
            }

            private void setVisable() {
                //System.out.println(user.getUserType());

                if (((String) cmbPersonType.getSelectedItem()).compareTo("Which User Type?") == 0) {
                    // Visable Labels
                    pnlMain.add(lblFirstName).setVisible(true);
                    pnlMain.add(lblLastName).setVisible(true);
                    pnlMain.add(lblMidInitial).setVisible(true);
                    pnlMain.add(lblAddress).setVisible(true);
                    pnlMain.add(lblHomePhone).setVisible(true);
                    pnlMain.add(lblWorkPhone).setVisible(true);
                    pnlMain.add(lblEmail).setVisible(true);
                    pnlMain.add(lblYearReg).setVisible(true);
                    pnlMain.add(lblGPA).setVisible(true);
                    pnlMain.add(lblTotalCred).setVisible(true);
                    pnlMain.add(lblThesisSupervisor).setVisible(true);
                    pnlMain.add(lblScholarshipAmount).setVisible(true);
                    pnlMain.add(lblThesisTitle).setVisible(true);
                    pnlMain.add(lblThesisArea).setVisible(true);

                    //Visable Buttons
                    pnlMain.add(btnSave);
                    pnlMain.add(btnBack);
                    pnlMain.add(btnDelete);

                    //Visable text fields
                    pnlMain.add(txtfFirstName).setEnabled(false);
                    pnlMain.add(txtfLastName).setEnabled(false);
                    pnlMain.add(txtfMidName).setEnabled(false);
                    pnlMain.add(txtfAddress).setEnabled(false);
                    pnlMain.add(txtfCity).setEnabled(false);
                    pnlMain.add(txtfCountry).setEnabled(false);
                    pnlMain.add(txtfZip).setEnabled(false);
                    pnlMain.add(txtfHomePhone).setEnabled(false);
                    pnlMain.add(txtfWorkPhone).setEnabled(false);
                    pnlMain.add(txtfEmail).setEnabled(false);
                    pnlMain.add(txtfYearReg).setEnabled(false);
                    pnlMain.add(txtfGPA).setEnabled(false);
                    pnlMain.add(txtfTotalCreds).setEnabled(false);
                    pnlMain.add(txtfScolarshipAmount).setEnabled(false);
                    pnlMain.add(txtfThesisSupervisor).setEnabled(false);

                    //Visable thesis areas
                    pnlMain.add(txtfThesisTitle).setEnabled(false);
                    pnlMain.add(txtaThesisArea).setEnabled(false);

                    //Visable separators
                    pnlMain.add(sptrSignUpTwo).setVisible(true);
                    pnlMain.add(sptrSignUpOne).setVisible(true);

                    //Visable ComboBoxes
                    pnlMain.add(cmbPersonType);
                    pnlMain.add(cmbDegreeProg).setEnabled(false);
                    pnlMain.add(cmbYear).setEnabled(false);
                    pnlMain.add(cmbDegreeType).setEnabled(false);
                }
                if (((String) cmbPersonType.getSelectedItem()).compareTo("Person") == 0) {
                    //Visable Text Fields
                    (txtfFirstName).setEnabled(true);
                    (txtfLastName).setEnabled(true);
                    (txtfMidName).setEnabled(true);
                    (txtfAddress).setEnabled(true);
                    (txtfCity).setEnabled(true);
                    (txtfCountry).setEnabled(true);
                    (txtfZip).setEnabled(true);
                    (txtfHomePhone).setEnabled(true);
                    (txtfWorkPhone).setEnabled(true);
                    (txtfEmail).setEnabled(true);
                    (txtfYearReg).setEnabled(false);
                    (txtfGPA).setEnabled(false);
                    (txtfTotalCreds).setEnabled(false);
                    (txtfScolarshipAmount).setEnabled(false);
                    (txtfThesisSupervisor).setEnabled(false);
                    //Adding text areas
                    (txtfThesisTitle).setEnabled(false);
                    (txtaThesisArea).setEnabled(false);
                    //Adding ComboBoxes                                 
                    (cmbDegreeProg).setEnabled(false);
                    (cmbYear).setEnabled(false);
                    (cmbDegreeType).setEnabled(false);
                }
                if (((String) cmbPersonType.getSelectedItem()).compareTo("Undergrad Student") == 0) {
                    //Visable Text Fields
                    (txtfFirstName).setEnabled(true);
                    (txtfLastName).setEnabled(true);
                    (txtfMidName).setEnabled(true);
                    (txtfAddress).setEnabled(true);
                    (txtfCity).setEnabled(true);
                    (txtfCountry).setEnabled(true);
                    (txtfZip).setEnabled(true);
                    (txtfHomePhone).setEnabled(true);
                    (txtfWorkPhone).setEnabled(true);
                    (txtfEmail).setEnabled(true);
                    (txtfYearReg).setEnabled(true);
                    (txtfGPA).setEnabled(true);
                    (txtfTotalCreds).setEnabled(true);
                    (txtfScolarshipAmount).setEnabled(false);
                    (txtfThesisSupervisor).setEnabled(false);
                    //Adding text areas
                    (txtfThesisTitle).setEnabled(false);
                    (txtaThesisArea).setEnabled(false);
                    //Adding ComboBoxes                                 
                    (cmbDegreeProg).setEnabled(true);
                    (cmbYear).setEnabled(true);
                    (cmbDegreeType).setEnabled(false);
                }
                if (((String) cmbPersonType.getSelectedItem()).compareTo("Grad Student") == 0) {
                    //Visable Text Fields
                    (txtfFirstName).setEnabled(true);
                    (txtfLastName).setEnabled(true);
                    (txtfMidName).setEnabled(true);
                    (txtfAddress).setEnabled(true);
                    (txtfCity).setEnabled(true);
                    (txtfCountry).setEnabled(true);
                    (txtfZip).setEnabled(true);
                    (txtfHomePhone).setEnabled(true);
                    (txtfWorkPhone).setEnabled(true);
                    (txtfEmail).setEnabled(true);
                    (txtfYearReg).setEnabled(true);
                    (txtfGPA).setEnabled(true);
                    (txtfTotalCreds).setEnabled(true);
                    (txtfScolarshipAmount).setEnabled(true);
                    (txtfThesisSupervisor).setEnabled(true);
                    //Adding text areas
                    (txtfThesisTitle).setEnabled(true);
                    (txtaThesisArea).setEnabled(true);
                    //Adding ComboBoxes                                 
                    (cmbDegreeProg).setEnabled(true);
                    (cmbYear).setEnabled(true);
                    (cmbDegreeType).setEnabled(true);
                }
            }
        });

        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String name = (String) txtfFirstName.getText() + " " + (String) txtfMidName.getText() + " " + (String) txtfLastName.getText();
                String address = (String) txtfAddress.getText() + "%" + (String) txtfCity.getText() + "%" + (String) txtfCountry.getText() + "%" + (String) txtfZip.getText();
                String workPhone = (String) txtfWorkPhone.getText();
                String homePhone = (String) txtfHomePhone.getText();
                String email = (String) txtfEmail.getText();
                String yearReg = (String) txtfYearReg.getText();
                String degProg = (String) cmbDegreeProg.getSelectedItem();
                String yearInProg = (String) cmbYear.getSelectedItem();
                String GPA = (String) txtfGPA.getText();
                String totalCredit = (String) txtfTotalCreds.getText();
                String thesisTitle = txtfThesisTitle.getText();
                String thesisArea = (String) txtaThesisArea.getText();
                String thesisSupervisor = txtfThesisSupervisor.getText();
                String scholarshipAmount = (String) txtfScolarshipAmount.getText();
                String degreeType = (String) cmbDegreeType.getSelectedItem();
                String userType = (String) cmbPersonType.getSelectedItem();
                String dataNum = (String) txtfDataNum.getText();

                if (txtfAddress.getText().compareTo("") == 0 || txtfCity.getText().compareTo("") == 0 || txtfCountry.getText().compareTo("") == 0 || txtfZip.getText().compareTo("") == 0) {
                    showError("Please finish filling out address.");
                } else if (txtfFirstName.getText().compareTo("") == 0 || txtfLastName.getText().compareTo("") == 0 || txtfMidName.getText().compareTo("") == 0) {
                    showError("Please finish filling out all three name forms.");
                } else {
                    controller.saveUserInfo(userType, name, address, workPhone, homePhone, email, yearReg, degProg,
                            yearInProg, GPA, totalCredit, thesisTitle, thesisArea, thesisSupervisor, scholarshipAmount, degreeType, dataNum);
                    editFrame.dispose();
                    controller.removeLock(user.getDataNum());
                }
                //Save the information entered by the user
            }
        });

        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                controller.removeLock(user.getDataNum());
                editFrame.dispose();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the user?", "Request",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
                        == JOptionPane.YES_OPTION) {
                    try {
                        controller.deleteUser(user);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    controller.removeLock(user.getDataNum());
                    editFrame.dispose();
                } else {
                    //Go back to normal
                }

            }
        });
    }

    /* Method: setBoundsAndAdd
     * Pre: every object that is in the GUI at any point
     * Post: will not actually display anything. but all objects are sized and added
     * to the appropiate GUI here.
     */
    private void setBoundsAndAdd(final JPanel pnlMain, final JLabel lblFirstName, final JLabel lblLastName,
            final JLabel lblMidInitial, final JLabel lblAddress, final JLabel lblCity, final JLabel lblCountry, final JLabel lblZip,
            final JLabel lblHomePhone, final JLabel lblWorkPhone,
            final JLabel lblEmail, final JLabel lblYearReg, final JLabel lblGPA, final JLabel lblTotalCred,
            final JLabel lblThesisSupervisor, final JLabel lblScholarshipAmount, final JLabel lblThesisArea, final JLabel lblThesisTitle,
            final JLabel lblDataNum, final JButton btnSave, final JButton btnDelete, final JButton btnBack, final JTextField txtfFirstName,
            final JTextField txtfLastName, final JTextField txtfMidName, final JTextField txtfAddress, final JTextField txtfCity, final JTextField txtfCountry, final JTextField txtfZip,
            final JTextField txtfHomePhone, final JTextField txtfWorkPhone, final JTextField txtfEmail,
            final JTextField txtfYearReg, final JTextField txtfGPA, final JTextField txtfTotalCreds,
            final JTextField txtfThesisSupervisor, final JTextField txtfScolarshipAmount, final JTextField txtfThesisTitle,
            final JTextArea txtfThesisArea, final JTextField txtfDataNum,
            final JSeparator sptrSignUpOne, final JSeparator sptrSignUpTwo, final JComboBox<?> cmbPersonType,
            final JComboBox<?> cmbDegreeProg, final JComboBox<?> cmbYear, final JComboBox<?> cmbDegreeType,
            final JFrame editFrame) {
        //JPanel bounds
        pnlMain.setBounds(1, 1, 600, 600);
        pnlMain.setLayout(null);
        //JLabel bounds
        lblFirstName.setBounds(10, 55, 220, 30);
        lblLastName.setBounds(330, 55, 220, 30);
        lblMidInitial.setBounds(220, 55, 100, 30);
        lblAddress.setBounds(10, 105, 220, 30);
        lblCity.setBounds(10, 155, 220, 30);
        lblCountry.setBounds(10, 205, 220, 30);
        lblZip.setBounds(220, 205, 220, 30);
        lblHomePhone.setBounds(330, 105, 220, 30);
        lblWorkPhone.setBounds(330, 155, 220, 30);
        lblEmail.setBounds(330, 205, 220, 30);
        lblYearReg.setBounds(10, 270, 220, 30);
        lblGPA.setBounds(10, 325, 220, 30);
        lblTotalCred.setBounds(120, 325, 220, 30);
        lblThesisSupervisor.setBounds(10, 385, 220, 30);
        lblScholarshipAmount.setBounds(240, 385, 220, 30);
        lblThesisTitle.setBounds(10, 445, 220, 30);
        lblThesisArea.setBounds(10, 505, 220, 30);
        lblDataNum.setBounds(350, 15, 220, 30);

        //JButton bounds
        btnSave.setBounds(10, 700, 140, 50);
        btnDelete.setBounds(160, 700, 140, 50);
        btnBack.setBounds(310, 700, 140, 50);
        //JTextField bounds
        txtfFirstName.setBounds(10, 80, 220, 30);
        txtfLastName.setBounds(330, 80, 220, 30);
        txtfMidName.setBounds(240, 80, 20, 30);
        txtfAddress.setBounds(10, 130, 220, 30);
        txtfCity.setBounds(10, 180, 220, 30);
        txtfCountry.setBounds(10, 230, 200, 30);
        txtfZip.setBounds(220, 230, 80, 30);
        txtfHomePhone.setBounds(330, 130, 220, 30);
        txtfWorkPhone.setBounds(330, 180, 220, 30);
        txtfEmail.setBounds(330, 230, 220, 30);
        txtfYearReg.setBounds(10, 295, 100, 30);
        txtfGPA.setBounds(10, 350, 100, 30);
        txtfTotalCreds.setBounds(120, 350, 100, 30);
        txtfThesisSupervisor.setBounds(10, 410, 220, 30);
        txtfScolarshipAmount.setBounds(240, 410, 220, 30);
        txtfThesisTitle.setBounds(10, 470, 220, 30);
        txtfThesisArea.setBounds(10, 535, 565, 150);
        txtfDataNum.setBounds(490, 20, 60, 30);
        //JSeparator bounds
        sptrSignUpOne.setBounds(0, 270, 600, 1);
        sptrSignUpTwo.setBounds(0, 385, 600, 1);
        //JComboBox bounds
        cmbPersonType.setBounds(10, 20, 220, 30);
        cmbDegreeProg.setBounds(120, 295, 220, 30);
        cmbYear.setBounds(350, 295, 160, 30);
        cmbDegreeType.setBounds(240, 470, 220, 30);

        //Adding to JFrame
        // Visible Labels
        pnlMain.add(lblFirstName).setVisible(true);
        pnlMain.add(lblLastName).setVisible(true);
        pnlMain.add(lblMidInitial).setVisible(true);
        pnlMain.add(lblAddress).setVisible(true);
        pnlMain.add(lblCity).setVisible(true);
        pnlMain.add(lblCountry).setVisible(true);
        pnlMain.add(lblZip).setVisible(true);
        pnlMain.add(lblHomePhone).setVisible(true);
        pnlMain.add(lblWorkPhone).setVisible(true);
        pnlMain.add(lblEmail).setVisible(true);
        pnlMain.add(lblYearReg).setVisible(true);
        pnlMain.add(lblGPA).setVisible(true);
        pnlMain.add(lblTotalCred).setVisible(true);
        pnlMain.add(lblThesisSupervisor).setVisible(true);
        pnlMain.add(lblScholarshipAmount).setVisible(true);
        pnlMain.add(lblThesisTitle).setVisible(true);
        pnlMain.add(lblThesisArea).setVisible(true);
        pnlMain.add(lblDataNum).setVisible(true);

        //Visible Buttons
        pnlMain.add(btnSave);
        pnlMain.add(btnBack);
        pnlMain.add(btnDelete);
        //Visible text fields
        pnlMain.add(txtfFirstName).setEnabled(false);
        pnlMain.add(txtfLastName).setEnabled(false);
        pnlMain.add(txtfMidName).setEnabled(false);
        pnlMain.add(txtfAddress).setEnabled(false);
        pnlMain.add(txtfCity).setEnabled(false);
        pnlMain.add(txtfCountry).setEnabled(false);
        pnlMain.add(txtfZip).setEnabled(false);
        pnlMain.add(txtfHomePhone).setEnabled(false);
        pnlMain.add(txtfWorkPhone).setEnabled(false);
        pnlMain.add(txtfEmail).setEnabled(false);
        pnlMain.add(txtfYearReg).setEnabled(false);
        pnlMain.add(txtfGPA).setEnabled(false);
        pnlMain.add(txtfTotalCreds).setEnabled(false);
        pnlMain.add(txtfScolarshipAmount).setEnabled(false);
        pnlMain.add(txtfThesisSupervisor).setEnabled(false);
        pnlMain.add(txtfDataNum).setEnabled(false);

        //Visible text areas
        pnlMain.add(txtfThesisTitle).setEnabled(false);
        pnlMain.add(txtfThesisArea).setEnabled(false);
        //Visible separators
        pnlMain.add(sptrSignUpTwo).setVisible(true);
        pnlMain.add(sptrSignUpOne).setVisible(true);
        //Visable ComboBoxes
        pnlMain.add(cmbPersonType);
        pnlMain.add(cmbDegreeProg).setEnabled(false);
        pnlMain.add(cmbYear).setEnabled(false);
        pnlMain.add(cmbDegreeType).setEnabled(false);

        editFrame.add(pnlMain);
        // JFrame and Panel properties
        editFrame.setSize(600, 800);
        editFrame.setTitle("Edit Menu");
        editFrame.setLocationRelativeTo(null);
        editFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        editFrame.setVisible(true);
    }

    // displays a error message
    /* Method: showError
     * Pre: a string that will display the error message
     * Post: a popup menu displays and tell you about a error you commited.
     * such as not properly filling in information in the editMenu GUI.
     */
    public void showError(String string) {
        JOptionPane.showMessageDialog(pnlMain, string);
    }
}
