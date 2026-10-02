package SingleResponsiblity01.FollowingSingleResponsibilty;

import SingleResponsiblity01.NotfollowingSingleResponsiblity.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Friendly GUI for Following Single Responsibility — cart only does cart.
 * Payment and Invoice are separate services.
 */
public class ShoppingCartFollowingGUI extends JFrame {

    private final ShoppingCart shoppingCart = new ShoppingCart();
    private final PaymentProcessor paymentProcessor = new PaymentProcessor();
    private final InvoicePrinter invoicePrinter = new InvoicePrinter();

    private final DefaultListModel<Product> listModel = new DefaultListModel<>();
    private final JList<Product> cartList = new JList<>(listModel);

    private final JTextField nameField = new JTextField(12);
    private final JTextField priceField = new JTextField(6);
    private final JLabel totalLabel = new JLabel("Total: ₹0  (items: 0)");
    private final JTextArea logArea = new JTextArea(7, 30);
    private final JLabel stepLabel = new JLabel("Step 1 of 4 — Add products to your cart");

    // Muted friendly palette (less distracting)
    private static final Color BG_MAIN = new Color(248, 250, 252);
    private static final Color PRIMARY = new Color(51, 65, 85);
    private static final Color PRIMARY_SOFT = new Color(241, 245, 249);
    private static final Color MUTED = new Color(100, 116, 139);
    private static final Color BORDER_SOFT = new Color(226, 232, 240);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color DARK = new Color(30, 41, 59);
    private static final Color GREEN_SOFT = new Color(236, 253, 245);
    private static final Color TEAL = new Color(6, 182, 212);
    private static final Color AMBER = new Color(245, 158, 11);
    private static final Color VIOLET = new Color(139, 92, 246);
    private static final Color VIOLET_SOFT = new Color(245, 243, 255);

