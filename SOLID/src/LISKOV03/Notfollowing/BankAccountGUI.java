package LISKOV03.Notfollowing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Rebuilt Colorful GUI for LISKOV03.Notfollowing — Bank Account LSP Violation.
 * Now with: Step-by-step guide + UML diagram button + easy-to-use instructions.
 * Shows FixedDepositAccount breaks LSP: inherits withdrawl() but throws.
 */
public class BankAccountGUI extends JFrame {

    private final DefaultListModel<Account> listModel = new DefaultListModel<>();
    private final JList<Account> accountList = new JList<>(listModel);

    private final JComboBox<String> typeCombo = new JComboBox<>(new String[]{"SavingsAccount", "CurrentAccount", "FixedDepositAccount"});
    private final JTextField balanceField = new JTextField(7);
    private final JTextField amountField = new JTextField(7);
    private final JLabel infoLabel = new JLabel("Step 2 → Select an account from the list below");
    private final JTextArea logArea = new JTextArea(8, 30);
    private final JLabel totalBannerLabel = new JLabel("Total Balance: ₹0  •  0 account(s)");
    private final JLabel stepLabel = new JLabel("Step 1 of 5 — Start by creating or using the 3 demo accounts");

    // Palette — muted, low-distraction (was too colorful)
    private static final Color BG_MAIN = new Color(248, 250, 252);
    private static final Color PURPLE = new Color(51, 65, 85);       // slate-700 — primary
    private static final Color PINK = new Color(148, 163, 184);      // slate-400 — muted accent
    private static final Color GREEN = new Color(71, 85, 105);       // slate-600
    private static final Color BLUE = new Color(71, 85, 105);
    private static final Color ORANGE = new Color(100, 116, 139);    // slate-500
    private static final Color RED = new Color(100, 116, 139);
    private static final Color VIOLET = new Color(51, 65, 85);
    private static final Color VIOLET_SOFT = new Color(241, 245, 249);
    private static final Color DARK = new Color(30, 41, 59);
    private static final Color TEAL = new Color(71, 85, 105);
    private static final Color YELLOW = new Color(248, 250, 252);    // log bg — light
    private static final Color BORDER_SOFT = new Color(226, 232, 240);
    private static final Color VIOLATION = new Color(220, 38, 38);      // kept vivid only for UML/logic highlight
    private static final Color VIOLATION_SOFT = new Color(254, 242, 242);

