// Curated Library of inspiring quotes
const quotes = [
    { text: "The only way to do great work is to love what you do.", author: "Steve Jobs", category: "Inspiration" },
    { text: "Believe you can and you're halfway there.", author: "Theodore Roosevelt", category: "Motivation" },
    { text: "It always seems impossible until it's done.", author: "Nelson Mandela", category: "Perseverance" },
    { text: "Success is not final, failure is not fatal: it is the courage to continue that counts.", author: "Winston Churchill", category: "Success" },
    { text: "Act as if what you do makes a difference. It does.", author: "William James", category: "Impact" },
    { text: "Keep your face always toward the sunshine—and shadows will fall behind you.", author: "Walt Whitman", category: "Optimism" },
    { text: "The best way to predict your future is to create it.", author: "Abraham Lincoln", category: "Wisdom" },
    { text: "What lies behind us and what lies before us are tiny matters compared to what lies within us.", author: "Ralph Waldo Emerson", category: "Strength" },
    { text: "You miss 100% of the shots you don't take.", author: "Wayne Gretzky", category: "Opportunity" },
    { text: "Whether you think you can or you think you can't, you're right.", author: "Henry Ford", category: "Mindset" },
    { text: "The mind is everything. What you think you become.", author: "Buddha", category: "Wisdom" },
    { text: "An unexamined life is not worth living.", author: "Socrates", category: "Philosophy" },
    { text: "Out of clutter, find simplicity. From discord, find harmony. In the middle of difficulty lies opportunity.", author: "Albert Einstein", category: "Wisdom" },
    { text: "Do what you can, with what you have, where you are.", author: "Theodore Roosevelt", category: "Practicality" },
    { text: "The only limit to our realization of tomorrow will be our doubts of today.", author: "Franklin D. Roosevelt", category: "Hope" },
    { text: "Happiness is not something ready made. It comes from your own actions.", author: "Dalai Lama", category: "Happiness" },
    { text: "Don't watch the clock; do what it does. Keep going.", author: "Sam Levenson", category: "Perseverance" },
    { text: "It is never too late to be what you might have been.", author: "George Eliot", category: "Potential" },
    { text: "We do not inherit the earth from our ancestors, we borrow it from our children.", author: "Native American Proverb", category: "Responsibility" },
    { text: "In the end, we will remember not the words of our enemies, but the silence of our friends.", author: "Martin Luther King Jr.", category: "Justice" }
];

// App State
let currentQuote = null;
let favorites = JSON.parse(localStorage.getItem('daily_spark_favorites')) || [];

// DOM Elements
const quoteText = document.getElementById('quote-text');
const quoteAuthor = document.getElementById('quote-author');
const quoteCategory = document.getElementById('quote-category');
const btnFavorite = document.getElementById('btn-favorite');
const favIcon = document.getElementById('fav-icon');
const favText = document.getElementById('fav-text');
const btnShare = document.getElementById('btn-share');
const btnRefresh = document.getElementById('btn-refresh');

const navDashboard = document.getElementById('nav-dashboard');
const navFavorites = document.getElementById('nav-favorites');
const viewDashboard = document.getElementById('view-dashboard');
const viewFavorites = document.getElementById('view-favorites');
const favoritesList = document.getElementById('favorites-list');
const toast = document.getElementById('toast');

// Initialize App
function init() {
    loadDailyQuote();
    setupEventListeners();
    updateFavoriteBtnState();
}

// Select Quote of the Day based on the Calendar Date
function loadDailyQuote() {
    const today = new Date();
    const dayOfYear = Math.floor((today - new Date(today.getFullYear(), 0, 0)) / 86400000);
    const index = Math.abs((dayOfYear + today.getFullYear() * 31) % quotes.length);
    currentQuote = quotes[index];
    displayQuote(currentQuote);
}

