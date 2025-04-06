import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculate {
    int boardWidth = 360;
    int boardHeight = 600;

    Color customLightGray = new Color(212, 212, 210);
    Color customDarkGray = new Color(80, 80, 80);
    Color customBlack = new Color(28, 28, 28);
    Color customOrange = new Color(255, 149, 0);

    String[] buttonValues = {
        "AC", "+/-", "%", "÷", 
        "7", "8", "9", "×", 
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "=",
        "DEL", "^", "sin", "cos",
        "(", ")", "tan", "ON/OFF"
    };
    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%", "ON/OFF"};

    JFrame frame = new JFrame("Calculate");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonPanel = new JPanel();

    String A = "0";
    String operator = null;
    String B = null;
    boolean isOn = true;

    Calculate() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font ("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonPanel.setLayout(new GridLayout(7, 4)); // Updated to fit 28 buttons
        buttonPanel.setBackground(customBlack);
        frame.add(buttonPanel);

        for (int i = 0; i < buttonValues.length; i++) {
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));

            if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(customLightGray);
                button.setForeground(customBlack);
            }
            else if (Arrays.asList(rightSymbols).contains(buttonValue)){
                button.setBackground(customOrange);
                button.setForeground(Color.white);
            }
            else {
                button.setBackground(customDarkGray);
                button.setForeground(Color.white);
            }

            buttonPanel.add(button);

            button.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();

                    if (!isOn && !buttonValue.equals("ON/OFF")) return; // Only allow ON/OFF when off

                    if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                        if (buttonValue.equals("=")){
                            if (A != null){
                                B = displayLabel.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);
                                
                                switch (operator) {
                                    case "+": displayLabel.setText(removeZeroDecimal(numA + numB)); break;
                                    case "-": displayLabel.setText(removeZeroDecimal(numA - numB)); break;
                                    case "×": displayLabel.setText(removeZeroDecimal(numA * numB)); break;
                                    case "÷": displayLabel.setText(removeZeroDecimal(numA / numB)); break;
                                    case "^": displayLabel.setText(removeZeroDecimal(Math.pow(numA, numB))); break;
                                }
                                clearAll();
                            }
                        }
                        else if ("+-×÷".contains(buttonValue)) {
                            if (operator == null){
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            }
                            operator = buttonValue;
                        }
                    }
                    else if (Arrays.asList(topSymbols).contains(buttonValue)) {
                        if (buttonValue.equals("AC")) {
                            clearAll();
                            displayLabel.setText("0");
                        } 
                        else if (buttonValue.equals("+/-")) {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay *= -1;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                        else if (buttonValue.equals("%")) {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay /= 100;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                        else if (buttonValue.equals("ON/OFF")) {
                            isOn = !isOn;
                            displayLabel.setText(isOn ? "0" : "OFF");
                        }
                    }
                    else {
                        if (buttonValue.equals(".")) {
                            if (!displayLabel.getText().contains(buttonValue)){
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                        else if (buttonValue.equals("DEL")) {
                            String currentText = displayLabel.getText();
                            if (currentText.length() > 1) {
                                displayLabel.setText(currentText.substring(0, currentText.length() - 1));
                            } else {
                                displayLabel.setText("0");
                            }
                        }
                        else if (buttonValue.equals("√")) {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            displayLabel.setText(removeZeroDecimal(Math.sqrt(numDisplay)));
                        }
                        else if (buttonValue.equals("^")) {
                            A = displayLabel.getText();
                            operator = "^";
                            displayLabel.setText("0");
                        }
                        else if (buttonValue.equals("(")) {
                            displayLabel.setText(displayLabel.getText() + "(");
                        }
                        else if (buttonValue.equals(")")) {
                            displayLabel.setText(displayLabel.getText() + ")");
                        }
                        else if (buttonValue.equals("tan")) {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            double result = Math.tan(Math.toRadians(numDisplay));
                            displayLabel.setText(removeZeroDecimal(result));
                        }
                        else if (buttonValue.equals("sin")) {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            double result = Math.sin(Math.toRadians(numDisplay));
                            displayLabel.setText(removeZeroDecimal(result));
                        }
                        else if (buttonValue.equals("cos")) {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            double result = Math.cos(Math.toRadians(numDisplay));
                            displayLabel.setText(removeZeroDecimal(result));
                        }
                        else if ("0123456789".contains(buttonValue)) {
                            if (displayLabel.getText().equals("0")) {
                                displayLabel.setText(buttonValue);
                            } else {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                    }
                }
            });
        }
    }

    void clearAll() {
        A = "0";
        operator = null;
        B = null;
    }

    String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0) {
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }

    public static void main(String[] args) {
        new Calculate();
    }
}
