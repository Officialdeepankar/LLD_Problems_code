package IntefaceSegrregationPrincipal04.Notfollowing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * ISP — NOT FOLLOWING (Violation) • Shapes UI that breaks ISP.
 * Single fat interface Shape with calculateArea() + Volume() forces 2D shapes to throw.
 */
public class ShapeViolationGUI extends JFrame {

    private final DefaultListModel<Shape> listModel = new DefaultListModel<>();
    private final JList<Shape> shapeList = new JList<>(listModel);

    private final JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Circle (2D)", "Rectangle (2D)", "Cube3D (3D)"});
    private final JTextField param1Field = new JTextField(6);
    private final JTextField param2Field = new JTextField(6);
    private final JLabel param1Label = new JLabel("Radius");
    private final JLabel param2Label = new JLabel("Breadth");
    private final JTextArea logArea = new JTextArea(8, 30);
    private final JLabel totalBannerLabel = new JLabel("Total Shapes: 0  •  List<Shape> polymorphic — fat interface");
    private final JLabel infoLabel = new JLabel("Step 2 → Select a shape from the list");
    private final JLabel stepLabel = new JLabel("Step 1 of 5 — Start by creating or using the 3 demo shapes");

    private static final Color BG_MAIN = new Color(248, 250, 252);
    private static final Color PURPLE = new Color(51, 65, 85);
    private static final Color PINK = new Color(148, 163, 184);
    private static final Color GREEN = new Color(71, 85, 105);
    private static final Color BLUE = new Color(71, 85, 105);
    private static final Color ORANGE = new Color(100, 116, 139);
    private static final Color RED = new Color(100, 116, 139);
    private static final Color VIOLET = new Color(51, 65, 85);
    private static final Color VIOLET_SOFT = new Color(241, 245, 249);
    private static final Color DARK = new Color(30, 41, 59);
    private static final Color TEAL = new Color(71, 85, 105);
    private static final Color BORDER_SOFT = new Color(226, 232, 240);
    private static final Color VIOLATION = new Color(220, 38, 38);
    private static final Color VIOLATION_SOFT = new Color(254, 242, 242);

