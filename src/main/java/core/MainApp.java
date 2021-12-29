package core;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.google.common.base.Stopwatch;
import static core.Utils.SCARLET;
import static core.Utils.write;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.ExecutionException;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.nfd.NativeFileDialog;
import static org.lwjgl.util.nfd.NativeFileDialog.NFD_PathSet_Free;
import static org.lwjgl.util.nfd.NativeFileDialog.NFD_PathSet_GetCount;
import static org.lwjgl.util.nfd.NativeFileDialog.NFD_PathSet_GetPath;
import org.lwjgl.util.nfd.NFDPathSet;


public class MainApp extends javax.swing.JFrame {

    public static MainApp window;
    private String savePath;
    private final ArrayList<String> loadPaths = new ArrayList<>();
    private FlatSVGIcon startSVG, loadSVG, loadOkSVG, saveSVG, saveOkSVG;
    private Icon whiteLoadingGIF, blackLoadingGIF;

    private static final String ES = "espcn",
            ED = "edsr",
            FS = "fsrcnn",
            LA = "lapsrn";

    private static final String[][] MODES = {
        {"ESPCNx2", ES, "2"}, // 0
        {"ESPCNx3", ES, "3"}, // 1
        {"ESPCNx4", ES, "4"}, // 2
        {"EDSRx2", ED, "2"}, // 3
        {"EDSRx3", ED, "3"}, // 4
        {"EDSRx4", ED, "4"}, // 5
        {"FSRCNNx2", FS, "2"}, // 6
        {"FSRCNNx3", FS, "3"}, // 7
        {"FSRCNNx4", FS, "4"}, // 8
        {"LapSRNx2", LA, "2"}, // 9
        {"LapSRNx4", LA, "4"}, // 10
        {"LapSRNx8", LA, "8"} // 11
    };


