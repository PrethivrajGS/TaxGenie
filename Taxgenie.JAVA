import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

class TaxGenie implements ActionListener {

    private JTextField grossPayField, expensesField, basicPayField, investmentField;
    private JFrame mainFrame, resultFrame;

    public static double tax(double amount) {
        if (300000 <= amount && amount < 700000) {
            return 0.05 * amount;
        } else if (700000 <= amount && amount < 1000000) {
            return 0.1 * amount;
        } else if (1000000 <= amount && amount < 1200000) {
            return 0.15 * amount;
        } else if (1200000 <= amount && amount < 1500000) {
            return 0.2 * amount;
        } else if (1500000 <= amount) {
            return 0.3 * amount;
        } else {
            return 0;
        }
    }

    public TaxGenie() {
        mainFrame = new JFrame("Tax Calculator");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(5, 2));
        mainFrame.getContentPane().setBackground(Color.BLACK);
        Color white = Color.WHITE;

        JLabel label = new JLabel("Gross Pay:", SwingConstants.RIGHT);
        label.setForeground(white);
        mainFrame.add(label);
        grossPayField = new JTextField();
        mainFrame.add(grossPayField);

        label = new JLabel("Expenses:", SwingConstants.RIGHT);
        label.setForeground(white);
        mainFrame.add(label);
        expensesField = new JTextField();
        mainFrame.add(expensesField);

        label = new JLabel("Basic Pay:", SwingConstants.RIGHT);
        label.setForeground(white);
        mainFrame.add(label);
        basicPayField = new JTextField();
        mainFrame.add(basicPayField);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.setBackground(Color.GREEN);
        calculateButton.setForeground(Color.WHITE);
        calculateButton.addActionListener(this);
        mainFrame.add(calculateButton);

        label = new JLabel("Investment:", SwingConstants.RIGHT);
        label.setForeground(white);
        mainFrame.add(label);

        investmentField = new JTextField();
        investmentField.setEditable(false);
        mainFrame.add(investmentField);

        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double grossPay = Double.parseDouble(grossPayField.getText());
            double expenses = Double.parseDouble(expensesField.getText());
            double basicPay = Double.parseDouble(basicPayField.getText());
            double taxAmount = tax(grossPay);
            double netPay = basicPay - expenses;
            String bank = getbank();
            double investment = calculateInvestment(netPay);

            investmentField.setText(String.format("%.2f", investment));

            resultFrame = new JFrame("Result");
            resultFrame.setLayout(new GridLayout(4, 2));
            resultFrame.getContentPane().setBackground(Color.BLACK);

            JLabel label = new JLabel("Gross Pay:", SwingConstants.RIGHT);
            label.setForeground(Color.WHITE);
            resultFrame.add(label);
            JLabel gp = new JLabel(String.format("%.2f", grossPay));
            gp.setForeground(Color.RED);
            resultFrame.add(gp);

            label = new JLabel("Tax Amount:", SwingConstants.RIGHT);
            label.setForeground(Color.WHITE);
            resultFrame.add(label);
            JLabel t = new JLabel(String.format("%.2f", taxAmount));
            t.setForeground(Color.RED);
            resultFrame.add(t);

            label = new JLabel("Net Pay:", SwingConstants.RIGHT);
            label.setForeground(Color.WHITE);
            resultFrame.add(label);
            JLabel np = new JLabel(String.format("%.2f", netPay));
            np.setForeground(Color.RED);
            resultFrame.add(np);

            label = new JLabel("Investment through " + bank + ":", SwingConstants.RIGHT);
            label.setForeground(Color.WHITE);
            resultFrame.add(label);
            JLabel in = new JLabel(String.format("%.2f", investment));
            in.setForeground(Color.RED);
            resultFrame.add(in);

            resultFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            resultFrame.setVisible(true);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainFrame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private String getbank() {
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/tax";
        String user = "root";
        String password = "root";
        String maxBank = "";
    
        try {
            Class.forName(driverClassName);
            Connection con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            String sql = "SELECT bank FROM interest ORDER BY rate DESC LIMIT 1";
            ResultSet rs = st.executeQuery(sql);
    
            if (rs.next()) {
                maxBank = rs.getString("bank");
                System.out.println("Bank with highest rate: " + maxBank);
            }
    
            rs.close();
            st.close();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        return maxBank;
    }
    
    private double calculateInvestment(double netPay) {
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/tax";
        String user = "root";
        String password = "root";
        double maxRate = 0;

        try {
            Class.forName(driverClassName);
            Connection con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            String sql = "SELECT rate FROM interest ORDER BY rate DESC LIMIT 1";
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                maxRate = rs.getDouble("rate");
            }

            rs.close();
            st.close();
            con.close();

            return (netPay * maxRate) / 100;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void main(String[] args) {
        new TaxGenie();
    }
}
