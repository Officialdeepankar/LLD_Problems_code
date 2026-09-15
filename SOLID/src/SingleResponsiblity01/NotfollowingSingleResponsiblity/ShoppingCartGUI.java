package SingleResponsiblity01.NotfollowingSingleResponsiblity;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Colorful GUI version of NotFollowing Single Responsibility example.
 * ShoppingCart still violates SRP (cart + payment + invoice in one class).
 */
public class ShoppingCartGUI extends JFrame {

    private final ShoppingCart shoppingCart = new ShoppingCart();
    private final DefaultListModel<Product> listModel = new DefaultListModel<>();
    private final JList<Product> cartList = new JList<>(listModel);

    private final JTextField nameField = new JTextField(12);
    private final JTextField priceField = new JTextField(6);
    private final JLabel totalLabel = new JLabel("Total: ₹0  (items: 0)");
    private final JTextArea logArea = new JTextArea(7, 30);

    // Color palette
    private static final Color BG_MAIN = new Color(245, 247, 250);
    private static final Color PURPLE = new Color(108, 92, 231);
    private static final Color PINK = new Color(253, 121, 168);
    private static final Color GREEN = new Color(0, 184, 148);
    private static final Color BLUE = new Color(9, 132, 227);
    private static final Color ORANGE = new Color(253, 203, 110);
    private static final Color RED = new Color(255, 118, 117);
    private static final Color DARK = new Color(45, 52, 54);
    private static final Color VIOLET = new Color(139, 92, 246);
    private static final Color VIOLET_SOFT = new Color(245, 243, 255);
    private static final Color BORDER_SOFT = new Color(226, 232, 240);