    public MainApp() {
        uiCode();
        initComponents();
        uiActions();
        redrawCounter();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                java.awt.EventQueue.invokeLater(() -> {
                    window = new MainApp();
                    window.setVisible(true);
                    Utils.attach(window);
                });
            } catch (Exception e) {
            }
        });
    }

    private void uiCode() {
        JFrame.setDefaultLookAndFeelDecorated(true); //custom window decoration
        UIManager.put("TabbedPane.showTabSeparators", true);
        URL _url = MainApp.class.getClassLoader().getResource("loadingWHITE.gif");
        URL _url2 = MainApp.class.getClassLoader().getResource("loadingBLACK.gif");
        if (_url != null) {
            whiteLoadingGIF = new ImageIcon(_url);
        }
        if (_url2 != null) {
            blackLoadingGIF = new ImageIcon(_url2);
        }
        Utils.setSkin(false);
        URL _url3 = MainApp.class.getClassLoader().getResource("Icon.png");
        if (_url3 != null) {
            setIconImage((new ImageIcon(_url3).getImage()));
        }
        setTitle("PNG Upscaler v1.0.1");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - getBounds().width) / 2,
                (screenSize.height - getBounds().height) / 2);
        startSVG = new FlatSVGIcon("start.svg", 36, 36, ClassLoader.getSystemClassLoader());
        loadSVG = new FlatSVGIcon("load.svg", 36, 36, ClassLoader.getSystemClassLoader());
        loadOkSVG = new FlatSVGIcon("loadOK.svg", 36, 36, ClassLoader.getSystemClassLoader());
        saveOkSVG = new FlatSVGIcon("saveOK.svg", 36, 36, ClassLoader.getSystemClassLoader());
        saveSVG = new FlatSVGIcon("save.svg", 36, 36, ClassLoader.getSystemClassLoader());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        mode = new javax.swing.ButtonGroup();
        mainSplitPane = new javax.swing.JSplitPane();
        upperSplitPanel = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        button_start = new javax.swing.JButton();
        button_import = new javax.swing.JButton();
        button_export_folder = new javax.swing.JButton();
        tabbedPane = new javax.swing.JTabbedPane();
        ESPCNpanel = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        btnESPCNx2 = new javax.swing.JRadioButton();
        jSeparator2 = new javax.swing.JSeparator();
        btnESPCNx3 = new javax.swing.JRadioButton();
        jSeparator3 = new javax.swing.JSeparator();
        btnESPCNx4 = new javax.swing.JRadioButton();
        jSeparator4 = new javax.swing.JSeparator();
        EDSRpanel = new javax.swing.JPanel();
        jSeparator5 = new javax.swing.JSeparator();
        btnESPCNx5 = new javax.swing.JRadioButton();
        jSeparator6 = new javax.swing.JSeparator();
        btnESPCNx6 = new javax.swing.JRadioButton();
        jSeparator7 = new javax.swing.JSeparator();
        btnESPCNx7 = new javax.swing.JRadioButton();
        jSeparator8 = new javax.swing.JSeparator();
        FSRCNNpanel = new javax.swing.JPanel();
        jSeparator9 = new javax.swing.JSeparator();
        btnESPCNx8 = new javax.swing.JRadioButton();
        jSeparator10 = new javax.swing.JSeparator();
        btnESPCNx9 = new javax.swing.JRadioButton();
        jSeparator11 = new javax.swing.JSeparator();
        btnESPCNx10 = new javax.swing.JRadioButton();
        jSeparator12 = new javax.swing.JSeparator();
        LapSRNpanel = new javax.swing.JPanel();
        jSeparator13 = new javax.swing.JSeparator();
        btnESPCNx11 = new javax.swing.JRadioButton();
        jSeparator14 = new javax.swing.JSeparator();
        btnESPCNx12 = new javax.swing.JRadioButton();
        jSeparator15 = new javax.swing.JSeparator();
        btnESPCNx13 = new javax.swing.JRadioButton();
        jSeparator16 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        console = new javax.swing.JTextPane();
        jPanel2 = new javax.swing.JPanel();
        item_counter = new javax.swing.JTextPane();
        button_remove_items = new javax.swing.JButton();
        button_change_theme = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(500, 180));
        setPreferredSize(new java.awt.Dimension(640, 480));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.X_AXIS));

        mainSplitPane.setDividerLocation(90);
        mainSplitPane.setDividerSize(4);
        mainSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        mainSplitPane.setMinimumSize(new java.awt.Dimension(315, 75));

        upperSplitPanel.setDividerLocation(300);

        jPanel1.setLayout(new java.awt.GridLayout());

        button_start.setIcon(startSVG);
        button_start.setText("Start");
        button_start.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button_start.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(button_start);

        button_import.setIcon(loadSVG);
        button_import.setText("Import Files");
        button_import.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button_import.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(button_import);

        button_export_folder.setIcon(startSVG);
        button_export_folder.setText("Export Folder");
        button_export_folder.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button_export_folder.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(button_export_folder);

        upperSplitPanel.setRightComponent(jPanel1);

        tabbedPane.setMinimumSize(new java.awt.Dimension(255, 75));

        ESPCNpanel.setToolTipText("");
        ESPCNpanel.setLayout(new javax.swing.BoxLayout(ESPCNpanel, javax.swing.BoxLayout.X_AXIS));
        ESPCNpanel.add(jSeparator1);

        mode.add(btnESPCNx2);
        btnESPCNx2.setText("x2");
        btnESPCNx2.setToolTipText("Upscale the image x2");
        btnESPCNx2.setActionCommand(MODES[0][0]);
        btnESPCNx2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ESPCNpanel.add(btnESPCNx2);
        ESPCNpanel.add(jSeparator2);

        mode.add(btnESPCNx3);
        btnESPCNx3.setText("x3");
        btnESPCNx3.setToolTipText("Upscale the image x3");
        btnESPCNx3.setActionCommand(MODES[1][0]);
        btnESPCNx3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ESPCNpanel.add(btnESPCNx3);
        ESPCNpanel.add(jSeparator3);

        mode.add(btnESPCNx4);
        btnESPCNx4.setText("x4");
        btnESPCNx4.setToolTipText("Upscale the image x4");
        btnESPCNx4.setActionCommand(MODES[2][0]);
        btnESPCNx4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ESPCNpanel.add(btnESPCNx4);
        ESPCNpanel.add(jSeparator4);

        tabbedPane.addTab("ESPCN", ESPCNpanel);

        EDSRpanel.setToolTipText("");
        EDSRpanel.setLayout(new javax.swing.BoxLayout(EDSRpanel, javax.swing.BoxLayout.X_AXIS));
        EDSRpanel.add(jSeparator5);

        mode.add(btnESPCNx5);
        btnESPCNx5.setText("x2");
        btnESPCNx5.setToolTipText("Upscale the image x2");
        btnESPCNx5.setActionCommand(MODES[3][0]);
        btnESPCNx5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        EDSRpanel.add(btnESPCNx5);
        EDSRpanel.add(jSeparator6);

        mode.add(btnESPCNx6);
        btnESPCNx6.setText("x3");
        btnESPCNx6.setToolTipText("Upscale the image x3");
        btnESPCNx6.setActionCommand(MODES[4][0]);
        btnESPCNx6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        EDSRpanel.add(btnESPCNx6);
        EDSRpanel.add(jSeparator7);

        mode.add(btnESPCNx7);
        btnESPCNx7.setText("x4");
        btnESPCNx7.setToolTipText("Upscale the image x4");
        btnESPCNx7.setActionCommand(MODES[5][0]);
        btnESPCNx7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        EDSRpanel.add(btnESPCNx7);
        EDSRpanel.add(jSeparator8);

        tabbedPane.addTab("EDSR", EDSRpanel);

        FSRCNNpanel.setToolTipText("");
        FSRCNNpanel.setLayout(new javax.swing.BoxLayout(FSRCNNpanel, javax.swing.BoxLayout.X_AXIS));
        FSRCNNpanel.add(jSeparator9);

        mode.add(btnESPCNx8);
        btnESPCNx8.setText("x2");
        btnESPCNx8.setToolTipText("Upscale the image x2");
        btnESPCNx8.setActionCommand(MODES[6][0]);
        btnESPCNx8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FSRCNNpanel.add(btnESPCNx8);
        FSRCNNpanel.add(jSeparator10);

        mode.add(btnESPCNx9);
        btnESPCNx9.setText("x3");
        btnESPCNx9.setToolTipText("Upscale the image x3");
        btnESPCNx9.setActionCommand(MODES[7][0]);
        btnESPCNx9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FSRCNNpanel.add(btnESPCNx9);
        FSRCNNpanel.add(jSeparator11);

        mode.add(btnESPCNx10);
        btnESPCNx10.setText("x4");
        btnESPCNx10.setToolTipText("Upscale the image x4");
        btnESPCNx10.setActionCommand(MODES[8][0]);
        btnESPCNx10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FSRCNNpanel.add(btnESPCNx10);
        FSRCNNpanel.add(jSeparator12);

        tabbedPane.addTab("FSRCNN", FSRCNNpanel);

        LapSRNpanel.setToolTipText("");
        LapSRNpanel.setLayout(new javax.swing.BoxLayout(LapSRNpanel, javax.swing.BoxLayout.X_AXIS));
        LapSRNpanel.add(jSeparator13);

        mode.add(btnESPCNx11);
        btnESPCNx11.setText("x2");
        btnESPCNx11.setToolTipText("Upscale the image x2");
        btnESPCNx11.setActionCommand(MODES[9][0]);
        btnESPCNx11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LapSRNpanel.add(btnESPCNx11);
        LapSRNpanel.add(jSeparator14);

        mode.add(btnESPCNx12);
        btnESPCNx12.setText("x3");
        btnESPCNx12.setToolTipText("Upscale the image x3");
        btnESPCNx12.setActionCommand(MODES[10][0]);
        btnESPCNx12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LapSRNpanel.add(btnESPCNx12);
        LapSRNpanel.add(jSeparator15);

        mode.add(btnESPCNx13);
        btnESPCNx13.setText("x4");
        btnESPCNx13.setToolTipText("Upscale the image x4");
        btnESPCNx13.setActionCommand(MODES[11][0]);
        btnESPCNx13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LapSRNpanel.add(btnESPCNx13);
        LapSRNpanel.add(jSeparator16);

        tabbedPane.addTab("LapSRN", LapSRNpanel);

        upperSplitPanel.setLeftComponent(tabbedPane);

        mainSplitPane.setTopComponent(upperSplitPanel);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setMinimumSize(new java.awt.Dimension(1, 1));

        console.setEditable(false);
        console.setMinimumSize(new java.awt.Dimension(150, 100));
        jScrollPane1.setViewportView(console);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel2.setPreferredSize(new java.awt.Dimension(10, 20));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        item_counter.setEnabled(false);
        item_counter.setMinimumSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(item_counter, gridBagConstraints);

        button_remove_items.setLabel("Remove all items");
        button_remove_items.setMinimumSize(new java.awt.Dimension(160, 20));
        jPanel2.add(button_remove_items, new java.awt.GridBagConstraints());

        button_change_theme.setLabel("Theme");
        button_change_theme.setMinimumSize(new java.awt.Dimension(100, 20));
        jPanel2.add(button_change_theme, new java.awt.GridBagConstraints());

        jPanel3.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        mainSplitPane.setRightComponent(jPanel3);

        getContentPane().add(mainSplitPane);

        pack();
    }// </editor-fold>                        

    private void uiActions() {
        button_start.addActionListener(e -> {
            if (!loadPaths.isEmpty()) {
                createWorker().execute();
            } else {
                write("No images are in queue, add some by clicking 'Import Files'", SCARLET);
            }
        });
        button_import.addActionListener((e) -> {
            NFDPathSet pathSet = NFDPathSet.calloc();
            switch (NativeFileDialog.NFD_OpenDialogMultiple("png", null, pathSet)) {
                case NativeFileDialog.NFD_OKAY -> {
                    long count = NFD_PathSet_GetCount(pathSet);
                    for (long i = 0; i < count; i++) {
                        String path = NFD_PathSet_GetPath(pathSet, i);
                        loadPaths.add(path);
                        write("Added file: " + path, null);
                    }
                    NFD_PathSet_Free(pathSet);
                    button_import.setIcon(loadOkSVG);
                    redrawCounter();
                }
                case NativeFileDialog.NFD_CANCEL -> write("Canceled Image Selection", null);
                default -> // NFD_ERROR
                    write("Error: %s%n" + NativeFileDialog.NFD_GetError(), SCARLET);
            }
        });
        button_export_folder.addActionListener((e) -> {
            PointerBuffer path = MemoryUtil.memAllocPointer(1);
            switch (NativeFileDialog.NFD_PickFolder((ByteBuffer) null, path)) {
                case NativeFileDialog.NFD_OKAY -> {
                    savePath = path.getStringUTF8(0);
                    write("Export Folder set to:" + savePath, null);
                    MemoryUtil.memFree(path);
                    button_export_folder.setIcon(saveOkSVG);
                }
                case NativeFileDialog.NFD_CANCEL -> write("Canceled Save Location Selection", null);
                default -> // NFD_ERROR
                    write("Error: %s%n" + NativeFileDialog.NFD_GetError(), SCARLET);
            }
        });
        
        button_remove_items.addActionListener((e) -> {
            loadPaths.clear();
            redrawCounter();
            Utils.write("All items in queue have been removed!", null);
        });
        
        button_change_theme.addActionListener((e) -> {
           Utils.setSkin(true);
        });

        setSelected();
        startTabbedPanel();
    }

    private void setSelected() {
        Enumeration<AbstractButton> buttons = mode.getElements();
        AbstractButton button;
        while (buttons.hasMoreElements()) {
            button = buttons.nextElement();
            if (button.getActionCommand().equals(Config.FIELD02.getString())) {
                button.setSelected(true);
            }
        }
    }

    private void startTabbedPanel() {
        int index;
        index = switch (Config.FIELD02.getString().substring(0, 2)) {
            case "ES" -> 0;
            case "ED" -> 1;
            case "FS" -> 2;
            default -> 3;
        };
        tabbedPane.setSelectedIndex(index);
    }

    public SwingWorker<Boolean, Integer> createWorker() {
        return new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() {
                setMode(false);
                Utils.stopwatch = Stopwatch.createStarted();
                while (!loadPaths.isEmpty()) {
                    File tempFile = new File(loadPaths.get(0));
                    String local_path = savePath != null ? savePath : tempFile.getPath();
                    local_path = local_path + "\\" + (savePath != null ? "" : "Upscaled_") + tempFile.getName();
                    Upscale.run(loadPaths.get(0), local_path);
                    loadPaths.remove(0);
                    redrawCounter();
                }
                return true;
            }

            @Override
            protected void done() {
                boolean success = false;
                try {
                    success = get();
                } catch (InterruptedException | ExecutionException ex) {
                }
                Utils.stopwatch.stop();
                setMode(true);
                if (success) {
                    savePath = null;
                    Utils.printStopwatch();
                }
            }
        };
    }

    public void setMode(boolean mode) {
        enableComponents(mainSplitPane, mode);
        if (mode) {
            button_start.setIcon(startSVG);
            button_start.setText("Start");
            button_export_folder.setIcon(saveSVG);
            button_import.setIcon(loadSVG);
            return;
        }
        button_start.setText(null);
        enableComponents(mainSplitPane, false);
        if (Config.FIELD03.getBoolean() && blackLoadingGIF != null) {
            button_start.setIcon(blackLoadingGIF);
            button_start.setDisabledIcon(blackLoadingGIF);
        } else if (whiteLoadingGIF != null) {
            button_start.setIcon(whiteLoadingGIF);
            button_start.setDisabledIcon(whiteLoadingGIF);
        }
    }

    public void enableComponents(Container container, boolean enable) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            component.setEnabled(enable);
            if (component instanceof Container) {
                enableComponents((Container) component, enable);
            }
        }
    }

    public static String[] getMode() {
        String thisMode = window.mode.getSelection().getActionCommand();
        for (String[] strings : MODES) {
            if (thisMode.equals(strings[0])) {
                return strings;
            }
        }
        return new String[]{"ERROR"};
    }
    
    private void redrawCounter(){
        item_counter.setText("Number of items: " +loadPaths.size()+".");
    }


    // Variables declaration - do not modify                     
    private javax.swing.JPanel EDSRpanel;
    private javax.swing.JPanel ESPCNpanel;
    private javax.swing.JPanel FSRCNNpanel;
    private javax.swing.JPanel LapSRNpanel;
    private javax.swing.JRadioButton btnESPCNx10;
    private javax.swing.JRadioButton btnESPCNx11;
    private javax.swing.JRadioButton btnESPCNx12;
    private javax.swing.JRadioButton btnESPCNx13;
    private javax.swing.JRadioButton btnESPCNx2;
    private javax.swing.JRadioButton btnESPCNx3;
    private javax.swing.JRadioButton btnESPCNx4;
    private javax.swing.JRadioButton btnESPCNx5;
    private javax.swing.JRadioButton btnESPCNx6;
    private javax.swing.JRadioButton btnESPCNx7;
    private javax.swing.JRadioButton btnESPCNx8;
    private javax.swing.JRadioButton btnESPCNx9;
    private javax.swing.JButton button_change_theme;
    private javax.swing.JButton button_export_folder;
    private javax.swing.JButton button_import;
    private javax.swing.JButton button_remove_items;
    private javax.swing.JButton button_start;
    public javax.swing.JTextPane console;
    private javax.swing.JTextPane item_counter;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JSplitPane mainSplitPane;
    private javax.swing.ButtonGroup mode;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JSplitPane upperSplitPanel;
    // End of variables declaration                   

}
