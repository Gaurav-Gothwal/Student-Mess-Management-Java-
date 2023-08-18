import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
// import java.awt.color;

class Student {
    private String name;
    private String rollNumber;
    private String branch;
    private String phoneNumber;
    private boolean feePayment;
    private boolean validity;

    public Student(String name, String rollNumber, String branch, String phoneNumber, boolean feePayment, boolean validity) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.branch = branch;
        this.phoneNumber = phoneNumber;
        this.feePayment = feePayment;
        this.validity = validity;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isFeePayment() {
        return feePayment;
    }

    public void setFeePayment(boolean feePayment) {
        this.feePayment = feePayment;
    }

    public boolean isValidity() {
        return validity;
    }

    public void setValidity(boolean validity) {
        this.validity = validity;
    }
}

class StudentMessManagementSystem extends JFrame implements ActionListener {
    private ArrayList<Student> students;
    private JTable table;
    private DefaultTableModel model;
    private JTextField nameField, rollNumberField, branchField, phoneNumberField;
    private JCheckBox feePaymentCheckBox, validityCheckBox;
    private JButton addButton, viewButton, searchButton, updateButton, deleteButton;

    public StudentMessManagementSystem() {
        students = new ArrayList<>();
        setTitle("Student_Mess_Management_System");
        setSize(900, 600);
        // setBackground(getForeground().green);
        // SystemColor
        setLayout(new FlowLayout());
        

        String[] columns = { "Name", "Roll Number", "Fee Payment, College" };
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(15);
        JLabel rollNumberLabel = new JLabel("Roll Number:");
        rollNumberField = new JTextField(10);
        JLabel branchLabel = new JLabel("Branch:");
        branchField = new JTextField(10);
        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberField = new JTextField(10);
        feePaymentCheckBox = new JCheckBox("Fee Payment");
        validityCheckBox = new JCheckBox("Validity");

        addButton = new JButton("Add");
        viewButton = new JButton("View");
        searchButton = new JButton("Search");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        add(nameLabel);
        add(nameField);
        add(rollNumberLabel);
        add(rollNumberField);
        add(branchLabel);
        add(branchField);
        add(phoneNumberLabel);
        add(phoneNumberField);
        add(feePaymentCheckBox);
        add(validityCheckBox);
        add(addButton);
        add(viewButton);
        add(searchButton);
        add(updateButton);
        add(deleteButton);

        addButton.addActionListener(this);
        viewButton.addActionListener(this);
        searchButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addStudent();
        } else if (e.getSource() == viewButton) {
            viewStudents();
        } else if (e.getSource() == searchButton) {
            searchStudent();
        } else if (e.getSource() == updateButton) {
            updateStudent();
        } else if (e.getSource() == deleteButton) {
            deleteStudent();
        }
    }

    private void addStudent() {
        String name = nameField.getText();
        String rollNumber = rollNumberField.getText();
        String branch = branchField.getText();
        String phoneNumber = phoneNumberField.getText();
        boolean feePayment = feePaymentCheckBox.isSelected();
        boolean validity = validityCheckBox.isSelected();

        if (!name.isEmpty() && !rollNumber.isEmpty() && !branch.isEmpty() && !phoneNumber.isEmpty()) {
            Student student = new Student(name, rollNumber, branch, phoneNumber, feePayment, validity);
            students.add(student);
            Object[] row = { name, rollNumber, feePayment, };
            model.addRow(row);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all the fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewStudents() {
        model.setRowCount(0);
        for (Student student : students) {
            Object[] row = { student.getName(), student.getRollNumber(), student.isFeePayment() };
            model.addRow(row);
        }
    }

    private void searchStudent() {
        String searchTerm = JOptionPane.showInputDialog(this, "Enter roll number or name:");
        if (searchTerm != null && !searchTerm.isEmpty()) {
            for (Student student : students) {
                if (student.getRollNumber().equals(searchTerm) || student.getName().equalsIgnoreCase(searchTerm)) {
                    JOptionPane.showMessageDialog(this, "Student Found:\n\nName: " + student.getName() +
                            "\nRoll Number: " + student.getRollNumber() + "\nFee Payment: " + student.isFeePayment(),
                            "Search Result", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Student not found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String newName = JOptionPane.showInputDialog(this, "Enter new name : ");
            boolean newFeePayment = JOptionPane.showConfirmDialog(this, "Is the fee payment updated?",
                    "Update Fee Payment", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
            students.get(selectedRow).setName(newName);
            students.get(selectedRow).setFeePayment(newFeePayment);
            model.setValueAt(newName, selectedRow, 0);
            model.setValueAt(newFeePayment, selectedRow, 2);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student from the table.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the student?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                students.remove(selectedRow);
                model.removeRow(selectedRow);
                clearFields();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student from the table.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        nameField.setText("");
        rollNumberField.setText("");
        branchField.setText("");
        phoneNumberField.setText("");
        feePaymentCheckBox.setSelected(false);
        validityCheckBox.setSelected(false);
    }
}

public class StudentMessManagment {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StudentMessManagementSystem();
            }
        });
    }
}
