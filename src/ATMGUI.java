import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATMGUI extends JFrame {
    private ATM atm;
    private JTextField pinField;
    private JButton loginButton;
    private JLabel balanceLabel;
    private JTextArea transactionArea;
    private JButton depositButton, withdrawButton, viewStatementButton, logoutButton;

    public ATMGUI(ATM atm) {
        this.atm = atm;
        initComponents();
        createGUI();
    }

    private void initComponents() {
        pinField = new JTextField();
        loginButton = new JButton("Login");

        balanceLabel = new JLabel("Balance: ");
        transactionArea = new JTextArea(10, 30);
        transactionArea.setEditable(false);

        depositButton = new JButton("Deposit");
        withdrawButton = new JButton("Withdraw");
        viewStatementButton = new JButton("View Statement");
        logoutButton = new JButton("Logout");

        loginButton.addActionListener(new LoginAction());
        depositButton.addActionListener(new DepositAction());
        withdrawButton.addActionListener(new WithdrawAction());
        viewStatementButton.addActionListener(new ViewStatementAction());
        logoutButton.addActionListener(new LogoutAction());
    }

    private void createGUI() {
        setLayout(new CardLayout());
        JPanel loginPanel = new JPanel(new GridLayout(3, 1));
        loginPanel.add(new JLabel("Enter PIN:"));
        loginPanel.add(pinField);
        loginPanel.add(loginButton);
        add(loginPanel, "Login");

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(3, 1));
        topPanel.add(balanceLabel);
        topPanel.add(depositButton);
        topPanel.add(withdrawButton);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JLabel("Transactions:"), BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(transactionArea), BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        bottomPanel.add(viewStatementButton);
        bottomPanel.add(logoutButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel, "Main");
        switchToLoginPanel();
    }

    private void switchToMainPanel() {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "Main");
        updateBalanceDisplay();
    }

    private void switchToLoginPanel() {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "Login");
    }

    private void updateBalanceDisplay() {
        balanceLabel.setText("Balance: $" + atm.getBalance());
    }

    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int pin = Integer.parseInt(pinField.getText());
            if (atm.checkPin(pin)) {
                switchToMainPanel();
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect PIN. Please try again.");
            }
        }
    }

    private class DepositAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String amountStr = JOptionPane.showInputDialog("Enter amount to deposit:");
            double amount = Double.parseDouble(amountStr);
            atm.deposit(amount);
            updateBalanceDisplay();
        }
    }

    private class WithdrawAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String amountStr = JOptionPane.showInputDialog("Enter amount to withdraw:");
            double amount = Double.parseDouble(amountStr);
            if (atm.withdraw(amount)) {
                updateBalanceDisplay();
            } else {
                JOptionPane.showMessageDialog(null, "Insufficient funds.");
            }
        }
    }

    private class ViewStatementAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringBuilder statement = new StringBuilder();
            for (String transaction : atm.getTransactions()) {
                statement.append(transaction).append("\n");
            }
            transactionArea.setText(statement.toString());
        }
    }

    private class LogoutAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            pinField.setText("");
            transactionArea.setText("");
            switchToLoginPanel();
        }
    }

    public static void main(String[] args) {
        ATM atm = new ATM();
        ATMGUI atmGUI = new ATMGUI(atm);
        atmGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        atmGUI.pack();
        atmGUI.setVisible(true);
    }
}