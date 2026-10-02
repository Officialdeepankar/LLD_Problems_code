package LISKOV03.following;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * LISKOV — FOLLOWING (Fixed) • Friendly Banking UI
 * Warm, approachable, non-terminal design for end users.
 * LSP is FOLLOWED: Withdrawable vs Non-Withdrawable separated — no exception on substitution.
 */
public class BankFollowingGUI extends JFrame {

    private final DefaultListModel<NonWithdrawableAccount> fixedModel = new DefaultListModel<>();
    private final DefaultListModel<WithdrawableAccount> withdrawableModel = new DefaultListModel<>();
    private final JList<NonWithdrawableAccount> fixedList = new JList<>(fixedModel);
    private final JList<WithdrawableAccount> withdrawList = new JList<>(withdrawableModel);

    private final JComboBox<String> typeCombo = new JComboBox<>(new String[]{"SavingAccount", "CurrentAccount", "FixedDeposit"});
    private final JTextField balanceField = new JTextField(7);
    private final JTextField amountField = new JTextField(7);
    private final JTextArea logArea = new JTextArea(8, 40);
    private final JLabel totalLabel = new JLabel();
    private final JLabel statusLabel = new JLabel("Select an account to get started  •  Deposits work for all, withdrawals only for Saving/Current");
    private final JLabel stepLabel = new JLabel("Step 1 of 5 — Start with the 3 demo accounts");

    // Friendly palette — soft, warm, trustworthy bank
    private static final Color BG_MAIN      = new Color(248, 250, 252);
    private static final Color CARD_BG      = Color.WHITE;
    private static final Color BORDER_SOFT  = new Color(226, 232, 240);
    private static final Color TEXT_DARK    = new Color(30, 41, 59);
    private static final Color TEXT_MUTED   = new Color(100, 116, 139);
    private static final Color PRIMARY      = new Color(79, 70, 229);   // indigo
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

