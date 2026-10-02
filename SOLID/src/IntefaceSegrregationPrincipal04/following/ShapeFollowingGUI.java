package IntefaceSegrregationPrincipal04.following;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * ISP — FOLLOWING (Fixed) • Friendly Shapes UI
 * Interface Segregation FOLLOWED: Shape2D and Shape3D are separate.
 * 2D shapes only have area(), 3D shapes have area() + Volume() — no forced methods.
 */
public class ShapeFollowingGUI extends JFrame {

    private final DefaultListModel<Shape2D> model2D = new DefaultListModel<>();
    private final DefaultListModel<Shape3D> model3D = new DefaultListModel<>();
    private final JList<Shape2D> list2D = new JList<>(model2D);
    private final JList<Shape3D> list3D = new JList<>(model3D);

    private final JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Circle (2D)", "Rectangle (2D)", "Cube3D (3D)"});
    private final JTextField param1Field = new JTextField(6);
    private final JTextField param2Field = new JTextField(6);
    private final JLabel param1Label = new JLabel("Radius");
    private final JLabel param2Label = new JLabel("Breadth");
    private final JTextArea logArea = new JTextArea(8, 40);
    private final JLabel totalLabel = new JLabel();
    private final JLabel statusLabel = new JLabel("Select a shape to get started  •  Area works for all, Volume only for 3D");
    private final JLabel stepLabel = new JLabel("Step 1 of 5 — Start with the 3 demo shapes");

    private static final Color BG_MAIN      = new Color(248, 250, 252);
    private static final Color CARD_BG      = Color.WHITE;
    private static final Color BORDER_SOFT  = new Color(226, 232, 240);
    private static final Color TEXT_DARK    = new Color(30, 41, 59);
    private static final Color TEXT_MUTED   = new Color(100, 116, 139);
    private static final Color PRIMARY      = new Color(79, 70, 229);
    private static final Color PRIMARY_SOFT = new Color(238, 242, 255);
    private static final Color TEAL         = new Color(6, 182, 212);
    private static final Color TEAL_SOFT    = new Color(236, 253, 245);
    private static final Color GREEN        = new Color(34, 197, 94);
    private static final Color GREEN_SOFT   = new Color(240, 253, 244);
    private static final Color AMBER        = new Color(245, 158, 11);
    private static final Color AMBER_SOFT   = new Color(255, 251, 235);
    private static final Color ROSE         = new Color(244, 63, 94);
    private static final Color VIOLET       = new Color(139, 92, 246);
    private static final Color VIOLET_SOFT  = new Color(245, 243, 255);
    private static final Color SLATE        = new Color(71, 85, 105);