    public ShapeViolationGUI() {
        super("Shapes — ISP Violation  |  Fat Interface Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 780);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_MAIN);
        setLayout(new BorderLayout(0, 0));

        // ===== Header =====
        JPanel header = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(30, 41, 59), getWidth(), 0, new Color(71, 85, 105));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setLayout(new BorderLayout());
        header.setPreferredSize(new Dimension(1000, 72));
        header.setBorder(new EmptyBorder(12, 20, 12, 20));
        JLabel title = new JLabel("Shapes  —  ISP Violation Demo (Fat Interface)");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        JLabel subtitle = new JLabel("Shape has calculateArea() + Volume()  •  Circle/Rectangle forced to implement Volume() and throw");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 11));
        subtitle.setForeground(new Color(226, 232, 240));
        JPanel titleBox = new JPanel(new BorderLayout());
        titleBox.setOpaque(false);
        titleBox.add(title, BorderLayout.NORTH);
        titleBox.add(subtitle, BorderLayout.SOUTH);
        JLabel badge = new JLabel("  ISP — Violation Example  ");
        badge.setFont(new Font("SansSerif", Font.BOLD, 11));
        badge.setForeground(new Color(51, 65, 85));
        badge.setBackground(new Color(241, 245, 249));
        badge.setOpaque(true);
        badge.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true), new EmptyBorder(6, 12, 6, 12)));
        JButton helpBtn = new JButton("❓ How to use — Start here!");
        helpBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        helpBtn.setForeground(new Color(30, 41, 59));
        helpBtn.setBackground(new Color(255, 235, 59));
        helpBtn.setFocusPainted(false);
        helpBtn.setOpaque(true);
        helpBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(255, 193, 7), 2, true), new EmptyBorder(8, 16, 8, 16)));
        helpBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        helpBtn.setToolTipText("Click for 5-step guide");
        helpBtn.addActionListener(e -> showGuide());
        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        headerRight.setOpaque(false);
        headerRight.add(helpBtn);
        headerRight.add(badge);
        header.add(titleBox, BorderLayout.WEST);
        header.add(headerRight, BorderLayout.EAST);

        // ===== STEP STRIP =====
        JPanel stepStrip = new JPanel(new BorderLayout(0, 4));
        stepStrip.setBackground(Color.WHITE);
        stepStrip.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_SOFT), new EmptyBorder(8, 14, 8, 14)));
        JPanel stepsRow = new JPanel(new GridLayout(1, 5, 8, 0));
        stepsRow.setBackground(Color.WHITE);
        stepsRow.add(stepChip("1", "Create", "Add shape", PURPLE, true));
        stepsRow.add(stepChip("2", "Select", "Pick from list", BLUE, false));
        stepsRow.add(stepChip("3", "Calculate", "Area / Volume", ORANGE, false));
        stepsRow.add(stepChip("4", "Demo Break", "See violation", RED, false));
        stepsRow.add(stepChip("5", "View UML", "See diagram", VIOLET, false));
        stepLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        stepLabel.setForeground(new Color(99, 110, 114));
        stepLabel.setHorizontalAlignment(SwingConstants.CENTER);
        stepLabel.setBorder(new EmptyBorder(4, 0, 0, 0));
        stepStrip.add(stepsRow, BorderLayout.CENTER);
        stepStrip.add(stepLabel, BorderLayout.SOUTH);

        // ===== Input Panel — Step 1 =====
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 12));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_SOFT), new EmptyBorder(5, 10, 5, 10)));
        JLabel step1Lbl = new JLabel("① Step 1 — Create:");
        step1Lbl.setFont(new Font("SansSerif", Font.BOLD, 11)); step1Lbl.setForeground(new Color(71,85,105));
        JLabel typeLbl = new JLabel("Type:"); typeLbl.setFont(new Font("SansSerif", Font.BOLD, 13)); typeLbl.setForeground(DARK);
        typeCombo.setFont(new Font("SansSerif", Font.PLAIN, 13)); typeCombo.setBackground(new Color(248,249,250));
        styleTextField(param1Field); styleTextField(param2Field);
        param1Label.setFont(new Font("SansSerif", Font.BOLD, 12)); param1Label.setForeground(DARK);
        param2Label.setFont(new Font("SansSerif", Font.BOLD, 12)); param2Label.setForeground(DARK);
        param1Field.setText("5"); param2Field.setText("4");
        JButton createBtn = createColorButton("Create Shape", new Color(51,65,85), Color.WHITE);
        createBtn.setToolTipText("Step 1: Creates shape of chosen type");
        JButton removeBtn = createColorButton("Remove Selected", Color.WHITE, new Color(51,65,85));
        removeBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), new EmptyBorder(8,14,8,14)));
        inputPanel.add(step1Lbl); inputPanel.add(typeLbl); inputPanel.add(typeCombo);
        inputPanel.add(param1Label); inputPanel.add(param1Field);
        inputPanel.add(param2Label); inputPanel.add(param2Field);
        inputPanel.add(createBtn); inputPanel.add(removeBtn);

        JPanel topWrapper = new JPanel(new BorderLayout(0, 0));
        topWrapper.setBackground(BG_MAIN);
        topWrapper.add(header, BorderLayout.NORTH);
        topWrapper.add(stepStrip, BorderLayout.CENTER);
        topWrapper.add(inputPanel, BorderLayout.SOUTH);

        // ===== List — Step 2 =====
        shapeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        shapeList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        shapeList.setFixedCellHeight(34);
        shapeList.setCellRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                lbl.setBorder(new EmptyBorder(4, 12, 4, 12));
                lbl.setOpaque(true);
                if (value instanceof Shape) {
                    Shape s = (Shape) value;
                    String type = s.getClass().getSimpleName();
                    String icon = "⬡"; Color c = PURPLE; String tag="";
                    if("Circle".equals(type)){ icon="○"; c=BLUE; tag="  —  Volume() throws ❌"; }
                    else if("Rectangle".equals(type)){ icon="▭"; c=TEAL; tag="  —  Volume() throws ❌"; }
                    else if("Cube3D".equals(type)){ icon="⬡"; c=ORANGE; tag="  —  Volume() ok ✓"; }
                    double area=0; try{ area=s.calculateArea(); }catch(Exception ignored){}
                    lbl.setText(String.format("  %d.  %s  %-12s  area≈%-10s %s", index+1, icon, type, String.format("%.1f",area), tag));
                    if(!isSelected) lbl.setForeground(c.darker());
                }
                if (isSelected) { lbl.setBackground(PURPLE); lbl.setForeground(Color.WHITE); }
                else { lbl.setBackground(index%2==0 ? Color.WHITE : new Color(245,243,255)); }
                lbl.setFont(new Font("SansSerif", isSelected? Font.BOLD: Font.PLAIN, 13));
                return lbl;
            }
        });
        JScrollPane listScroll = new JScrollPane(shapeList);
        listScroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), " ② Step 2 — Select a shape  (List<Shape> — fat interface, click any row)", 0,0, new Font("SansSerif", Font.BOLD, 12), new Color(71,85,105)));
        listScroll.getViewport().setBackground(Color.WHITE);

        JPanel totalBanner = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2=(Graphics2D)g; g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(241,245,249)); g2.fillRoundRect(0,0,getWidth(),getHeight(),12,12);
                g2.setColor(BORDER_SOFT); g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,12,12);
            }
        };
        totalBanner.setOpaque(false); totalBanner.setBorder(new EmptyBorder(10,16,10,16));
        totalBannerLabel.setFont(new Font("SansSerif", Font.BOLD, 14)); totalBannerLabel.setForeground(new Color(30,41,59)); totalBannerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalBanner.add(totalBannerLabel, BorderLayout.CENTER);
        infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 11)); infoLabel.setForeground(new Color(100,116,139)); infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel totalWrap = new JPanel(new BorderLayout(0,6)); totalWrap.setOpaque(false); totalWrap.setBorder(new EmptyBorder(8,0,0,0));
        totalWrap.add(totalBanner, BorderLayout.CENTER); totalWrap.add(infoLabel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0,8));
        centerPanel.setBackground(BG_MAIN); centerPanel.setBorder(new EmptyBorder(10,14,10,14));
        centerPanel.add(listScroll, BorderLayout.CENTER); centerPanel.add(totalWrap, BorderLayout.SOUTH);

        // ===== Bottom — Actions — Step 3/4/5 =====
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        actionPanel.setBackground(Color.WHITE);
        actionPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), new EmptyBorder(6,6,6,6)));
        JPanel actionWrap = new JPanel(new BorderLayout());
        actionWrap.setBackground(BG_MAIN);
        JLabel actionTitle = new JLabel("  ③ Step 3 — Try Calculate    •    ④ Demo Break    •    ⑤ View UML");
        actionTitle.setFont(new Font("SansSerif", Font.BOLD, 11)); actionTitle.setForeground(new Color(100,116,139)); actionTitle.setBorder(new EmptyBorder(0,4,4,4));
        actionWrap.add(actionTitle, BorderLayout.NORTH); actionWrap.add(actionPanel, BorderLayout.CENTER);

        styleTextField(param1Field); styleTextField(param2Field);
        JButton areaBtn = createColorButton("Calculate Area", Color.WHITE, new Color(30,41,59));
        areaBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), new EmptyBorder(8,14,8,14)));
        areaBtn.setToolTipText("Step 3: calculateArea() via Shape — always works");
        JButton volBtn = createColorButton("Calculate Volume", Color.WHITE, new Color(30,41,59));
        volBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), new EmptyBorder(8,14,8,14)));
        volBtn.setToolTipText("Step 3: Volume() via Shape — throws for Circle/Rectangle (ISP violation)");
        JButton detBtn = createColorButton("View details", Color.WHITE, new Color(30,41,59));
        detBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), new EmptyBorder(8,14,8,14)));
        JButton lspBtn = createColorButton("Demo ISP Break", new Color(51,65,85), Color.WHITE);
        lspBtn.setToolTipText("Step 4: Loops List<Shape> calling Volume() — 2D throws");
        JButton umlBtn = createColorButton("View UML", Color.WHITE, new Color(51,65,85));
        umlBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), new EmptyBorder(8,14,8,14)));
        umlBtn.setToolTipText("Step 5: Show fat interface UML");
        JButton clearBtn = createColorButton("Clear All", Color.WHITE, new Color(100,116,139));
        clearBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), new EmptyBorder(8,14,8,14)));

        actionPanel.add(areaBtn); actionPanel.add(volBtn); actionPanel.add(detBtn); actionPanel.add(lspBtn); actionPanel.add(umlBtn); actionPanel.add(clearBtn);

        logArea.setEditable(false);
        logArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
        logArea.setBackground(Color.WHITE);
        logArea.setForeground(new Color(30,41,59));
        logArea.setCaretColor(new Color(30,41,59));
        logArea.setBorder(new EmptyBorder(8,8,8,8));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), " Activity log ", 0,0, new Font("SansSerif", Font.BOLD, 11), new Color(100,116,139)));
        logScroll.setPreferredSize(new Dimension(1000, 150));

        JPanel southPanel = new JPanel(new BorderLayout(0,8));
        southPanel.setBackground(BG_MAIN); southPanel.setBorder(new EmptyBorder(0,14,14,14));
        southPanel.add(actionWrap, BorderLayout.NORTH); southPanel.add(logScroll, BorderLayout.CENTER);

        add(topWrapper, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        // Listeners
        typeCombo.addActionListener(e -> updateParamVisibility());
        createBtn.addActionListener(e -> { createShape(); setStep(1); });
        removeBtn.addActionListener(e -> removeSelected());
        areaBtn.addActionListener(e -> { doArea(); setStep(3); });
        volBtn.addActionListener(e -> { doVolume(); setStep(3); });
        detBtn.addActionListener(e -> { doDetails(); setStep(3); });
        lspBtn.addActionListener(e -> { demoBreak(); setStep(4); });
        umlBtn.addActionListener(e -> { showUmlDiagram(); setStep(5); });
        clearBtn.addActionListener(e -> clearAll());
        param1Field.addActionListener(e -> createShape());
        param2Field.addActionListener(e -> createShape());
        shapeList.addListSelectionListener(e -> { if(!e.getValueIsAdjusting()){ updateInfo(); setStep(2); } });

        // demo data
        listModel.addElement(new Circle(5));
        listModel.addElement(new Rectangle(4,6));
        listModel.addElement(new Cube3D(3));
        shapeList.setSelectedIndex(0);
        updateParamVisibility(); updateTotal(); updateInfo();
        log("✨ GUI loaded — 3 demo shapes via fat Shape interface: Circle(r=5), Rectangle(4×6), Cube3D(side=3)");
        log("");
        log("▶ HOW TO USE — Follow the steps at the top:");
        log("  ① Create: choose type + dimensions → Create Shape");
        log("  ② Select: click any row in the list");
        log("  ③ Calculate: Area always works, Volume throws for Circle/Rectangle (violation!)");
        log("  ④ Demo Break: click Demo ISP Break — loops List<Shape> calling Volume() — 2D fails");
        log("  ⑤ View UML: click View UML to see fat interface");
        log("  💡 Tip: Click ❓ How to use anytime");
        setStep(1);
    }

    private JPanel stepChip(String num, String title, String sub, Color color, boolean active){
        JPanel p=new JPanel(new BorderLayout(0,1));
        p.setBackground(active? new Color(color.getRed(), color.getGreen(), color.getBlue(),18): Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(active? color: BORDER_SOFT, active?2:1,true), new EmptyBorder(6,8,6,8)));
        JLabel n=new JLabel(num); n.setFont(new Font("SansSerif",Font.BOLD,11)); n.setForeground(Color.WHITE); n.setBackground(color); n.setOpaque(true); n.setHorizontalAlignment(SwingConstants.CENTER); n.setBorder(new EmptyBorder(2,6,2,6));
        JLabel t=new JLabel(title); t.setFont(new Font("SansSerif",Font.BOLD,11)); t.setForeground(active? color.darker(): DARK);
        JLabel s=new JLabel(sub); s.setFont(new Font("SansSerif",Font.PLAIN,10)); s.setForeground(new Color(100,116,139));
        JPanel top=new JPanel(new FlowLayout(FlowLayout.LEFT,6,0)); top.setOpaque(false); top.add(n); top.add(t);
        p.add(top, BorderLayout.NORTH); p.add(s, BorderLayout.SOUTH); return p;
    }
    private void setStep(int step){
        String[] msgs={
            "Step 1 of 5 — Start by creating or using the 3 demo shapes (fat interface)",
            "Step 2 of 5 — Select a shape from the list (click any row)",
            "Step 3 of 5 — Calculate Area (always) / Volume (throws for 2D) / View details",
            "Step 4 of 5 — Click Demo ISP Break to see the violation in a loop",
            "Step 5 of 5 — Click View UML to see why fat interface breaks ISP"
        };
        if(step>=1 && step<=5) stepLabel.setText(msgs[step-1]);
    }
    private void showGuide(){
        JOptionPane.showMessageDialog(this,
                "<html><body style='width:520px; font-family:sans-serif;'>"
                + "<h2 style='color:#334155; margin:0;'>How to use — 5 easy steps (Violation • ISP Broken)</h2>"
                + "<p style='color:#64748b; margin:4 0 12 0;'>This demo shows <b>ISP violation</b> — fat interface forces 2D shapes to implement Volume().</p>"
                + "<table style='width:100%; border-collapse:collapse; font-size:12px;'>"
                + "<tr><td style='padding:6 8; background:#fef2f2; border-radius:8px;'><b>① Create</b></td><td style='padding:6 8;'>Choose <b>Type</b> + dimensions → <b>Create Shape</b>. Demo gives 3 shapes already.</td></tr>"
                + "<tr><td style='padding:6 8; background:#f1f5f9; border-radius:8px;'><b>② Select</b></td><td style='padding:6 8;'>Click any row in the list. Selected turns dark.</td></tr>"
                + "<tr><td style='padding:6 8; background:#ffedd5; border-radius:8px;'><b>③ Calculate</b></td><td style='padding:6 8;'>• <b>Calculate Area</b> — always works<br>• <b>Calculate Volume</b> — <b>throws for Circle/Rectangle</b> ✓ shows violation</td></tr>"
                + "<tr><td style='padding:6 8; background:#fee2e2; border-radius:8px;'><b>④ Demo Break</b></td><td style='padding:6 8;'>Click <b>Demo ISP Break</b> — loops <code>List&lt;Shape&gt;</code> calling <code>Volume()</code>. Cube succeeds, 2D throws.</td></tr>"
                + "<tr><td style='padding:6 8; background:#f5f3ff; border-radius:8px;'><b>⑤ View UML</b></td><td style='padding:6 8;'>Click <b>View UML</b> — see fat Shape with red violation.</td></tr>"
                + "</table>"
                + "<p style='background:#fefce8; padding:8; border-radius:8px; color:#854d0e;'><b>💡 Try first:</b> Select <b>○ Circle</b> → <b>Calculate Volume</b> → watch error & log. Then select <b>⬡ Cube3D</b> → Volume → succeeds.</p>"
                + "</body></html>",
                "How to use — Step by step", JOptionPane.INFORMATION_MESSAGE);
    }
    private void showUmlDiagram(){
        JDialog dlg=new JDialog(this,"UML — ISP Not Following (Fat Interface)",true);
        dlg.setSize(880,620); dlg.setLocationRelativeTo(this); dlg.setLayout(new BorderLayout(0,0)); dlg.getContentPane().setBackground(BG_MAIN);
        JPanel head=new JPanel(new BorderLayout());
        head.setBackground(Color.WHITE);
        head.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,1,0,BORDER_SOFT), new EmptyBorder(14,18,14,18)));
        JLabel hTitle=new JLabel("Class Diagram — ISP Violation  •  Fat Interface Forces Unneeded Methods");
        hTitle.setFont(new Font("SansSerif",Font.BOLD,14)); hTitle.setForeground(DARK);
        JLabel hSub=new JLabel("Shape defines calculateArea() + Volume()  •  Circle/Rectangle forced to implement Volume() and throw");
        hSub.setFont(new Font("SansSerif",Font.PLAIN,11)); hSub.setForeground(new Color(100,116,139));
        JPanel hText=new JPanel(new BorderLayout(0,2)); hText.setBackground(Color.WHITE); hText.add(hTitle, BorderLayout.NORTH); hText.add(hSub, BorderLayout.SOUTH);
        JLabel hBadge=new JLabel("  ✗  ISP Broken  "); hBadge.setFont(new Font("SansSerif",Font.BOLD,11)); hBadge.setForeground(VIOLATION); hBadge.setBackground(VIOLATION_SOFT); hBadge.setOpaque(true); hBadge.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(254,205,211),1,true), new EmptyBorder(6,12,6,12)));
        head.add(hText, BorderLayout.WEST); head.add(hBadge, BorderLayout.EAST);
        UmlPanel uml=new UmlPanel(); JScrollPane scroll=new JScrollPane(uml); scroll.setBorder(BorderFactory.createEmptyBorder()); scroll.getViewport().setBackground(BG_MAIN); uml.setPreferredSize(new Dimension(840,480));
        JPanel legend=new JPanel(new BorderLayout(0,6)); legend.setBackground(new Color(248,250,252)); legend.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1,0,0,0,BORDER_SOFT), new EmptyBorder(10,18,10,18)));
        JLabel leg1=new JLabel("▵  Hollow triangle = extends   •   Red dashed = violation   •   <<abstract>> = Shape"); leg1.setFont(new Font("SansSerif",Font.PLAIN,11)); leg1.setForeground(new Color(71,85,105));
        JLabel leg2=new JLabel("Why it breaks ISP:  Client depending only on area is forced to depend on Volume(). 2D shapes throw — interface not segregated. Fix: split into Shape2D / Shape3D (see Following case).");
        leg2.setFont(new Font("SansSerif",Font.PLAIN,11)); leg2.setForeground(new Color(100,116,139)); legend.add(leg1, BorderLayout.NORTH); legend.add(leg2, BorderLayout.SOUTH);
        JPanel bottom=new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10)); bottom.setBackground(Color.WHITE); bottom.setBorder(BorderFactory.createMatteBorder(1,0,0,0,BORDER_SOFT));
        JButton close=createColorButton("Close", VIOLET, Color.WHITE); close.addActionListener(e->dlg.dispose()); bottom.add(close);
        dlg.add(head, BorderLayout.NORTH); dlg.add(scroll, BorderLayout.CENTER);
        JPanel southWrap=new JPanel(new BorderLayout(0,0)); southWrap.add(legend, BorderLayout.NORTH); southWrap.add(bottom, BorderLayout.SOUTH); dlg.add(southWrap, BorderLayout.SOUTH);
        dlg.setVisible(true);
    }
    private class UmlPanel extends JPanel{
        UmlPanel(){ setBackground(BG_MAIN); }
        @Override protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2=(Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            int W=getWidth();
            int boxW=230, boxH=100;
            int cx=W/2; int topY=30, botY=180;
            Rect rRoot=new Rect(cx-boxW/2, topY, boxW, boxH);
            Rect rCir=new Rect(cx-180-boxW/2 - 50, botY, 210, 92);
            Rect rRec=new Rect(cx-boxW/2, botY, 210, 92);
            Rect rCube=new Rect(cx+180+boxW/2 - 160, botY, 210, 92);
            g2.setStroke(new BasicStroke(1.6f));
            drawInheritance(g2, rCir, rRoot, true);
            drawInheritance(g2, rRec, rRoot, true);
            drawInheritance(g2, rCube, rRoot, false);
            g2.setFont(new Font("SansSerif",Font.ITALIC,10));
            g2.setColor(VIOLATION); g2.drawString("extends ✗ forces Volume", rCir.centerX()-38, rCir.y-10);
            g2.drawString("extends ✗ forces", rRec.centerX()-30, rRec.y-10);
            g2.setColor(new Color(100,116,139)); g2.drawString("extends", rCube.centerX()-18, rCube.y-10);
            drawClassBox(g2, rRoot, "<<abstract>>", "Shape (fat)", new String[]{"+ calculateArea(): double", "+ Volume(): double ★ fat", "forces 2D to have Volume"}, PURPLE, new Color(241,245,249), true, true);
            drawClassBox(g2, rCir, "", "Circle", new String[]{"- radius: double", "+ calculateArea() ✓", "+ Volume() ✗ throws!"}, VIOLATION, VIOLATION_SOFT, false, true);
            drawClassBox(g2, rRec, "", "Rectangle", new String[]{"- l,b: Integer", "+ calculateArea() ✓", "+ Volume() ✗ throws!"}, VIOLATION, VIOLATION_SOFT, false, true);
            drawClassBox(g2, rCube, "", "Cube3D", new String[]{"- side: Integer", "+ calculateArea() ✓", "+ Volume() ✓"}, new Color(71,85,105), new Color(241,245,249), false, false);
            g2.setFont(new Font("SansSerif",Font.BOLD,11));
            String callout="✗  Fat interface — 2D forced to implement Volume()";
            int cw=g2.getFontMetrics().stringWidth(callout)+24; int ch=28; int cx2=W/2-cw/2; int cy2=botY+92+28;
            g2.setColor(VIOLATION_SOFT); g2.fillRoundRect(cx2,cy2,cw,ch,14,14); g2.setColor(VIOLATION); g2.drawRoundRect(cx2,cy2,cw,ch,14,14); g2.drawString(callout, cx2+12, cy2+18);
            // note
            String n1="Client: List<Shape> list = [Circle, Rectangle, Cube3D]";
            String n2="for(Shape s: list) s.Volume(); → Circle/Rectangle throw!";
            int nx=W-340, ny=botY+92+72;
            g2.setColor(Color.WHITE); g2.fillRoundRect(nx-12, ny-16, 330, 52,10,10); g2.setColor(BORDER_SOFT); g2.drawRoundRect(nx-12, ny-16, 330,52,10,10);
            g2.setColor(DARK); g2.setFont(new Font("SansSerif",Font.BOLD,10)); g2.drawString(n1, nx, ny);
            g2.setFont(new Font("SansSerif",Font.PLAIN,10)); g2.setColor(VIOLATION.darker()); g2.drawString(n2, nx, ny+14);
            g2.setColor(new Color(71,85,105)); g2.drawString("→ Should segregate into Shape2D / Shape3D", nx, ny+28);
            g2.setFont(new Font("SansSerif",Font.ITALIC,10)); g2.setColor(new Color(100,116,139));
            g2.drawString("Compare with Following: Shape2D vs Shape3D — no forced methods.", 18, getHeight()-10);
        }
        private void drawClassBox(Graphics2D g2, Rect r, String stereo, String name, String[] members, Color accent, Color soft, boolean isAbstract, boolean isViolation){
            g2.setColor(new Color(0,0,0,10)); g2.fillRoundRect(r.x+3,r.y+3,r.w,r.h,14,14);
            g2.setColor(Color.WHITE); g2.fillRoundRect(r.x,r.y,r.w,r.h,14,14);
            g2.setColor(soft); g2.fillRoundRect(r.x,r.y,r.w,28,14,14); g2.fillRect(r.x,r.y+14,r.w,14);
            g2.setColor(accent); g2.fillRoundRect(r.x,r.y,r.w,3,3,3);
            if(isViolation){
                g2.setColor(VIOLATION); g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{6,4},0));
                g2.drawRoundRect(r.x,r.y,r.w,r.h,14,14); g2.setStroke(new BasicStroke(1.2f));
            } else { g2.setColor(BORDER_SOFT); g2.setStroke(new BasicStroke(1.2f)); g2.drawRoundRect(r.x,r.y,r.w,r.h,14,14); }
            if(!stereo.isEmpty()){
                g2.setFont(new Font("SansSerif",Font.ITALIC,10)); g2.setColor(new Color(100,116,139));
                int sw=g2.getFontMetrics().stringWidth(stereo); g2.drawString(stereo, r.x+(r.w-sw)/2, r.y+14);
            }
            g2.setFont(new Font("SansSerif",Font.BOLD,12)); g2.setColor(DARK);
            if(isAbstract) g2.setFont(new Font("SansSerif",Font.BOLD|Font.ITALIC,12));
            int nw=g2.getFontMetrics().stringWidth(name); g2.drawString(name, r.x+(r.w-nw)/2, r.y+(stereo.isEmpty()?19:26));
            g2.setColor(BORDER_SOFT); g2.drawLine(r.x+12,r.y+32,r.x+r.w-12,r.y+32);
            g2.setFont(new Font("SansSerif",Font.PLAIN,10)); g2.setColor(new Color(71,85,105));
            int my=r.y+46; for(String m: members){
                if(m.contains("✗")) g2.setColor(VIOLATION.darker()); else g2.setColor(new Color(71,85,105));
                g2.drawString(m, r.x+12, my); my+=13;
            }
            String badge = isViolation ? (name.contains("fat")? "fat interface" : "violates ISP") : "ok";
            if(isViolation){
                g2.setFont(new Font("SansSerif",Font.BOLD,9)); int bw=g2.getFontMetrics().stringWidth(badge)+10;
                int bx=r.x+r.w-bw-8, by=r.y+8; g2.setColor(VIOLATION_SOFT); g2.fillRoundRect(bx,by,bw,14,7,7);
                g2.setColor(VIOLATION); g2.drawRoundRect(bx,by,bw,14,7,7); g2.drawString(badge,bx+5,by+10);
            }
        }
        private void drawInheritance(Graphics2D g2, Rect child, Rect parent, boolean violation){
            int x1=child.centerX(), y1=child.y, x2=parent.centerX(), y2=parent.y+parent.h;
            if(violation){ g2.setColor(VIOLATION); g2.setStroke(new BasicStroke(1.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{6,4},0)); }
            else { g2.setColor(new Color(100,116,139)); g2.setStroke(new BasicStroke(1.6f)); }
            int midY=(y1+y2)/2;
            if(Math.abs(x1-x2)<6){ g2.drawLine(x1,y1,x2,y2); drawTriangle(g2,x2,y2,true,violation); }
            else { g2.drawLine(x1,y1,x1,midY); g2.drawLine(x1,midY,x2,midY); g2.drawLine(x2,midY,x2,y2); drawTriangle(g2,x2,y2,true,violation); }
            g2.setStroke(new BasicStroke(1.2f));
        }
        private void drawTriangle(Graphics2D g2,int x,int y,boolean up,boolean violation){
            Polygon tri=new Polygon(); int s=9;
            tri.addPoint(x,y); tri.addPoint(x-s,y+s+3); tri.addPoint(x+s,y+s+3);
            g2.setColor(Color.WHITE); g2.fillPolygon(tri); g2.setColor(violation? VIOLATION: new Color(100,116,139)); g2.setStroke(new BasicStroke(1.4f)); g2.drawPolygon(tri);
        }
        private class Rect{ int x,y,w,h; Rect(int x,int y,int w,int h){this.x=x;this.y=y;this.w=w;this.h=h;} int centerX(){return x+w/2;} }
    }
    private void styleTextField(JTextField tf){
        tf.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tf.setBackground(Color.WHITE);
        tf.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), new EmptyBorder(6,8,6,8)));
    }
    private JButton createColorButton(String text, Color bg, Color fg){
        JButton btn=new JButton(text);
        btn.setFont(new Font("SansSerif",Font.BOLD,12)); btn.setBackground(bg); btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(bg.darker(),1,true), new EmptyBorder(8,14,8,14)));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); btn.setOpaque(true);
        btn.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override public void mouseEntered(java.awt.event.MouseEvent e){ btn.setBackground(bg.brighter()); }
            @Override public void mouseExited(java.awt.event.MouseEvent e){ btn.setBackground(bg); }
        });
        return btn;
    }
    private void updateParamVisibility(){
        String t=(String)typeCombo.getSelectedItem();
        if(t!=null && t.startsWith("Circle")){
            param1Label.setText("Radius"); param1Field.setEnabled(true);
            param2Label.setVisible(false); param2Field.setVisible(false);
            param1Field.setText("5");
        } else if(t!=null && t.startsWith("Rectangle")){
            param1Label.setText("Length"); param2Label.setText("Breadth");
            param1Label.setVisible(true); param1Field.setVisible(true); param1Field.setEnabled(true);
            param2Label.setVisible(true); param2Field.setVisible(true); param2Field.setEnabled(true);
            param1Field.setText("4"); param2Field.setText("6");
        } else {
            param1Label.setText("Side"); param1Field.setEnabled(true);
            param2Label.setVisible(false); param2Field.setVisible(false);
            param1Field.setText("3");
        }
        revalidate(); repaint(); updateInfo();
    }
    private void createShape(){
        String t=(String)typeCombo.getSelectedItem();
        try{
            if(t.startsWith("Circle")){
                int r=Integer.parseInt(param1Field.getText().trim()); if(r<=0) throw new NumberFormatException();
                Circle c=new Circle(r); listModel.addElement(c); shapeList.setSelectedValue(c,true);
                log("Added Circle(r=" + r + ") — forced to have Volume() that throws ❌");
            } else if(t.startsWith("Rectangle")){
                int l=Integer.parseInt(param1Field.getText().trim()); int b=Integer.parseInt(param2Field.getText().trim()); if(l<=0||b<=0) throw new NumberFormatException();
                Rectangle rec=new Rectangle(l,b); listModel.addElement(rec); shapeList.setSelectedValue(rec,true);
                log("Added Rectangle(" + l + "×" + b + ") — forced to have Volume() that throws ❌");
            } else {
                int s=Integer.parseInt(param1Field.getText().trim()); if(s<=0) throw new NumberFormatException();
                Cube3D cu=new Cube3D(s); listModel.addElement(cu); shapeList.setSelectedValue(cu,true);
                log("Added Cube3D(side=" + s + ") — Volume() actually works ✓");
            }
            updateTotal(); updateInfo();
        } catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this,"Please enter positive integer dimensions.","Check input",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void removeSelected(){
        Shape sel=shapeList.getSelectedValue();
        if(sel==null){ JOptionPane.showMessageDialog(this,"Select a shape to remove","No selection",JOptionPane.WARNING_MESSAGE); return; }
        listModel.removeElement(sel); updateTotal(); log("Removed: " + sel.getClass().getSimpleName());
    }
    private void clearAll(){
        if(listModel.isEmpty()) return;
        int c=JOptionPane.showConfirmDialog(this,"Clear all shapes?","Confirm",JOptionPane.YES_NO_OPTION);
        if(c!=JOptionPane.YES_OPTION) return;
        listModel.clear(); updateTotal(); log("Cleared all shapes.");
    }
    private void doArea(){
        Shape s=shapeList.getSelectedValue();
        if(s==null){ JOptionPane.showMessageDialog(this,"Select a shape first!","No selection",JOptionPane.WARNING_MESSAGE); return; }
        try{
            double a=s.calculateArea();
            log(String.format("calculateArea() of %s = %.2f — OK (but via fat interface)", s.getClass().getSimpleName(), a));
            shapeList.repaint(); updateTotal();
            JOptionPane.showMessageDialog(this, "<html><h3>" + s.getClass().getSimpleName() + "</h3><p>Area: <b>" + String.format("%.2f",a) + "</b></p><p style='color:#64748b;'>Via fat Shape — works for all, but client forced to see Volume() too</p></html>", "Area", JOptionPane.INFORMATION_MESSAGE);
        } catch(Exception ex){
            log("Area failed: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void doVolume(){
        Shape s=shapeList.getSelectedValue();
        if(s==null){ JOptionPane.showMessageDialog(this,"Select a shape first!","No selection",JOptionPane.WARNING_MESSAGE); return; }
        try{
            double v=s.Volume();
            log(String.format("Volume() of %s = %.2f — success (only Cube3D should have this)", s.getClass().getSimpleName(), v));
            shapeList.repaint(); updateTotal();
            JOptionPane.showMessageDialog(this, "<html><h3>" + s.getClass().getSimpleName() + "</h3><p>Volume: <b>" + String.format("%.2f",v) + "</b></p></html>", "Volume", JOptionPane.INFORMATION_MESSAGE);
        } catch(IllegalArgumentException ex){
            String err="Volume FAILED for " + s.getClass().getSimpleName() + ": " + ex.getMessage();
            log("❌ " + err);
            log("   💥 ISP VIOLATION! 2D shape forced to implement Volume() — fat interface");
            JOptionPane.showMessageDialog(this, "<html><h3 style='color:#dc2626;'>Volume Failed</h3><p><b>" + s.getClass().getSimpleName() + "</b>: " + ex.getMessage() + "</p><p style='color:#e17055;'><b>ISP Broken:</b> 2D shouldn't have Volume() at all!</p><p style='color:#64748b;'>This is the violation — see Step 5 UML for why.</p></html>", "Volume Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void doDetails(){
        Shape s=shapeList.getSelectedValue();
        if(s==null){ JOptionPane.showMessageDialog(this,"Select a shape first!","No selection",JOptionPane.WARNING_MESSAGE); return; }
        try{
            double a=s.calculateArea();
            String volStr; try{ volStr=String.format("%.2f", s.Volume()); } catch(Exception ex){ volStr="throws: " + ex.getMessage(); }
            log(s.getClass().getSimpleName() + " — area " + String.format("%.2f",a) + ", Volume: " + volStr);
            JOptionPane.showMessageDialog(this, "<html><h3>" + s.getClass().getSimpleName() + "</h3><p>Area: <b>" + String.format("%.2f",a) + "</b><br>Volume: <b>" + volStr + "</b></p><p style='color:gray;'>Fat Shape interface</p></html>", "Details", JOptionPane.INFORMATION_MESSAGE);
        } catch(Exception ex){ log("Details error: " + ex.getMessage()); }
    }
    private void demoBreak(){
        if(listModel.isEmpty()){ JOptionPane.showMessageDialog(this,"No shapes!","Demo",JOptionPane.WARNING_MESSAGE); return; }
        log("\n========== 💥 ISP BREAK DEMO (Step 4) ==========");
        log("Looping List<Shape> — calling Volume() via fat interface...");
        for(int i=0;i<listModel.size();i++){
            Shape s=listModel.get(i);
            log(" → " + s.getClass().getSimpleName() + ".Volume() …");
            try{ double v=s.Volume(); log("   ✅ Success → " + String.format("%.2f",v)); }
            catch(Exception ex){ log("   ❌ FAILED: " + ex.getMessage() + "  ← ISP VIOLATED for " + s.getClass().getSimpleName() + "!"); }
        }
        log("========== End — 2D shapes cannot handle Volume() ==========\n");
        shapeList.repaint(); updateTotal(); updateInfo();
        JOptionPane.showMessageDialog(this,
                "<html><h3 style='color:#334155;'>ISP Demo Complete — Step 4 done</h3>"
                + "<p>Check log: <b>Cube3D succeeded</b>, <b>Circle/Rectangle failed</b>.</p>"
                + "<p><b>ISP:</b> no client should be forced to depend on methods it does not use.</p>"
                + "<p><code>Shape</code> fat interface forces 2D to have <code>Volume()</code>.</p>"
                + "<p style='color:#7c3aed;'>Next → Step 5: Click <b>View UML</b> to see fat interface.</p></html>",
                "ISP Break Demo", JOptionPane.WARNING_MESSAGE);
    }
    private void updateTotal(){
        totalBannerLabel.setText(String.format("Total Shapes: %d   •   List<Shape> fat interface — Volume() forced on all", listModel.size()));
    }
    private void updateInfo(){
        Shape sel=shapeList.getSelectedValue();
        if(sel==null){ infoLabel.setText("Step 2 → Select a shape (click any row)  •  Then go to Step 3"); return; }
        String extra=(sel instanceof Cube3D) ? "  ✓ Cube3D — Volume() ok  → Step 3 ready" : "  ⚠️ 2D shape — Volume() will throw ❌ (ISP violation) → Try Step 3 Volume to see it";
        infoLabel.setText("Selected: " + sel.getClass().getSimpleName() + extra);
    }
    private void log(String msg){ logArea.append(msg+"\n"); logArea.setCaretPosition(logArea.getDocument().getLength()); System.out.println(msg); }
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }catch(Exception ignored){}
            new ShapeViolationGUI().setVisible(true);
        });
    }
}
