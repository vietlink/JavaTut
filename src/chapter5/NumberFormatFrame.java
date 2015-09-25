package chapter5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by v11424 on 25/09/2015.
 */
public class NumberFormatFrame extends JFrame {
    private Locale[] locales;
    private double currentNumber;
    private JComboBox<String> localeCombo= new JComboBox<>();
    private JButton parseButton= new JButton("Parse");
    private JTextField numberText= new JTextField(30);
    private JRadioButton numberRadioButton= new JRadioButton("Number");
    private JRadioButton currencyRadioButton= new JRadioButton("Currency");
    private JRadioButton percentRadioButton= new JRadioButton("Percent");
    private ButtonGroup rbGroup= new ButtonGroup();
    private NumberFormat currentNumberFormat;

    public NumberFormatFrame() throws HeadlessException {
        setLayout(new GridBagLayout());
        ActionListener listener= new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDisplay();
            }
        };
        JPanel p= new JPanel();
        addRadioButton(p, numberRadioButton, rbGroup, listener);
        addRadioButton(p, currencyRadioButton, rbGroup, listener);
        addRadioButton(p, percentRadioButton, rbGroup, listener);
        add(new JLabel("Locale: "), new GBC(0, 0).setAnchor(GBC.EAST));
        add(p, new GBC(1, 1));
        add(parseButton, new GBC(0, 2).setInsets(2));
        add(localeCombo, new GBC(1, 0).setAnchor(GBC.WEST));
        add(numberText, new GBC(1,2).setFill(GBC.HORIZONTAL));
        locales= (Locale[])NumberFormat.getAvailableLocales().clone();
        Arrays.sort(locales, new Comparator<Locale>() {
            @Override
            public int compare(Locale o1, Locale o2) {
                return o1.getDisplayName().compareTo(o2.getDisplayName());
            }
        });
        for (Locale loc: locales){
            localeCombo.addItem(loc.getDisplayName());
            localeCombo.setSelectedItem(Locale.getDefault().getDisplayName());
            currentNumber=123456.78;
            updateDisplay();
            localeCombo.addActionListener(listener);
            parseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String s= numberText.getText().trim();
                    try{
                        Number n= currentNumberFormat.parse(s);
                        if(n!=null){
                            currentNumber=n.doubleValue();
                            updateDisplay();
                        }
                        else{
                            numberText.setText("Parse error: "+s);
                        }
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            pack();
        }
    }

    public void addRadioButton(Container p, JRadioButton b, ButtonGroup g, ActionListener listener){
        b.setSelected(g.getButtonCount() == 0);
        b.addActionListener(listener);
        g.add(b);
        p.add(b);
    }
//    Update display and formats the number according to the user setting
    public void updateDisplay(){
        Locale currentLocale= locales[localeCombo.getSelectedIndex()];
        currentNumberFormat= null;
        if(numberRadioButton.isSelected())
            currentNumberFormat=NumberFormat.getNumberInstance(currentLocale);
        else if(currencyRadioButton.isSelected())
            currentNumberFormat= NumberFormat.getNumberInstance(currentLocale);
        else if (percentRadioButton.isSelected())
            currentNumberFormat=NumberFormat.getNumberInstance(currentLocale);
        String n= currentNumberFormat.format(currentNumber);
        numberText.setText(n);
    }
}