    public BankAccountGUI() {
        super("🏦 Bank Accounts - Not Following LSP  |  Colorful Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 780);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_MAIN);
        setLayout(new BorderLayout(0, 0));

        // ===== Header — subtle, muted (was purple→pink gradient, too distracting) =====
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // muted slate gradient — low saturation
                GradientPaint gp = new GradientPaint(0, 0, new Color(30, 41, 59), getWidth(), 0, new Color(71, 85, 105));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setLayout(new BorderLayout());
        header.setPreferredSize(new Dimension(900, 72));
        header.setBorder(new EmptyBorder(12, 20, 12, 20));
        JLabel title = new JLabel("🏦  Bank Accounts  —  LSP Violation Demo");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        JLabel subtitle = new JLabel("Account.withdrawl() works for Savings/Current  •  FixedDeposit throws — child narrows parent");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 11));
        subtitle.setForeground(new Color(226, 232, 240));
        JPanel titleBox = new JPanel(new BorderLayout());
        titleBox.setOpaque(false);
        titleBox.add(title, BorderLayout.NORTH);
        titleBox.add(subtitle, BorderLayout.SOUTH);
        JLabel badge = new JLabel("  LSP — Violation Example  ");
        badge.setFont(new Font("SansSerif", Font.BOLD, 11));
        badge.setForeground(new Color(51, 65, 85));
        badge.setBackground(new Color(241, 245, 249));
        badge.setOpaque(true);
        badge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true),
                new EmptyBorder(6, 12, 6, 12)));
        // Help button in header — HIGHLIGHTED (was invisible on muted header)
        JButton helpBtn = new JButton("❓ How to use — Start here!");
        helpBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        helpBtn.setForeground(new Color(30, 41, 59));
        helpBtn.setBackground(new Color(255, 235, 59)); // bright amber — pops on dark slate
        helpBtn.setFocusPainted(false);
        helpBtn.setOpaque(true);
        helpBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 193, 7), 2, true),
                new EmptyBorder(8, 16, 8, 16)
        ));
        helpBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        helpBtn.setToolTipText("Click for 5-step guide");
        helpBtn.addActionListener(e -> showGuide());
        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        headerRight.setOpaque(false);
        headerRight.add(helpBtn);
        headerRight.add(badge);
        header.add(titleBox, BorderLayout.WEST);
        header.add(headerRight, BorderLayout.EAST);

        // ===== STEP-BY-STEP STRIP (easy to use) =====
        JPanel stepStrip = new JPanel(new BorderLayout(0, 4));
        stepStrip.setBackground(Color.WHITE);
        stepStrip.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_SOFT),
                new EmptyBorder(8, 14, 8, 14)
        ));
        // Row of 5 steps
        JPanel stepsRow = new JPanel(new GridLayout(1, 5, 8, 0));
        stepsRow.setBackground(Color.WHITE);
        stepsRow.add(stepChip("1", "Create", "Add account", PURPLE, true));
        stepsRow.add(stepChip("2", "Select", "Pick from list", BLUE, false));
        stepsRow.add(stepChip("3", "Transact", "Deposit / Withdraw", ORANGE, false));
        stepsRow.add(stepChip("4", "Demo Break", "See LSP violation", RED, false));
        stepsRow.add(stepChip("5", "View UML", "See diagram", VIOLET, false));
        stepLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        stepLabel.setForeground(new Color(99, 110, 114));
        stepLabel.setHorizontalAlignment(SwingConstants.CENTER);
        stepLabel.setBorder(new EmptyBorder(4, 0, 0, 0));
        stepStrip.add(stepsRow, BorderLayout.CENTER);
        stepStrip.add(stepLabel, BorderLayout.SOUTH);

        // ===== Input Panel — Create Account — Step 1 — muted border =====
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 12));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_SOFT),
                new EmptyBorder(5, 10, 5, 10)));
        JLabel step1Lbl = new JLabel("① Step 1 — Create:");
        step1Lbl.setFont(new Font("SansSerif", Font.BOLD, 11));
        step1Lbl.setForeground(new Color(71, 85, 105));
        JLabel typeLbl = new JLabel("🏦 Type:");
        typeLbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        typeLbl.setForeground(DARK);
        JLabel balLbl = new JLabel("💰 Initial ₹:");
        balLbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        balLbl.setForeground(DARK);
        typeCombo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        typeCombo.setBackground(new Color(248, 249, 250));
        styleTextField(balanceField);
        balanceField.setText("1000");
        balanceField.setToolTipText("e.g. 2000 — Step 1: set balance then click Create");
        JButton createBtn = createColorButton("Create Account", new Color(51, 65, 85), Color.WHITE);
        createBtn.setToolTipText("Step 1: Creates a new account of chosen type");
        JButton removeBtn = createColorButton("Remove Selected", Color.WHITE, new Color(51, 65, 85));
        removeBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_SOFT, 1, true),
                new EmptyBorder(8, 14, 8, 14)
        ));
        removeBtn.setToolTipText("Removes the selected account from the list");
        inputPanel.add(step1Lbl);
        inputPanel.add(typeLbl);
        inputPanel.add(typeCombo);
        inputPanel.add(balLbl);
        inputPanel.add(balanceField);
        inputPanel.add(createBtn);
        inputPanel.add(removeBtn);

        JPanel topWrapper = new JPanel(new BorderLayout(0, 0));
        topWrapper.setBackground(BG_MAIN);
        topWrapper.add(header, BorderLayout.NORTH);
        topWrapper.add(stepStrip, BorderLayout.CENTER);
        topWrapper.add(inputPanel, BorderLayout.SOUTH);

        // ===== Center - Account List — Step 2 =====
        accountList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        accountList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        accountList.setFixedCellHeight(34);
        accountList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                lbl.setBorder(new EmptyBorder(4, 12, 4, 12));
                lbl.setOpaque(true);
                if (value instanceof Account) {
                    Account acc = (Account) value;
                    String type = acc.getClass().getSimpleName();
                    String icon = "🏦";
                    Color c = PURPLE;
                    String tag = "";
                    if ("SavingsAccount".equals(type)) { icon = "🐖"; c = BLUE; tag = "  ✓ withdraw ok"; }
                    else if ("CurrentAccount".equals(type)) { icon = "💼"; c = TEAL; tag = "  ✓ withdraw ok"; }
                    else if ("FixedDepositAccount".equals(type)) { icon = "🔒"; c = ORANGE; tag = "  ❌ withdraw throws — LSP broken!"; }
                    lbl.setText(String.format("  %d.  %s  %-20s  —  ₹%-6d %s", index + 1, icon, type, acc.getBalance(), tag));
                    if (!isSelected) lbl.setForeground(c.darker());
                }
                if (isSelected) {
                    lbl.setBackground(PURPLE);
                    lbl.setForeground(Color.WHITE);
                } else {
                    lbl.setBackground(index % 2 == 0 ? Color.WHITE : new Color(245, 243, 255));
                    if (value == null) lbl.setForeground(DARK);
                }
                lbl.setFont(new Font("SansSerif", isSelected ? Font.BOLD : Font.PLAIN, 13));
                return lbl;
            }
        });
        JScrollPane listScroll = new JScrollPane(accountList);
        listScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_SOFT, 1, true),
                " ② Step 2 — Select an account  (List<Account> — click any row)", 0, 0,
                new Font("SansSerif", Font.BOLD, 12), new Color(71, 85, 105)));
        listScroll.getViewport().setBackground(Color.WHITE);

        // ===== Total Banner — muted solid (was bright gradient, distracting) =====
        JPanel totalBanner = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(241, 245, 249));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(BORDER_SOFT);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
            }
        };
        totalBanner.setOpaque(false);
        totalBanner.setBorder(new EmptyBorder(10, 16, 10, 16));
        totalBannerLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        totalBannerLabel.setForeground(new Color(30, 41, 59));
        totalBannerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalBanner.add(totalBannerLabel, BorderLayout.CENTER);
        infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        infoLabel.setForeground(new Color(100, 116, 139));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel totalWrap = new JPanel(new BorderLayout(0, 6));
        totalWrap.setOpaque(false);
        totalWrap.setBorder(new EmptyBorder(8, 0, 0, 0));
        totalWrap.add(totalBanner, BorderLayout.CENTER);
        totalWrap.add(infoLabel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 8));
        centerPanel.setBackground(BG_MAIN);
        centerPanel.setBorder(new EmptyBorder(10, 14, 10, 14));
        centerPanel.add(listScroll, BorderLayout.CENTER);
        centerPanel.add(totalWrap, BorderLayout.SOUTH);

        // ===== Bottom — Transactions & Log — Step 3/4/5 — muted =====
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        actionPanel.setBackground(Color.WHITE);
        actionPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_SOFT, 1, true),
                new EmptyBorder(6, 6, 6, 6)));
        JPanel actionWrap = new JPanel(new BorderLayout());
        actionWrap.setBackground(BG_MAIN);
        JLabel actionTitle = new JLabel("  ③ Step 3 — Try transactions    •    ④ Demo Break    •    ⑤ View UML");
        actionTitle.setFont(new Font("SansSerif", Font.BOLD, 11));
        actionTitle.setForeground(new Color(100, 116, 139));
        actionTitle.setBorder(new EmptyBorder(0, 4, 4, 4));
        actionWrap.add(actionTitle, BorderLayout.NORTH);
        actionWrap.add(actionPanel, BorderLayout.CENTER);

        JLabel amtLbl = new JLabel("₹ Amount:");
        amtLbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        amtLbl.setForeground(DARK);
        styleTextField(amountField);
        amountField.setText("200");
        amountField.setToolTipText("Step 3: Enter amount for Deposit / Withdrawl");

        JButton depositBtn = createColorButton("Deposit", Color.WHITE, new Color(30, 41, 59));
        depositBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), new EmptyBorder(8,14,8,14)));
        depositBtn.setToolTipText("Step 3: Deposit to selected account — always works");
        JButton withdrawBtn = createColorButton("Withdraw", Color.WHITE, new Color(30, 41, 59));
        withdrawBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), new EmptyBorder(8,14,8,14)));
        withdrawBtn.setToolTipText("Step 3: Withdraw from selected — fails for FixedDeposit (LSP violation)");
        JButton detailsBtn = createColorButton("Get Details", Color.WHITE, new Color(30, 41, 59));
        detailsBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), new EmptyBorder(8,14,8,14)));
        detailsBtn.setToolTipText("Step 3: Show balance & type of selected account");
        JButton lspBtn = createColorButton("Demo LSP Break", new Color(51, 65, 85), Color.WHITE);
        lspBtn.setToolTipText("Step 4: Loops List<Account> and calls withdrawl(100) — FixedDeposit throws");
        JButton umlBtn = createColorButton("View UML", Color.WHITE, new Color(51, 65, 85));
        umlBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), new EmptyBorder(8,14,8,14)));
        umlBtn.setToolTipText("Step 5: Show UML diagram — why FixedDeposit breaks LSP");
        JButton clearBtn = createColorButton("Clear All", Color.WHITE, new Color(100, 116, 139));
        clearBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), new EmptyBorder(8,14,8,14)));
        clearBtn.setToolTipText("Clears all accounts");

        actionPanel.add(amtLbl);
        actionPanel.add(amountField);
        actionPanel.add(depositBtn);
        actionPanel.add(withdrawBtn);
        actionPanel.add(detailsBtn);
        actionPanel.add(lspBtn);
        actionPanel.add(umlBtn);
        actionPanel.add(clearBtn);

        // Log area — light, low-distraction (was dark terminal, too high contrast)
        logArea.setEditable(false);
        logArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
        logArea.setBackground(Color.WHITE);
        logArea.setForeground(new Color(30, 41, 59));
        logArea.setCaretColor(new Color(30, 41, 59));
        logArea.setBorder(new EmptyBorder(8, 8, 8, 8));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_SOFT, 1, true),
                " Activity log ", 0, 0,
                new Font("SansSerif", Font.BOLD, 11), new Color(100, 116, 139)));
        logScroll.setPreferredSize(new Dimension(900, 150));

        JPanel southPanel = new JPanel(new BorderLayout(0, 8));
        southPanel.setBackground(BG_MAIN);
        southPanel.setBorder(new EmptyBorder(0, 14, 14, 14));
        southPanel.add(actionWrap, BorderLayout.NORTH);
        southPanel.add(logScroll, BorderLayout.CENTER);

        // Assemble frame
        add(topWrapper, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        // ===== Listeners =====
        createBtn.addActionListener(e -> { createAccount(); setStep(1); });
        removeBtn.addActionListener(e -> removeSelected());
        depositBtn.addActionListener(e -> { doDeposit(); setStep(3); });
        withdrawBtn.addActionListener(e -> { doWithdrawl(); setStep(3); });
        detailsBtn.addActionListener(e -> { doGetDetails(); setStep(3); });
        lspBtn.addActionListener(e -> { demoLSPBreak(); setStep(4); });
        umlBtn.addActionListener(e -> { showUmlDiagram(); setStep(5); });
        clearBtn.addActionListener(e -> clearAll());
        balanceField.addActionListener(e -> createAccount());
        amountField.addActionListener(e -> doDeposit());
        accountList.addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) { updateInfo(); setStep(2); } });
        typeCombo.addActionListener(e -> updateInfo());

        // Demo data — matches BankClient
        addToModel(new SavingsAccount(1000));
        addToModel(new FixedDepositAccount(2000));
        addToModel(new CurrentAccount(3000));
        accountList.setSelectedIndex(0);
        updateTotal(); updateInfo();
        log("✨ GUI loaded — 3 demo accounts: Savings ₹1000, FixedDeposit ₹2000, Current ₹3000");
        log("");
        log("▶ HOW TO USE — Follow the steps at the top:");
        log("  ① Create: choose type + balance → Add account");
        log("  ② Select: click any row in the list");
        log("  ③ Transact: enter amount → Deposit / Withdraw / Get Details");
        log("     → Try: select 🔒 FixedDeposit → Withdraw → see LSP violation!");
        log("  ④ Demo Break: click 💥 to loop all accounts — FixedDeposit fails");
        log("  ⑤ View UML: click ▭ to see why this design breaks LSP");
        log("  💡 Tip: Click ❓ How to use anytime for help");
        log("📚 LSP: subtypes must be substitutable for base type without altering correctness.");
        setStep(1);
    }

    private JPanel stepChip(String num, String title, String sub, Color color, boolean active) {
        JPanel p = new JPanel(new BorderLayout(0, 1));
        p.setBackground(active ? new Color(color.getRed(), color.getGreen(), color.getBlue(), 18) : Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(active ? color : BORDER_SOFT, active ? 2 : 1, true),
                new EmptyBorder(6, 8, 6, 8)
        ));
        JLabel n = new JLabel(num);
        n.setFont(new Font("SansSerif", Font.BOLD, 11));
        n.setForeground(Color.WHITE);
        n.setBackground(color);
        n.setOpaque(true);
        n.setHorizontalAlignment(SwingConstants.CENTER);
        n.setBorder(new EmptyBorder(2, 6, 2, 6));
        JLabel t = new JLabel(title);
        t.setFont(new Font("SansSerif", Font.BOLD, 11));
        t.setForeground(active ? color.darker() : DARK);
        JLabel s = new JLabel(sub);
        s.setFont(new Font("SansSerif", Font.PLAIN, 10));
        s.setForeground(new Color(100, 116, 139));
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        top.setOpaque(false);
        top.add(n); top.add(t);
        p.add(top, BorderLayout.NORTH);
        p.add(s, BorderLayout.SOUTH);
        return p;
    }

    private void setStep(int step) {
        String[] msgs = {
            "Step 1 of 5 — Start by creating or using the 3 demo accounts",
            "Step 2 of 5 — Select an account from the list (click any row)",
            "Step 3 of 5 — Enter amount → Deposit / Withdraw / Get Details",
            "Step 4 of 5 — Click 💥 Demo LSP Break to see the violation in a loop",
            "Step 5 of 5 — Click ▭ View UML to see why FixedDeposit breaks LSP"
        };
        if (step >= 1 && step <= 5) stepLabel.setText(msgs[step - 1]);
    }

    private void showGuide() {
        JOptionPane.showMessageDialog(this,
                "<html><body style='width:520px; font-family:sans-serif;'>"
                + "<h2 style='color:#6c5ce7; margin:0;'>How to use — 5 easy steps</h2>"
                + "<p style='color:#64748b; margin:4 0 12 0;'>This demo shows <b>LSP violation</b> — FixedDeposit cannot be substituted where Account is expected.</p>"
                + "<table style='width:100%; border-collapse:collapse; font-size:12px;'>"
                + "<tr><td style='padding:6 8; background:#eef2ff; border-radius:8px;'><b>① Create</b></td><td style='padding:6 8;'>Choose <b>Type</b> + <b>Initial ₹</b> → click <b>➕ Create Account</b>. Demo gives you 3 accounts already.</td></tr>"
                + "<tr><td style='padding:6 8; background:#e0f2fe; border-radius:8px;'><b>② Select</b></td><td style='padding:6 8;'>Click any row in the middle list. Selected row turns purple. Info appears below.</td></tr>"
                + "<tr><td style='padding:6 8; background:#ffedd5; border-radius:8px;'><b>③ Transact</b></td><td style='padding:6 8;'>Enter <b>₹ Amount</b> then:<br>• <b>⬆ Deposit</b> — always works<br>• <b>⬇ Withdrawl</b> — <b>fails for FixedDeposit</b> (throws) ✓ shows violation<br>• <b>📄 Get Details</b> — shows balance</td></tr>"
                + "<tr><td style='padding:6 8; background:#fee2e2; border-radius:8px;'><b>④ Demo Break</b></td><td style='padding:6 8;'>Click <b>💥 Demo LSP Break</b> — loops <code>List&lt;Account&gt;</code> calling <code>withdrawl(100)</code>. Savings/Current succeed, FixedDeposit throws.</td></tr>"
                + "<tr><td style='padding:6 8; background:#f5f3ff; border-radius:8px;'><b>⑤ View UML</b></td><td style='padding:6 8;'>Click <b>▭ View UML</b> — see class diagram with red violation on FixedDeposit.withdrawl().</td></tr>"
                + "</table>"
                + "<p style='background:#fefce8; padding:8; border-radius:8px; color:#854d0e;'><b>💡 Try this first:</b> Select <b>🔒 FixedDepositAccount</b> → set Amount 200 → click <b>⬇ Withdrawl</b> → watch the error & log.</p>"
                + "<p style='color:#94a3b8; font-size:11px;'>Log at bottom shows every action. Total banner updates automatically.</p>"
                + "</body></html>",
                "How to use — Step by step", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showUmlDiagram() {
        JDialog dlg = new JDialog(this, "UML — Liskov Not Following (Violation)", true);
        dlg.setSize(860, 620);
        dlg.setLocationRelativeTo(this);
        dlg.setLayout(new BorderLayout(0, 0));
        dlg.getContentPane().setBackground(BG_MAIN);

        JPanel head = new JPanel(new BorderLayout());
        head.setBackground(Color.WHITE);
        head.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_SOFT),
                new EmptyBorder(14, 18, 14, 18)
        ));
        JLabel hTitle = new JLabel("Class Diagram — LSP Violation  •  Single Hierarchy Breaks Substitutability");
        hTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        hTitle.setForeground(DARK);
        JLabel hSub = new JLabel("Account defines withdrawl()  •  FixedDepositAccount inherits but throws — child narrows parent contract");
        hSub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        hSub.setForeground(new Color(100, 116, 139));
        JPanel hText = new JPanel(new BorderLayout(0, 2));
        hText.setBackground(Color.WHITE);
        hText.add(hTitle, BorderLayout.NORTH);
        hText.add(hSub, BorderLayout.SOUTH);
        JLabel hBadge = new JLabel("  ✗  LSP Broken  ");
        hBadge.setFont(new Font("SansSerif", Font.BOLD, 11));
        hBadge.setForeground(VIOLATION);
        hBadge.setBackground(VIOLATION_SOFT);
        hBadge.setOpaque(true);
        hBadge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(254, 205, 211), 1, true),
                new EmptyBorder(6, 12, 6, 12)
        ));
        head.add(hText, BorderLayout.WEST);
        head.add(hBadge, BorderLayout.EAST);

        UmlPanel uml = new UmlPanel();
        JScrollPane scroll = new JScrollPane(uml);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(BG_MAIN);
        uml.setPreferredSize(new Dimension(820, 460));

        JPanel legend = new JPanel(new BorderLayout(0, 6));
        legend.setBackground(new Color(248, 250, 252));
        legend.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_SOFT),
                new EmptyBorder(10, 18, 10, 18)
        ));
        JLabel leg1 = new JLabel("▵  Hollow triangle = extends   •   Red dashed = violation   •   <<abstract>> = Account");
        leg1.setFont(new Font("SansSerif", Font.PLAIN, 11));
        leg1.setForeground(new Color(71, 85, 105));
        JLabel leg2 = new JLabel("Why this breaks LSP:  Client loops List<Account> calling withdrawl() — FixedDeposit throws IllegalArgumentException. Subtype not substitutable for base type. Fix: split into Withdrawable / NonWithdrawable (see Following case).");
        leg2.setFont(new Font("SansSerif", Font.PLAIN, 11));
        leg2.setForeground(new Color(100, 116, 139));
        legend.add(leg1, BorderLayout.NORTH);
        legend.add(leg2, BorderLayout.SOUTH);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottom.setBackground(Color.WHITE);
        bottom.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_SOFT));
        JButton close = createColorButton("Close", VIOLET, Color.WHITE);
        close.addActionListener(e -> dlg.dispose());
        JButton hint = new JButton("Tip: Compare with Following case UML");
        hint.setFont(new Font("SansSerif", Font.PLAIN, 11));
        hint.setForeground(new Color(100, 116, 139));
        hint.setBackground(Color.WHITE);
        hint.setFocusPainted(false);
        hint.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_SOFT, 1, true),
                new EmptyBorder(6, 12, 6, 12)
        ));
        hint.setEnabled(false);
        bottom.add(hint);
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
        UmlPanel() { setBackground(BG_MAIN); }
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            int W = getWidth();
            int boxW = 210, boxH = 92;
            int cx = W/2;
            int topY = 30;
            int botY = 180;
            Rect rRoot = new Rect(cx - boxW/2, topY, boxW, boxH);
            Rect rSav = new Rect(cx - 150 - boxW/2 - 90, botY, boxW, boxH);
            Rect rCur = new Rect(cx - boxW/2, botY, boxW, boxH);
            Rect rFix = new Rect(cx + 150 + boxW/2 - 90, botY, boxW, boxH);

            // Connections — normal for Sav/Cur, red violated for Fixed
            g2.setStroke(new BasicStroke(1.6f));
            drawInheritance(g2, rSav, rRoot, false);
            drawInheritance(g2, rCur, rRoot, false);
            drawInheritance(g2, rFix, rRoot, true);

            g2.setFont(new Font("SansSerif", Font.ITALIC, 10));
            g2.setColor(new Color(100, 116, 139));
            g2.drawString("extends", rSav.centerX() + 8, rSav.y - 10);
            g2.drawString("extends", rCur.centerX() - 18, rCur.y - 10);
            g2.setColor(VIOLATION);
            g2.drawString("extends ✗ violates", rFix.centerX() - 38, rFix.y - 10);

            // Boxes
            drawClassBox(g2, rRoot, "<<abstract>>", "Account", new String[]{"- balance: Integer", "+ deposit(amount)", "+ withdrawl(amount) ★", "+ getDetails()"}, PURPLE, new Color(238,242,255), true, false);
            drawClassBox(g2, rSav, "", "SavingsAccount", new String[]{"+ deposit(amount)", "+ withdrawl(amount) ✓"}, BLUE, new Color(239,246,255), false, false);
            drawClassBox(g2, rCur, "", "CurrentAccount", new String[]{"+ deposit(amount)", "+ withdrawl(amount) ✓"}, TEAL, new Color(236,253,245), false, false);
            drawClassBox(g2, rFix, "", "FixedDepositAccount", new String[]{"+ deposit(amount)", "+ withdrawl(amount) ✗ throws!"}, VIOLATION, VIOLATION_SOFT, false, true);

            // Callout violation
            g2.setFont(new Font("SansSerif", Font.BOLD, 11));
            String callout = "✗  FixedDeposit narrows parent — LSP broken";
            int cw = g2.getFontMetrics().stringWidth(callout) + 24;
            int ch = 28;
            int cx2 = W/2 - cw/2;
            int cy2 = botY + boxH + 28;
            g2.setColor(VIOLATION_SOFT);
            g2.fillRoundRect(cx2, cy2, cw, ch, 14, 14);
            g2.setColor(VIOLATION);
            g2.drawRoundRect(cx2, cy2, cw, ch, 14, 14);
            g2.drawString(callout, cx2 + 12, cy2 + 18);

            // Note card — polymorphic loop
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g2.setColor(new Color(100, 116, 139));
            String n1 = "Client: List<Account> list = [Savings, Fixed, Current]";
            String n2 = "for (Account a : list) a.withdrawl(100); → Fixed throws!";
            String n3 = "→ Not substitutable — should split hierarchy";
            int nx = W - 320;
            int ny = botY + boxH + 72;
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(nx - 12, ny - 16, 310, 52, 10, 10);
            g2.setColor(BORDER_SOFT);
            g2.drawRoundRect(nx - 12, ny - 16, 310, 52, 10, 10);
            g2.setColor(DARK);
            g2.setFont(new Font("SansSerif", Font.BOLD, 10));
            g2.drawString(n1, nx, ny);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g2.setColor(VIOLATION.darker());
            g2.drawString(n2, nx, ny + 14);
            g2.setColor(new Color(71,85,105));
            g2.drawString(n3, nx, ny + 28);

            // Step hint
            g2.setFont(new Font("SansSerif", Font.ITALIC, 10));
            g2.setColor(new Color(100,116,139));
            g2.drawString("Compare with Following case: two hierarchies (Withdrawable / NonWithdrawable) — no violation.", 18, getHeight() - 10);
        }
        private void drawClassBox(Graphics2D g2, Rect r, String stereo, String name, String[] members, Color accent, Color soft, boolean isAbstract, boolean isViolation) {
            g2.setColor(new Color(0,0,0,10));
            g2.fillRoundRect(r.x+3, r.y+3, r.w, r.h, 14, 14);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(r.x, r.y, r.w, r.h, 14, 14);
            g2.setColor(soft);
            g2.fillRoundRect(r.x, r.y, r.w, 28, 14, 14);
            g2.fillRect(r.x, r.y+14, r.w, 14);
            g2.setColor(accent);
            g2.fillRoundRect(r.x, r.y, r.w, 3, 3, 3);
            if (isViolation) {
                g2.setColor(VIOLATION);
                g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{6, 4}, 0));
                g2.drawRoundRect(r.x, r.y, r.w, r.h, 14, 14);
                g2.setStroke(new BasicStroke(1.2f));
            } else {
                g2.setColor(BORDER_SOFT);
                g2.setStroke(new BasicStroke(1.2f));
                g2.drawRoundRect(r.x, r.y, r.w, r.h, 14, 14);
            }
            if (!stereo.isEmpty()) {
                g2.setFont(new Font("SansSerif", Font.ITALIC, 10));
                g2.setColor(new Color(100,116,139));
                int sw = g2.getFontMetrics().stringWidth(stereo);
                g2.drawString(stereo, r.x + (r.w - sw)/2, r.y + 14);
            }
            g2.setFont(new Font("SansSerif", Font.BOLD, 12));
            g2.setColor(DARK);
            if (isAbstract) g2.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
            int nw = g2.getFontMetrics().stringWidth(name);
            g2.drawString(name, r.x + (r.w - nw)/2, r.y + (stereo.isEmpty()? 19 : 26));
            g2.setColor(BORDER_SOFT);
            g2.drawLine(r.x + 12, r.y + 32, r.x + r.w - 12, r.y + 32);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g2.setColor(new Color(71,85,105));
            int my = r.y + 46;
            for (String m : members) {
                if (m.contains("✗")) g2.setColor(VIOLATION.darker());
                else g2.setColor(new Color(71,85,105));
                g2.drawString(m, r.x + 12, my);
                my += 13;
            }
            if (isViolation) {
                String badge = "violates LSP";
                g2.setFont(new Font("SansSerif", Font.BOLD, 9));
                int bw = g2.getFontMetrics().stringWidth(badge) + 10;
                int bx = r.x + r.w - bw - 8;
                int by = r.y + 8;
                g2.setColor(VIOLATION_SOFT);
                g2.fillRoundRect(bx, by, bw, 14, 7, 7);
                g2.setColor(VIOLATION);
                g2.drawRoundRect(bx, by, bw, 14, 7, 7);
                g2.drawString(badge, bx + 5, by + 10);
            } else if (isAbstract) {
                String badge = "abstract";
                g2.setFont(new Font("SansSerif", Font.BOLD, 9));
                int bw = g2.getFontMetrics().stringWidth(badge) + 10;
                int bx = r.x + r.w - bw - 8;
                int by = r.y + 8;
                g2.setColor(VIOLET_SOFT);
                g2.fillRoundRect(bx, by, bw, 14, 7, 7);
                g2.setColor(VIOLET);
                g2.drawRoundRect(bx, by, bw, 14, 7, 7);
                g2.drawString(badge, bx + 5, by + 10);
            }
        }
        private void drawInheritance(Graphics2D g2, Rect child, Rect parent, boolean violation) {
            int x1 = child.centerX();
            int y1 = child.y;
            int x2 = parent.centerX();
            int y2 = parent.y + parent.h;
            if (violation) {
                g2.setColor(VIOLATION);
                g2.setStroke(new BasicStroke(1.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{6, 4}, 0));
            } else {
                g2.setColor(new Color(100, 116, 139));
                g2.setStroke(new BasicStroke(1.6f));
            }
            int midY = (y1 + y2)/2;
            if (Math.abs(x1 - x2) < 6) {
                g2.drawLine(x1, y1, x2, y2);
                drawTriangle(g2, x2, y2, true, violation);
            } else {
                g2.drawLine(x1, y1, x1, midY);
                g2.drawLine(x1, midY, x2, midY);
                g2.drawLine(x2, midY, x2, y2);
                drawTriangle(g2, x2, y2, true, violation);
            }
            g2.setStroke(new BasicStroke(1.2f));
        }
        private void drawTriangle(Graphics2D g2, int x, int y, boolean up, boolean violation) {
            Polygon tri = new Polygon();
            int s = 9;
            tri.addPoint(x, y);
            tri.addPoint(x - s, y + s + 3);
            tri.addPoint(x + s, y + s + 3);
            g2.setColor(Color.WHITE);
            g2.fillPolygon(tri);
            g2.setColor(violation ? VIOLATION : new Color(100, 116, 139));
            g2.setStroke(new BasicStroke(1.4f));
            g2.drawPolygon(tri);
        }
        private class Rect { int x,y,w,h; Rect(int x,int y,int w,int h){this.x=x;this.y=y;this.w=w;this.h=h;} int centerX(){return x+w/2;} }
    }

    private void styleTextField(JTextField tf) {
        tf.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tf.setBackground(Color.WHITE);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_SOFT, 1, true),
                new EmptyBorder(6, 8, 6, 8)));
    }

    private JButton createColorButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bg.darker(), 1, true),
                new EmptyBorder(8, 14, 8, 14)));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(bg.brighter()); }
            @Override public void mouseExited(java.awt.event.MouseEvent e) { btn.setBackground(bg); }
        });
        return btn;
    }

    private void createAccount() {
        String s = balanceField.getText().trim();
        if (s.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter initial balance", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int bal = Integer.parseInt(s);
            if (bal < 0) throw new NumberFormatException();
            String t = (String) typeCombo.getSelectedItem();
            Account acc = "SavingsAccount".equals(t) ? new SavingsAccount(bal)
                    : "CurrentAccount".equals(t) ? new CurrentAccount(bal)
                    : new FixedDepositAccount(bal);
            addToModel(acc);
            log("✅ Created " + t + " with ₹" + bal);
            accountList.setSelectedValue(acc, true);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Balance must be a positive integer", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addToModel(Account acc) {
        listModel.addElement(acc);
        updateTotal();
        accountList.repaint();
    }

    private void removeSelected() {
        Account sel = accountList.getSelectedValue();
        if (sel == null) {
            JOptionPane.showMessageDialog(this, "Select an account to remove", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        listModel.removeElement(sel);
        updateTotal();
        log("🗑 Removed: " + sel.getClass().getSimpleName() + " (₹" + sel.getBalance() + ")");
    }

    private void clearAll() {
        if (listModel.isEmpty()) return;
        int c = JOptionPane.showConfirmDialog(this, "Clear all accounts?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (c != JOptionPane.YES_OPTION) return;
        listModel.clear();
        updateTotal();
        log("🧹 All accounts cleared.");
    }

    private Account selectedOrWarn() {
        Account sel = accountList.getSelectedValue();
        if (sel == null) JOptionPane.showMessageDialog(this, "Select an account first!", "No Selection", JOptionPane.WARNING_MESSAGE);
        return sel;
    }

    private Integer parseAmount() {
        String s = amountField.getText().trim();
        if (s.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter amount", "Input Error", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        try {
            int v = Integer.parseInt(s);
            if (v <= 0) throw new NumberFormatException();
            return v;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Amount must be positive integer", "Input Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void doDeposit() {
        Account acc = selectedOrWarn();
        if (acc == null) return;
        Integer amt = parseAmount();
        if (amt == null) return;
        try {
            acc.deposit(amt);
            log("⬆ Deposited ₹" + amt + " → " + acc.getClass().getSimpleName() + " new balance ₹" + acc.getBalance());
            accountList.repaint();
            updateTotal();
            updateInfo();
        } catch (IllegalArgumentException ex) {
            log("❌ Deposit failed: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Deposit Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void doWithdrawl() {
        Account acc = selectedOrWarn();
        if (acc == null) return;
        Integer amt = parseAmount();
        if (amt == null) return;
        try {
            acc.withdrawl(amt); // polymorphic — breaks for FixedDeposit
            log("⬇ Withdrew ₹" + amt + " from " + acc.getClass().getSimpleName() + " → new balance ₹" + acc.getBalance());
            accountList.repaint();
            updateTotal();
            updateInfo();
        } catch (IllegalArgumentException ex) {
            String err = "❌ Withdrawl FAILED for " + acc.getClass().getSimpleName() + ": " + ex.getMessage();
            log(err);
            if (acc instanceof FixedDepositAccount) {
                log("   💥 LSP VIOLATION! Account.withdrawl() contract broken by FixedDepositAccount");
                log("   → Cannot substitute FixedDeposit where Account is expected.");
            }
            JOptionPane.showMessageDialog(this,
                    "<html><h3 style='color:#d63031;'>Withdrawl Failed</h3>"
                            + "<p><b>" + acc.getClass().getSimpleName() + "</b>: " + ex.getMessage() + "</p>"
                            + (acc instanceof FixedDepositAccount ? "<p style='color:#e17055;'><b>LSP Broken:</b> child narrows parent capability — not substitutable!</p><p style='color:#64748b;'>This is the violation — see Step 5 UML for why.</p>" : "")
                            + "</html>", "Withdrawl Error", JOptionPane.ERROR_MESSAGE);
            accountList.repaint();
        }
    }

    private void doGetDetails() {
        Account acc = selectedOrWarn();
        if (acc == null) return;
        log("📄 getDetails() — " + acc.getClass().getSimpleName() + " (₹" + acc.getBalance() + "):");
        acc.getDetails(); // System.out
        log("   → Current balance : ₹" + acc.getBalance());
        JOptionPane.showMessageDialog(this,
                "<html><h3>" + acc.getClass().getSimpleName() + "</h3><p>Balance: <b>₹" + acc.getBalance() + "</b></p>"
                        + "<p style='color:gray;'>Account.getDetails() invoked polymorphically</p></html>",
                "Account Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void demoLSPBreak() {
        if (listModel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No accounts!", "Demo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        log("\n========== 💥 LSP BREAK DEMO (Step 4) ==========");
        log("Looping List<Account> — calling withdrawl(100) via base-type reference...");
        for (int i = 0; i < listModel.size(); i++) {
            Account acc = listModel.get(i);
            String n = acc.getClass().getSimpleName();
            log(" → " + n + " (₹" + acc.getBalance() + ").withdrawl(100) …");
            try {
                acc.withdrawl(100);
                log("   ✅ Success → balance ₹" + acc.getBalance());
            } catch (Exception ex) {
                log("   ❌ FAILED: " + ex.getMessage() + "  ← LSP BROKEN for " + n + "!");
            }
        }
        log("========== End — FixedDeposit breaks substitutability ==========\n");
        accountList.repaint();
        updateTotal();
        updateInfo();
        JOptionPane.showMessageDialog(this,
                "<html><h3 style='color:#6c5ce7;'>LSP Demo Complete — Step 4 done</h3>"
                        + "<p>Check log: Savings/Current succeeded, <b>FixedDeposit failed</b>.</p>"
                        + "<p><b>Liskov:</b> if S is subtype of T, objects of T should be replaceable with S without breaking.</p>"
                        + "<p><code>FixedDepositAccount</code> violates by throwing on <code>withdrawl()</code>.</p>"
                        + "<p style='color:#7c3aed;'>Next → Step 5: Click <b>▭ View UML</b> to see the diagram.</p></html>",
                "LSP Break Demo", JOptionPane.WARNING_MESSAGE);
    }

    private void updateTotal() {
        int total = 0;
        for (int i = 0; i < listModel.size(); i++) total += listModel.get(i).getBalance();
        totalBannerLabel.setText(String.format("💰  Total Balance: ₹%d   •   %d account(s)   •   List<Account> polymorphic", total, listModel.size()));
    }

    private void updateInfo() {
        Account sel = accountList.getSelectedValue();
        if (sel == null) {
            infoLabel.setText("Step 2 → Select an account from the list (click any row)  •  Then go to Step 3");
            return;
        }
        String extra = (sel instanceof FixedDepositAccount)
                ? "  ⚠️ FixedDeposit — withdrawl() will throw IllegalArgumentException (LSP violation)!  → Try Step 3 Withdraw to see it"
                : "  ✓ withdrawl() allowed for this type  → Step 3 ready";
        infoLabel.setText("Selected: " + sel.getClass().getSimpleName() + "  •  Balance ₹" + sel.getBalance() + extra);
    }

    private void log(String msg) {
        logArea.append(msg + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
        System.out.println(msg);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
            new BankAccountGUI().setVisible(true);
        });
    }
}