    public ShoppingCartGUI() {
        super("🛒 ShoppingCart - Not Following SRP  |  Colorful Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(780, 620);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_MAIN);
        setLayout(new BorderLayout(0, 0));

        // ===== Header with Gradient =====
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, PURPLE, getWidth(), 0, PINK);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setLayout(new BorderLayout());
        header.setPreferredSize(new Dimension(780, 70));
        header.setBorder(new EmptyBorder(12, 20, 12, 20));
        JLabel title = new JLabel("🛒  Shopping Cart  —  SRP Violation Demo");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        JLabel subtitle = new JLabel("One class does EVERYTHING: Cart • Payment • Invoice  ❌  (Should be separate!)");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(new Color(255, 255, 255, 220));
        JPanel titleBox = new JPanel(new BorderLayout());
        titleBox.setOpaque(false);
        titleBox.add(title, BorderLayout.NORTH);
        titleBox.add(subtitle, BorderLayout.SOUTH);
        JLabel badge = new JLabel("  Not Following SRP  ");
        badge.setFont(new Font("SansSerif", Font.BOLD, 11));
        badge.setForeground(PURPLE);
        badge.setBackground(Color.WHITE);
        badge.setOpaque(true);
        badge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                new EmptyBorder(6, 12, 6, 12)));
        header.add(titleBox, BorderLayout.WEST);
        header.add(badge, BorderLayout.EAST);

        // ===== Input Panel (colorful) =====
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 12));
        inputPanel.setBackground(new Color(255, 255, 255));
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 3, 0, new Color(108, 92, 231, 60)),
                new EmptyBorder(5, 10, 5, 10)));
        JLabel nameLbl = new JLabel("📦 Name:");
        nameLbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        nameLbl.setForeground(DARK);
        JLabel priceLbl = new JLabel("💰 Price: ₹");
        priceLbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        priceLbl.setForeground(DARK);
        styleTextField(nameField);
        styleTextField(priceField);
        nameField.setToolTipText("e.g. Clinic Plus, iPhone");
        priceField.setToolTipText("e.g. 1000");
        JButton addBtn = createColorButton("➕ Add to Cart", GREEN, Color.WHITE);
        JButton removeBtn = createColorButton("🗑 Remove Selected", RED, Color.WHITE);
        inputPanel.add(nameLbl);
        inputPanel.add(nameField);
        inputPanel.add(priceLbl);
        inputPanel.add(priceField);
        inputPanel.add(addBtn);
        inputPanel.add(removeBtn);

        // ===== Center - Cart List =====
        cartList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cartList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cartList.setFixedCellHeight(32);
        cartList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                lbl.setBorder(new EmptyBorder(4, 12, 4, 12));
                if (value instanceof Product) {
                    Product p = (Product) value;
                    lbl.setText(String.format("  %d.  %s  —  ₹%d", index + 1, p.getName(), p.getPrice()));
                }
                if (isSelected) {
                    lbl.setBackground(PURPLE);
                    lbl.setForeground(Color.WHITE);
                } else {
                    lbl.setBackground(index % 2 == 0 ? Color.WHITE : new Color(245, 243, 255));
                    lbl.setForeground(DARK);
                }
                lbl.setOpaque(true);
                lbl.setFont(new Font("SansSerif", index % 2 == 0 ? Font.PLAIN : Font.BOLD, 13));
                return lbl;
            }
        });
        JScrollPane listScroll = new JScrollPane(cartList);
        listScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(108, 92, 231, 80), 2, true),
                " 🧺 Cart Items  (Product 1 — * ShoppingCart) ", 0, 0,
                new Font("SansSerif", Font.BOLD, 12), PURPLE));
        listScroll.getViewport().setBackground(Color.WHITE);

        // Total Banner
        JPanel totalBanner = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, new Color(0, 184, 148), getWidth(), 0, new Color(0, 206, 201)));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
            }
        };
        totalBanner.setOpaque(false);
        totalBanner.setBorder(new EmptyBorder(10, 16, 10, 16));
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        totalLabel.setForeground(Color.WHITE);
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalBanner.add(totalLabel, BorderLayout.CENTER);
        JLabel hint = new JLabel("💡 ShoppingCart.TotalCost() is called here");
        hint.setFont(new Font("SansSerif", Font.ITALIC, 10));
        hint.setForeground(new Color(255, 255, 255, 200));
        hint.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel totalWrap = new JPanel(new BorderLayout(0, 6));
        totalWrap.setOpaque(false);
        totalWrap.setBorder(new EmptyBorder(8, 0, 0, 0));
        totalWrap.add(totalBanner, BorderLayout.CENTER);
        totalWrap.add(hint, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 8));
        centerPanel.setBackground(BG_MAIN);
        centerPanel.setBorder(new EmptyBorder(10, 14, 10, 14));
        centerPanel.add(listScroll, BorderLayout.CENTER);
        centerPanel.add(totalWrap, BorderLayout.SOUTH);

        // ===== Bottom - Actions & Log =====
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        actionPanel.setBackground(new Color(255, 255, 255));
        actionPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(253, 121, 168, 80), 2, true),
                new EmptyBorder(6, 6, 6, 6)));
        // Wrap with title label
        JPanel actionWrap = new JPanel(new BorderLayout());
        actionWrap.setBackground(BG_MAIN);
        JLabel actionTitle = new JLabel("  ⚡ Actions  (All inside ONE ShoppingCart class — SRP Violation!)");
        actionTitle.setFont(new Font("SansSerif", Font.BOLD, 11));
        actionTitle.setForeground(PINK);
        actionTitle.setBorder(new EmptyBorder(0, 4, 4, 4));
        actionWrap.add(actionTitle, BorderLayout.NORTH);
        actionWrap.add(actionPanel, BorderLayout.CENTER);

        JButton totalBtn = createColorButton("🧮 Calculate Total", BLUE, Color.WHITE);
        JButton payBtn = createColorButton("💳 Handle Payment", new Color(230, 126, 34), Color.WHITE);
        JButton invoiceBtn = createColorButton("🧾 Print Invoice", new Color(142, 68, 173), Color.WHITE);
        JButton clearBtn = createColorButton("🧹 Clear Cart", new Color(99, 110, 114), Color.WHITE);
        JButton umlBtn = createColorButton("▭ View UML", VIOLET, Color.WHITE);
        umlBtn.setToolTipText("Show UML — why this violates SRP");
        actionPanel.add(totalBtn);
        actionPanel.add(payBtn);
        actionPanel.add(invoiceBtn);
        actionPanel.add(clearBtn);
        actionPanel.add(umlBtn);

        // Log area - dark colorful terminal style
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        logArea.setBackground(new Color(45, 52, 54));
        logArea.setForeground(new Color(255, 234, 167));
        logArea.setCaretColor(Color.WHITE);
        logArea.setBorder(new EmptyBorder(8, 8, 8, 8));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(DARK, 2, true),
                " 📜 Log / Invoice Output ", 0, 0,
                new Font("SansSerif", Font.BOLD, 11), DARK));
        logScroll.setPreferredSize(new Dimension(780, 160));

        JPanel southPanel = new JPanel(new BorderLayout(0, 8));
        southPanel.setBackground(BG_MAIN);
        southPanel.setBorder(new EmptyBorder(0, 14, 14, 14));
        southPanel.add(actionWrap, BorderLayout.NORTH);
        southPanel.add(logScroll, BorderLayout.CENTER);

        // Assemble
        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.add(header, BorderLayout.NORTH);
        topWrapper.add(inputPanel, BorderLayout.SOUTH);

        add(topWrapper, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        // Listeners
        addBtn.addActionListener(e -> addProduct());
        removeBtn.addActionListener(e -> removeSelected());
        totalBtn.addActionListener(e -> { updateTotal(); log("🧮 Total recalculated: ₹" + shoppingCart.TotalCost()); });
        payBtn.addActionListener(e -> handlePayment());
        invoiceBtn.addActionListener(e -> printInvoice());
        clearBtn.addActionListener(e -> clearCart());
        umlBtn.addActionListener(e -> showUmlDiagram());
        priceField.addActionListener(e -> addProduct());
        nameField.addActionListener(e -> priceField.requestFocus());

        // Demo data
        addProductToCart(new Product("Clinic Plus", 2));
        addProductToCart(new Product("earphones", 1000));
        addProductToCart(new Product("Book - LLD", 499));
        log("✨ GUI loaded with 3 demo products. Try: Add your own, Remove, Pay, Invoice!");
    }

    private void styleTextField(JTextField tf) {
        tf.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tf.setBackground(new Color(248, 249, 250));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(108, 92, 231, 90), 1, true),
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
        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(bg.brighter()); }
            @Override public void mouseExited(java.awt.event.MouseEvent e) { btn.setBackground(bg); }
        });
        return btn;
    }

    private void addProduct() {
        String name = nameField.getText().trim();
        String priceStr = priceField.getText().trim();
        if (name.isEmpty() || priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter name and price", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int price = Integer.parseInt(priceStr);
            if (price < 0) throw new NumberFormatException();
            Product p = new Product(name, price);
            addProductToCart(p);
            nameField.setText("");
            priceField.setText("");
            nameField.requestFocus();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Price must be a positive integer", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addProductToCart(Product p) {
        shoppingCart.AddProduct(p);
        listModel.addElement(p);
        updateTotal();
        log("✅ Added: " + p.getName() + " — ₹" + p.getPrice());
    }

    private void removeSelected() {
        Product selected = cartList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a product to remove", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        shoppingCart.RemoveProduct(selected);
        listModel.removeElement(selected);
        updateTotal();
        log("🗑 Removed: " + selected.getName());
    }

    private void updateTotal() {
        int total = shoppingCart.TotalCost();
        totalLabel.setText(String.format("💰  Total: ₹%d   •   %d item(s)  ", total, listModel.size()));
    }

    private void handlePayment() {
        if (listModel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty!", "Payment", JOptionPane.WARNING_MESSAGE);
            return;
        }
        shoppingCart.handlePayment(); // SRP violation call
        String msg = "💳 Handling payments for ₹" + shoppingCart.TotalCost() + "  [via ShoppingCart.handlePayment()]";
        log(">>> " + msg);
        JOptionPane.showMessageDialog(this,
                "<html><h2 style='color:#e67e22;'>Payment Successful!</h2><p>Paid: <b>₹" + shoppingCart.TotalCost() + "</b></p></html>",
                "Payment", JOptionPane.INFORMATION_MESSAGE);
    }

    private void printInvoice() {
        if (listModel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty!", "Invoice", JOptionPane.WARNING_MESSAGE);
            return;
        }
        shoppingCart.InvoicePrint();
        StringBuilder sb = new StringBuilder();
        sb.append("╔══════════════ INVOICE ══════════════\n");
        for (int i = 0; i < listModel.size(); i++) {
            Product p = listModel.get(i);
            sb.append(String.format("║ %d. %-20s  ₹%4d\n", i + 1, p.getName(), p.getPrice()));
        }
        sb.append("╠════════════════════════════════════\n");
        sb.append(String.format("║ TOTAL:              ₹%4d\n", shoppingCart.TotalCost()));
        sb.append("╚════════════════════════════════════\n");
        log(sb.toString());

        JTextArea invoiceArea = new JTextArea(sb.toString());
        invoiceArea.setFont(new Font("Monospaced", Font.BOLD, 13));
        invoiceArea.setBackground(new Color(255, 250, 240));
        invoiceArea.setForeground(DARK);
        invoiceArea.setEditable(false);
        invoiceArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        JOptionPane.showMessageDialog(this, new JScrollPane(invoiceArea), "🧾 Invoice", JOptionPane.PLAIN_MESSAGE);
    }

    private void clearCart() {
        for (int i = listModel.size() - 1; i >= 0; i--) {
            shoppingCart.RemoveProduct(listModel.get(i));
        }
        listModel.clear();
        updateTotal();
        log("🧹 Cart cleared.");
    }

    private void showUmlDiagram() {
        JDialog dlg = new JDialog(this, "UML — SRP Not Following (Violation)", true);
        dlg.setSize(880, 620);
        dlg.setLocationRelativeTo(this);
        dlg.setLayout(new BorderLayout(0, 0));
        dlg.getContentPane().setBackground(BG_MAIN);

        JPanel head = new JPanel(new BorderLayout());
        head.setBackground(Color.WHITE);
        head.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_SOFT),
                new EmptyBorder(14, 18, 14, 18)
        ));
        JLabel hTitle = new JLabel("Class Diagram — SRP Violation  •  One Class Does Everything");
        hTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        hTitle.setForeground(DARK);
        JLabel hSub = new JLabel("ShoppingCart handles cart + payment + invoice — 3 reasons to change, violates Single Responsibility");
        hSub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        hSub.setForeground(new Color(100, 116, 139));
        JPanel hText = new JPanel(new BorderLayout(0, 2));
        hText.setBackground(Color.WHITE);
        hText.add(hTitle, BorderLayout.NORTH);
        hText.add(hSub, BorderLayout.SOUTH);
        JLabel hBadge = new JLabel("  ✗  SRP Broken  ");
        hBadge.setFont(new Font("SansSerif", Font.BOLD, 11));
        hBadge.setForeground(new Color(220, 38, 38));
        hBadge.setBackground(new Color(254, 242, 242));
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
        uml.setPreferredSize(new Dimension(840, 480));

        JPanel legend = new JPanel(new BorderLayout(0, 6));
        legend.setBackground(new Color(248, 250, 252));
        legend.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_SOFT),
                new EmptyBorder(10, 18, 10, 18)
        ));
        JLabel leg1 = new JLabel("◆  Association  •  Solid box = class  •  Red = violation (too many responsibilities)");
        leg1.setFont(new Font("SansSerif", Font.PLAIN, 11));
        leg1.setForeground(new Color(71, 85, 105));
        JLabel leg2 = new JLabel("Why it violates SRP: ShoppingCart should only manage cart. Payment & invoice should be separate services. One change in payment logic forces re-test of cart.");
        leg2.setFont(new Font("SansSerif", Font.PLAIN, 11));
        leg2.setForeground(new Color(100, 116, 139));
        legend.add(leg1, BorderLayout.NORTH);
        legend.add(leg2, BorderLayout.SOUTH);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottom.setBackground(Color.WHITE);
        bottom.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_SOFT));
        JButton close = createColorButton("Close", VIOLET, Color.WHITE);
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
        UmlPanel() { setBackground(BG_MAIN); }
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            int W = getWidth();
            int boxW = 260, boxH = 148;
            int smallW = 200, smallH = 96;
            // Large violating ShoppingCart in center
            int cx = W/2;
            int cartX = cx - boxW/2;
            int cartY = 40;
            // Product below-left
            int prodX = 60;
            int prodY = 280;
            // Ghost correct boxes (faded) for comparison on right
            int payX = W - smallW - 60;
            int payY = 280;
            int invX = payX;
            int invY = 400;

            // Draw violating ShoppingCart
            drawViolationBox(g2, cartX, cartY, boxW, boxH);
            // Draw Product
            drawSimpleBox(g2, prodX, prodY, smallW, smallH, "Product", new String[]{"- name: String", "- price: Integer", "+ getName() / getPrice()"}, new Color(71,85,105), Color.WHITE, false);
            // Draw separate responsibilities (ghost) to hint fix
            g2.setColor(new Color(100,116,139, 80));
            g2.setFont(new Font("SansSerif", Font.ITALIC, 10));
            g2.drawString("Should be separate →", payX - 110, payY + 18);
            drawSimpleBox(g2, payX, payY, smallW, smallH, "PaymentProcessor", new String[]{"+ handlePayment(cart)"}, new Color(16,185,129), new Color(236,253,245), true);
            drawSimpleBox(g2, invX, invY, smallW, smallH, "InvoicePrinter", new String[]{"+ printInvoice(cart)"}, new Color(6,182,212), new Color(236,253,245), true);

            // Associations
            g2.setColor(new Color(71,85,105));
            g2.setStroke(new BasicStroke(1.4f));
            // Product association (ShoppingCart * — 1 Product) — violation has direct list
            int cartCX = cartX + boxW/2;
            int cartBottom = cartY + boxH;
            g2.drawLine(cartCX - 40, cartBottom, prodX + smallW/2, prodY);
            drawDiamond(g2, cartCX - 40, cartBottom);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g2.setColor(new Color(100,116,139));
            g2.drawString("1 — *", (cartCX - 40 + prodX + smallW/2)/2 - 10, (cartBottom + prodY)/2);
            g2.drawString("ShoppingCart holds List<Product>", prodX, prodY - 10);
            // Red responsibilities inside cart
            g2.setFont(new Font("SansSerif", Font.BOLD, 11));
            g2.setColor(new Color(220,38,38));
            g2.drawString("✗  3 responsibilities in ONE class", cartX, cartY + boxH + 18);

            // Callout
            String callout = "✗  SRP Violated — 3 reasons to change";
            g2.setFont(new Font("SansSerif", Font.BOLD, 11));
            int cw = g2.getFontMetrics().stringWidth(callout) + 24;
            int ch = 28;
            int cx2 = W/2 - cw/2;
            int cy2 = cartY + boxH + 30;
            // already drawn above, now bottom hint
            g2.setColor(new Color(254,242,242));
            g2.fillRoundRect(cx2, cy2, cw, ch, 14, 14);
            g2.setColor(new Color(220,38,38));
            g2.drawRoundRect(cx2, cy2, cw, ch, 14, 14);
            g2.drawString(callout, cx2 + 12, cy2 + 18);

            // Bottom note
            g2.setFont(new Font("SansSerif", Font.ITALIC, 10));
            g2.setColor(new Color(100,116,139));
            g2.drawString("Fix: split into ShoppingCart + PaymentProcessor + InvoicePrinter (see Following case).", 18, getHeight() - 14);
        }
        private void drawViolationBox(Graphics2D g2, int x, int y, int w, int h) {
            g2.setColor(new Color(0,0,0,10));
            g2.fillRoundRect(x+3, y+3, w, h, 14, 14);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(x, y, w, h, 14, 14);
            g2.setColor(new Color(254,242,242));
            g2.fillRoundRect(x, y, w, 28, 14, 14);
            g2.fillRect(x, y+14, w, 14);
            g2.setColor(new Color(220,38,38));
            g2.fillRoundRect(x, y, w, 3, 3, 3);
            g2.setColor(BORDER_SOFT);
            g2.setStroke(new BasicStroke(1.2f));
            g2.drawRoundRect(x, y, w, h, 14, 14);
            // dashed red border to emphasize violation
            g2.setColor(new Color(220,38,38));
            g2.setStroke(new BasicStroke(1.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{6,4}, 0));
            g2.drawRoundRect(x, y, w, h, 14, 14);
            g2.setStroke(new BasicStroke(1.2f));
            g2.setFont(new Font("SansSerif", Font.BOLD, 12));
            g2.setColor(new Color(30,41,59));
            String name = "ShoppingCart  ✗";
            int nw = g2.getFontMetrics().stringWidth(name);
            g2.drawString(name, x + (w - nw)/2, y + 19);
            g2.setColor(BORDER_SOFT);
            g2.drawLine(x+12, y+32, x+w-12, y+32);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            String[] members = {"+ AddProduct(p) / RemoveProduct(p)", "+ TotalCost(): Integer   ← cart", "+ handlePayment()   ← should be separate ✗", "+ InvoicePrint()      ← should be separate ✗", "- cart: List<Product>"};
            int my = y + 48;
            for (String m : members) {
                if (m.contains("✗")) g2.setColor(new Color(220,38,38));
                else g2.setColor(new Color(71,85,105));
                g2.drawString(m, x+12, my);
                my += 14;
            }
            // badge
            String badge = "violates SRP";
            g2.setFont(new Font("SansSerif", Font.BOLD, 9));
            int bw = g2.getFontMetrics().stringWidth(badge) + 10;
            int bx = x + w - bw - 8;
            int by = y + 8;
            g2.setColor(new Color(254,242,242));
            g2.fillRoundRect(bx, by, bw, 14, 7, 7);
            g2.setColor(new Color(220,38,38));
            g2.drawRoundRect(bx, by, bw, 14, 7, 7);
            g2.drawString(badge, bx+5, by+10);
        }
        private void drawSimpleBox(Graphics2D g2, int x, int y, int w, int h, String name, String[] members, Color accent, Color soft, boolean faded) {
            if (faded) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f));
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(x, y, w, h, 12, 12);
            g2.setColor(soft);
            g2.fillRoundRect(x, y, w, 24, 12, 12);
            g2.fillRect(x, y+12, w, 12);
            g2.setColor(accent);
            g2.fillRoundRect(x, y, w, 3, 3, 3);
            g2.setColor(BORDER_SOFT);
            g2.setStroke(new BasicStroke(1.1f));
            g2.drawRoundRect(x, y, w, h, 12, 12);
            g2.setFont(new Font("SansSerif", Font.BOLD, 11));
            g2.setColor(new Color(30,41,59));
            int nw = g2.getFontMetrics().stringWidth(name);
            g2.drawString(name, x + (w - nw)/2, y + 17);
            g2.setColor(BORDER_SOFT);
            g2.drawLine(x+10, y+26, x+w-10, y+26);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g2.setColor(new Color(71,85,105));
            int my = y + 42;
            for (String m : members) { g2.drawString(m, x+10, my); my+=13; }
            if (faded) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
        private void drawDiamond(Graphics2D g2, int x, int y) {
            Polygon p = new Polygon();
            p.addPoint(x, y);
            p.addPoint(x+7, y+7);
            p.addPoint(x, y+14);
            p.addPoint(x-7, y+7);
            g2.setColor(Color.WHITE);
            g2.fillPolygon(p);
            g2.setColor(new Color(71,85,105));
            g2.drawPolygon(p);
        }
    }

    private void log(String msg) {
        logArea.append(msg + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
        System.out.println(msg);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Use system L&F for native look, but keep our colors
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new ShoppingCartGUI().setVisible(true);
        });
    }
}
