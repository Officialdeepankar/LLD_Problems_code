package ArguablyGoodDesign;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

/**
 * GUI for ArguablyGoodDesign — shows the improved separation vs BadDesign01.
 * Features: add text / image element, render preview, save via SaveToFile, View UML.
 */
public class DocumentEditorGUI extends JFrame {

    private final DocumentEditor editor = new DocumentEditor();
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> elementList = new JList<>(listModel);

    private final JTextField textField = new JTextField(22);
    private final JTextField imageField = new JTextField(18);
    private final JTextArea previewArea = new JTextArea(8, 32);
    private final JTextArea logArea = new JTextArea(6, 32);

    private static final Color BG = new Color(248, 250, 252);
    private static final Color DARK = new Color(30, 41, 59);
    private static final Color PURPLE = new Color(79, 70, 229);
    private static final Color PURPLE_SOFT = new Color(238, 242, 255);
    private static final Color BORDER = new Color(226, 232, 240);
    private static final Color GREEN = new Color(16, 185, 129);
    private static final Color GREEN_SOFT = new Color(236, 253, 245);
    private static final Color BLUE = new Color(14, 165, 233);
    private static final Color AMBER = new Color(245, 158, 11);
    private static final Color AMBER_SOFT = new Color(255, 251, 235);

    public DocumentEditorGUI() {
        super("DocumentEditor — ArguablyGoodDesign  |  GUI + UML");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(880, 700);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout(0, 0));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER),
                new EmptyBorder(14, 18, 14, 18)));
        JLabel title = new JLabel("📄  DocumentEditor — Arguably Good Design");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(DARK);
        JLabel sub = new JLabel("DocumentElement abstraction + Document holds List<DocumentElement> + PersistInDb strategy  •  Open for extension");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        sub.setForeground(new Color(100, 116, 139));
        JPanel titleBox = new JPanel(new BorderLayout(0, 2));
        titleBox.setBackground(Color.WHITE);
        titleBox.add(title, BorderLayout.NORTH);
        titleBox.add(sub, BorderLayout.SOUTH);
        JLabel badge = new JLabel("  ✓ Arguably Good  ");
        badge.setFont(new Font("SansSerif", Font.BOLD, 11));
        badge.setForeground(new Color(5, 122, 80));
        badge.setBackground(GREEN_SOFT);
        badge.setOpaque(true);
        badge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(167, 243, 208), 1, true),
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
        textField.setToolTipText("TextElement — stored as typed object");
        JButton addTextBtn = btn("Add Text", GREEN, Color.WHITE);

        JLabel imgLbl = new JLabel("Image path:");
        imgLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        imgLbl.setForeground(DARK);
        styleField(imageField);
        imageField.setToolTipText("e.g. /images/cat.png — stored as ImageElement");
        JButton addImageBtn = btn("Add Image", BLUE, Color.WHITE);

        gbc.gridx = 0; gbc.gridy = 0; input.add(textLbl, gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1; input.add(textField, gbc);
        gbc.gridx = 2; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; input.add(addTextBtn, gbc);
        gbc.gridx = 0; gbc.gridy = 1; input.add(imgLbl, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1; input.add(imageField, gbc);
        gbc.gridx = 2; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; input.add(addImageBtn, gbc);

        // Center: list + preview
        elementList.setFont(new Font("SansSerif", Font.PLAIN, 13));
        elementList.setFixedCellHeight(28);
        elementList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        elementList.setCellRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                lbl.setBorder(new EmptyBorder(2, 10, 2, 10));
                String v = (String) value;
                boolean isImage = v.startsWith("[Image]");
                String prefix = isImage ? "🖼  " : "📝  ";
                String type = isImage ? "ImageElement" : "TextElement";
                lbl.setText((index + 1) + ".  " + prefix + v + "   · " + type);
                if (isSelected) { lbl.setBackground(PURPLE); lbl.setForeground(Color.WHITE); }
                else {
                    lbl.setBackground(index % 2 == 0 ? Color.WHITE : PURPLE_SOFT);
                    lbl.setForeground(DARK);
                }
                lbl.setOpaque(true);
                return lbl;
            }
        });
        JScrollPane listScroll = new JScrollPane(elementList);
        listScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                " Elements  (Document.document : List<DocumentElement>)", 0, 0,
                new Font("SansSerif", Font.BOLD, 11), new Color(71, 85, 105)));
        listScroll.getViewport().setBackground(Color.WHITE);

        previewArea.setEditable(false);
        previewArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        previewArea.setBackground(Color.WHITE);
        previewArea.setForeground(DARK);
        previewArea.setBorder(new EmptyBorder(8, 8, 8, 8));
        previewArea.setText("Preview appears here after Render...");
        JScrollPane previewScroll = new JScrollPane(previewArea);
        previewScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                " Rendered Document  (Document.render() delegates to element.render())", 0, 0,
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
        JButton saveBtn = btn("Save (PersistInDb)", GREEN, Color.WHITE);
        JButton clearBtn = btn("Clear", Color.WHITE, DARK);
        clearBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER,1,true), new EmptyBorder(7,14,7,14)));
        JButton removeBtn = btn("Remove Selected", Color.WHITE, new Color(220,38,38));
        removeBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(254,205,211),1,true), new EmptyBorder(7,14,7,14)));
        JButton umlBtn = btn("View UML", PURPLE, Color.WHITE);
        umlBtn.setToolTipText("Show UML — ArguablyGoodDesign class diagram");

        actions.add(renderBtn);
        actions.add(saveBtn);
        actions.add(removeBtn);
        actions.add(clearBtn);
        actions.add(umlBtn);

        JPanel actionsWrap = new JPanel(new BorderLayout());
        actionsWrap.setBackground(BG);
        JLabel actTitle = new JLabel("  Actions  (decoupled: rendering vs persistence vs editing)");
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
        logScroll.setPreferredSize(new Dimension(880, 120));

        JPanel south = new JPanel(new BorderLayout(0, 8));
        south.setBackground(BG);
        south.setBorder(new EmptyBorder(0, 14, 14, 14));
        south.add(actionsWrap, BorderLayout.NORTH);
        south.add(logScroll, BorderLayout.CENTER);

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
        addDemoText("Hello World! ");
        addDemoImage("/images/logo.png");
        log("Loaded 2 demo elements: 1× TextElement + 1× ImageElement");
        log("Try: add Text/Image → Render → Save. Notice elements keep their type (not just String).");
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

    private void addDemoText(String s) {
        editor.addText(s);
        listModel.addElement(s);
    }
    private void addDemoImage(String path) {
        editor.addImage(path);
        listModel.addElement("[Image] " + path + " ");
    }

    private void addText() {
        String s = textField.getText().trim();
        if (s.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter some text", "Input", JOptionPane.WARNING_MESSAGE); return; }
        editor.addText(s + " ");
        listModel.addElement(s + " ");
        textField.setText("");
        log("addText(\"" + s + "\") → new TextElement  •  total=" + listModel.size());
        textField.requestFocus();
    }

    private void addImage() {
        String s = imageField.getText().trim();
        if (s.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter image path", "Input", JOptionPane.WARNING_MESSAGE); return; }
        editor.addImage(s);
        listModel.addElement("[Image] " + s + " ");
        imageField.setText("");
        log("addImage(\"" + s + "\") → new ImageElement  •  total=" + listModel.size());
    }

    private void render() {
        String out = editor.render();
        if (out.isEmpty()) out = "(empty document — add elements first)";
        previewArea.setText(out);
        log("render() → \"" + (out.length() > 90 ? out.substring(0,90)+"..." : out) + "\"  (delegates Document → DocumentElement.render())");
    }

    private void saveToFile() {
        render();
        editor.save();
        // Verify by reading back the file we just wrote (resolve via user.dir like SaveToFile does)
        File f = new File(System.getProperty("user.dir"), "example.txt");
        if (!f.exists()) f = new File("example.txt"); // fallback relative
        String msg = "save() → PersistInDb.save() [SaveToFile writes example.txt]";
        String fileContent = "";
        if (f.exists()) {
            try { fileContent = java.nio.file.Files.readString(f.toPath()); } catch (Exception e) { fileContent = "<read error: "+e.getMessage()+">"; }
            msg += "  • " + f.length() + " bytes @ " + f.getAbsolutePath() + "\nContent: \"" + fileContent + "\"";
        } else {
            msg += "  • (file not found — check working dir: " + new File("example.txt").getAbsolutePath() + ")";
        }
        log(msg.replace("\n", " | "));
        // Show content + offer to open folder — explains why IDE's src/example.txt may look empty
        String dialogMsg = msg + "\n\nIf you opened DocumentEditor02/src/ArguablyGoodDesign/example.txt and it looks empty,\n"
                + "that's a stale file — the real file is at the path above (user.dir). It is now synced.";
        // Try to sync stale src file view immediately so IDE refresh shows content
        try {
            File stale = new File("DocumentEditor02/src/ArguablyGoodDesign/example.txt");
            if (!stale.exists()) stale = new File(System.getProperty("user.dir"), "src/ArguablyGoodDesign/example.txt");
            if (stale.getParentFile().exists() && f.exists()) {
                java.nio.file.Files.writeString(stale.toPath(), fileContent);
            }
        } catch (Exception ignore) {}
        JOptionPane.showMessageDialog(this, dialogMsg, "Saved via PersistInDb — " + (fileContent.isEmpty()?"empty!":"verified"), JOptionPane.INFORMATION_MESSAGE);
    }

    private void removeSelected() {
        int idx = elementList.getSelectedIndex();
        if (idx < 0) { JOptionPane.showMessageDialog(this, "Select an element to remove", "No Selection", JOptionPane.WARNING_MESSAGE); return; }
        String removed = listModel.get(idx);
        // remove from Document's list (keeps GUI and model in sync)
        if (idx < editor.doc.document.size()) {
            DocumentElement el = editor.doc.document.get(idx);
            editor.doc.removeElement(el);
        }
        listModel.remove(idx);
        log("Removed [" + (idx+1) + "] \"" + removed.trim() + "\" → document.size=" + editor.doc.document.size());
        previewArea.setText("(removed — click Render to refresh)");
    }

    private void clearAll() {
        // clear document
        editor.doc.document.clear();
        listModel.clear();
        previewArea.setText("(cleared)");
        log("Cleared all elements — Document.document is empty.");
    }

    private void showUml() {
        JDialog dlg = new JDialog(this, "UML — ArguablyGoodDesign", true);
        dlg.setSize(920, 660);
        dlg.setLocationRelativeTo(this);
        dlg.setLayout(new BorderLayout(0,0));
        dlg.getContentPane().setBackground(BG);

        JPanel head = new JPanel(new BorderLayout());
        head.setBackground(Color.WHITE);
        head.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0,0,1,0,BORDER),
                new EmptyBorder(14,18,14,18)));
        JLabel hTitle = new JLabel("Class Diagram — Arguably Good Design  •  Separation + Polymorphism");
        hTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        hTitle.setForeground(DARK);
        JLabel hSub = new JLabel("DocumentElement abstraction, Document aggregation, PersistInDb strategy — open for extension (add VideoElement without editing Document)");
        hSub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        hSub.setForeground(new Color(100,116,139));
        JPanel hText = new JPanel(new BorderLayout(0,2));
        hText.setBackground(Color.WHITE);
        hText.add(hTitle, BorderLayout.NORTH);
        hText.add(hSub, BorderLayout.SOUTH);
        JLabel hBadge = new JLabel("  ✓ Extensible  ");
        hBadge.setFont(new Font("SansSerif", Font.BOLD, 11));
        hBadge.setForeground(new Color(5,122,80));
        hBadge.setBackground(GREEN_SOFT);
        hBadge.setOpaque(true);
        hBadge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(167,243,208),1,true),
                new EmptyBorder(6,12,6,12)));
        head.add(hText, BorderLayout.WEST);
        head.add(hBadge, BorderLayout.EAST);

        UmlPanel uml = new UmlPanel();
        JScrollPane scroll = new JScrollPane(uml);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(BG);
        uml.setPreferredSize(new Dimension(900, 520));

        JPanel legend = new JPanel(new BorderLayout(0,4));
        legend.setBackground(new Color(248,250,252));
        legend.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1,0,0,0,BORDER),
                new EmptyBorder(10,18,10,18)));
        JLabel l1 = new JLabel("▸ Solid = inheritance  •  dashed = dependency/association  •  ◇ = aggregation  •  Green = good separation  •  Amber = arguably-good caveat");
        l1.setFont(new Font("SansSerif", Font.PLAIN, 11));
        l1.setForeground(new Color(71,85,105));
        JLabel l2 = new JLabel("Arguably good because: PersistInDb is abstract class not interface, naming is confusing (SaveToFile extends PersistInDb), Document exposes public List (encapsulation leak). Fix: interface Persistence {save}, private List + getter.");
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

            // Layout boxes: top row: DocumentElement, middle: Text/Image, center: Document, top-right: PersistInDb hierarchy, top-center: DocumentEditor
            int deX= W/2 - 90, deY= 20, deW=180, deH=70;
            int teX= deX - 110, teY= deY + deH + 34, teW=160, teH=62;
            int ieX= deX + deW + 20 - 50, ieY= teY, ieW=160, ieH=62;
            int docX= W/2 - 100, docY= teY + teH + 44, docW=200, docH=88;
            int persistX= W - 210, persistY= 20, persistW=180, persistH=62;
            int saveX= W - 210, saveY= persistY + persistH + 30, saveW=180, saveH=62;
            int editorX= 22, editorY= 22, editorW=210, editorH=118;
            int clientX= 22, clientY= editorY + editorH + 36, clientW=150, clientH=60;

            // Draw boxes
            drawAbstractBox(g2, deX, deY, deW, deH, "<<abstract>>", "DocumentElement", new String[]{"+ render(): String"}, GREEN);
            drawBox(g2, teX, teY, teW, teH, "TextElement", new String[]{"- textelement: String", "+ render(): String"}, GREEN, GREEN_SOFT);
            drawBox(g2, ieX, ieY, ieW, ieH, "ImageElement", new String[]{"- Imageaddress: String", "+ render(): String"}, BLUE, new Color(240,249,255));
            drawBox(g2, docX, docY, docW, docH, "Document", new String[]{"- document: List<DocumentElement>", "+ render(): String", "+ addelement(e)", "+ removeElement(e)"}, DARK, Color.WHITE);
            drawAbstractBox(g2, persistX, persistY, persistW, persistH, "<<abstract>>", "PersistInDb", new String[]{"+ save(result: String)"}, AMBER);
            drawBox(g2, saveX, saveY, saveW, saveH, "SaveToFile", new String[]{"+ save(result: String)", "  → Files.writeString()"}, AMBER, AMBER_SOFT);
            drawBox(g2, editorX, editorY, editorW, editorH, "DocumentEditor", new String[]{"- doc: Document", "- persistInDb: PersistInDb", "+ addText(s)", "+ addImage(path)", "+ render(): String", "+ save()"}, PURPLE, new Color(238,242,255));
            drawBox(g2, clientX, clientY, clientW, clientH, "Client / GUI", new String[]{"uses DocumentEditor"}, new Color(51,65,85), Color.WHITE);

            // Inheritance: TextElement -> DocumentElement, ImageElement -> DocumentElement
            g2.setColor(DARK); g2.setStroke(new BasicStroke(1.3f));
            // vertical + horizontal nicely
            drawInheritance(g2, teX+teW/2, teY, deX+30, deY+deH);
            drawInheritance(g2, ieX+ieW/2, ieY, deX+deW-30, deY+deH);
            // SaveToFile -> PersistInDb
            drawInheritance(g2, saveX+saveW/2, saveY, persistX+persistW/2, persistY+persistH);

            // Aggregation Document ◇— DocumentElement (1..* )
            g2.setColor(DARK); g2.setStroke(new BasicStroke(1.3f));
            // from doc top center to de bottom center
            int docTopX = docX + docW/2, docTopY = docY;
            int deBotX = deX + deW/2, deBotY = deY + deH;
            // use a line with diamond at Document side
            // draw line slightly curved via two segments
            int midY = (docTopY + deBotY)/2;
            g2.drawLine(docTopX, docTopY, docTopX, midY);
            g2.drawLine(docTopX, midY, deBotX, midY);
            g2.drawLine(deBotX, midY, deBotX, deBotY);
            drawDiamond(g2, docTopX, docTopY, true);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 9));
            g2.setColor(new Color(100,116,139));
            g2.drawString("1", docTopX+6, docTopY+12);
            g2.drawString("*", deBotX+6, deBotY-4);
            g2.setFont(new Font("SansSerif", Font.ITALIC, 10));
            g2.drawString("aggregates", docTopX+18, midY-6);

            // DocumentEditor has-a Document (composition/association)
            g2.setColor(DARK); g2.setStroke(new BasicStroke(1.3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{6,4}, 0));
            int edRightX = editorX + editorW, edMidY = editorY + editorH/2;
            int docLeftX = docX, docMidY = docY + docH/2;
            g2.drawLine(edRightX, edMidY, docLeftX, docMidY);
            drawDiamond(g2, edRightX, edMidY, false);
            g2.setFont(new Font("SansSerif", Font.ITALIC, 10));
            g2.setColor(new Color(100,116,139));
            g2.drawString("has-a", (edRightX+docLeftX)/2 - 14, edMidY - 8);

            // DocumentEditor -> PersistInDb (strategy)
            g2.setColor(new Color(100,116,139)); g2.setStroke(new BasicStroke(1.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{6,4}, 0));
            int edTopRightX = editorX + editorW - 20, edTopY = editorY;
            int persistLeftX = persistX, persistMidY = persistY + persistH/2;
            // L shaped
            g2.drawLine(edTopRightX, edTopY, edTopRightX, persistMidY);
            g2.drawLine(edTopRightX, persistMidY, persistLeftX, persistMidY);
            drawOpenArrow(g2, persistLeftX, persistMidY, true);
            g2.setFont(new Font("SansSerif", Font.ITALIC, 10));
            g2.drawString("strategy", edTopRightX+8, persistMidY-8);

            // Client -> DocumentEditor
            g2.setColor(DARK); g2.setStroke(new BasicStroke(1.3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{6,4}, 0));
            int clientTopX = clientX + clientW/2, clientTopY = clientY;
            int edBotX = editorX + editorW/2, edBotY = editorY + editorH;
            g2.drawLine(clientTopX, clientTopY, edBotX, edBotY);
            drawOpenArrow(g2, edBotX, edBotY, false);
            g2.setFont(new Font("SansSerif", Font.ITALIC, 10));
            g2.setColor(new Color(100,116,139));
            g2.drawString("uses", clientTopX+10, clientTopY-8);

            // Extension hint: dotted box for VideoElement future
            g2.setColor(new Color(16,185,129, 160));
            g2.setStroke(new BasicStroke(1.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{5,5}, 0));
            int veX = ieX, veY = ieY + ieH + 10, veW = ieW, veH = 36;
            g2.drawRoundRect(veX, veY, veW, veH, 10,10);
            g2.setFont(new Font("SansSerif", Font.ITALIC, 10));
            g2.setColor(new Color(5,122,80, 190));
            g2.drawString(" + VideoElement (future) — no edit", veX+8, veY+22);

            // Caveat callout bottom
            g2.setFont(new Font("SansSerif", Font.BOLD, 11));
            String callout="✓ OCP: add new DocumentElement without touching Document  ✗ caveat: PersistInDb naming / public List";
            int cw=g2.getFontMetrics().stringWidth(callout)+24;
            int ch=28;
            int callX = W/2 - cw/2;
            int callY = docY + docH + 62;
            g2.setColor(AMBER_SOFT);
            g2.fillRoundRect(callX, callY, cw, ch, 14,14);
            g2.setColor(AMBER);
            g2.drawRoundRect(callX, callY, cw, ch, 14,14);
            g2.setColor(DARK);
            g2.drawString(callout, callX+12, callY+18);

            // Delegation note
            g2.setFont(new Font("SansSerif", Font.ITALIC, 10));
            g2.setColor(new Color(100,116,139));
            String note="Document.render() iterates List<DocumentElement> and delegates to element.render() — polymorphism. DocumentEditor delegates to Document.";
            g2.drawString(note, 18, getHeight()-10);
        }

        private void drawBox(Graphics2D g2,int x,int y,int w,int h,String name,String[] members, Color accent, Color soft){
            g2.setColor(new Color(0,0,0,10));
            g2.fillRoundRect(x+3,y+3,w,h,12,12);
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
            g2.setFont(new Font("SansSerif", Font.BOLD, 11));
            g2.setColor(DARK);
            int nw=g2.getFontMetrics().stringWidth(name);
            g2.drawString(name, x+(w-nw)/2, y+17);
            g2.setColor(BORDER);
            g2.drawLine(x+10,y+26,x+w-10,y+26);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g2.setColor(new Color(71,85,105));
            int my=y+42;
            for(String m: members){ g2.drawString(m, x+10, my); my+=13; }
        }
        private void drawAbstractBox(Graphics2D g2,int x,int y,int w,int h,String stereo,String name,String[] members, Color accent){
            g2.setColor(new Color(0,0,0,10));
            g2.fillRoundRect(x+3,y+3,w,h,12,12);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(x,y,w,h,12,12);
            g2.setColor(new Color(248,250,252));
            g2.fillRoundRect(x,y,w,28,12,12);
            g2.fillRect(x,y+14,w,14);
            g2.setColor(accent);
            g2.fillRoundRect(x,y,w,3,3,3);
            g2.setColor(BORDER);
            g2.setStroke(new BasicStroke(1.1f));
            g2.drawRoundRect(x,y,w,h,12,12);
            g2.setFont(new Font("SansSerif", Font.ITALIC, 9));
            g2.setColor(new Color(100,116,139));
            int sw=g2.getFontMetrics().stringWidth(stereo);
            g2.drawString(stereo, x+(w-sw)/2, y+12);
            g2.setFont(new Font("SansSerif", Font.BOLD, 11));
            g2.setColor(DARK);
            int nw=g2.getFontMetrics().stringWidth(name);
            g2.drawString(name, x+(w-nw)/2, y+24);
            g2.setColor(BORDER);
            g2.drawLine(x+10,y+32,x+w-10,y+32);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g2.setColor(new Color(71,85,105));
            int my=y+48;
            for(String m: members){ g2.drawString(m, x+10, my); my+=13; }
        }
        private void drawInheritance(Graphics2D g2,int x1,int y1,int x2,int y2){
            g2.drawLine(x1,y1,x2,y2);
            int s=10;
            Polygon p=new Polygon();
            p.addPoint(x2,y2);
            p.addPoint(x2-s/2,y2+s);
            p.addPoint(x2+s/2,y2+s);
            g2.setColor(Color.WHITE);
            g2.fillPolygon(p);
            g2.setColor(DARK);
            g2.drawPolygon(p);
        }
        private void drawDiamond(Graphics2D g2,int x,int y, boolean filled){
            int s=8;
            Polygon p=new Polygon();
            p.addPoint(x,y);
            p.addPoint(x+s,y+s/2);
            p.addPoint(x,y+s);
            p.addPoint(x-s,y+s/2);
            if(filled){ g2.setColor(Color.WHITE); g2.fillPolygon(p); }
            g2.setColor(DARK); g2.drawPolygon(p);
        }
        private void drawOpenArrow(Graphics2D g2,int x,int y, boolean left){
            int s=8;
            Polygon p=new Polygon();
            if(left){
                p.addPoint(x,y);
                p.addPoint(x+s, y-s);
                p.addPoint(x+s, y+s);
            } else {
                // arrow pointing up (for vertical), here we use down case; generic upward
                p.addPoint(x,y);
                p.addPoint(x-s, y+s);
                p.addPoint(x+s, y+s);
            }
            g2.setColor(Color.WHITE); g2.fillPolygon(p);
            g2.setColor(new Color(100,116,139)); g2.drawPolygon(p);
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