    public ShoppingCartFollowingGUI() {
        super("ShoppingCart - Following SRP  •  Clean Design");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 680);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_MAIN);
        setLayout(new BorderLayout(0, 0));

        // Header — muted slate gradient
        JPanel header = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(30, 41, 59), getWidth(), 0, new Color(71, 85, 105));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setPreferredSize(new Dimension(820, 72));
        header.setBorder(new EmptyBorder(12, 20, 12, 20));
        header.setLayout(new BorderLayout());
        JLabel title = new JLabel("Shopping Cart — Following SRP");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        JLabel subtitle = new JLabel("Each class has one job: Cart • PaymentProcessor • InvoicePrinter  ✓  Clean & maintainable");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 11));
        subtitle.setForeground(new Color(226, 232, 240));
        JPanel titleBox = new JPanel(new BorderLayout(2,2));
        titleBox.setOpaque(false);
        titleBox.add(title, BorderLayout.NORTH);
        titleBox.add(subtitle, BorderLayout.SOUTH);
        JButton helpBtn = new JButton("❓ How to use — Start here!");
        helpBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        helpBtn.setForeground(new Color(30,41,59));
        helpBtn.setBackground(new Color(255, 235, 59));
        helpBtn.setOpaque(true);
        helpBtn.setFocusPainted(false);
        helpBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255,193,7),2,true),
                new EmptyBorder(7,14,7,14)
        ));
        helpBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        helpBtn.addActionListener(e -> showGuide());
        JLabel badge = new JLabel("  Following SRP  ");
        badge.setFont(new Font("SansSerif", Font.BOLD, 11));
        badge.setForeground(new Color(51,65,85));
        badge.setBackground(new Color(236,253,245));
        badge.setOpaque(true);
        badge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(167,243,208),1,true),
                new EmptyBorder(6,12,6,12)
        ));
        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        headerRight.setOpaque(false);
        headerRight.add(helpBtn);
        headerRight.add(badge);
        header.add(titleBox, BorderLayout.WEST);
        header.add(headerRight, BorderLayout.EAST);

        JPanel headerWrap = new JPanel(new BorderLayout());
        headerWrap.setBackground(BG_MAIN);
        headerWrap.setBorder(new EmptyBorder(12,12,0,12));
        headerWrap.add(header, BorderLayout.CENTER);

        // Step strip
        JPanel stepStrip = new JPanel(new BorderLayout(0,4));
        stepStrip.setBackground(Color.WHITE);
        stepStrip.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0,0,1,0,BORDER_SOFT),
                new EmptyBorder(8,14,8,14)
        ));
        JPanel stepsRow = new JPanel(new GridLayout(1,4,8,0));
        stepsRow.setBackground(Color.WHITE);
        stepsRow.add(stepChip("1", "Add", "Products", PRIMARY, true));
        stepsRow.add(stepChip("2", "Cart", "View & total", MUTED, false));
        stepsRow.add(stepChip("3", "Pay/Print", "Separate services", VIOLET, false));
        stepsRow.add(stepChip("4", "UML", "See design", VIOLET, false));
        stepLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        stepLabel.setForeground(MUTED);
        stepLabel.setHorizontalAlignment(SwingConstants.CENTER);
        stepLabel.setBorder(new EmptyBorder(4,0,0,0));
        stepStrip.add(stepsRow, BorderLayout.CENTER);
        stepStrip.add(stepLabel, BorderLayout.SOUTH);

        // Input panel — Step 1
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 12));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0,0,1,0,BORDER_SOFT),
                new EmptyBorder(5,10,5,10)
        ));
        JLabel step1 = new JLabel("① Step 1 — Add:");
        step1.setFont(new Font("SansSerif", Font.BOLD, 11));
        step1.setForeground(MUTED);
        JLabel nameLbl = new JLabel("Name:");
        nameLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        nameLbl.setForeground(DARK);
        JLabel priceLbl = new JLabel("Price ₹:");
        priceLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        priceLbl.setForeground(DARK);
        styleTextField(nameField);
        styleTextField(priceField);
        nameField.setToolTipText("e.g. iPhone");
        priceField.setToolTipText("e.g. 1000");
        JButton addBtn = softButton("Add to Cart", PRIMARY, Color.WHITE);
        JButton removeBtn = softButton("Remove Selected", Color.WHITE, DARK);
        removeBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), new EmptyBorder(8,14,8,14)));
        inputPanel.add(step1);
        inputPanel.add(nameLbl); inputPanel.add(nameField);
        inputPanel.add(priceLbl); inputPanel.add(priceField);
        inputPanel.add(addBtn); inputPanel.add(removeBtn);

        JPanel topWrapper = new JPanel(new BorderLayout(0,0));
        topWrapper.setBackground(BG_MAIN);
        topWrapper.add(headerWrap, BorderLayout.NORTH);
        topWrapper.add(stepStrip, BorderLayout.CENTER);
        topWrapper.add(inputPanel, BorderLayout.SOUTH);
        JPanel topOuter = new JPanel(new BorderLayout());
        topOuter.setBackground(BG_MAIN);
        topOuter.setBorder(new EmptyBorder(0,0,0,0));
        topOuter.add(topWrapper, BorderLayout.CENTER);

        // Center — cart list — Step 2
        cartList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cartList.setFixedCellHeight(32);
        cartList.setCellRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                lbl.setBorder(new EmptyBorder(4,12,4,12));
                if (value instanceof Product) {
                    Product p = (Product) value;
                    lbl.setText(String.format("  %d.  %s  —  ₹%d", index+1, p.getName(), p.getPrice()));
                }
                if (isSelected) { lbl.setBackground(PRIMARY); lbl.setForeground(Color.WHITE); }
                else { lbl.setBackground(index%2==0? Color.WHITE : new Color(248,250,252)); lbl.setForeground(DARK); }
                lbl.setOpaque(true);
                lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
                return lbl;
            }
        });
        JScrollPane listScroll = new JScrollPane(cartList);
        listScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_SOFT,1,true),
                " ② Step 2 — Cart Items  (ShoppingCart holds List<Product> — single responsibility)", 0,0,
                new Font("SansSerif", Font.BOLD, 11), MUTED));
        listScroll.getViewport().setBackground(Color.WHITE);

        JPanel totalBanner = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2=(Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(241,245,249));
                g2.fillRoundRect(0,0,getWidth(),getHeight(),12,12);
                g2.setColor(BORDER_SOFT);
                g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,12,12);
            }
        };
        totalBanner.setOpaque(false);
        totalBanner.setBorder(new EmptyBorder(10,16,10,16));
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        totalLabel.setForeground(DARK);
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalBanner.add(totalLabel, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout(0,8));
        centerPanel.setBackground(BG_MAIN);
        centerPanel.setBorder(new EmptyBorder(10,14,10,14));
        centerPanel.add(listScroll, BorderLayout.CENTER);
        centerPanel.add(totalBanner, BorderLayout.SOUTH);

        // Bottom — actions — Steps 3 & 4
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        actionPanel.setBackground(Color.WHITE);
        actionPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_SOFT,1,true),
                new EmptyBorder(6,6,6,6)
        ));
        JPanel actionWrap = new JPanel(new BorderLayout());
        actionWrap.setBackground(BG_MAIN);
        JLabel actionTitle = new JLabel("  ③ Step 3 — Actions via separate services  •  ④ View UML");
        actionTitle.setFont(new Font("SansSerif", Font.BOLD, 11));
        actionTitle.setForeground(MUTED);
        actionTitle.setBorder(new EmptyBorder(0,4,4,4));
        actionWrap.add(actionTitle, BorderLayout.NORTH);
        actionWrap.add(actionPanel, BorderLayout.CENTER);

        JButton totalBtn = softButton("Calculate Total", Color.WHITE, DARK);
        totalBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), new EmptyBorder(8,14,8,14)));
        totalBtn.setToolTipText("Step 2: ShoppingCart.TotalCost()");
        JButton payBtn = softButton("Handle Payment", Color.WHITE, DARK);
        payBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), new EmptyBorder(8,14,8,14)));
        payBtn.setToolTipText("Step 3: via PaymentProcessor (separate class)");
        JButton invoiceBtn = softButton("Print Invoice", Color.WHITE, DARK);
        invoiceBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), new EmptyBorder(8,14,8,14)));
        invoiceBtn.setToolTipText("Step 3: via InvoicePrinter (separate class)");
        JButton clearBtn = softButton("Clear Cart", Color.WHITE, MUTED);
        clearBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), new EmptyBorder(8,14,8,14)));
        JButton umlBtn = softButton("View UML", VIOLET, Color.WHITE);
        umlBtn.setToolTipText("Step 4: Show clean SRP diagram");

        actionPanel.add(totalBtn); actionPanel.add(payBtn); actionPanel.add(invoiceBtn); actionPanel.add(clearBtn); actionPanel.add(umlBtn);

        logArea.setEditable(false);
        logArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
        logArea.setBackground(Color.WHITE);
        logArea.setForeground(DARK);
        logArea.setBorder(new EmptyBorder(8,8,8,8));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_SOFT,1,true),
                " Activity log ",0,0, new Font("SansSerif", Font.BOLD,11), MUTED));
        logScroll.setPreferredSize(new Dimension(820, 140));

        JPanel southPanel = new JPanel(new BorderLayout(0,8));
        southPanel.setBackground(BG_MAIN);
        southPanel.setBorder(new EmptyBorder(0,14,14,14));
        southPanel.add(actionWrap, BorderLayout.NORTH);
        southPanel.add(logScroll, BorderLayout.CENTER);

        add(topOuter, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> { addProduct(); setStep(1); });
        removeBtn.addActionListener(e -> { removeSelected(); setStep(1); });
        totalBtn.addActionListener(e -> { updateTotal(); log("🧮 Total via ShoppingCart.TotalCost(): ₹" + shoppingCart.TotalCost()); setStep(2); });
        payBtn.addActionListener(e -> { handlePayment(); setStep(3); });
        invoiceBtn.addActionListener(e -> { printInvoice(); setStep(3); });
        clearBtn.addActionListener(e -> { clearCart(); setStep(2); });
        umlBtn.addActionListener(e -> { showUmlDiagram(); setStep(4); });
        priceField.addActionListener(e -> addProduct());
        nameField.addActionListener(e -> priceField.requestFocus());
        cartList.addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) setStep(2); });

        addProductToCart(new Product("Clinic Plus", 2));
        addProductToCart(new Product("earphones", 1000));
        addProductToCart(new Product("Book - LLD", 499));
        log("✨ Clean SRP demo ready — Cart only manages products.");
        log("① Add products → ② View total (ShoppingCart) → ③ Pay via PaymentProcessor / Invoice via InvoicePrinter → ④ View UML");
        setStep(1);
    }

    private JPanel stepChip(String num, String title, String sub, Color color, boolean active) {
        JPanel p = new JPanel(new BorderLayout(0,1));
        p.setBackground(active ? new Color(color.getRed(), color.getGreen(), color.getBlue(), 18) : Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(active? color : BORDER_SOFT, active?2:1, true),
                new EmptyBorder(6,8,6,8)
        ));
        JLabel n=new JLabel(num); n.setFont(new Font("SansSerif",Font.BOLD,11)); n.setForeground(Color.WHITE); n.setBackground(color); n.setOpaque(true); n.setHorizontalAlignment(SwingConstants.CENTER); n.setBorder(new EmptyBorder(2,6,2,6));
        JLabel t=new JLabel(title); t.setFont(new Font("SansSerif",Font.BOLD,11)); t.setForeground(active? color.darker(): DARK);
        JLabel s=new JLabel(sub); s.setFont(new Font("SansSerif",Font.PLAIN,10)); s.setForeground(MUTED);
        JPanel top=new JPanel(new FlowLayout(FlowLayout.LEFT,6,0)); top.setOpaque(false); top.add(n); top.add(t);
        p.add(top, BorderLayout.NORTH); p.add(s, BorderLayout.SOUTH); return p;
    }
    private void setStep(int step){
        String[] msgs={"Step 1 of 4 — Add products","Step 2 of 4 — View cart & total (ShoppingCart only)","Step 3 of 4 — Pay / Print via separate services","Step 4 of 4 — View UML for clean design"};
        if(step>=1&&step<=4) stepLabel.setText(msgs[step-1]);
    }
    private void showGuide(){
        JOptionPane.showMessageDialog(this,
                "<html><body style='width:540px; font-family:sans-serif;'>"
                + "<h2 style='color:#334155; margin:0;'>How to use — Following SRP (Clean)</h2>"
                + "<p style='color:#64748b;'>Each class has <b>one job</b>. Cart never handles payment/invoice directly.</p>"
                + "<table style='width:100%; font-size:12px;'>"
                + "<tr><td style='padding:6 8; background:#f1f5f9;'><b>① Add</b></td><td style='padding:6 8;'>Enter Name + Price → <b>Add to Cart</b></td></tr>"
                + "<tr><td style='padding:6 8; background:#f8fafc;'><b>② Cart</b></td><td style='padding:6 8;'><b>Calculate Total</b> calls <code>ShoppingCart.TotalCost()</code> only</td></tr>"
                + "<tr><td style='padding:6 8; background:#fef3c7;'><b>③ Pay/Print</b></td><td style='padding:6 8;'><b>Handle Payment</b> → <code>PaymentProcessor.handlePayment(cart)</code><br><b>Print Invoice</b> → <code>InvoicePrinter.printInvoice(cart)</code></td></tr>"
                + "<tr><td style='padding:6 8; background:#f5f3ff;'><b>④ UML</b></td><td style='padding:6 8;'><b>View UML</b> shows 3 separate boxes — no violation</td></tr>"
                + "</table>"
                + "<p style='background:#ecfdf5; padding:8; border-radius:8px; color:#065f46;'><b>Try:</b> Add item → Calculate Total → Handle Payment → Print Invoice → View UML</p>"
                + "</body></html>",
                "How to use", JOptionPane.INFORMATION_MESSAGE);
    }

    private void styleTextField(JTextField tf){
        tf.setFont(new Font("SansSerif", Font.PLAIN,13));
        tf.setBackground(Color.WHITE);
        tf.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_SOFT,1,true), new EmptyBorder(6,8,6,8)));
    }
    private JButton softButton(String text, Color bg, Color fg){
        JButton b=new JButton(text);
        b.setFont(new Font("SansSerif",Font.BOLD,12));
        b.setBackground(bg); b.setForeground(fg);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(bg.darker(),1,true), new EmptyBorder(8,14,8,14)));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        b.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override public void mouseEntered(java.awt.event.MouseEvent e){ b.setBackground(bg.brighter()); }
            @Override public void mouseExited(java.awt.event.MouseEvent e){ b.setBackground(bg); }
        });
        return b;
    }
    private void addProduct(){
        String name=nameField.getText().trim();
        String priceStr=priceField.getText().trim();
        if(name.isEmpty()||priceStr.isEmpty()){ JOptionPane.showMessageDialog(this,"Enter name and price","Input Error",JOptionPane.WARNING_MESSAGE); return; }
        try{
            int price=Integer.parseInt(priceStr);
            if(price<0) throw new NumberFormatException();
            Product p=new Product(name, price);
            addProductToCart(p);
            nameField.setText(""); priceField.setText(""); nameField.requestFocus();
        }catch(NumberFormatException ex){ JOptionPane.showMessageDialog(this,"Price must be a positive integer","Input Error",JOptionPane.ERROR_MESSAGE); }
    }
    private void addProductToCart(Product p){ shoppingCart.AddProduct(p); listModel.addElement(p); updateTotal(); log("Added: " + p.getName() + " — ₹" + p.getPrice()); }
    private void removeSelected(){
        Product sel=cartList.getSelectedValue();
        if(sel==null){ JOptionPane.showMessageDialog(this,"Select a product to remove","No Selection",JOptionPane.WARNING_MESSAGE); return; }
        shoppingCart.RemoveProduct(sel); listModel.removeElement(sel); updateTotal(); log("Removed: " + sel.getName());
    }
    private void updateTotal(){ int total=shoppingCart.TotalCost(); totalLabel.setText(String.format("Total: ₹%d   •   %d item(s)  — via ShoppingCart only", total, listModel.size())); }
    private void handlePayment(){
        if(listModel.isEmpty()){ JOptionPane.showMessageDialog(this,"Cart is empty!","Payment",JOptionPane.WARNING_MESSAGE); return; }
        paymentProcessor.handlePayment(shoppingCart);
        String msg="Payment via PaymentProcessor for ₹" + shoppingCart.TotalCost();
        log(">>> " + msg);
        JOptionPane.showMessageDialog(this, "<html><h3 style='color:#065f46;'>Payment Successful</h3><p>Via <b>PaymentProcessor</b><br>Paid: <b>₹"+shoppingCart.TotalCost()+"</b></p></html>", "Payment", JOptionPane.INFORMATION_MESSAGE);
    }
    private void printInvoice(){
        if(listModel.isEmpty()){ JOptionPane.showMessageDialog(this,"Cart is empty!","Invoice",JOptionPane.WARNING_MESSAGE); return; }
        invoicePrinter.printInvoice(shoppingCart);
        StringBuilder sb=new StringBuilder();
        sb.append("INVOICE (via InvoicePrinter)\n");
        for(int i=0;i<listModel.size();i++){ Product p=listModel.get(i); sb.append(String.format("%d. %-20s  ₹%4d\n", i+1, p.getName(), p.getPrice())); }
        sb.append(String.format("TOTAL: ₹%d\n", shoppingCart.TotalCost()));
        log(sb.toString());
        JTextArea area=new JTextArea(sb.toString());
        area.setFont(new Font("Monospaced", Font.BOLD,13));
        area.setEditable(false);
        area.setBorder(new EmptyBorder(10,10,10,10));
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Invoice — via InvoicePrinter", JOptionPane.PLAIN_MESSAGE);
    }
    private void clearCart(){ for(int i=listModel.size()-1;i>=0;i--) shoppingCart.RemoveProduct(listModel.get(i)); listModel.clear(); updateTotal(); log("Cart cleared."); }

    private void showUmlDiagram(){
        JDialog dlg=new JDialog(this,"UML — SRP Following (Clean)",true);
        dlg.setSize(880,620); dlg.setLocationRelativeTo(this); dlg.setLayout(new BorderLayout(0,0)); dlg.getContentPane().setBackground(BG_MAIN);
        JPanel head=new JPanel(new BorderLayout());
        head.setBackground(Color.WHITE);
        head.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,1,0,BORDER_SOFT), new EmptyBorder(14,18,14,18)));
        JLabel hTitle=new JLabel("Class Diagram — SRP Following  •  One Job Per Class");
        hTitle.setFont(new Font("SansSerif",Font.BOLD,14)); hTitle.setForeground(DARK);
        JLabel hSub=new JLabel("ShoppingCart (cart) + PaymentProcessor (payment) + InvoicePrinter (invoice) — each has one reason to change");
        hSub.setFont(new Font("SansSerif",Font.PLAIN,11)); hSub.setForeground(MUTED);
        JPanel hText=new JPanel(new BorderLayout(0,2)); hText.setBackground(Color.WHITE); hText.add(hTitle, BorderLayout.NORTH); hText.add(hSub, BorderLayout.SOUTH);
        JLabel hBadge=new JLabel("  ✓  SRP OK  "); hBadge.setFont(new Font("SansSerif",Font.BOLD,11)); hBadge.setForeground(new Color(5,122,80)); hBadge.setBackground(GREEN_SOFT); hBadge.setOpaque(true); hBadge.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(167,243,208),1,true), new EmptyBorder(6,12,6,12)));
        head.add(hText, BorderLayout.WEST); head.add(hBadge, BorderLayout.EAST);
        UmlPanel uml=new UmlPanel(); JScrollPane scroll=new JScrollPane(uml); scroll.setBorder(BorderFactory.createEmptyBorder()); scroll.getViewport().setBackground(BG_MAIN); uml.setPreferredSize(new Dimension(840,480));
        JPanel legend=new JPanel(new BorderLayout(0,6)); legend.setBackground(new Color(248,250,252)); legend.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1,0,0,0,BORDER_SOFT), new EmptyBorder(10,18,10,18)));
        JLabel leg1=new JLabel("—  Solid line = uses / association  •  Each box = single responsibility"); leg1.setFont(new Font("SansSerif",Font.PLAIN,11)); leg1.setForeground(new Color(71,85,105));
        JLabel leg2=new JLabel("Why it follows SRP: Change payment logic → only PaymentProcessor changes. Change invoice format → only InvoicePrinter changes. Cart stays untouched."); leg2.setFont(new Font("SansSerif",Font.PLAIN,11)); leg2.setForeground(MUTED);
        legend.add(leg1, BorderLayout.NORTH); legend.add(leg2, BorderLayout.SOUTH);
        JPanel bottom=new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10)); bottom.setBackground(Color.WHITE); bottom.setBorder(BorderFactory.createMatteBorder(1,0,0,0,BORDER_SOFT));
        JButton close=softButton("Close", VIOLET, Color.WHITE); close.addActionListener(e->dlg.dispose()); bottom.add(close);
        dlg.add(head, BorderLayout.NORTH); dlg.add(scroll, BorderLayout.CENTER);
        JPanel southWrap=new JPanel(new BorderLayout(0,0)); southWrap.add(legend, BorderLayout.NORTH); southWrap.add(bottom, BorderLayout.SOUTH); dlg.add(southWrap, BorderLayout.SOUTH);
        dlg.setVisible(true);
    }
    private class UmlPanel extends JPanel {
        UmlPanel(){ setBackground(BG_MAIN); }
        @Override protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2=(Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            int W=getWidth();
            int boxW=220, boxH=118;
            int cartX=W/2 - boxW/2; int cartY=40;
            int payX=80; int payY=240;
            int invX=W - 80 - boxW; int invY=240;
            int prodX=W/2 - 100; int prodY=400;
            // Draw boxes
            drawBox(g2, cartX, cartY, boxW, boxH, "ShoppingCart", new String[]{"- cart: List<Product>", "+ AddProduct(p)", "+ RemoveProduct(p)", "+ TotalCost(): Integer"}, PRIMARY, PRIMARY_SOFT, "single: cart only");
            drawBox(g2, payX, payY, boxW, boxH, "PaymentProcessor", new String[]{"+ handlePayment(cart)", "single: payment only"}, new Color(16,185,129), GREEN_SOFT, "single: payment");
            drawBox(g2, invX, invY, boxW, boxH, "InvoicePrinter", new String[]{"+ printInvoice(cart)", "single: invoice only"}, TEAL, new Color(236,253,245), "single: invoice");
            drawBox(g2, prodX, prodY, 200, 90, "Product", new String[]{"- name: String", "- price: Integer"}, new Color(71,85,105), Color.WHITE, null);
            // Associations
            g2.setColor(new Color(71,85,105)); g2.setStroke(new BasicStroke(1.4f));
            // Cart -> Product (composition)
            int cartBottom=cartY+boxH; int prodTop=prodY;
            g2.drawLine(cartX+boxW/2, cartBottom, prodX+100, prodTop);
            drawDiamond(g2, cartX+boxW/2, cartBottom);
            g2.setFont(new Font("SansSerif",Font.PLAIN,10)); g2.setColor(MUTED);
            g2.drawString("1 — *", (cartX+boxW/2 + prodX+100)/2 -10, (cartBottom+prodTop)/2);
            // Cart uses PaymentProcessor / InvoicePrinter (dashed)
            g2.setStroke(new BasicStroke(1.4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{6,4}, 0));
            g2.setColor(new Color(100,116,139));
            // to payment
            g2.drawLine(cartX+30, cartY+boxH/2, payX+boxW, payY+boxH/2);
            drawArrow(g2, payX+boxW, payY+boxH/2, false);
            g2.drawString("uses", (cartX+30+payX+boxW)/2 -12, cartY+boxH/2 -8);
            // to invoice
            g2.drawLine(cartX+boxW-30, cartY+boxH/2, invX, invY+boxH/2);
            drawArrow(g2, invX, invY+boxH/2, true);
            g2.drawString("uses", (cartX+boxW-30+invX)/2 -12, cartY+boxH/2 -8);

            // Callout
            String callout="✓  Each class has one reason to change — SRP followed";
            g2.setFont(new Font("SansSerif",Font.BOLD,11));
            int cw=g2.getFontMetrics().stringWidth(callout)+24; int ch=28;
            int cx2=W/2 - cw/2; int cy2=payY+boxH+50;
            g2.setColor(GREEN_SOFT); g2.fillRoundRect(cx2, cy2, cw, ch,14,14);
            g2.setColor(new Color(5,122,80)); g2.drawRoundRect(cx2, cy2, cw, ch,14,14);
            g2.drawString(callout, cx2+12, cy2+18);
        }
        private void drawBox(Graphics2D g2, int x,int y,int w,int h,String name,String[] members,Color accent,Color soft,String badgeText){
            g2.setColor(new Color(0,0,0,8)); g2.fillRoundRect(x+3,y+3,w,h,12,12);
            g2.setColor(Color.WHITE); g2.fillRoundRect(x,y,w,h,12,12);
            g2.setColor(soft); g2.fillRoundRect(x,y,w,26,12,12); g2.fillRect(x,y+12,w,14);
            g2.setColor(accent); g2.fillRoundRect(x,y,w,3,3,3);
            g2.setColor(BORDER_SOFT); g2.setStroke(new BasicStroke(1.1f)); g2.drawRoundRect(x,y,w,h,12,12);
            g2.setFont(new Font("SansSerif",Font.BOLD,11)); g2.setColor(DARK);
            int nw=g2.getFontMetrics().stringWidth(name); g2.drawString(name, x+(w-nw)/2, y+18);
            g2.setColor(BORDER_SOFT); g2.drawLine(x+10,y+28,x+w-10,y+28);
            g2.setFont(new Font("SansSerif",Font.PLAIN,10)); g2.setColor(new Color(71,85,105));
            int my=y+44; for(String m: members){ g2.drawString(m, x+10, my); my+=13; }
            if(badgeText!=null){
                g2.setFont(new Font("SansSerif",Font.BOLD,9));
                int bw=g2.getFontMetrics().stringWidth(badgeText)+10;
                int bx=x+w-bw-8; int by=y+6;
                g2.setColor(soft); g2.fillRoundRect(bx,by,bw,14,7,7);
                g2.setColor(accent); g2.drawRoundRect(bx,by,bw,14,7,7);
                g2.drawString(badgeText, bx+5, by+10);
            }
        }
        private void drawDiamond(Graphics2D g2,int x,int y){
            Polygon p=new Polygon(); p.addPoint(x,y); p.addPoint(x+7,y+7); p.addPoint(x,y+14); p.addPoint(x-7,y+7);
            g2.setColor(Color.WHITE); g2.fillPolygon(p); g2.setColor(new Color(71,85,105)); g2.drawPolygon(p);
        }
        private void drawArrow(Graphics2D g2,int x,int y, boolean left){
            int s=8;
            if(left){ g2.drawLine(x,y, x+s, y-s); g2.drawLine(x,y, x+s, y+s); }
            else { g2.drawLine(x,y, x-s, y-s); g2.drawLine(x,y, x-s, y+s); }
        }
    }

    private void log(String msg){ logArea.append(msg+"\n"); logArea.setCaretPosition(logArea.getDocument().getLength()); System.out.println(msg); }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }catch(Exception ignored){}
            new ShoppingCartFollowingGUI().setVisible(true);
        });
    }
}
