package zoli.szakdoga.cinema.gui.model;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.Component;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

/**
 * Implements a cell editor that uses a formatted text field
 * to edit Integer values.
 */
public class IntegerEditor extends DefaultCellEditor {
    JFormattedTextField ftf;
    DateFormat integerFormat;
    private boolean DEBUG = false;

    public IntegerEditor() {
        super(new JFormattedTextField());
        ftf = (JFormattedTextField)getComponent();

        //Set up the editor for the integer cells.
        integerFormat = new SimpleDateFormat("yyyy/MM/dd");
        DateFormatter intFormatter = new DateFormatter(integerFormat);
        intFormatter.setFormat(integerFormat);
        ftf.setFormatterFactory(
                new DefaultFormatterFactory(intFormatter));
        ftf.setHorizontalAlignment(JTextField.TRAILING);
        ftf.setFocusLostBehavior(JFormattedTextField.PERSIST);

        //React when the user presses Enter while the editor is
        //active.  (Tab is handled as specified by
        //JFormattedTextField's focusLostBehavior property.)
        ftf.getInputMap().put(KeyStroke.getKeyStroke(
                                        KeyEvent.VK_ENTER, 0),
                                        "check");
        ftf.getActionMap().put("check", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
		if (!ftf.isEditValid()) { //The text is invalid.
                    if (userSaysRevert()) { //reverted
		        ftf.postActionEvent(); //inform the editor
		    }
                } else try {              //The text is valid,
                    ftf.commitEdit();     //so use it.
                    ftf.postActionEvent(); //stop editing
                } catch (java.text.ParseException exc) { }
            }
        });
    }

        public Component getTableCellEditorComponent(JTable table,
            Object value, boolean isSelected,
            int row, int column) {
        JFormattedTextField ftf =
            (JFormattedTextField)super.getTableCellEditorComponent(
                table, value, isSelected, row, column);
        ftf.setValue(value);
        return ftf;
    }

    //Override to ensure that the value remains an Integer.
    public Object getCellEditorValue() {
        JFormattedTextField ftf = (JFormattedTextField)getComponent();
        Object o = ftf.getValue();
        if (o instanceof String) {
            return o;
        } else {
            if (DEBUG) {
                System.out.println("getCellEditorValue: o isn't a Number");
            }
            try {
                return integerFormat.parseObject(o.toString());
            } catch (Exception exc) {
                System.err.println("getCellEditorValue: can't parse o: " + o);
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

                // Get the date today using Calendar object.
                Date date = new Date();
                Calendar today = Calendar.getInstance();
                today.setTime(date);
                today.add(Calendar.DATE, +7);
                Date newDate = today.getTime();
                // Using DateFormat format method we can create a string 
                // representation of a date with the defined format.
                String reportDate = df.format(newDate);
                return reportDate;
            }
        }
    }

    //Override to check whether the edit is valid,
    //setting the value if it is and complaining if
    //it isn't.  If it's OK for the editor to go
    //away, we need to invoke the superclass's version 
    //of this method so that everything gets cleaned up.
    public boolean stopCellEditing() {
        JFormattedTextField ftf = (JFormattedTextField)getComponent();
        if (ftf.isEditValid()) {
            try {
                ftf.commitEdit();
            } catch (java.text.ParseException exc) { }
	    
        } else { //text is invalid
            if (!userSaysRevert()) { //user wants to edit
	        return false; //don't let the editor go away
	    } 
        }
        return super.stopCellEditing();
    }

    /** 
     * Lets the user know that the text they entered is 
     * bad. Returns true if the user elects to revert to
     * the last good value.  Otherwise, returns false, 
     * indicating that the user wants to continue editing.
     */
    protected boolean userSaysRevert() {
        ftf.selectAll();
        Object[] options = {"Edit",
                            "Revert"};
        int answer = JOptionPane.showOptionDialog(
            SwingUtilities.getWindowAncestor(ftf),
            "The value must be an integer between "
            + "You can either continue editing "
            + "or revert to the last valid value.",
            "Invalid Text Entered",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.ERROR_MESSAGE,
            null,
            options,
            options[1]);
	    
        if (answer == 1) { //Revert!
            ftf.setValue(ftf.getValue());
	    return true;
        }
	return false;
    }
}