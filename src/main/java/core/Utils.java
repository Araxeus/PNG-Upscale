package core;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.google.common.base.Stopwatch;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author USUARIO
 */
public class Utils {

    private static long clickTimer = 0;
    public static Stopwatch stopwatch;
    private static MainApp _frame;
    public static final Color SVGBLUE = new Color(115, 208, 244),
            SCARLET = new Color(255, 36, 0),
            LIGHT = new Color(194, 236, 255),
            DARK = new Color(17, 19, 19);

    public static void attach(MainApp frame) {
        if (frame != null) {
            _frame = frame;
        }
    }
    public static MouseAdapter mouseListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if (clickTimer == 0) {
                clickTimer = System.currentTimeMillis();
                return;
            }
            if (System.currentTimeMillis() - clickTimer < 600) {
                SwingUtilities.invokeLater(() -> setSkin(true));
            }
            clickTimer = 0;
        }
    };

    public static void printStopwatch() {
        long minutes = stopwatch.elapsed(TimeUnit.MINUTES);
        long seconds = stopwatch.elapsed(TimeUnit.SECONDS);
        String minutesString = "";
        if (minutes != 0) {
            seconds = seconds % 60;
            minutesString = minutes + " ";
            if (minutes > 1) {
                minutesString += "Minutes";
            } else {
                minutesString += "Minute";
            }
        }
        String secondsString = "";
        if (!minutesString.equals("")) {
            secondsString += ", ";
        }
        secondsString += seconds;
        secondsString += " Second";
        if (seconds != 1) {
            secondsString += "s";
        }
        if (seconds > 10 || minutes >= 1) {
            write("Done In " + minutesString + secondsString, null);
        }
        stopwatch = null;
    }

    public static void setSkin(boolean next) {
        if (next) {
            Config.FIELD03.setValue(!Config.FIELD03.getBoolean());
        }
        if (Config.FIELD03.getBoolean()) {

            FlatDarculaLaf.install();
            UIManager.put("tabbedPane.selectedBackground", DARK);
        } else {
            FlatIntelliJLaf.install();
            UIManager.put("tabbedPane.selectedBackground", LIGHT);
        }

        if (next && _frame != null) {
            SwingUtilities.updateComponentTreeUI(_frame);
        }
    }

    public static void write(String text, Color color) {
        StyledDocument doc = _frame.console.getStyledDocument();
        Style style = _frame.console.addStyle("Color Style", null);
        if (color == null) {
            color = UIManager.getColor("Button.foreground");
        } else if (color.equals(SVGBLUE)) {
            color = new Color(40, 164, 195);
        }
        StyleConstants.setForeground(style, color);
        StyleConstants.setFontFamily(style, "Segoe UI");
        StyleConstants.setFontSize(style, 13);
        try {
            int length = doc.getLength();
            doc.insertString(length, " >    " + text + "\n", style);
            doc.setParagraphAttributes(length, 1, style, false);
            _frame.console.setCaretPosition(doc.getLength());
        } catch (BadLocationException e) {
        }
    }

}
