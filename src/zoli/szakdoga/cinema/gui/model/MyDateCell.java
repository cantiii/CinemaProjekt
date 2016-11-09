package zoli.szakdoga.cinema.gui.model;

import java.awt.Color;
import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import zoli.szakdoga.cinema.gui.GuiConstants;

public class MyDateCell extends DefaultCellEditor {

    private static final Border RED = new LineBorder(Color.RED);
    private static final Border BLACK = new LineBorder(Color.BLACK);

    public MyDateCell() {
        super(new JTextField());
    }

    @Override
    public boolean stopCellEditing() {
        JTextField textField = (JTextField) getComponent();
        String data = textField.getText();
        if (data.trim().equals("")) {
            JOptionPane.showMessageDialog(null, GuiConstants.LENGHT_ERROR, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
            textField.setBorder(RED);
            return false;
        } else {
            SimpleDateFormat sample = new SimpleDateFormat("yyyy/MM/dd");
            sample.setLenient(false);

            Date nextDay = new Date();
            Date nextCDay = new Date();

            Calendar c = Calendar.getInstance();
            c.setTime(nextDay);
            c.add(Calendar.DATE, 1);
            nextDay = c.getTime();

            c.add(Calendar.DATE, 100);
            nextCDay = c.getTime();

            try {
                //ha nem valid dátum formátum, akkor ParseException
                Date date = sample.parse(data);
                if (date.after(nextCDay) || date.before(nextDay)) {
                    JOptionPane.showMessageDialog(null, GuiConstants.DATE_ERROR, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, GuiConstants.FORMAT_ERROR, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
                textField.setBorder(RED);
                return false;
            }
        }
        return super.stopCellEditing();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        JTextField textField
                = (JTextField) super.getTableCellEditorComponent(
                        table, value, isSelected, row, column);
        textField.setBorder(BLACK);
        return textField;
    }
}