    public BankFollowingGUI() {
        super("MyBank — Accounts (LSP Following)  •  Clean & Safe Design");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 720);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_MAIN);
        setLayout(new BorderLayout(0, 0));

        // ===== HEADER — warm gradient, rounded, friendly copy =====
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
        JLabel title = new JLabel("MyBank  •  Your accounts, handled safely");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        JLabel subtitle = new JLabel("Clean account design  •  Saving & Current can withdraw  •  Fixed Deposit is deposit-only by design  ✓  No surprises");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(new Color(255,255,255,230));
        titleBox.add(title, BorderLayout.NORTH);
        titleBox.add(subtitle, BorderLayout.CENTER);

        // Friendly hierarchy chip + HIGHLIGHTED help button
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
        JLabel chip = new JLabel("  ✓  LSP Following — Safe to use  ");
        chip.setFont(new Font("SansSerif", Font.BOLD, 11));
        chip.setForeground(PRIMARY);
        chip.setBackground(Color.WHITE);
        chip.setOpaque(true);
        chip.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true),
                new EmptyBorder(7, 14, 7, 14)
        ));
        JLabel smallHint = new JLabel("Two clear account types — no hidden errors");
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

        // ===== STEP-BY-STEP STRIP — clear, highlighted =====
        JPanel stepStrip = new JPanel(new BorderLayout(0, 4));
        stepStrip.setBackground(Color.WHITE);
        stepStrip.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_SOFT),
                new EmptyBorder(8, 14, 8, 14)
        ));
        JPanel stepsRow = new JPanel(new GridLayout(1, 5, 8, 0));
        stepsRow.setBackground(Color.WHITE);
        stepsRow.add(stepChip("1", "Create", "Add account", PRIMARY, true));
        stepsRow.add(stepChip("2", "Select", "Pick from list", TEAL, false));
        stepsRow.add(stepChip("3", "Transact", "Deposit / Withdraw", AMBER, false));
        stepsRow.add(stepChip("4", "Demo Safe", "See LSP OK", PRIMARY, false));
        stepsRow.add(stepChip("5", "View UML", "See diagram", VIOLET, false));
        stepLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        stepLabel.setForeground(SLATE);
        stepLabel.setHorizontalAlignment(SwingConstants.CENTER);
        stepLabel.setBorder(new EmptyBorder(4, 0, 0, 0));
        stepStrip.add(stepsRow, BorderLayout.CENTER);
        stepStrip.add(stepLabel, BorderLayout.SOUTH);

        // ===== CREATE CARD — soft, rounded, friendly =====
        JPanel createCard = new JPanel(new BorderLayout(0, 6));
        createCard.setBackground(CARD_BG);
        createCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_SOFT, 1, true),
                new EmptyBorder(12, 14, 12, 14)
        ));
        JLabel createTitle = new JLabel("① Step 1 — Add a new account");
        createTitle.setFont(new Font("SansSerif", Font.BOLD, 13));
        createTitle.setForeground(TEXT_DARK);
        JLabel createSub = new JLabel("Choose the type and starting balance. Demo already gives you 3 accounts.");
        createSub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        createSub.setForeground(TEXT_MUTED);
        JPanel createHead = new JPanel(new BorderLayout());
        createHead.setBackground(CARD_BG);
        createHead.add(createTitle, BorderLayout.NORTH);
        createHead.add(createSub, BorderLayout.SOUTH);

        JPanel createRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        createRow.setBackground(CARD_BG);
        JLabel typeLbl = label("Account type", TEXT_DARK);
        JLabel balLbl = label("Starting balance  ₹", TEXT_DARK);
        typeCombo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        typeCombo.setBackground(Color.WHITE);
        typeCombo.setForeground(TEXT_DARK);
        typeCombo.setBorder(BorderFactory.createLineBorder(BORDER_SOFT, 1, true));
        styleField(balanceField, "1000");
        balanceField.setToolTipText("e.g. 2000");
        JButton createBtn = softButton("Add account", PRIMARY, Color.WHITE);
        JButton removeBtn = softButton("Remove selected", Color.WHITE, TEXT_DARK);
        removeBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_SOFT, 1, true),
                new EmptyBorder(8, 16, 8, 16)
        ));
        createRow.add(typeLbl); createRow.add(typeCombo);
        createRow.add(balLbl); createRow.add(balanceField);
        createRow.add(createBtn); createRow.add(removeBtn);
        // legible type hint
        JLabel typeHint = new JLabel("Saving / Current → can deposit & withdraw   •   Fixed Deposit → deposit only (by design)");
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
        // subtle wrapper padding
        JPanel topOuter = new JPanel(new BorderLayout(0, 12));
        topOuter.setBackground(BG_MAIN);
        topOuter.setBorder(new EmptyBorder(12, 12, 0, 12));
        topOuter.add(topWrap, BorderLayout.CENTER);
        // reassign
        topWrap = topOuter;

        // ===== LISTS — two friendly cards =====
        withdrawList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fixedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        withdrawList.setCellRenderer(new FriendlyRenderer(TEAL, TEAL_SOFT, true));
        fixedList.setCellRenderer(new FriendlyRenderer(AMBER, AMBER_SOFT, false));
        styleFriendlyList(withdrawList);
        styleFriendlyList(fixedList);
        JScrollPane withdrawScroll = wrapFriendlyList(withdrawList, "② Everyday accounts", "Saving & Current — deposit and withdraw anytime (Step 2: click to select)", TEAL, TEAL_SOFT);
        JScrollPane fixedScroll = wrapFriendlyList(fixedList, "② Fixed deposits", "Deposit-only — designed for saving (Step 2: click to select)", AMBER, AMBER_SOFT);

        withdrawList.addListSelectionListener(e -> { if(!e.getValueIsAdjusting() && withdrawList.getSelectedIndex()!=-1) fixedList.clearSelection(); updateStatus(); });
        fixedList.addListSelectionListener(e -> { if(!e.getValueIsAdjusting() && fixedList.getSelectedIndex()!=-1) withdrawList.clearSelection(); updateStatus(); });

        JPanel listsPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        listsPanel.setBackground(BG_MAIN);
        listsPanel.setBorder(new EmptyBorder(12, 12, 0, 12));
        listsPanel.add(cardWrap(withdrawScroll, "Everyday"));
        listsPanel.add(cardWrap(fixedScroll, "Fixed"));

        // Total banner — soft, reassuring
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

        // ===== TRANSACTIONS — friendly action card =====
        JPanel txnCard = new JPanel(new BorderLayout(0, 8));
        txnCard.setBackground(CARD_BG);
        txnCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_SOFT, 1, true),
                new EmptyBorder(12, 14, 12, 14)
        ));
        JLabel txnTitle = new JLabel("③ Step 3 — Manage your money   •   ④ Demo Safe   •   ⑤ View UML");
        txnTitle.setFont(new Font("SansSerif", Font.BOLD, 13));
        txnTitle.setForeground(TEXT_DARK);
        JLabel txnSub = new JLabel("Step 3: select an account above → enter amount → Deposit / Withdraw. Steps 4 & 5 are one-click demos.");
        txnSub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        txnSub.setForeground(TEXT_MUTED);
        JPanel txnHead = new JPanel(new BorderLayout());
        txnHead.setBackground(CARD_BG);
        txnHead.add(txnTitle, BorderLayout.NORTH);
        txnHead.add(txnSub, BorderLayout.SOUTH);

        JPanel txnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        txnRow.setBackground(CARD_BG);
        JLabel amtLbl = label("Amount  ₹", TEXT_DARK);
        styleField(amountField, "200");
        amountField.setToolTipText("e.g. 200");
        JButton depBtn = softButton("Deposit", GREEN, Color.WHITE);
        JButton witBtn = softButton("Withdraw", TEAL, Color.WHITE);
        JButton detBtn = softButton("View details", Color.WHITE, TEXT_DARK);
        detBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), new EmptyBorder(8,16,8,16)));
        JButton demoBtn = softButton("See safe demo", PRIMARY, Color.WHITE);
        JButton umlBtn = softButton("View UML  ▭", VIOLET, Color.WHITE);
        umlBtn.setToolTipText("Show class diagram (LSP Following)");
        JButton clearBtn = softButton("Clear all", Color.WHITE, ROSE);
        clearBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(254,205,211),1,true), new EmptyBorder(8,16,8,16)));

        txnRow.add(amtLbl); txnRow.add(amountField);
        txnRow.add(depBtn); txnRow.add(witBtn); txnRow.add(detBtn); txnRow.add(demoBtn); txnRow.add(umlBtn); txnRow.add(clearBtn);

        txnCard.add(txnHead, BorderLayout.NORTH);
        txnCard.add(txnRow, BorderLayout.CENTER);

        // Log — light, friendly activity feed (not terminal)
        logArea.setEditable(false);
        logArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
        logArea.setBackground(Color.WHITE);
        logArea.setForeground(TEXT_DARK);
        logArea.setBorder(new EmptyBorder(8, 8, 8, 8));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_SOFT, 1, true),
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(BORDER_SOFT, 1, true),
                        " Activity ", 0, 0, new Font("SansSerif", Font.BOLD, 11), TEXT_MUTED)
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

        // Listeners — update step highlight
        createBtn.addActionListener(e -> { createAccount(); setStep(1); });
        removeBtn.addActionListener(e -> removeSelected());
        depBtn.addActionListener(e -> { doDeposit(); setStep(3); });
        witBtn.addActionListener(e -> { doWithdraw(); setStep(3); });
        detBtn.addActionListener(e -> { doDetails(); setStep(3); });
        demoBtn.addActionListener(e -> { demoLspOk(); setStep(4); });
        umlBtn.addActionListener(e -> { showUmlDiagram(); setStep(5); });
        clearBtn.addActionListener(e -> clearAll());
        balanceField.addActionListener(e -> { createAccount(); setStep(1); });
        amountField.addActionListener(e -> { doDeposit(); setStep(3); });
        typeCombo.addActionListener(e -> updateStatus());
        withdrawList.addListSelectionListener(e -> { if (!e.getValueIsAdjusting() && withdrawList.getSelectedIndex()!=-1) setStep(2); });
        fixedList.addListSelectionListener(e -> { if (!e.getValueIsAdjusting() && fixedList.getSelectedIndex()!=-1) setStep(2); });

        // Demo data (same as BankClient: Fixed 1000, Saving 2000, Current 4000)
        addToModel(new SavingAccount(2000));
        addToModel(new CurrentAccount(4000));
        addToModel(new FixedDeposit(1000));
        withdrawList.setSelectedIndex(0);
        updateTotal(); updateStatus();
        setStep(1);
        log("Welcome to MyBank  •  Your accounts are ready.");
        log("Everyday: Saving ₹2,000 • Current ₹4,000  —  Fixed: ₹1,000");
        log("");
        log("▶ HOW TO USE — Follow the steps at the top:");
        log("  ① Create: choose type + balance → Add account");
        log("  ② Select: click any row (Everyday or Fixed)");
        log("  ③ Transact: enter amount → Deposit / Withdraw / View details");
        log("  ④ Demo Safe: click See safe demo — all operations succeed (LSP OK)");
        log("  ⑤ View UML: click View UML to see clean two-hierarchy design");
        log("  💡 Tip: Click ❓ How to use — Start here! anytime for help");
    }

    private JLabel label(String t, Color c){ JLabel l=new JLabel(t); l.setFont(new Font("SansSerif", Font.BOLD, 12)); l.setForeground(c); return l; }

    private void styleField(JTextField tf, String def){
        tf.setText(def);
        tf.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tf.setBackground(Color.WHITE);
        tf.setForeground(TEXT_DARK);
        tf.setCaretColor(TEXT_DARK);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_SOFT,1,true),
                new EmptyBorder(6,10,6,10)
        ));
        tf.setPreferredSize(new Dimension(110, 34));
    }

    private JButton softButton(String text, Color bg, Color fg){
        JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setBackground(bg); b.setForeground(fg);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bg.darker(),1,true),
                new EmptyBorder(8,16,8,16)
        ));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        b.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override public void mouseEntered(java.awt.event.MouseEvent e){ b.setBackground(bg.brighter()); }
            @Override public void mouseExited(java.awt.event.MouseEvent e){ b.setBackground(bg); }
        });
        return b;
    }

    private void styleFriendlyList(JList<?> list){
        list.setBackground(Color.WHITE);
        list.setFont(new Font("SansSerif", Font.PLAIN, 13));
        list.setFixedCellHeight(42);
        list.setBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true));
    }

    private JScrollPane wrapFriendlyList(JList<?> list, String title, String subtitle, Color accent, Color soft){
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
        // wrap with header + list
        JPanel wrapper = new JPanel(new BorderLayout(0,0));
        wrapper.setBackground(Color.WHITE);
        wrapper.add(header, BorderLayout.NORTH);
        wrapper.add(sp, BorderLayout.CENTER);
        sp.setBorder(BorderFactory.createMatteBorder(1,0,0,0, BORDER_SOFT));
        // Use wrapper as scroll is inside, return custom panel via scroll's parent trick: just return sp with titled border simulated
        // Simpler: create outer scroll that contains wrapper; but we want titled look — use sp with header above via outer panel
        JScrollPane outer = new JScrollPane(wrapper);
        outer.setBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true));
        outer.getViewport().setBackground(Color.WHITE);
        outer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // Replace sp with outer
        // To keep method signature, we return outer and caller adds it directly
        return outer;
    }

    private JPanel cardWrap(JComponent inner, String tag){
        JPanel p = new JPanel(new BorderLayout(0,0));
        p.setBackground(CARD_BG);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_SOFT,1,true),
                new EmptyBorder(0,0,0,0)
        ));
        p.add(inner, BorderLayout.CENTER);
        return p;
    }

    private class FriendlyRenderer extends DefaultListCellRenderer {
        private final Color accent; private final Color soft; private final boolean withdrawable;
        FriendlyRenderer(Color accent, Color soft, boolean withdrawable){ this.accent=accent; this.soft=soft; this.withdrawable=withdrawable; }
        @Override public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
            JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            lbl.setBorder(new EmptyBorder(8,12,8,12));
            lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
            lbl.setOpaque(true);
            if(value instanceof NonWithdrawableAccount){
                NonWithdrawableAccount acc=(NonWithdrawableAccount)value;
                String type=acc.getClass().getSimpleName();
                String icon = withdrawable ? "💳" : "🔒";
                if("SavingAccount".equals(type)) icon="🐖 Saving";
                else if("CurrentAccount".equals(type)) icon="💼 Current";
                else if("FixedDeposit".equals(type)) icon="🏦 Fixed Deposit";
                String badge = withdrawable ? "Can withdraw" : "Deposit only";
                lbl.setText(String.format("  %s  —  ₹%d   •  %s", icon, acc.getBalance(), badge));
                if(!isSelected) lbl.setForeground(TEXT_DARK);
            }
            if(isSelected){ lbl.setBackground(accent); lbl.setForeground(Color.WHITE); }
            else { lbl.setBackground(index%2==0 ? Color.WHITE : new Color(248,250,252)); }
            return lbl;
        }
    }

    private void addToModel(NonWithdrawableAccount acc){
        if(acc instanceof WithdrawableAccount) withdrawableModel.addElement((WithdrawableAccount)acc);
        else fixedModel.addElement(acc);
        updateTotal();
    }

    private void createAccount(){
        String s=balanceField.getText().trim();
        if(s.isEmpty()){ JOptionPane.showMessageDialog(this,"Please enter a starting balance.","Missing balance",JOptionPane.WARNING_MESSAGE); return; }
        try{
            int bal=Integer.parseInt(s); if(bal<0) throw new NumberFormatException();
            String t=(String)typeCombo.getSelectedItem();
            NonWithdrawableAccount acc;
            if("SavingAccount".equals(t)) acc=new SavingAccount(bal);
            else if("CurrentAccount".equals(t)) acc=new CurrentAccount(bal);
            else acc=new FixedDeposit(bal);
            addToModel(acc);
            String kind = (acc instanceof WithdrawableAccount) ? "Everyday" : "Fixed Deposit";
            log("Added " + t + " ("+kind+") with ₹" + bal + ".");
            if(acc instanceof WithdrawableAccount){ withdrawList.setSelectedValue(acc,true); fixedList.clearSelection(); }
            else { fixedList.setSelectedValue(acc,true); withdrawList.clearSelection(); }
            updateStatus();
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this,"Balance should be a positive number.","Check balance",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeSelected(){
        if(withdrawList.getSelectedIndex()!=-1){
            WithdrawableAccount a=withdrawList.getSelectedValue(); withdrawableModel.removeElement(a);
            log("Removed " + a.getClass().getSimpleName() + " (₹" + a.getBalance() + ").");
        } else if(fixedList.getSelectedIndex()!=-1){
            NonWithdrawableAccount a=fixedList.getSelectedValue(); fixedModel.removeElement(a);
            log("Removed " + a.getClass().getSimpleName() + " (₹" + a.getBalance() + ").");
        } else { JOptionPane.showMessageDialog(this,"Please select an account to remove.","No selection",JOptionPane.WARNING_MESSAGE); return; }
        updateTotal(); updateStatus();
    }

    private void clearAll(){
        if(withdrawableModel.isEmpty() && fixedModel.isEmpty()) return;
        int c=JOptionPane.showConfirmDialog(this,"Clear all accounts?","Confirm",JOptionPane.YES_NO_OPTION);
        if(c!=JOptionPane.YES_OPTION) return;
        withdrawableModel.clear(); fixedModel.clear();
        log("Cleared all accounts.");
        updateTotal(); updateStatus();
    }

    private NonWithdrawableAccount selectedAny(){
        if(withdrawList.getSelectedIndex()!=-1) return withdrawList.getSelectedValue();
        if(fixedList.getSelectedIndex()!=-1) return fixedList.getSelectedValue();
        return null;
    }
    private WithdrawableAccount selectedWithdrawable(){ return withdrawList.getSelectedValue(); }

    private Integer parseAmount(){
        String s=amountField.getText().trim();
        if(s.isEmpty()){ JOptionPane.showMessageDialog(this,"Please enter an amount.","Missing amount",JOptionPane.WARNING_MESSAGE); return null; }
        try{ int v=Integer.parseInt(s); if(v<=0) throw new NumberFormatException(); return v; }
        catch(NumberFormatException ex){ JOptionPane.showMessageDialog(this,"Amount should be a positive number.","Check amount",JOptionPane.ERROR_MESSAGE); return null; }
    }

    private void doDeposit(){
        NonWithdrawableAccount acc=selectedAny();
        if(acc==null){ JOptionPane.showMessageDialog(this,"Please select an account first.","No selection",JOptionPane.WARNING_MESSAGE); return; }
        Integer amt=parseAmount(); if(amt==null) return;
        try{
            acc.deposit(amt);
            log("Deposited ₹" + amt + " to " + acc.getClass().getSimpleName() + " — new balance ₹" + acc.getBalance() + ".");
            withdrawList.repaint(); fixedList.repaint(); updateTotal(); updateStatus();
        }catch(Exception ex){
            log("Deposit failed: " + ex.getMessage());
            JOptionPane.showMessageDialog(this,ex.getMessage(),"Deposit failed",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void doWithdraw(){
        WithdrawableAccount acc=selectedWithdrawable();
        if(acc==null){
            NonWithdrawableAccount any=selectedAny();
            if(any instanceof FixedDeposit){
                log("Heads up: Fixed Deposits are deposit-only — withdrawals aren’t available for this account type.");
                JOptionPane.showMessageDialog(this,
                        "<html><h3 style='margin:0;'>Fixed Deposit — deposit only</h3><p>This account is designed for saving, not withdrawing.<br>Please use a Saving or Current account to withdraw.</p><p style='color:#64748b;'>This is intentional — it keeps the design clean and predictable (LSP following).</p></html>",
                        "Deposit-only account", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this,"Please select a Saving or Current account to withdraw.","Choose everyday account",JOptionPane.WARNING_MESSAGE); return;
        }
        Integer amt=parseAmount(); if(amt==null) return;
        try{
            acc.withdrawl(amt);
            log("Withdrew ₹" + amt + " from " + acc.getClass().getSimpleName() + " — new balance ₹" + acc.getBalance() + ".");
            withdrawList.repaint(); fixedList.repaint(); updateTotal(); updateStatus();
        }catch(Exception ex){
            log("Withdrawal note: " + ex.getMessage());
            JOptionPane.showMessageDialog(this,ex.getMessage(),"Check amount",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void doDetails(){
        NonWithdrawableAccount acc=selectedAny();
        if(acc==null){ JOptionPane.showMessageDialog(this,"Please select an account first.","No selection",JOptionPane.WARNING_MESSAGE); return; }
        String kind=(acc instanceof WithdrawableAccount) ? "Everyday — deposit & withdraw" : "Fixed Deposit — deposit only";
        log(acc.getClass().getSimpleName() + " — " + kind + " — balance ₹" + acc.getBalance() + ".");
        JOptionPane.showMessageDialog(this,
                "<html><h3 style='margin:0;'>" + acc.getClass().getSimpleName() + "</h3><p style='margin:4 0;'>" + kind + "</p><p><b>Balance: ₹" + acc.getBalance() + "</b></p></html>",
                "Account details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void demoLspOk(){
        log(""); log("Safe design demo — everything works as expected:");
        for(int i=0;i<withdrawableModel.size();i++){
            WithdrawableAccount a=withdrawableModel.get(i);
            log("  Everyday " + a.getClass().getSimpleName() + " — withdrawing ₹100…");
            try{ a.withdrawl(100); log("    Done — new balance ₹" + a.getBalance() + "."); } catch(Exception ex){ log("    Note: " + ex.getMessage()); }
        }
        for(int i=0;i<fixedModel.size();i++){
            NonWithdrawableAccount a=fixedModel.get(i);
            log("  Fixed " + a.getClass().getSimpleName() + " — depositing ₹100…");
            try{ a.deposit(100); log("    Done — new balance ₹" + a.getBalance() + "."); } catch(Exception ex){ log("    Note: " + ex.getMessage()); }
        }
        for(int i=0;i<withdrawableModel.size();i++){
            NonWithdrawableAccount a=withdrawableModel.get(i);
            try{ a.deposit(50); log("  Everyday " + a.getClass().getSimpleName() + " — deposit ₹50 — balance ₹" + a.getBalance() + "."); } catch(Exception ex){}
        }
        log("All account types behaved as expected — no surprises. That’s LSP following in action.");
        log("");
        withdrawList.repaint(); fixedList.repaint(); updateTotal(); updateStatus();
        JOptionPane.showMessageDialog(this,
                "<html><h3 style='margin:0;'>All good — no surprises</h3><p>Everyday accounts withdrew fine, fixed deposits deposited fine.<br>Each account does exactly what its type promises.</p></html>",
                "Safe demo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateTotal(){
        int tot=0, cnt=0;
        for(int i=0;i<withdrawableModel.size();i++){ tot+=withdrawableModel.get(i).getBalance(); cnt++; }
        for(int i=0;i<fixedModel.size();i++){ tot+=fixedModel.get(i).getBalance(); cnt++; }
        totalLabel.setText(String.format("Total across all accounts: ₹%d  •  %d account(s)  —  %d Everyday + %d Fixed", tot, cnt, withdrawableModel.size(), fixedModel.size()));
    }
    private void updateStatus(){
        NonWithdrawableAccount sel=selectedAny();
        if(sel==null){ statusLabel.setText("Step 2 → Select an account to get started  •  Deposits work for all, withdrawals only for Saving/Current"); return; }
        if(sel instanceof FixedDeposit) statusLabel.setText("Selected " + sel.getClass().getSimpleName() + " (₹" + sel.getBalance() + ") — deposit-only → Step 3 ready");
        else statusLabel.setText("Selected " + sel.getClass().getSimpleName() + " (₹" + sel.getBalance() + ") — you can deposit or withdraw → Step 3 ready");
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
        t.setForeground(active ? color.darker() : TEXT_DARK);
        JLabel s = new JLabel(sub);
        s.setFont(new Font("SansSerif", Font.PLAIN, 10));
        s.setForeground(TEXT_MUTED);
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
            "Step 3 of 5 — Enter amount → Deposit / Withdraw / View details",
            "Step 4 of 5 — Click See safe demo to see LSP OK in a loop",
            "Step 5 of 5 — Click View UML to see why this design is clean"
        };
        if (step >= 1 && step <= 5) stepLabel.setText(msgs[step - 1]);
    }

    private void showGuide() {
        JOptionPane.showMessageDialog(this,
                "<html><body style='width:560px; font-family:sans-serif;'>"
                + "<h2 style='color:#4f46e5; margin:0;'>How to use — 5 easy steps (Following • LSP OK)</h2>"
                + "<p style='color:#64748b; margin:4 0 12 0;'>This demo shows a <b>clean design</b> — no surprises, every account does what its type promises.</p>"
                + "<table style='width:100%; border-collapse:collapse; font-size:12px;'>"
                + "<tr><td style='padding:6 8; background:#eef2ff; border-radius:8px;'><b>① Create</b></td><td style='padding:6 8;'>Choose <b>Type</b> + <b>Initial ₹</b> → <b>Add account</b>. 3 demo accounts are already there.</td></tr>"
                + "<tr><td style='padding:6 8; background:#ecfdf5; border-radius:8px;'><b>② Select</b></td><td style='padding:6 8;'>Click any row in the two lists. Selected row is highlighted. Status below total shows what you can do.</td></tr>"
                + "<tr><td style='padding:6 8; background:#fef3c7; border-radius:8px;'><b>③ Transact</b></td><td style='padding:6 8;'>Enter <b>Amount ₹</b> then:<br>• <b>Deposit</b> — works for all<br>• <b>Withdraw</b> — works for Everyday (Saving/Current); Fixed shows friendly hint, not an error<br>• <b>View details</b> — shows balance & kind</td></tr>"
                + "<tr><td style='padding:6 8; background:#eef2ff; border-radius:8px;'><b>④ Demo Safe</b></td><td style='padding:6 8;'>Click <b>See safe demo</b> — loops both lists: withdraw for Everyday, deposit for Fixed — all succeed.</td></tr>"
                + "<tr><td style='padding:6 8; background:#f5f3ff; border-radius:8px;'><b>⑤ View UML</b></td><td style='padding:6 8;'>Click <b>View UML</b> — see two hierarchies: <code>Withdrawable vs NonWithdrawable</code> — no child breaks parent.</td></tr>"
                + "</table>"
                + "<p style='background:#ecfdf5; padding:8; border-radius:8px; color:#065f46;'><b>💡 Try this:</b> Select <b>🐖 Saving</b> → Amount 200 → <b>Withdraw</b> → Done. Then select <b>🏦 Fixed Deposit</b> → <b>Withdraw</b> → see friendly guidance (not an error).</p>"
                + "<p style='color:#94a3b8; font-size:11px;'>Use the yellow <b>❓ How to use</b> button anytime. Top step strip shows where you are.</p>"
                + "</body></html>",
                "How to use — Step by step", JOptionPane.INFORMATION_MESSAGE);
    }
    private void showUmlDiagram() {
        JDialog dlg = new JDialog(this, "UML — Liskov Following (Clean Hierarchy)", true);
        dlg.setSize(860, 620);
        dlg.setLocationRelativeTo(this);
        dlg.setLayout(new BorderLayout(0, 0));
        dlg.getContentPane().setBackground(BG_MAIN);

        // Header
        JPanel head = new JPanel(new BorderLayout());
        head.setBackground(Color.WHITE);
        head.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_SOFT),
                new EmptyBorder(14, 18, 14, 18)
        ));
        JLabel hTitle = new JLabel("Class Diagram — LSP Following  •  Two-Type Separation");
        hTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
        hTitle.setForeground(TEXT_DARK);
        JLabel hSub = new JLabel("NonWithdrawableAccount is the root  •  WithdrawableAccount adds withdraw()  •  FixedDeposit never inherits what it can't do");
        hSub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        hSub.setForeground(TEXT_MUTED);
        JPanel hText = new JPanel(new BorderLayout(0, 2));
        hText.setBackground(Color.WHITE);
        hText.add(hTitle, BorderLayout.NORTH);
        hText.add(hSub, BorderLayout.SOUTH);
        JLabel hBadge = new JLabel("  ✓  LSP OK  ");
        hBadge.setFont(new Font("SansSerif", Font.BOLD, 11));
        hBadge.setForeground(VIOLET);
        hBadge.setBackground(VIOLET_SOFT);
        hBadge.setOpaque(true);
        hBadge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(221, 214, 254), 1, true),
                new EmptyBorder(6, 12, 6, 12)
        ));
        head.add(hText, BorderLayout.WEST);
        head.add(hBadge, BorderLayout.EAST);

        UmlPanel uml = new UmlPanel();
        JScrollPane scroll = new JScrollPane(uml);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(BG_MAIN);
        uml.setPreferredSize(new Dimension(820, 460));

        // Legend / explanation
        JPanel legend = new JPanel(new BorderLayout(0, 6));
        legend.setBackground(new Color(248, 250, 252));
        legend.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_SOFT),
                new EmptyBorder(10, 18, 10, 18)
        ));
        JLabel leg1 = new JLabel("▵  Hollow triangle = extends (generalization)   •   Solid boxes = classes   •   <<abstract>> = cannot be instantiated");
        leg1.setFont(new Font("SansSerif", Font.PLAIN, 11));
        leg1.setForeground(SLATE);
        JLabel leg2 = new JLabel("Why this follows LSP:  Every WithdrawableAccount can be used where NonWithdrawableAccount is expected — deposit() always works. FixedDeposit stays out of the withdraw hierarchy, so no child throws “not supported”.");
        leg2.setFont(new Font("SansSerif", Font.PLAIN, 11));
        leg2.setForeground(TEXT_MUTED);
        legend.add(leg1, BorderLayout.NORTH);
        legend.add(leg2, BorderLayout.SOUTH);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottom.setBackground(Color.WHITE);
        bottom.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_SOFT));
        JButton close = softButton("Close", VIOLET, Color.WHITE);
        close.addActionListener(e -> dlg.dispose());
        JButton exportHint = new JButton("Tip: Screenshot to share");
        exportHint.setFont(new Font("SansSerif", Font.PLAIN, 11));
        exportHint.setForeground(TEXT_MUTED);
        exportHint.setBackground(Color.WHITE);
        exportHint.setFocusPainted(false);
        exportHint.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_SOFT, 1, true),
                new EmptyBorder(6, 12, 6, 12)
        ));
        exportHint.setEnabled(false);
        bottom.add(exportHint);
        bottom.add(close);

        dlg.add(head, BorderLayout.NORTH);
        dlg.add(scroll, BorderLayout.CENTER);
        JPanel southWrap = new JPanel(new BorderLayout(0,0));
        southWrap.add(legend, BorderLayout.NORTH);
        southWrap.add(bottom, BorderLayout.SOUTH);
        dlg.add(southWrap, BorderLayout.SOUTH);
        dlg.setVisible(true);
    }

    // Custom painted UML — friendly, non-terminal style
    private class UmlPanel extends JPanel {
        UmlPanel() { setBackground(BG_MAIN); }
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int W = getWidth();
            // Box metrics
            int boxW = 220, boxH = 86;
            // Positions — centered layout
            int cx = W / 2;
            int topY = 28;
            int midY = 164;
            int botY = 300;

            // Boxes: root top center, two children mid row, two leaves bottom row
            Rect rRoot = new Rect(cx - boxW/2, topY, boxW, boxH);
            Rect rWithdraw = new Rect(cx - 150 - boxW, midY, boxW, boxH);
            Rect rFixed = new Rect(cx + 150, midY, boxW, boxH);
            Rect rSaving = new Rect(cx - 150 - boxW, botY, boxW, boxH);
            Rect rCurrent = new Rect(cx - 150, botY, boxW, boxH);

            // Draw connections first (under boxes)
            g2.setStroke(new BasicStroke(1.6f));
            g2.setColor(new Color(148, 163, 184));
            // FixedDeposit -> NonWithdrawable
            drawInheritance(g2, rFixed, rRoot);
            // Withdrawable -> NonWithdrawable
            drawInheritance(g2, rWithdraw, rRoot);
            // Saving -> Withdrawable
            drawInheritance(g2, rSaving, rWithdraw);
            // Current -> Withdrawable
            drawInheritance(g2, rCurrent, rWithdraw);

            // Small “extends” labels near arrows
            g2.setFont(new Font("SansSerif", Font.ITALIC, 10));
            g2.setColor(TEXT_MUTED);
            g2.drawString("extends", rFixed.centerX() - 18, rFixed.y - 10);
            g2.drawString("extends", rWithdraw.centerX() - 18, rWithdraw.y - 10);
            g2.drawString("extends", rSaving.centerX() + 18, rSaving.y - 8);
            g2.drawString("extends", rCurrent.centerX() - 36, rCurrent.y - 8);

            // Draw boxes
            drawClassBox(g2, rRoot, "<<abstract>>", "NonWithdrawableAccount", new String[]{"- balance: Integer", "+ deposit(amount)", "+ getBalance() / setBalance()"}, PRIMARY, PRIMARY_SOFT, true);
            drawClassBox(g2, rWithdraw, "<<abstract>>", "WithdrawableAccount", new String[]{"+ withdrawl(amount) : void", "inherits deposit()"}, VIOLET, VIOLET_SOFT, true);
            drawClassBox(g2, rFixed, "", "FixedDeposit", new String[]{"+ deposit(amount)", "no withdrawl() here ✓"}, AMBER, AMBER_SOFT, false);
            drawClassBox(g2, rSaving, "", "SavingAccount", new String[]{"+ deposit(amount)", "+ withdrawl(amount)"}, TEAL, TEAL_SOFT, false);
            drawClassBox(g2, rCurrent, "", "CurrentAccount", new String[]{"+ deposit(amount)", "+ withdrawl(amount)"}, TEAL, TEAL_SOFT, false);

            // Friendly callout
            g2.setFont(new Font("SansSerif", Font.BOLD, 11));
            g2.setColor(new Color(16, 185, 129));
            String callout = "✓  No child narrows parent — LSP followed";
            int cw = g2.getFontMetrics().stringWidth(callout) + 24;
            int ch = 28;
            int cx2 = W/2 - cw/2;
            int cy2 = botY + boxH + 28;
            g2.setColor(new Color(236, 253, 245));
            g2.fillRoundRect(cx2, cy2, cw, ch, 14, 14);
            g2.setColor(new Color(16, 185, 129));
            g2.drawRoundRect(cx2, cy2, cw, ch, 14, 14);
            g2.drawString(callout, cx2 + 12, cy2 + 18);

            // Side note — lists at client
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g2.setColor(TEXT_MUTED);
            String note1 = "Client keeps two lists:";
            String note2 = "List<WithdrawableAccount>  +  List<NonWithdrawableAccount>";
            String note3 = "→ type-safe, no instanceof checks needed for withdraw";
            int nx = W - 300;
            int ny = botY + boxH + 70;
            // note card
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(nx - 12, ny - 16, 290, 52, 10, 10);
            g2.setColor(BORDER_SOFT);
            g2.drawRoundRect(nx - 12, ny - 16, 290, 52, 10, 10);
            g2.setColor(TEXT_DARK);
            g2.setFont(new Font("SansSerif", Font.BOLD, 10));
            g2.drawString(note1, nx, ny);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g2.setColor(SLATE);
            g2.drawString(note2, nx, ny + 14);
            g2.drawString(note3, nx, ny + 28);
        }

        private void drawClassBox(Graphics2D g2, Rect r, String stereotype, String name, String[] members, Color accent, Color soft, boolean isAbstract) {
            // Shadow
            g2.setColor(new Color(0,0,0,10));
            g2.fillRoundRect(r.x+3, r.y+3, r.w, r.h, 14, 14);
            // Fill
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(r.x, r.y, r.w, r.h, 14, 14);
            // Top accent bar
            g2.setColor(soft);
            g2.fillRoundRect(r.x, r.y, r.w, 28, 14, 14);
            g2.fillRect(r.x, r.y+14, r.w, 14);
            g2.setColor(accent);
            g2.fillRoundRect(r.x, r.y, r.w, 3, 3, 3);
            // Border
            g2.setColor(BORDER_SOFT);
            g2.setStroke(new BasicStroke(1.2f));
            g2.drawRoundRect(r.x, r.y, r.w, r.h, 14, 14);
            // Stereotype
            if (!stereotype.isEmpty()) {
                g2.setFont(new Font("SansSerif", Font.ITALIC, 10));
                g2.setColor(TEXT_MUTED);
                int sw = g2.getFontMetrics().stringWidth(stereotype);
                g2.drawString(stereotype, r.x + (r.w - sw)/2, r.y + 14);
            }
            // Name
            g2.setFont(new Font("SansSerif", Font.BOLD, 12));
            g2.setColor(TEXT_DARK);
            if (isAbstract) g2.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
            int nw = g2.getFontMetrics().stringWidth(name);
            g2.drawString(name, r.x + (r.w - nw)/2, r.y + (stereotype.isEmpty()? 19 : 26));
            // Divider
            g2.setColor(BORDER_SOFT);
            g2.drawLine(r.x + 12, r.y + 32, r.x + r.w - 12, r.y + 32);
            // Members
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g2.setColor(SLATE);
            int my = r.y + 46;
            for (String m : members) {
                g2.drawString(m, r.x + 12, my);
                my += 13;
            }
            // Abstract badge
            if (isAbstract) {
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

        private void drawInheritance(Graphics2D g2, Rect child, Rect parent) {
            int x1 = child.centerX();
            int y1 = child.y;
            int x2 = parent.centerX();
            int y2 = parent.y + parent.h;
            // L-shaped if x differs
            g2.setColor(new Color(100, 116, 139));
            if (Math.abs(x1 - x2) < 6) {
                g2.drawLine(x1, y1, x2, y2);
                drawTriangle(g2, x2, y2, true);
            } else {
                int midY = (y1 + y2)/2;
                g2.drawLine(x1, y1, x1, midY);
                g2.drawLine(x1, midY, x2, midY);
                g2.drawLine(x2, midY, x2, y2);
                drawTriangle(g2, x2, y2, true);
            }
        }

        private void drawTriangle(Graphics2D g2, int x, int y, boolean up) {
            Polygon tri = new Polygon();
            int s = 9;
            if (up) {
                tri.addPoint(x, y);
                tri.addPoint(x - s, y + s + 3);
                tri.addPoint(x + s, y + s + 3);
            } else {
                tri.addPoint(x, y);
                tri.addPoint(x - s, y - s - 3);
                tri.addPoint(x + s, y - s - 3);
            }
            g2.setColor(Color.WHITE);
            g2.fillPolygon(tri);
            g2.setColor(new Color(100, 116, 139));
            g2.setStroke(new BasicStroke(1.4f));
            g2.drawPolygon(tri);
        }

        private class Rect { int x,y,w,h; Rect(int x,int y,int w,int h){this.x=x;this.y=y;this.w=w;this.h=h;} int centerX(){return x+w/2;} }
    }

    private void log(String msg){ logArea.append(msg+"\n"); logArea.setCaretPosition(logArea.getDocument().getLength()); System.out.println(msg); }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }catch(Exception ignored){}
            new BankFollowingGUI().setVisible(true);
        });
    }
}
