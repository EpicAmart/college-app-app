/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universityapp;


import java.awt.Component;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;


/**
 *
 * @author satranga
 */
public class ProgressCellRender extends JProgressBar implements TableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        int progress = 0;
        if (value instanceof Float) {
            progress = Math.round(((Float) value) * 100f);
            
        } else if (value instanceof Integer){
            progress = (int) value;
        }
        setValue(progress);
        this.setStringPainted(true);
        return this;
    }
    
}
