package src;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class QuoteApp extends JFrame {
    private final QuoteDatabase quoteDatabase;
    private QuoteDatabase.Quote currentQuote;
    private final List<QuoteDatabase.Quote> favorites;
    private final File favoritesFile;

    // UI Components
    private JPanel mainContentPanel;
    private CardLayout cardLayout;
    private JLabel quoteLabel;
    private JLabel authorLabel;
    private JLabel categoryLabel;
    private JButton favoriteButton;
    private JPanel favoritesListPanel;
    private JLabel toastLabel;
    private Timer toastTimer;

    // Design System Colors
    private static final Color COLOR_BG = new Color(18, 18, 24);
    private static final Color COLOR_SIDEBAR = new Color(26, 26, 36);
    private static final Color COLOR_CARD_START = new Color(74, 0, 224);
    private static final Color COLOR_CARD_END = new Color(142, 45, 226);
    private static final Color COLOR_TEXT_PRIMARY = new Color(245, 245, 250);
    private static final Color COLOR_TEXT_MUTED = new Color(170, 170, 190);
    private static final Color COLOR_ACCENT = new Color(255, 75, 75);
    private static final Color COLOR_BUTTON_BG = new Color(40, 40, 55);

    public QuoteApp() {
        quoteDatabase = new QuoteDatabase();
        favorites = new ArrayList<>();
        favoritesFile = new File("favorites.txt");

        loadFavorites();
        currentQuote = quoteDatabase.getQuoteOfDay();

        setupUI();
    }

    private void setupUI() {
        setTitle("Quote of the Day");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);
        setLayout(new BorderLayout());

        // Custom Title/Sidebar Panel
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        // Main Card Layout Panel
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setBackground(COLOR_BG);
        mainContentPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        mainContentPanel.add(createDashboardPanel(), "dashboard");
        mainContentPanel.add(createFavoritesPanel(), "favorites");

        add(mainContentPanel, BorderLayout.CENTER);

        // Setup Floating Toast Notification overlay
        setupToastNotification();
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(220, 600));
        sidebar.setBackground(COLOR_SIDEBAR);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(30, 20, 30, 20));

        // App Logo/Title
        JLabel appTitle = new JLabel("Daily Spark");
        appTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        appTitle.setForeground(COLOR_TEXT_PRIMARY);
        appTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(appTitle);

        JLabel appSub = new JLabel("Your Daily Inspiration");
        appSub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        appSub.setForeground(COLOR_TEXT_MUTED);
        appSub.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(appSub);

        sidebar.add(Box.createRigidArea(new Dimension(0, 50)));

        // Navigation Buttons
        JButton btnHome = createSidebarButton("Dashboard", "home");
        JButton btnFav = createSidebarButton("Favorite Quotes", "favorites");

        btnHome.addActionListener(e -> cardLayout.show(mainContentPanel, "dashboard"));
        btnFav.addActionListener(e -> {
            refreshFavoritesPanel();
            cardLayout.show(mainContentPanel, "favorites");
        });

        sidebar.add(btnHome);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnFav);

        sidebar.add(Box.createVerticalGlue());

        // Footer version info
        JLabel versionLabel = new JLabel("v1.0.0");
        versionLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        versionLabel.setForeground(COLOR_TEXT_MUTED);
        versionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(versionLabel);

        return sidebar;
    }

    private JButton createSidebarButton(String text, String iconName) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(COLOR_BUTTON_BG.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(COLOR_BUTTON_BG);
                } else {
                    g2.setColor(COLOR_SIDEBAR);
                }
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setForeground(COLOR_TEXT_PRIMARY);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 45));
        btn.setPreferredSize(new Dimension(180, 45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(0, 15, 0, 15));
        return btn;
    }

    private JPanel createDashboardPanel() {
        JPanel dashboard = new JPanel(new BorderLayout());
        dashboard.setBackground(COLOR_BG);

        // Header Panel
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_BG);
        JLabel headerTitle = new JLabel("Quote of the Day");
        headerTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        headerTitle.setForeground(COLOR_TEXT_PRIMARY);
        header.add(headerTitle, BorderLayout.WEST);

        categoryLabel = new JLabel("Category: " + currentQuote.getCategory());
        categoryLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        categoryLabel.setForeground(COLOR_TEXT_MUTED);
        header.add(categoryLabel, BorderLayout.EAST);
        header.setBorder(new EmptyBorder(0, 0, 20, 0));
        dashboard.add(header, BorderLayout.NORTH);

        // Gradient Quote Card Panel
        JPanel quoteCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, COLOR_CARD_START, getWidth(), getHeight(), COLOR_CARD_END);
                g2.setPaint(gp);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
                g2.dispose();
            }
        };
        quoteCard.setLayout(new BorderLayout());
        quoteCard.setOpaque(false);
        quoteCard.setBorder(new EmptyBorder(40, 40, 40, 40));

        // Quote display
        quoteLabel = new JLabel("<html><div style='text-align: center; width: 450px;'>" + currentQuote.getText() + "</div></html>");
        quoteLabel.setFont(new Font("SansSerif", Font.ITALIC, 22));
        quoteLabel.setForeground(COLOR_TEXT_PRIMARY);
        quoteLabel.setHorizontalAlignment(SwingConstants.CENTER);
        quoteCard.add(quoteLabel, BorderLayout.CENTER);

        // Author display
        authorLabel = new JLabel("— " + currentQuote.getAuthor());
        authorLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        authorLabel.setForeground(COLOR_TEXT_PRIMARY);
        authorLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        authorLabel.setBorder(new EmptyBorder(20, 0, 0, 0));
        quoteCard.add(authorLabel, BorderLayout.SOUTH);

        dashboard.add(quoteCard, BorderLayout.CENTER);

        // Bottom Controls
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        controls.setBackground(COLOR_BG);

        favoriteButton = createStyledButton(isFavorited(currentQuote) ? "★ Favorited" : "☆ Favorite", COLOR_BUTTON_BG);
        JButton btnShare = createStyledButton("🔗 Share / Copy", COLOR_BUTTON_BG);
        JButton btnRefresh = createStyledButton("🔄 Refresh Quote", COLOR_BUTTON_BG);

        favoriteButton.addActionListener(e -> toggleFavorite());
        btnShare.addActionListener(e -> shareQuote(currentQuote));
        btnRefresh.addActionListener(e -> {
            currentQuote = quoteDatabase.getRandomQuote();
            updateQuoteDisplay();
            showToast("Random quote loaded!");
        });

        controls.add(favoriteButton);
        controls.add(btnShare);
        controls.add(btnRefresh);

        dashboard.add(controls, BorderLayout.SOUTH);

        return dashboard;
    }

    private JPanel createFavoritesPanel() {
        JPanel favPanel = new JPanel(new BorderLayout());
        favPanel.setBackground(COLOR_BG);

        JLabel title = new JLabel("Your Favorite Quotes");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(COLOR_TEXT_PRIMARY);
        title.setBorder(new EmptyBorder(0, 0, 20, 0));
        favPanel.add(title, BorderLayout.NORTH);

        favoritesListPanel = new JPanel();
        favoritesListPanel.setLayout(new BoxLayout(favoritesListPanel, BoxLayout.Y_AXIS));
        favoritesListPanel.setBackground(COLOR_BG);

        JScrollPane scrollPane = new JScrollPane(favoritesListPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(COLOR_BG);
        favPanel.add(scrollPane, BorderLayout.CENTER);

        refreshFavoritesPanel();

        return favPanel;
    }

    private void refreshFavoritesPanel() {
        favoritesListPanel.removeAll();

        if (favorites.isEmpty()) {
            JLabel emptyLabel = new JLabel("No favorite quotes saved yet. Go add some!");
            emptyLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
            emptyLabel.setForeground(COLOR_TEXT_MUTED);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            emptyLabel.setBorder(new EmptyBorder(50, 0, 0, 0));
            favoritesListPanel.add(emptyLabel);
        } else {
            for (QuoteDatabase.Quote q : favorites) {
                favoritesListPanel.add(createFavoriteCard(q));
                favoritesListPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }

        favoritesListPanel.revalidate();
        favoritesListPanel.repaint();
    }

    private JPanel createFavoriteCard(QuoteDatabase.Quote q) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_SIDEBAR);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setMaximumSize(new Dimension(650, 100));
        card.setPreferredSize(new Dimension(650, 100));
        card.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel text = new JLabel("<html><div style='width: 450px;'>" + q.getText() + "</div></html>");
        text.setFont(new Font("SansSerif", Font.ITALIC, 14));
        text.setForeground(COLOR_TEXT_PRIMARY);

        JLabel author = new JLabel("— " + q.getAuthor());
        author.setFont(new Font("SansSerif", Font.BOLD, 12));
        author.setForeground(COLOR_TEXT_MUTED);

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setOpaque(false);
        infoPanel.add(text, BorderLayout.CENTER);
        infoPanel.add(author, BorderLayout.SOUTH);

        JPanel cardControls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        cardControls.setOpaque(false);

        JButton btnCopy = createStyledButton("Copy", COLOR_BUTTON_BG);
        JButton btnRemove = createStyledButton("Remove", COLOR_ACCENT);

        btnCopy.setPreferredSize(new Dimension(70, 30));
        btnRemove.setPreferredSize(new Dimension(80, 30));

        btnCopy.addActionListener(e -> shareQuote(q));
        btnRemove.addActionListener(e -> {
            favorites.remove(q);
            saveFavorites();
            refreshFavoritesPanel();
            updateQuoteDisplay();
            showToast("Removed from favorites");
        });

        cardControls.add(btnCopy);
        cardControls.add(btnRemove);

        card.add(infoPanel, BorderLayout.CENTER);
        card.add(cardControls, BorderLayout.EAST);

        return card;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(bg.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(bg.brighter());
                } else {
                    g2.setColor(bg);
                }
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 12, 12));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setForeground(COLOR_TEXT_PRIMARY);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setPreferredSize(new Dimension(140, 38));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void updateQuoteDisplay() {
        quoteLabel.setText("<html><div style='text-align: center; width: 450px;'>" + currentQuote.getText() + "</div></html>");
        authorLabel.setText("— " + currentQuote.getAuthor());
        categoryLabel.setText("Category: " + currentQuote.getCategory());
        favoriteButton.setText(isFavorited(currentQuote) ? "★ Favorited" : "☆ Favorite");
    }

    private void toggleFavorite() {
        if (isFavorited(currentQuote)) {
            // Find and remove
            favorites.removeIf(q -> q.getText().equals(currentQuote.getText()));
            favoriteButton.setText("☆ Favorite");
            showToast("Removed from favorites");
        } else {
            favorites.add(currentQuote);
            favoriteButton.setText("★ Favorited");
            showToast("Added to favorites!");
        }
        saveFavorites();
    }

    private boolean isFavorited(QuoteDatabase.Quote quote) {
        for (QuoteDatabase.Quote q : favorites) {
            if (q.getText().equals(quote.getText())) {
                return true;
            }
        }
        return false;
    }

    private void shareQuote(QuoteDatabase.Quote q) {
        String textToCopy = q.toString();
        StringSelection selection = new StringSelection(textToCopy);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        showToast("Quote copied to clipboard!");
    }

    private void setupToastNotification() {
        toastLabel = new JLabel("", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(30, 30, 45, 230));
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        toastLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        toastLabel.setForeground(COLOR_TEXT_PRIMARY);
        toastLabel.setOpaque(false);
        toastLabel.setVisible(false);
        add(toastLabel, BorderLayout.SOUTH);
    }

    private void showToast(String message) {
        toastLabel.setText(message);
        toastLabel.setVisible(true);
        if (toastTimer != null && toastTimer.isRunning()) {
            toastTimer.stop();
        }
        toastTimer = new Timer(2500, e -> toastLabel.setVisible(false));
        toastTimer.setRepeats(false);
        toastTimer.start();
    }

    private void loadFavorites() {
        if (!favoritesFile.exists()) return;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(favoritesFile), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";;;");
                if (parts.length == 3) {
                    favorites.add(new QuoteDatabase.Quote(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.err.println("Could not load favorites: " + e.getMessage());
        }
    }

    private void saveFavorites() {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(favoritesFile), StandardCharsets.UTF_8))) {
            for (QuoteDatabase.Quote q : favorites) {
                writer.write(q.getText() + ";;;" + q.getAuthor() + ";;;" + q.getCategory());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Could not save favorites: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Run application
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            QuoteApp app = new QuoteApp();
            app.setVisible(true);
        });
    }
}
