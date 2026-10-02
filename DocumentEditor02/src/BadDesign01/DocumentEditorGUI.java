package BadDesign01;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

/**
 * Simple GUI for DocumentEditor — BadDesign01 (violates SRP / OCP).
 * Keeps it simple: add text / image path, render preview, save to file, and View UML.
 */
public class DocumentEditorGUI extends JFrame {

    private final DocumentEditor editor = new DocumentEditor();
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> elementList = new JList<>(listModel);

    private final JTextField textField = new JTextField(22);
    private final JTextField imageField = new JTextField(18);
    private final JTextArea previewArea = new JTextArea(8, 32);
    private final JTextArea logArea = new JTextArea(6, 32);

    // simple palette
    private static final Color BG = new Color(248, 250, 252);
    private static final Color DARK = new Color(30, 41, 59);
    private static final Color PURPLE = new Color(79, 70, 229);
    private static final Color PURPLE_SOFT = new Color(238, 242, 255);
    private static final Color BORDER = new Color(226, 232, 240);
    private static final Color RED = new Color(220, 38, 38);
    private static final Color RED_SOFT = new Color(254, 242, 242);
    private static final Color GREEN = new Color(16, 185, 129);
    private static final Color BLUE = new Color(14, 165, 233);

    public DocumentEditorGUI() {
        super("DocumentEditor — BadDesign01  |  Simple GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(860, 680);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout(0, 0));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER),
                new EmptyBorder(14, 18, 14, 18)));
        JLabel title = new JLabel("📄  DocumentEditor — Bad Design Demo");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(DARK);
        JLabel sub = new JLabel("Single class does everything: holds List<String>, renders & saves  •  Not extensible (no DocumentElement)");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        sub.setForeground(new Color(100, 116, 139));
        JPanel titleBox = new JPanel(new BorderLayout(0, 2));
        titleBox.setBackground(Color.WHITE);
        titleBox.add(title, BorderLayout.NORTH);
        titleBox.add(sub, BorderLayout.SOUTH);
        JLabel badge = new JLabel("  ✗ Bad Design  ");
        badge.setFont(new Font("SansSerif", Font.BOLD, 11));
        badge.setForeground(RED);
        badge.setBackground(RED_SOFT);
        badge.setOpaque(true);
        badge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(254, 205, 211), 1, true),
                new EmptyBorder(6, 12, 6, 12)));
        header.add(titleBox, BorderLayout.WEST);
        header.add(badge, BorderLayout.EAST);

        // Input panel
        JPanel input = new JPanel(new GridBagLayout());
        input.setBackground(Color.WHITE);
        input.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER),
                new EmptyBorder(10, 14, 10, 14)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel textLbl = new JLabel("Text:");
        textLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        textLbl.setForeground(DARK);
        styleField(textField);
        textField.setToolTipText("Type any text then Add Text");
        JButton addTextBtn = btn("Add Text", PURPLE, Color.WHITE);
        JButton addImageBtn = btn("Add Image Path", BLUE, Color.WHITE);

        JLabel imgLbl = new JLabel("Image path:");
        imgLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        imgLbl.setForeground(DARK);
        styleField(imageField);
        imageField.setToolTipText("e.g. /images/cat.png  (BadDesign stores as plain String)");

        gbc.gridx = 0; gbc.gridy = 0; input.add(textLbl, gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1; input.add(textField, gbc);
        gbc.gridx = 2; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; input.add(addTextBtn, gbc);
        gbc.gridx = 0; gbc.gridy = 1; input.add(imgLbl, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1; input.add(imageField, gbc);
        gbc.gridx = 2; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; input.add(addImageBtn, gbc);

        // Center: list + preview
        elementList.setFont(new Font("SansSerif", Font.PLAIN, 13));
        elementList.setFixedCellHeight(26);
        elementList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        elementList.setCellRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                lbl.setBorder(new EmptyBorder(2, 10, 2, 10));
                lbl.setText((index + 1) + ".  " + value);
                if (isSelected) { lbl.setBackground(PURPLE); lbl.setForeground(Color.WHITE); }
                else { lbl.setBackground(index % 2 == 0 ? Color.WHITE : PURPLE_SOFT); lbl.setForeground(DARK); }
                lbl.setOpaque(true);
                return lbl;
            }
        });
        JScrollPane listScroll = new JScrollPane(elementList);
        listScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                " Elements  (editor.elements : List<String> — text & image mixed)", 0, 0,
                new Font("SansSerif", Font.BOLD, 11), new Color(71, 85, 105)));
        listScroll.getViewport().setBackground(Color.WHITE);

        previewArea.setEditable(false);
        previewArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        previewArea.setBackground(new Color(255, 255, 255));
        previewArea.setForeground(DARK);
        previewArea.setBorder(new EmptyBorder(8, 8, 8, 8));
        previewArea.setText("Preview appears here after Render...");
        JScrollPane previewScroll = new JScrollPane(previewArea);
        previewScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                " Rendered Document  (renderDoc() just concatenates strings)", 0, 0,
                new Font("SansSerif", Font.BOLD, 11), new Color(71, 85, 105)));

        JPanel center = new JPanel(new GridLayout(1, 2, 10, 0));
        center.setBackground(BG);
        center.setBorder(new EmptyBorder(10, 14, 10, 14));
        center.add(listScroll);
        center.add(previewScroll);

        // Actions
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        actions.setBackground(Color.WHITE);
        actions.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                new EmptyBorder(6, 6, 6, 6)));
        JButton renderBtn = btn("Render Document", DARK, Color.WHITE);
        JButton saveBtn = btn("Save to File", GREEN, Color.WHITE);
        JButton clearBtn = btn("Clear", Color.WHITE, DARK);
        clearBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER,1,true), new EmptyBorder(7,14,7,14)));
        JButton removeBtn = btn("Remove Selected", Color.WHITE, RED);
        removeBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(254,205,211),1,true), new EmptyBorder(7,14,7,14)));
        JButton umlBtn = btn("View UML", PURPLE, Color.WHITE);
        umlBtn.setToolTipText("Show UML — why this is Bad Design");

        actions.add(renderBtn);
        actions.add(saveBtn);
        actions.add(removeBtn);
        actions.add(clearBtn);
        actions.add(umlBtn);

        JPanel actionsWrap = new JPanel(new BorderLayout());
        actionsWrap.setBackground(BG);
        JLabel actTitle = new JLabel("  Actions  (all in one class — violation)");
        actTitle.setFont(new Font("SansSerif", Font.BOLD, 11));
        actTitle.setForeground(new Color(100, 116, 139));
        actTitle.setBorder(new EmptyBorder(0, 4, 4, 4));
        actionsWrap.add(actTitle, BorderLayout.NORTH);
        actionsWrap.add(actions, BorderLayout.CENTER);

        // Log
        logArea.setEditable(false);
        logArea.setFont(new Font("SansSerif", Font.PLAIN, 11));
        logArea.setBackground(Color.WHITE);
        logArea.setForeground(DARK);
        logArea.setBorder(new EmptyBorder(6, 6, 6, 6));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                " Log ", 0, 0, new Font("SansSerif", Font.BOLD, 11), new Color(100,116,139)));
        logScroll.setPreferredSize(new Dimension(860, 120));

        JPanel south = new JPanel(new BorderLayout(0, 8));
        south.setBackground(BG);
        south.setBorder(new EmptyBorder(0, 14, 14, 14));
        south.add(actionsWrap, BorderLayout.NORTH);
        south.add(logScroll, BorderLayout.CENTER);

        // Assemble
        JPanel north = new JPanel(new BorderLayout(0, 0));
        north.add(header, BorderLayout.NORTH);
        north.add(input, BorderLayout.SOUTH);

        add(north, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        // Listeners
        addTextBtn.addActionListener(e -> addText());
        addImageBtn.addActionListener(e -> addImage());
        textField.addActionListener(e -> addText());
        imageField.addActionListener(e -> addImage());
        renderBtn.addActionListener(e -> render());
        saveBtn.addActionListener(e -> saveToFile());
        removeBtn.addActionListener(e -> removeSelected());
        clearBtn.addActionListener(e -> clearAll());
        umlBtn.addActionListener(e -> showUml());

        // demo data
        addDemo("Hello World! ");
        addDemo("[Image: /images/logo.png] ");
        log("Loaded 2 demo elements. Add text / image path, then Render & Save.");
        log("BadDesign hint: both Text and Image are just Strings in one List — no types.");
    }

    private void styleField(JTextField tf) {
        tf.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                new EmptyBorder(6, 8, 6, 8)));
        tf.setBackground(new Color(248, 250, 252));
    }

    private JButton btn(String t, Color bg, Color fg) {
        JButton b = new JButton(t);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bg.darker(), 1, true),
                new EmptyBorder(7, 14, 7, 14)));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) { b.setBackground(bg.brighter()); }
            @Override public void mouseExited(java.awt.event.MouseEvent e) { b.setBackground(bg); }
        });
        return b;
    }

    private void addDemo(String s) {
        editor.addText(s);
        listModel.addElement(s);
    }

    private void addText() {
        String s = textField.getText().trim();
        if (s.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter some text", "Input", JOptionPane.WARNING_MESSAGE); return; }
        editor.addText(s + " ");
        listModel.addElement(s + " ");
        textField.setText("");
        log("addText(\"" + s + "\")  → elements.size=" + listModel.size());
        textField.requestFocus();
    }

    private void addImage() {
        String s = imageField.getText().trim();
        if (s.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter image path", "Input", JOptionPane.WARNING_MESSAGE); return; }
        editor.addimage(s + " ");
        listModel.addElement(s + " ");
        imageField.setText("");
        log("addimage(\"" + s + "\")  → stored as plain String (no type check)");
    }

    private void render() {
        String out = editor.renderDoc();
        if (out.isEmpty()) out = "(empty document — add elements first)";
        previewArea.setText(out);
        log("renderDoc() → \"" + (out.length() > 80 ? out.substring(0,80)+"..." : out) + "\"");
    }

    private void saveToFile() {
        render(); // ensure latest
        editor.saveTofile();
        File f = new File("example.txt");
        // also try src path where DocumentEditor writes relative to cwd
        String msg = "saveTofile() called — writes to \"example.txt\" (hard-coded path)";
        if (f.exists()) msg += "  • file size " + f.length() + " bytes @ " + f.getAbsolutePath();
        log(msg);
        JOptionPane.showMessageDialog(this, msg, "Saved", JOptionPane.INFORMATION_MESSAGE);
    }

    private void removeSelected() {
        int idx = elementList.getSelectedIndex();
        if (idx < 0) { JOptionPane.showMessageDialog(this, "Select an element to remove", "No Selection", JOptionPane.WARNING_MESSAGE); return; }
        // rebuild editor.elements to reflect removal (BadDesign exposes List directly)
        String removed = listModel.get(idx);
        editor.elements.remove(idx);
        listModel.remove(idx);
        log("Removed element [" + (idx+1) + "] \"" + removed.trim() + "\"");
        previewArea.setText("(removed — click Render to refresh)");
    }

    private void clearAll() {
        editor.elements.clear();
        listModel.clear();
        previewArea.setText("(cleared)");
        log("Cleared all elements.");
    }

    private void showUml() {
        JDialog dlg = new JDialog(this, "UML — DocumentEditor BadDesign", true);
        dlg.setSize(860, 600);
        dlg.setLocationRelativeTo(this);
        dlg.setLayout(new BorderLayout(0,0));
        dlg.getContentPane().setBackground(BG);

        JPanel head = new JPanel(new BorderLayout());
        head.setBackground(Color.WHITE);
        head.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0,0,1,0,BORDER),
                new EmptyBorder(14,18,14,18)));
        JLabel hTitle = new JLabel("Class Diagram — Bad Design  •  One Class Does Everything");
        hTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        hTitle.setForeground(DARK);
        JLabel hSub = new JLabel("No DocumentElement abstraction • render + persistence in same class • List<String> for all types");
        hSub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        hSub.setForeground(new Color(100,116,139));
        JPanel hText = new JPanel(new BorderLayout(0,2));
        hText.setBackground(Color.WHITE);
        hText.add(hTitle, BorderLayout.NORTH);
        hText.add(hSub, BorderLayout.SOUTH);
        JLabel hBadge = new JLabel("  ✗ Not extensible  ");
        hBadge.setFont(new Font("SansSerif", Font.BOLD, 11));
        hBadge.setForeground(RED);
        hBadge.setBackground(RED_SOFT);
        hBadge.setOpaque(true);
        hBadge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(254,205,211),1,true),
                new EmptyBorder(6,12,6,12)));
        head.add(hText, BorderLayout.WEST);
        head.add(hBadge, BorderLayout.EAST);

        UmlPanel uml = new UmlPanel();
        JScrollPane scroll = new JScrollPane(uml);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(BG);
        uml.setPreferredSize(new Dimension(820, 420));

        JPanel legend = new JPanel(new BorderLayout(0,4));
        legend.setBackground(new Color(248,250,252));
        legend.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1,0,0,0,BORDER),
                new EmptyBorder(10,18,10,18)));
        JLabel l1 = new JLabel("Red dashed border = violation  •  Grey boxes on right = what Good Design should look like (faded)");
        l1.setFont(new Font("SansSerif", Font.PLAIN, 11));
        l1.setForeground(new Color(71,85,105));
        JLabel l2 = new JLabel("Fix: interface DocumentElement { render() } with TextElement / ImageElement / VideoElement, plus separate DocumentRenderer & DocumentPersistence, open for extension.");
        l2.setFont(new Font("SansSerif", Font.PLAIN, 11));
        l2.setForeground(new Color(100,116,139));
        legend.add(l1, BorderLayout.NORTH);
        legend.add(l2, BorderLayout.SOUTH);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
        bottom.setBackground(Color.WHITE);
        bottom.setBorder(BorderFactory.createMatteBorder(1,0,0,0,BORDER));
        JButton close = btn("Close", PURPLE, Color.WHITE);
        close.addActionListener(e -> dlg.dispose());
        bottom.add(close);

        dlg.add(head, BorderLayout.NORTH);
        dlg.add(scroll, BorderLayout.CENTER);
        JPanel southWrap = new JPanel(new BorderLayout(0,0));
        southWrap.add(legend, BorderLayout.NORTH);
        southWrap.add(bottom, BorderLayout.SOUTH);
        dlg.add(southWrap, BorderLayout.SOUTH);
        dlg.setVisible(true);
    }

    private class UmlPanel extends JPanel {
        UmlPanel(){ setBackground(BG); }
        @Override protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2=(Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            int W=getWidth();
            int boxW=280, boxH=150;
            int cx=W/2 - 130;
            int cy=36;

            // Main violating DocumentEditor
            drawViolationBox(g2, cx, cy, boxW, boxH);

            // Client below
            int clientW=160, clientH=74;
            int clientX = cx + (boxW - clientW)/2;
            int clientY = cy + boxH + 46;
            drawSimpleBox(g2, clientX, clientY, clientW, clientH, "Client", new String[]{"uses DocumentEditor", "directly"}, new Color(51,65,85), Color.WHITE, false);

            // Connection Client -> DocumentEditor (dependency)
            g2.setColor(new Color(71,85,105));
            g2.setStroke(new BasicStroke(1.4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{6,4}, 0));
            g2.drawLine(clientX + clientW/2, clientY, cx + boxW/2, cy + boxH);
            drawOpenArrow(g2, cx + boxW/2, cy + boxH);
            g2.setFont(new Font("SansSerif", Font.ITALIC, 10));
            g2.setColor(new Color(100,116,139));
            g2.drawString("uses", clientX + clientW/2 + 10, clientY - 8);

            // Good-design hint boxes on right (faded)
            int gx = W - 210;
            int gh = 86;
            g2.setFont(new Font("SansSerif", Font.ITALIC, 10));
            g2.setColor(new Color(100,116,139,120));
            g2.drawString("Should be →", gx - 72, cy + 18);
            drawSimpleBox(g2, gx, cy, 180, gh, "<<interface>> DocumentElement", new String[]{"+ render(): String"}, new Color(16,185,129), new Color(236,253,245), true);
            drawSimpleBox(g2, gx, cy+106, 180, gh, "TextElement", new String[]{"+ render()"}, new Color(16,185,129), new Color(236,253,245), true);
            drawSimpleBox(g2, gx, cy+212, 180, gh, "ImageElement", new String[]{"+ render()"}, new Color(14,165,233), new Color(240,249,255), true);
            drawSimpleBox(g2, gx, cy+318, 85, 48, "Renderer", new String[]{"+ render()"}, new Color(100,116,139), Color.WHITE, true);
            drawSimpleBox(g2, gx+95, cy+318, 85, 48, "Persistence", new String[]{"+ save()"}, new Color(100,116,139), Color.WHITE, true);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 9));
            g2.setColor(new Color(100,116,139,140));
            g2.drawString("separate — SRP & OCP", gx + 18, cy+376);

            // Callout
            g2.setFont(new Font("SansSerif", Font.BOLD, 11));
            String callout="✗  God class — 3 responsibilities + no abstraction";
            int cw=g2.getFontMetrics().stringWidth(callout)+24;
            int ch=28;
            int callX = cx + (boxW - cw)/2;
            int callY = clientY + clientH + 18;
            g2.setColor(RED_SOFT);
            g2.fillRoundRect(callX, callY, cw, ch, 14,14);
            g2.setColor(RED);
            g2.drawRoundRect(callX, callY, cw, ch, 14,14);
            g2.drawString(callout, callX+12, callY+18);

            // bottom note
            g2.setFont(new Font("SansSerif", Font.ITALIC, 10));
            g2.setColor(new Color(100,116,139));
            g2.drawString("Adding Video/Table needs editing DocumentEditor (OCP violation). String concat loses formatting.", 18, getHeight()-10);
        }
        private void drawViolationBox(Graphics2D g2,int x,int y,int w,int h){
            g2.setColor(new Color(0,0,0,10));
            g2.fillRoundRect(x+3,y+3,w,h,14,14);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(x,y,w,h,14,14);
            g2.setColor(RED_SOFT);
            g2.fillRoundRect(x,y,w,28,14,14);
            g2.fillRect(x,y+14,w,14);
            g2.setColor(RED);
            g2.fillRoundRect(x,y,w,3,3,3);
            g2.setColor(BORDER);
            g2.setStroke(new BasicStroke(1.2f));
            g2.drawRoundRect(x,y,w,h,14,14);
            g2.setColor(RED);
            g2.setStroke(new BasicStroke(1.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,0,new float[]{6,4},0));
            g2.drawRoundRect(x,y,w,h,14,14);
            g2.setStroke(new BasicStroke(1.2f));
            g2.setFont(new Font("SansSerif", Font.BOLD, 12));
            g2.setColor(DARK);
            String name="DocumentEditor  ✗";
            int nw=g2.getFontMetrics().stringWidth(name);
            g2.drawString(name, x+(w-nw)/2, y+19);
            g2.setColor(BORDER);
            g2.drawLine(x+12,y+32,x+w-12,y+32);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            String[] members={
                "- elements: List<String> ✗ mixed",
                "+ addText(s: String)",
                "+ addimage(path: String) ✗ duplicate",
                "+ renderDoc(): String ✗ concat only",
                "+ saveTofile() ✗ hard-coded + SRP"
            };
            int my=y+48;
            for(String m: members){
                if(m.contains("✗")) g2.setColor(RED);
                else g2.setColor(new Color(71,85,105));
                g2.drawString(m, x+12, my); my+=14;
            }
        }
        private void drawSimpleBox(Graphics2D g2,int x,int y,int w,int h,String name,String[] members, Color accent, Color soft, boolean faded){
            if(faded) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f));
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(x,y,w,h,12,12);
            g2.setColor(soft);
            g2.fillRoundRect(x,y,w,24,12,12);
            g2.fillRect(x,y+12,w,12);
            g2.setColor(accent);
            g2.fillRoundRect(x,y,w,3,3,3);
            g2.setColor(BORDER);
            g2.setStroke(new BasicStroke(1.1f));
            g2.drawRoundRect(x,y,w,h,12,12);
            g2.setFont(new Font("SansSerif", Font.BOLD, 10));
            g2.setColor(DARK);
            int nw=g2.getFontMetrics().stringWidth(name);
            g2.drawString(name, x+(w-nw)/2, y+17);
            g2.setColor(BORDER);
            g2.drawLine(x+10,y+26,x+w-10,y+26);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 9));
            g2.setColor(new Color(71,85,105));
            int my=y+42;
            for(String m: members){ g2.drawString(m, x+10, my); my+=13; }
            if(faded) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
        private void drawOpenArrow(Graphics2D g2,int x,int y){
            Polygon p=new Polygon();
            int s=8;
            p.addPoint(x, y);
            p.addPoint(x-s, y+s+4);
            p.addPoint(x+s, y+s+4);
            g2.setColor(Color.WHITE);
            g2.fillPolygon(p);
            g2.setColor(new Color(71,85,105));
            g2.drawPolygon(p);
        }
    }

    private void log(String msg){
        logArea.append(msg+"\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
        System.out.println(msg);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }catch(Exception ignored){}
            new DocumentEditorGUI().setVisible(true);
        });
    }
}