    public ShapeFollowingGUI() {
        super("Shapes — ISP Following  •  Clean Segregation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 740);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_MAIN);
        setLayout(new BorderLayout(0, 0));

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY, getWidth(), 0, TEAL);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
            }
        };
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(18, 22, 18, 22));
        header.setPreferredSize(new Dimension(1000, 92));

        JPanel titleBox = new JPanel(new BorderLayout(2, 2));
        titleBox.setOpaque(false);
        JLabel title = new JLabel("Shapes  •  Clean segregation, no forced methods");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        JLabel subtitle = new JLabel("ISP FOLLOWED  •  Shape2D has area() only  •  Shape3D has area() + Volume()  ✓  No 2D forced to have Volume");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(new Color(255,255,255,230));
        titleBox.add(title, BorderLayout.NORTH);
        titleBox.add(subtitle, BorderLayout.CENTER);

        JPanel chipPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        chipPanel.setOpaque(false);
        JButton helpBtn = new JButton("❓ How to use — Start here!");
        helpBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        helpBtn.setForeground(new Color(30, 41, 59));
        helpBtn.setBackground(new Color(255, 235, 59));
        helpBtn.setOpaque(true);
        helpBtn.setFocusPainted(false);
        helpBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 193, 7), 2, true),
                new EmptyBorder(8, 16, 8, 16)
        ));
        helpBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        helpBtn.setToolTipText("Click for 5-step guide");
        helpBtn.addActionListener(e -> showGuide());
        JLabel chip = new JLabel("  ✓  ISP Following — Segregated  ");
        chip.setFont(new Font("SansSerif", Font.BOLD, 11));
        chip.setForeground(PRIMARY);
        chip.setBackground(Color.WHITE);
        chip.setOpaque(true);
        chip.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true),
                new EmptyBorder(7, 14, 7, 14)
        ));
        JLabel smallHint = new JLabel("Two focused interfaces — clients use only what they need");
        smallHint.setFont(new Font("SansSerif", Font.PLAIN, 11));
        smallHint.setForeground(new Color(255,255,255,200));
        JPanel chipWrap = new JPanel(new BorderLayout(0, 4));
        chipWrap.setOpaque(false);
        JPanel chipRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        chipRow.setOpaque(false);
        chipRow.add(helpBtn);
        chipRow.add(chip);
        chipWrap.add(chipRow, BorderLayout.NORTH);
        chipWrap.add(smallHint, BorderLayout.SOUTH);
        chipPanel.add(chipWrap);

        header.add(titleBox, BorderLayout.WEST);
        header.add(chipPanel, BorderLayout.EAST);

        JPanel headerWrap = new JPanel(new BorderLayout());
        headerWrap.setBackground(BG_MAIN);
        headerWrap.setBorder(new EmptyBorder(12, 12, 0, 12));
        headerWrap.add(header, BorderLayout.CENTER);

        // ===== STEP STRIP =====
        JPanel stepStrip = new JPanel(new BorderLayout(0, 4));
        stepStrip.setBackground(Color.WHITE);
        stepStrip.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_SOFT),
                new EmptyBorder(8, 14, 8, 14)
        ));
        JPanel stepsRow = new JPanel(new GridLayout(1, 5, 8, 0));
        stepsRow.setBackground(Color.WHITE);
        stepsRow.add(stepChip("1", "Create", "Add shape", PRIMARY, true));
        stepsRow.add(stepChip("2", "Select", "Pick from list", TEAL, false));
        stepsRow.add(stepChip("3", "Calculate", "Area / Volume", AMBER, false));
        stepsRow.add(stepChip("4", "Demo Safe", "See ISP OK", PRIMARY, false));
        stepsRow.add(stepChip("5", "View UML", "See diagram", VIOLET, false));
        stepLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        stepLabel.setForeground(SLATE);
        stepLabel.setHorizontalAlignment(SwingConstants.CENTER);
        stepLabel.setBorder(new EmptyBorder(4, 0, 0, 0));
        stepStrip.add(stepsRow, BorderLayout.CENTER);
        stepStrip.add(stepLabel, BorderLayout.SOUTH);

        // ===== CREATE CARD =====
        JPanel createCard = new JPanel(new BorderLayout(0, 6));
        createCard.setBackground(CARD_BG);
        createCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_SOFT, 1, true),
                new EmptyBorder(12, 14, 12, 14)
        ));
        JLabel createTitle = new JLabel("① Step 1 — Add a new shape");
        createTitle.setFont(new Font("SansSerif", Font.BOLD, 13));
        createTitle.setForeground(TEXT_DARK);
        JLabel createSub = new JLabel("Pick type → fill dimensions → Add. Demo already gives you 3 shapes (segregated by design).");
        createSub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        createSub.setForeground(TEXT_MUTED);
        JPanel createHead = new JPanel(new BorderLayout());
        createHead.setBackground(CARD_BG);
        createHead.add(createTitle, BorderLayout.NORTH);
        createHead.add(createSub, BorderLayout.SOUTH);

        JPanel createRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        createRow.setBackground(CARD_BG);
        JLabel typeLbl = label("Type", TEXT_DARK);
        typeCombo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        typeCombo.setBackground(Color.WHITE);
        typeCombo.setForeground(TEXT_DARK);
        styleField(param1Field, "5");
        styleField(param2Field, "4");
        param1Label.setFont(new Font("SansSerif", Font.BOLD, 12)); param1Label.setForeground(TEXT_DARK);
        param2Label.setFont(new Font("SansSerif", Font.BOLD, 12)); param2Label.setForeground(TEXT_DARK);
        JButton createBtn = softButton("Add shape", PRIMARY, Color.WHITE);
        JButton removeBtn = softButton("Remove selected", Color.WHITE, TEXT_DARK);
        removeBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_SOFT, 1, true),
                new EmptyBorder(8, 16, 8, 16)
        ));
        createRow.add(typeLbl); createRow.add(typeCombo);
        createRow.add(param1Label); createRow.add(param1Field);
        createRow.add(param2Label); createRow.add(param2Field);
        createRow.add(createBtn); createRow.add(removeBtn);
        JLabel typeHint = new JLabel("Circle → radius only   •   Rectangle → length & breadth   •   Cube3D → side only (goes to 3D list)");
        typeHint.setFont(new Font("SansSerif", Font.PLAIN, 11));
        typeHint.setForeground(TEXT_MUTED);
        typeHint.setBorder(new EmptyBorder(0, 4, 0, 0));

        createCard.add(createHead, BorderLayout.NORTH);
        createCard.add(createRow, BorderLayout.CENTER);
        createCard.add(typeHint, BorderLayout.SOUTH);

        JPanel topWrap = new JPanel(new BorderLayout(0, 0));
        topWrap.setBackground(BG_MAIN);
        topWrap.add(headerWrap, BorderLayout.NORTH);
        topWrap.add(stepStrip, BorderLayout.CENTER);
        topWrap.add(createCard, BorderLayout.SOUTH);
        JPanel topOuter = new JPanel(new BorderLayout(0, 12));
        topOuter.setBackground(BG_MAIN);
        topOuter.setBorder(new EmptyBorder(12, 12, 0, 12));
        topOuter.add(topWrap, BorderLayout.CENTER);
        topWrap = topOuter;

        // ===== LISTS =====
        list2D.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list3D.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list2D.setCellRenderer(new ShapeRenderer(true));
        list3D.setCellRenderer(new ShapeRenderer(false));
        styleList(list2D);
        styleList(list3D);
        JScrollPane pane2D = wrapList(list2D, "② 2D Shapes — Shape2D", "Circle & Rectangle — only area() here (Step 2: click to select)", TEAL, TEAL_SOFT);
        JScrollPane pane3D = wrapList(list3D, "② 3D Shapes — Shape3D", "Cube3D — area() + Volume() here (Step 2: click to select)", AMBER, AMBER_SOFT);

        list2D.addListSelectionListener(e -> { if(!e.getValueIsAdjusting() && list2D.getSelectedIndex()!=-1){ list3D.clearSelection(); updateStatus(); setStep(2); }});
        list3D.addListSelectionListener(e -> { if(!e.getValueIsAdjusting() && list3D.getSelectedIndex()!=-1){ list2D.clearSelection(); updateStatus(); setStep(2); }});

        JPanel listsPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        listsPanel.setBackground(BG_MAIN);
        listsPanel.setBorder(new EmptyBorder(12, 12, 0, 12));
        listsPanel.add(cardWrap(pane2D));
        listsPanel.add(cardWrap(pane3D));

        JPanel totalCard = new JPanel(new BorderLayout(0, 4));
        totalCard.setBackground(PRIMARY_SOFT);
        totalCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(199, 210, 254), 1, true),
                new EmptyBorder(10, 14, 10, 14)
        ));
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        totalLabel.setForeground(PRIMARY);
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        statusLabel.setForeground(SLATE);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalCard.add(totalLabel, BorderLayout.CENTER);
        totalCard.add(statusLabel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 12));
        centerPanel.setBackground(BG_MAIN);
        centerPanel.add(listsPanel, BorderLayout.CENTER);
        JPanel totalWrap = new JPanel(new BorderLayout());
        totalWrap.setBackground(BG_MAIN);
        totalWrap.setBorder(new EmptyBorder(0, 12, 0, 12));
        totalWrap.add(totalCard, BorderLayout.CENTER);
        centerPanel.add(totalWrap, BorderLayout.SOUTH);

        // ===== ACTIONS =====
        JPanel txnCard = new JPanel(new BorderLayout(0, 8));
        txnCard.setBackground(CARD_BG);
        txnCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_SOFT, 1, true),
                new EmptyBorder(12, 14, 12, 14)
        ));
        JLabel txnTitle = new JLabel("③ Step 3 — Calculate   •   ④ Demo Safe   •   ⑤ View UML");
        txnTitle.setFont(new Font("SansSerif", Font.BOLD, 13));
        txnTitle.setForeground(TEXT_DARK);
        JLabel txnSub = new JLabel("Step 3: select a shape → Area (works for all) / Volume (only 3D). Steps 4 & 5 are one-click demos.");
        txnSub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        txnSub.setForeground(TEXT_MUTED);
        JPanel txnHead = new JPanel(new BorderLayout());
        txnHead.setBackground(CARD_BG);
        txnHead.add(txnTitle, BorderLayout.NORTH);
        txnHead.add(txnSub, BorderLayout.SOUTH);

        JPanel txnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        txnRow.setBackground(CARD_BG);
        JButton areaBtn = softButton("Calculate Area", GREEN, Color.WHITE);
        JButton volBtn = softButton("Calculate Volume", TEAL, Color.WHITE);
        JButton detBtn = softButton("View details", Color.WHITE, TEXT_DARK);
        detBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), new EmptyBorder(8,16,8,16)));
        JButton demoBtn = softButton("See safe demo", PRIMARY, Color.WHITE);
        JButton umlBtn = softButton("View UML  ▭", VIOLET, Color.WHITE);
        JButton clearBtn = softButton("Clear all", Color.WHITE, ROSE);
        clearBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(254,205,211),1,true), new EmptyBorder(8,16,8,16)));

        txnRow.add(areaBtn); txnRow.add(volBtn); txnRow.add(detBtn); txnRow.add(demoBtn); txnRow.add(umlBtn); txnRow.add(clearBtn);
        txnCard.add(txnHead, BorderLayout.NORTH);
        txnCard.add(txnRow, BorderLayout.CENTER);

        logArea.setEditable(false);
        logArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
        logArea.setBackground(Color.WHITE);
        logArea.setForeground(TEXT_DARK);
        logArea.setBorder(new EmptyBorder(8, 8, 8, 8));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_SOFT, 1, true),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(BORDER_SOFT, 1, true), " Activity ", 0, 0, new Font("SansSerif", Font.BOLD, 11), TEXT_MUTED)
        ));
        logScroll.getViewport().setBackground(Color.WHITE);
        logScroll.setPreferredSize(new Dimension(1000, 150));

        JPanel logCard = new JPanel(new BorderLayout(0, 0));
        logCard.setBackground(CARD_BG);
        logCard.setBorder(BorderFactory.createLineBorder(BORDER_SOFT, 1, true));
        logCard.add(logScroll, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout(0, 12));
        southPanel.setBackground(BG_MAIN);
        southPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        southPanel.add(txnCard, BorderLayout.NORTH);
        southPanel.add(logCard, BorderLayout.CENTER);

        add(topWrap, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        // listeners
        typeCombo.addActionListener(e -> updateParamVisibility());
        createBtn.addActionListener(e -> { createShape(); setStep(1); });
        removeBtn.addActionListener(e -> removeSelected());
        areaBtn.addActionListener(e -> { doArea(); setStep(3); });
        volBtn.addActionListener(e -> { doVolume(); setStep(3); });
        detBtn.addActionListener(e -> { doDetails(); setStep(3); });
        demoBtn.addActionListener(e -> { demoSafe(); setStep(4); });
        umlBtn.addActionListener(e -> { showUmlDiagram(); setStep(5); });
        clearBtn.addActionListener(e -> clearAll());
        param1Field.addActionListener(e -> { createShape(); setStep(1); });
        param2Field.addActionListener(e -> { createShape(); setStep(1); });

        // demo data
        model2D.addElement(new Circle(5));
        model2D.addElement(new Rectangle(4, 6));
        model3D.addElement(new Cube3D(3));
        list2D.setSelectedIndex(0);
        updateParamVisibility(); updateTotal(); updateStatus();
        setStep(1);
        log("Welcome — ISP Following: Shape2D vs Shape3D segregated.");
        log("2D: Circle(r=5) • Rectangle(4×6)  —  3D: Cube3D(side=3)");
        log("");
        log("▶ HOW TO USE — Follow the steps at the top:");
        log("  ① Create: choose type + dimensions → Add shape");
        log("  ② Select: click any row (2D or 3D)");
        log("  ③ Calculate: Area works for all, Volume only for 3D (segregated ✓)");
        log("  ④ Demo Safe: click See safe demo — no exceptions, each type does only what it needs");
        log("  ⑤ View UML: click View UML to see two focused interfaces");
        log("  💡 Tip: Click ❓ How to use anytime");
    }

    private JLabel label(String t, Color c){ JLabel l=new JLabel(t); l.setFont(new Font("SansSerif", Font.BOLD, 12)); l.setForeground(c); return l; }
    private void styleField(JTextField tf, String def){
        tf.setText(def);
        tf.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tf.setBackground(Color.WHITE);
        tf.setForeground(TEXT_DARK);
        tf.setCaretColor(TEXT_DARK);
        tf.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), new EmptyBorder(6,10,6,10)));
        tf.setPreferredSize(new Dimension(90, 34));
    }
    private JButton softButton(String text, Color bg, Color fg){
        JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setBackground(bg); b.setForeground(fg);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(bg.darker(),1,true), new EmptyBorder(8,16,8,16)));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        b.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override public void mouseEntered(java.awt.event.MouseEvent e){ b.setBackground(bg.brighter()); }
            @Override public void mouseExited(java.awt.event.MouseEvent e){ b.setBackground(bg); }
        });
        return b;
    }
    private void styleList(JList<?> list){
        list.setBackground(Color.WHITE);
        list.setFont(new Font("SansSerif", Font.PLAIN, 13));
        list.setFixedCellHeight(42);
        list.setBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true));
    }
    private JScrollPane wrapList(JList<?> list, String title, String subtitle, Color accent, Color soft){
        JScrollPane sp = new JScrollPane(list);
        JPanel header = new JPanel(new BorderLayout(0,2));
        header.setBackground(soft);
        header.setBorder(new EmptyBorder(10,12,10,12));
        JLabel t = new JLabel(title);
        t.setFont(new Font("SansSerif", Font.BOLD, 13)); t.setForeground(TEXT_DARK);
        JLabel s = new JLabel(subtitle);
        s.setFont(new Font("SansSerif", Font.PLAIN, 11)); s.setForeground(TEXT_MUTED);
        header.add(t, BorderLayout.NORTH);
        header.add(s, BorderLayout.SOUTH);
        JPanel wrapper = new JPanel(new BorderLayout(0,0));
        wrapper.setBackground(Color.WHITE);
        wrapper.add(header, BorderLayout.NORTH);
        wrapper.add(sp, BorderLayout.CENTER);
        sp.setBorder(BorderFactory.createMatteBorder(1,0,0,0, BORDER_SOFT));
        JScrollPane outer = new JScrollPane(wrapper);
        outer.setBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true));
        outer.getViewport().setBackground(Color.WHITE);
        outer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        return outer;
    }
    private JPanel cardWrap(JComponent inner){
        JPanel p = new JPanel(new BorderLayout(0,0));
        p.setBackground(CARD_BG);
        p.setBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true));
        p.add(inner, BorderLayout.CENTER);
        return p;
    }
    private class ShapeRenderer extends DefaultListCellRenderer {
        private final boolean is2D;
        ShapeRenderer(boolean is2D){ this.is2D=is2D; }
        @Override public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
            JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            lbl.setBorder(new EmptyBorder(8,12,8,12));
            lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
            lbl.setOpaque(true);
            String txt="";
            Color accent = is2D ? TEAL : AMBER;
            Color soft = is2D ? TEAL_SOFT : AMBER_SOFT;
            if(value instanceof Circle){
                Circle c=(Circle)value;
                txt = String.format("○ Circle  —  r=%d   •  area ≈ %.2f   •  no Volume ✓", c.getRadius(), Math.PI*c.getRadius()*c.getRadius());
            } else if(value instanceof Rectangle){
                Rectangle r=(Rectangle)value;
                txt = String.format("▭ Rectangle  —  %d × %d   •  area=%d   •  no Volume ✓", r.getL(), r.getB(), r.getL()*r.getB());
            } else if(value instanceof Cube3D){
                Cube3D cu=(Cube3D)value;
                txt = String.format("⬡ Cube3D  —  side=%d   •  area=%d   •  vol=%d", cu.getSide(), 6*cu.getSide()*cu.getSide(), cu.getSide()*cu.getSide()*cu.getSide());
            }
            lbl.setText("  " + txt);
            if(isSelected){ lbl.setBackground(accent); lbl.setForeground(Color.WHITE); }
            else { lbl.setBackground(index%2==0 ? Color.WHITE : new Color(248,250,252)); lbl.setForeground(TEXT_DARK); }
            return lbl;
        }
    }
    private void updateParamVisibility(){
        String t=(String)typeCombo.getSelectedItem();
        if(t!=null && t.startsWith("Circle")){
            param1Label.setText("Radius");
            param1Field.setEnabled(true);
            param2Label.setVisible(false); param2Field.setVisible(false);
            param1Field.setText("5");
        } else if(t!=null && t.startsWith("Rectangle")){
            param1Label.setText("Length");
            param2Label.setText("Breadth");
            param1Label.setVisible(true); param1Field.setVisible(true); param1Field.setEnabled(true);
            param2Label.setVisible(true); param2Field.setVisible(true); param2Field.setEnabled(true);
            param1Field.setText("4"); param2Field.setText("6");
        } else {
            param1Label.setText("Side");
            param1Field.setEnabled(true);
            param2Label.setVisible(false); param2Field.setVisible(false);
            param1Field.setText("3");
        }
        revalidate(); repaint();
        updateStatus();
    }
    private void createShape(){
        String t=(String)typeCombo.getSelectedItem();
        try{
            if(t.startsWith("Circle")){
                int r=Integer.parseInt(param1Field.getText().trim());
                if(r<=0) throw new NumberFormatException();
                Circle c=new Circle(r);
                model2D.addElement(c);
                list2D.setSelectedValue(c,true); list3D.clearSelection();
                log("Added Circle(r=" + r + ") → 2D list (only area()).");
            } else if(t.startsWith("Rectangle")){
                int l=Integer.parseInt(param1Field.getText().trim());
                int b=Integer.parseInt(param2Field.getText().trim());
                if(l<=0||b<=0) throw new NumberFormatException();
                Rectangle rec=new Rectangle(l,b);
                model2D.addElement(rec);
                list2D.setSelectedValue(rec,true); list3D.clearSelection();
                log("Added Rectangle(" + l + "×" + b + ") → 2D list (only area()).");
            } else {
                int s=Integer.parseInt(param1Field.getText().trim());
                if(s<=0) throw new NumberFormatException();
                Cube3D cu=new Cube3D(s);
                model3D.addElement(cu);
                list3D.setSelectedValue(cu,true); list2D.clearSelection();
                log("Added Cube3D(side=" + s + ") → 3D list (area + Volume).");
            }
            updateTotal(); updateStatus();
        } catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this,"Please enter positive integer dimensions.","Check input",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void removeSelected(){
        if(list2D.getSelectedIndex()!=-1){
            Shape2D s=list2D.getSelectedValue(); model2D.removeElement(s);
            log("Removed " + s.getClass().getSimpleName() + ".");
        } else if(list3D.getSelectedIndex()!=-1){
            Shape3D s=list3D.getSelectedValue(); model3D.removeElement(s);
            log("Removed " + s.getClass().getSimpleName() + ".");
        } else { JOptionPane.showMessageDialog(this,"Please select a shape to remove.","No selection",JOptionPane.WARNING_MESSAGE); return; }
        updateTotal(); updateStatus();
    }
    private void clearAll(){
        if(model2D.isEmpty() && model3D.isEmpty()) return;
        int c=JOptionPane.showConfirmDialog(this,"Clear all shapes?","Confirm",JOptionPane.YES_NO_OPTION);
        if(c!=JOptionPane.YES_OPTION) return;
        model2D.clear(); model3D.clear();
        log("Cleared all shapes.");
        updateTotal(); updateStatus();
    }
    private void doArea(){
        if(list2D.getSelectedIndex()!=-1){
            Shape2D s=list2D.getSelectedValue();
            double a=s.area();
            String msg=String.format("Area of %s = %.2f  ✓ (via Shape2D)", s.getClass().getSimpleName(), a);
            log(msg);
            JOptionPane.showMessageDialog(this, "<html><h3>" + s.getClass().getSimpleName() + "</h3><p>Area: <b>" + String.format("%.2f",a) + "</b></p><p style='color:#64748b;'>Called via segregated Shape2D — only area()</p></html>", "Area", JOptionPane.INFORMATION_MESSAGE);
        } else if(list3D.getSelectedIndex()!=-1){
            Shape3D s=list3D.getSelectedValue();
            double a=s.area();
            String msg=String.format("Surface area of %s = %.2f  ✓ (via Shape3D)", s.getClass().getSimpleName(), a);
            log(msg);
            JOptionPane.showMessageDialog(this, "<html><h3>" + s.getClass().getSimpleName() + "</h3><p>Surface area: <b>" + String.format("%.2f",a) + "</b></p><p style='color:#64748b;'>Called via Shape3D — focused interface</p></html>", "Area", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,"Please select a shape first.","No selection",JOptionPane.WARNING_MESSAGE);
        }
        list2D.repaint(); list3D.repaint();
    }
    private void doVolume(){
        if(list3D.getSelectedIndex()!=-1){
            Shape3D s=list3D.getSelectedValue();
            double v=s.Volume();
            String msg=String.format("Volume of %s = %.2f  ✓ (only 3D has Volume)", s.getClass().getSimpleName(), v);
            log(msg);
            JOptionPane.showMessageDialog(this, "<html><h3>" + s.getClass().getSimpleName() + "</h3><p>Volume: <b>" + String.format("%.2f",v) + "</b></p><p style='color:#065f46;'>✓ Segregated — only Shape3D has Volume()</p></html>", "Volume", JOptionPane.INFORMATION_MESSAGE);
        } else if(list2D.getSelectedIndex()!=-1){
            Shape2D s=list2D.getSelectedValue();
            log("Heads up: " + s.getClass().getSimpleName() + " is 2D — Volume is not part of Shape2D by design (ISP).");
            JOptionPane.showMessageDialog(this,
                    "<html><h3 style='margin:0;'>" + s.getClass().getSimpleName() + " — 2D shape</h3><p>Volume is not available for 2D shapes — by design.</p><p style='color:#065f46;'><b>This is intentional (ISP Following):</b> 2D types aren't forced to have Volume().</p><p style='color:#64748b;'>Select a <b>Cube3D</b> in the 3D list to calculate Volume.</p></html>",
                    "No Volume for 2D", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,"Please select a shape first. Volume is only for 3D shapes.","No selection",JOptionPane.WARNING_MESSAGE);
        }
    }
    private void doDetails(){
        if(list2D.getSelectedIndex()!=-1){
            Shape2D s=list2D.getSelectedValue();
            double a=s.area();
            log(String.format("%s — 2D — area %.2f — only Shape2D methods", s.getClass().getSimpleName(), a));
            JOptionPane.showMessageDialog(this, "<html><h3>" + s.getClass().getSimpleName() + " (2D)</h3><p>Area: <b>" + String.format("%.2f",a) + "</b></p><p style='color:#64748b;'>Implements Shape2D only</p></html>", "Details", JOptionPane.INFORMATION_MESSAGE);
        } else if(list3D.getSelectedIndex()!=-1){
            Shape3D s=list3D.getSelectedValue();
            double a=s.area(); double v=s.Volume();
            log(String.format("%s — 3D — area %.2f, volume %.2f — via Shape3D", s.getClass().getSimpleName(), a, v));
            JOptionPane.showMessageDialog(this, "<html><h3>" + s.getClass().getSimpleName() + " (3D)</h3><p>Area: <b>" + String.format("%.2f",a) + "</b><br>Volume: <b>" + String.format("%.2f",v) + "</b></p><p style='color:#64748b;'>Implements Shape3D (area + Volume)</p></html>", "Details", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,"Please select a shape first.","No selection",JOptionPane.WARNING_MESSAGE);
        }
    }
    private void demoSafe(){
        log(""); log("Safe segregation demo — each type uses only its own interface:");
        for(int i=0;i<model2D.size();i++){
            Shape2D s=model2D.get(i);
            double a=s.area();
            log(String.format("  2D %s — area %.2f — OK (no Volume to call)", s.getClass().getSimpleName(), a));
        }
        for(int i=0;i<model3D.size();i++){
            Shape3D s=model3D.get(i);
            double a=s.area(); double v=s.Volume();
            log(String.format("  3D %s — area %.2f, volume %.2f — OK (has both)", s.getClass().getSimpleName(), a, v));
        }
        log("All shapes used only what they need — no exceptions. That's ISP Following.");
        log("");
        JOptionPane.showMessageDialog(this, "<html><h3 style='margin:0;'>All good — no forced methods</h3><p>2D shapes called <code>area()</code> only, 3D called <code>area() + Volume()</code>.<br>Each client uses a focused interface.</p></html>", "Safe demo", JOptionPane.INFORMATION_MESSAGE);
    }
    private void updateTotal(){
        int cnt2D=model2D.size(), cnt3D=model3D.size();
        totalLabel.setText(String.format("Total shapes: %d  •  %d × 2D + %d × 3D  —  segregated, no fat interface", cnt2D+cnt3D, cnt2D, cnt3D));
    }
    private void updateStatus(){
        if(list2D.getSelectedIndex()!=-1){
            Shape2D s=list2D.getSelectedValue();
            statusLabel.setText("Selected " + s.getClass().getSimpleName() + " (2D) — area() available, no Volume by design → Step 3 ready");
        } else if(list3D.getSelectedIndex()!=-1){
            Shape3D s=list3D.getSelectedValue();
            statusLabel.setText("Selected " + s.getClass().getSimpleName() + " (3D) — area() + Volume() available → Step 3 ready");
        } else {
            statusLabel.setText("Step 2 → Select a shape (2D or 3D list)  •  Area works for all, Volume only for 3D");
        }
    }
    private JPanel stepChip(String num, String title, String sub, Color color, boolean active) {
        JPanel p = new JPanel(new BorderLayout(0, 1));
        p.setBackground(active ? new Color(color.getRed(), color.getGreen(), color.getBlue(), 18) : Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(active ? color : BORDER_SOFT, active ? 2 : 1, true), new EmptyBorder(6, 8, 6, 8)));
        JLabel n = new JLabel(num);
        n.setFont(new Font("SansSerif", Font.BOLD, 11));
        n.setForeground(Color.WHITE); n.setBackground(color); n.setOpaque(true); n.setHorizontalAlignment(SwingConstants.CENTER); n.setBorder(new EmptyBorder(2, 6, 2, 6));
        JLabel t = new JLabel(title); t.setFont(new Font("SansSerif", Font.BOLD, 11)); t.setForeground(active ? color.darker() : TEXT_DARK);
        JLabel s = new JLabel(sub); s.setFont(new Font("SansSerif", Font.PLAIN, 10)); s.setForeground(TEXT_MUTED);
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0)); top.setOpaque(false); top.add(n); top.add(t);
        p.add(top, BorderLayout.NORTH); p.add(s, BorderLayout.SOUTH); return p;
    }
    private void setStep(int step) {
        String[] msgs = {
            "Step 1 of 5 — Add a shape: choose type + dimensions → Add shape",
            "Step 2 of 5 — Select a shape from either list (click any row)",
            "Step 3 of 5 — Calculate Area (all) / Volume (3D only) / View details",
            "Step 4 of 5 — Click See safe demo to see segregation succeed",
            "Step 5 of 5 — Click View UML to see two focused interfaces"
        };
        if (step >= 1 && step <= 5) stepLabel.setText(msgs[step - 1]);
    }
    private void showGuide() {
        JOptionPane.showMessageDialog(this,
                "<html><body style='width:560px; font-family:sans-serif;'>"
                + "<h2 style='color:#4f46e5; margin:0;'>How to use — 5 easy steps (Following • ISP OK)</h2>"
                + "<p style='color:#64748b; margin:4 0 12 0;'>This demo shows <b>segregated interfaces</b> — 2D and 3D are separate, no class is forced to implement what it doesn't need.</p>"
                + "<table style='width:100%; border-collapse:collapse; font-size:12px;'>"
                + "<tr><td style='padding:6 8; background:#eef2ff; border-radius:8px;'><b>① Create</b></td><td style='padding:6 8;'>Choose <b>Type</b> + dimensions → <b>Add shape</b>. 3 demo shapes are already there.</td></tr>"
                + "<tr><td style='padding:6 8; background:#ecfdf5; border-radius:8px;'><b>② Select</b></td><td style='padding:6 8;'>Click any row in the two lists. Selected row is highlighted.</td></tr>"
                + "<tr><td style='padding:6 8; background:#fef3c7; border-radius:8px;'><b>③ Calculate</b></td><td style='padding:6 8;'>• <b>Calculate Area</b> — works for <b>all</b> (both lists)<br>• <b>Calculate Volume</b> — works only for <b>3D</b>; selecting 2D shows friendly hint, not an error</td></tr>"
                + "<tr><td style='padding:6 8; background:#eef2ff; border-radius:8px;'><b>④ Demo Safe</b></td><td style='padding:6 8;'>Click <b>See safe demo</b> — loops both lists with correct methods — all succeed.</td></tr>"
                + "<tr><td style='padding:6 8; background:#f5f3ff; border-radius:8px;'><b>⑤ View UML</b></td><td style='padding:6 8;'>Click <b>View UML</b> — see <code>Shape2D</code> vs <code>Shape3D</code> — no fat interface.</td></tr>"
                + "</table>"
                + "<p style='background:#ecfdf5; padding:8; border-radius:8px; color:#065f46;'><b>💡 Try this:</b> Select <b>○ Circle</b> → <b>Calculate Volume</b> → see guidance (no exception). Then select <b>⬡ Cube3D</b> → <b>Calculate Volume</b> → succeeds.</p>"
                + "</body></html>",
                "How to use — Step by step", JOptionPane.INFORMATION_MESSAGE);
    }
    private void showUmlDiagram() {
        JDialog dlg = new JDialog(this, "UML — ISP Following (Segregated)", true);
        dlg.setSize(880, 620);
        dlg.setLocationRelativeTo(this);
        dlg.setLayout(new BorderLayout(0, 0));
        dlg.getContentPane().setBackground(BG_MAIN);
        JPanel head = new JPanel(new BorderLayout());
        head.setBackground(Color.WHITE);
        head.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_SOFT), new EmptyBorder(14, 18, 14, 18)));
        JLabel hTitle = new JLabel("Class Diagram — ISP Following  •  Segregated Interfaces");
        hTitle.setFont(new Font("SansSerif", Font.BOLD, 15)); hTitle.setForeground(TEXT_DARK);
        JLabel hSub = new JLabel("Shape2D has area() only  •  Shape3D has area() + Volume()  •  No 2D forced to implement Volume");
        hSub.setFont(new Font("SansSerif", Font.PLAIN, 11)); hSub.setForeground(TEXT_MUTED);
        JPanel hText = new JPanel(new BorderLayout(0, 2)); hText.setBackground(Color.WHITE); hText.add(hTitle, BorderLayout.NORTH); hText.add(hSub, BorderLayout.SOUTH);
        JLabel hBadge = new JLabel("  ✓  ISP OK  "); hBadge.setFont(new Font("SansSerif", Font.BOLD, 11)); hBadge.setForeground(VIOLET); hBadge.setBackground(VIOLET_SOFT); hBadge.setOpaque(true); hBadge.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(221, 214, 254), 1, true), new EmptyBorder(6, 12, 6, 12)));
        head.add(hText, BorderLayout.WEST); head.add(hBadge, BorderLayout.EAST);
        UmlPanel uml = new UmlPanel(); JScrollPane scroll = new JScrollPane(uml); scroll.setBorder(BorderFactory.createEmptyBorder()); scroll.getViewport().setBackground(BG_MAIN); uml.setPreferredSize(new Dimension(840, 480));
        JPanel legend = new JPanel(new BorderLayout(0, 6)); legend.setBackground(new Color(248, 250, 252)); legend.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_SOFT), new EmptyBorder(10, 18, 10, 18)));
        JLabel leg1 = new JLabel("▵  Hollow triangle = extends  •  Dashed = segregated (no fat interface)  •  <<abstract>> = cannot be instantiated");
        leg1.setFont(new Font("SansSerif", Font.PLAIN, 11)); leg1.setForeground(SLATE);
        JLabel leg2 = new JLabel("Why it follows ISP:  Clients needing 2D depend only on Shape2D; 3D clients depend on Shape3D. No client is forced to depend on methods it doesn't use.");
        leg2.setFont(new Font("SansSerif", Font.PLAIN, 11)); leg2.setForeground(TEXT_MUTED);
        legend.add(leg1, BorderLayout.NORTH); legend.add(leg2, BorderLayout.SOUTH);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10)); bottom.setBackground(Color.WHITE); bottom.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_SOFT));
        JButton close = softButton("Close", VIOLET, Color.WHITE); close.addActionListener(e -> dlg.dispose()); bottom.add(close);
        dlg.add(head, BorderLayout.NORTH); dlg.add(scroll, BorderLayout.CENTER);
        JPanel southWrap = new JPanel(new BorderLayout(0,0)); southWrap.add(legend, BorderLayout.NORTH); southWrap.add(bottom, BorderLayout.SOUTH); dlg.add(southWrap, BorderLayout.SOUTH);
        dlg.setVisible(true);
    }
    private class UmlPanel extends JPanel {
        UmlPanel() { setBackground(BG_MAIN); }
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            int W = getWidth();
            int boxW = 220, boxH = 92;
            int topY = 28, midY = 170, botY = 310;
            int cx = W/2;
            // Two roots at top
            Rect r2D = new Rect(cx - 170 - boxW/2, topY, boxW, boxH);
            Rect r3D = new Rect(cx + 170 - boxW/2, topY, boxW, boxH);
            // Children
            Rect rCircle = new Rect(cx - 170 - boxW/2, midY, boxW, boxH);
            Rect rRect = new Rect(cx - 170 - boxW/2, botY, boxW, boxH);
            Rect rCube = new Rect(cx + 170 - boxW/2, midY, boxW, 100);
            g2.setStroke(new BasicStroke(1.6f));
            g2.setColor(new Color(148, 163, 184));
            drawInheritance(g2, rCircle, r2D);
            drawInheritance(g2, rRect, r2D);
            drawInheritance(g2, rCube, r3D);
            g2.setFont(new Font("SansSerif", Font.ITALIC, 10)); g2.setColor(TEXT_MUTED);
            g2.drawString("extends", rCircle.centerX() + 12, rCircle.y - 10);
            g2.drawString("extends", rRect.centerX() + 12, rRect.y - 10);
            g2.drawString("extends", rCube.centerX() - 42, rCube.y - 10);
            // dashed separation line between 2D and 3D columns
            g2.setStroke(new BasicStroke(1.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{6,4}, 0));
            g2.setColor(new Color(199,210,254));
            g2.drawLine(cx, topY, cx, botY + boxH + 20);
            g2.setFont(new Font("SansSerif", Font.BOLD, 10)); g2.setColor(VIOLET);
            g2.drawString("segregated", cx - 32, topY + 12);
            g2.setStroke(new BasicStroke(1.2f));
            drawClassBox(g2, r2D, "<<abstract>>", "Shape2D", new String[]{"+ area(): double", "only 2D contract"}, PRIMARY, PRIMARY_SOFT, true, false);
            drawClassBox(g2, r3D, "<<abstract>>", "Shape3D", new String[]{"+ area(): double", "+ Volume(): double", "only 3D contract"}, AMBER, AMBER_SOFT, true, false);
            drawClassBox(g2, rCircle, "", "Circle", new String[]{"- radius: Integer", "+ area(): double ✓"}, TEAL, TEAL_SOFT, false, false);
            drawClassBox(g2, rRect, "", "Rectangle", new String[]{"- l,b: Integer", "+ area(): double ✓"}, TEAL, TEAL_SOFT, false, false);
            drawClassBox(g2, rCube, "", "Cube3D", new String[]{"- side: Integer", "+ area(): double ✓", "+ Volume(): double ✓"}, AMBER, AMBER_SOFT, false, false);
            // callout
            g2.setFont(new Font("SansSerif", Font.BOLD, 11)); g2.setColor(new Color(16,185,129));
            String callout = "✓  No fat interface — each type implements only what it needs";
            int cw = g2.getFontMetrics().stringWidth(callout) + 24; int ch = 28; int cx2 = W/2 - cw/2; int cy2 = botY + boxH + 34;
            g2.setColor(new Color(236,253,245)); g2.fillRoundRect(cx2, cy2, cw, ch, 14,14);
            g2.setColor(new Color(16,185,129)); g2.drawRoundRect(cx2, cy2, cw, ch,14,14); g2.drawString(callout, cx2+12, cy2+18);
        }
        private void drawClassBox(Graphics2D g2, Rect r, String stereo, String name, String[] members, Color accent, Color soft, boolean isAbstract, boolean violation){
            g2.setColor(new Color(0,0,0,10)); g2.fillRoundRect(r.x+3,r.y+3,r.w,r.h,14,14);
            g2.setColor(Color.WHITE); g2.fillRoundRect(r.x,r.y,r.w,r.h,14,14);
            g2.setColor(soft); g2.fillRoundRect(r.x,r.y,r.w,28,14,14); g2.fillRect(r.x,r.y+14,r.w,14);
            g2.setColor(accent); g2.fillRoundRect(r.x,r.y,r.w,3,3,3);
            g2.setColor(BORDER_SOFT); g2.setStroke(new BasicStroke(1.2f)); g2.drawRoundRect(r.x,r.y,r.w,r.h,14,14);
            if(!stereo.isEmpty()){
                g2.setFont(new Font("SansSerif", Font.ITALIC, 10)); g2.setColor(TEXT_MUTED);
                int sw=g2.getFontMetrics().stringWidth(stereo); g2.drawString(stereo, r.x+(r.w-sw)/2, r.y+14);
            }
            g2.setFont(new Font("SansSerif", Font.BOLD, 12)); g2.setColor(TEXT_DARK);
            if(isAbstract) g2.setFont(new Font("SansSerif", Font.BOLD|Font.ITALIC, 12));
            int nw=g2.getFontMetrics().stringWidth(name); g2.drawString(name, r.x+(r.w-nw)/2, r.y+(stereo.isEmpty()?19:26));
            g2.setColor(BORDER_SOFT); g2.drawLine(r.x+12,r.y+32,r.x+r.w-12,r.y+32);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10)); g2.setColor(SLATE);
            int my=r.y+46; for(String m: members){ g2.drawString(m, r.x+12, my); my+=13; }
            if(isAbstract){
                String badge="abstract"; g2.setFont(new Font("SansSerif", Font.BOLD, 9));
                int bw=g2.getFontMetrics().stringWidth(badge)+10; int bx=r.x+r.w-bw-8; int by=r.y+8;
                g2.setColor(VIOLET_SOFT); g2.fillRoundRect(bx,by,bw,14,7,7); g2.setColor(VIOLET); g2.drawRoundRect(bx,by,bw,14,7,7); g2.drawString(badge,bx+5,by+10);
            }
        }
        private void drawInheritance(Graphics2D g2, Rect child, Rect parent){
            int x1=child.centerX(), y1=child.y, x2=parent.centerX(), y2=parent.y+parent.h;
            g2.setColor(new Color(100,116,139));
            if(Math.abs(x1-x2)<6){ g2.drawLine(x1,y1,x2,y2); drawTriangle(g2,x2,y2,true); }
            else { int midY=(y1+y2)/2; g2.drawLine(x1,y1,x1,midY); g2.drawLine(x1,midY,x2,midY); g2.drawLine(x2,midY,x2,y2); drawTriangle(g2,x2,y2,true); }
        }
        private void drawTriangle(Graphics2D g2,int x,int y,boolean up){
            Polygon tri=new Polygon(); int s=9;
            tri.addPoint(x,y); tri.addPoint(x-s,y+s+3); tri.addPoint(x+s,y+s+3);
            g2.setColor(Color.WHITE); g2.fillPolygon(tri); g2.setColor(new Color(100,116,139)); g2.setStroke(new BasicStroke(1.4f)); g2.drawPolygon(tri);
        }
        private class Rect{ int x,y,w,h; Rect(int x,int y,int w,int h){this.x=x;this.y=y;this.w=w;this.h=h;} int centerX(){return x+w/2;} }
    }
    private void log(String msg){ logArea.append(msg+"\n"); logArea.setCaretPosition(logArea.getDocument().getLength()); System.out.println(msg); }
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }catch(Exception ignored){}
            new ShapeFollowingGUI().setVisible(true);
        });
    }
}