function displayQuote(quote) {
    quoteText.textContent = quote.text;
    quoteAuthor.textContent = `— ${quote.author}`;
    quoteCategory.textContent = quote.category;
    updateFavoriteBtnState();
}

// Setup Event Listeners
function setupEventListeners() {
    // Navigation
    navDashboard.addEventListener('click', () => {
        switchView('dashboard');
    });

    navFavorites.addEventListener('click', () => {
        renderFavorites();
        switchView('favorites');
    });

    // Favorite Button
    btnFavorite.addEventListener('click', toggleFavorite);

    // Share Button
    btnShare.addEventListener('click', () => {
        shareQuote(currentQuote);
    });

    // Refresh Button
    btnRefresh.addEventListener('click', () => {
        let newQuote;
        do {
            newQuote = quotes[Math.floor(Math.random() * quotes.length)];
        } while (newQuote.text === currentQuote.text);
        
        currentQuote = newQuote;
        displayQuote(currentQuote);
        showToast("Random quote loaded!");
    });
}

function switchView(viewName) {
    if (viewName === 'dashboard') {
        navDashboard.classList.add('active');
        navFavorites.classList.remove('active');
        viewDashboard.classList.add('active');
        viewFavorites.classList.remove('active');
    } else {
        navDashboard.classList.remove('active');
        navFavorites.classList.add('active');
        viewDashboard.classList.remove('active');
        viewFavorites.classList.add('active');
    }
}

// Favorites Logic
function isFavorited(quote) {
    return favorites.some(f => f.text === quote.text);
}

function toggleFavorite() {
    if (isFavorited(currentQuote)) {
        favorites = favorites.filter(f => f.text !== currentQuote.text);
        showToast("Removed from favorites");
    } else {
        favorites.push(currentQuote);
        showToast("Added to favorites!");
    }
    localStorage.setItem('daily_spark_favorites', JSON.stringify(favorites));
    updateFavoriteBtnState();
}

function updateFavoriteBtnState() {
    if (isFavorited(currentQuote)) {
        favIcon.textContent = "★";
        favText.textContent = "Favorited";
    } else {
        favIcon.textContent = "☆";
        favText.textContent = "Favorite";
    }
}

function renderFavorites() {
    favoritesList.innerHTML = "";

    if (favorites.length === 0) {
        favoritesList.innerHTML = `<div class="empty-state" style="text-align: center; padding: 50px 0; color: var(--text-muted);">No saved quotes yet. Find inspiration and click favorite!</div>`;
        return;
    }

    favorites.forEach(q => {
        const card = document.createElement('div');
        card.className = "fav-card";
        card.innerHTML = `
            <div class="fav-content">
                <p class="fav-text">"${q.text}"</p>
                <cite class="fav-author">— ${q.author}</cite>
            </div>
            <div class="fav-controls">
                <button class="fav-btn-action copy-fav">Copy</button>
                <button class="fav-btn-action remove remove-fav">Remove</button>
            </div>
        `;

        // Add action handlers
        card.querySelector('.copy-fav').addEventListener('click', () => {
            shareQuote(q);
        });

        card.querySelector('.remove-fav').addEventListener('click', () => {
            favorites = favorites.filter(f => f.text !== q.text);
            localStorage.setItem('daily_spark_favorites', JSON.stringify(favorites));
            renderFavorites();
            updateFavoriteBtnState();
            showToast("Removed from favorites");
        });

        favoritesList.appendChild(card);
    });
}

// Copy & Toast logic
function shareQuote(quote) {
    const formatted = `"${quote.text}" — ${quote.author}`;
    navigator.clipboard.writeText(formatted).then(() => {
        showToast("Quote copied to clipboard!");
    }).catch(err => {
        console.error("Clipboard copy failed: ", err);
    });
}

function showToast(message) {
    toast.textContent = message;
    toast.classList.add('show');
    setTimeout(() => {
        toast.classList.remove('show');
    }, 2500);
}

// Run App
document.addEventListener('DOMContentLoaded', init);
