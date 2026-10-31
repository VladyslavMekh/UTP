package ACS;

import ACS.core.ReflectionCLI;
import ACS.ui.theme.ThemeColors;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.function.Function;

public class ObjectInspector extends JFrame {
    static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ObjectInspector inspector = new ObjectInspector();
            inspector.setVisible(true);
        });
    }

    public JTable table;
    public DefaultTableModel tableModel;
    public JLabel statusLabel;
    public JTabbedPane tabbedPane;

    public ObjectInspector() {
        setTitle("Object Inspector");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(ThemeColors.BACKGROUND);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JButton loadButton = new JButton("Select file .class");
        loadButton.setBackground(new Color(80, 100, 220));
        loadButton.setForeground(ThemeColors.TEXT);
        loadButton.setFont(new Font("Arial", Font.BOLD, 14));
        loadButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton clearButton = new JButton("Clear");
        clearButton.setBackground(new Color(80, 100, 220));
        loadButton.setForeground(ThemeColors.TEXT);
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        loadButton.addActionListener(e -> loadClassFile());
        clearButton.addActionListener(e -> clearTable());

        topPanel.add(loadButton);
        topPanel.add(clearButton);

        statusLabel = new JLabel("Select file .class for analysis");
        tabbedPane = new JTabbedPane();

        String[] columns = {"Type", "Modifiers", "Name", "Details"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(350);

        JScrollPane scrollPane = new JScrollPane(table);
        tabbedPane.add(scrollPane, "All elements");

        add(topPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        setSize(900, 600);
    }

    public void loadClassFile() {
        JFileChooser jFileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Java Class files (.class)", "class"
        );
        jFileChooser.addChoosableFileFilter(filter);
        jFileChooser.setAcceptAllFileFilterUsed(false);

        int result = jFileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            analyzeClassFile(jFileChooser.getSelectedFile());
        }
    }

    public void clearTable() {
        tableModel.setRowCount(0);
        statusLabel.setText("Table cleared!");
    }

    public void analyzeClassFile(File selectedFile) {
        try {
            String className = selectedFile.getName().replace(".class", "");
            File parentDir = selectedFile.getParentFile();
            URLClassLoader classLoader = new URLClassLoader(
                    new URL[]{parentDir.toURI().toURL()}
            );
            Class<?> loadedClass = classLoader.loadClass(className);

            analyzeClass(loadedClass);
            statusLabel.setText("Class loaded successfully");
            classLoader.close();

        } catch (Exception e) {
            statusLabel.setText("Error loading class!");
            e.printStackTrace();
        }
    }

    public void analyzeClass(Class<?> clazz) {
        addReflectionElements(clazz.getDeclaredFields(), "FIELD", ReflectionCLI::createFieldRow);
        addReflectionElements(clazz.getDeclaredConstructors(), "CONSTRUCTORS", ReflectionCLI::createConstructorRow);
        addReflectionElements(clazz.getDeclaredMethods(), "METHODS", ReflectionCLI::createMethodRow);
        addReflectionElements(clazz.getInterfaces(), "INTERFACES", ReflectionCLI::createInterfaceRow);
    }

    public <T> void addReflectionElements(
            T[] elements,
            String sectionTitle,
            Function<T, Object[]> rowCreator
    ) {
        if (elements == null || elements.length == 0)
            IO.print("Error table is empty or equals null! ");
        IO.println(sectionTitle);
        for (T element : elements)
            tableModel.addRow(rowCreator.apply(element));
        IO.println();
    }
}
