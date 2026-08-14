package src;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class QuoteDatabase {
    public static class Quote {
        private final String text;
        private final String author;
        private final String category;

        public Quote(String text, String author, String category) {
            this.text = text;
            this.author = author;
            this.category = category;
        }

        public String getText() {
            return text;
        }

        public String getAuthor() {
            return author;
        }

        public String getCategory() {
            return category;
        }

        @Override
        public String toString() {
            return "\"" + text + "\" — " + author;
        }
    }

    private final List<Quote> quotes;

    public QuoteDatabase() {
        quotes = new ArrayList<>();
        initializeQuotes();
    }

    private void initializeQuotes() {
        // A rich collection of high-quality inspiring quotes
        quotes.add(new Quote("The only way to do great work is to love what you do.", "Steve Jobs", "Inspiration"));
        quotes.add(new Quote("Believe you can and you're halfway there.", "Theodore Roosevelt", "Motivation"));
        quotes.add(new Quote("It always seems impossible until it's done.", "Nelson Mandela", "Perseverance"));
        quotes.add(new Quote("Success is not final, failure is not fatal: it is the courage to continue that counts.", "Winston Churchill", "Success"));
        quotes.add(new Quote("Act as if what you do makes a difference. It does.", "William James", "Impact"));
        quotes.add(new Quote("Keep your face always toward the sunshine—and shadows will fall behind you.", "Walt Whitman", "Optimism"));
        quotes.add(new Quote("The best way to predict your future is to create it.", "Abraham Lincoln", "Wisdom"));
        quotes.add(new Quote("What lies behind us and what lies before us are tiny matters compared to what lies within us.", "Ralph Waldo Emerson", "Strength"));
        quotes.add(new Quote("You miss 100% of the shots you don't take.", "Wayne Gretzky", "Opportunity"));
        quotes.add(new Quote("Whether you think you can or you think you can't, you're right.", "Henry Ford", "Mindset"));
        quotes.add(new Quote("The mind is everything. What you think you become.", "Buddha", "Wisdom"));
        quotes.add(new Quote("An unexamined life is not worth living.", "Socrates", "Philosophy"));
        quotes.add(new Quote("Out of clutter, find simplicity. From discord, find harmony. In the middle of difficulty lies opportunity.", "Albert Einstein", "Wisdom"));
        quotes.add(new Quote("Do what you can, with what you have, where you are.", "Theodore Roosevelt", "Practicality"));
        quotes.add(new Quote("The only limit to our realization of tomorrow will be our doubts of today.", "Franklin D. Roosevelt", "Hope"));
        quotes.add(new Quote("Happiness is not something ready made. It comes from your own actions.", "Dalai Lama", "Happiness"));
        quotes.add(new Quote("Don't watch the clock; do what it does. Keep going.", "Sam Levenson", "Perseverance"));
        quotes.add(new Quote("It is never too late to be what you might have been.", "George Eliot", "Potential"));
        quotes.add(new Quote("We do not inherit the earth from our ancestors, we borrow it from our children.", "Native American Proverb", "Responsibility"));
        quotes.add(new Quote("In the end, we will remember not the words of our enemies, but the silence of our friends.", "Martin Luther King Jr.", "Justice"));
    }

    public List<Quote> getAllQuotes() {
        return quotes;
    }

    public Quote getQuoteOfDay() {
        if (quotes.isEmpty()) {
            return new Quote("No quotes available.", "System", "Info");
        }
        Calendar cal = Calendar.getInstance();
        int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
        int year = cal.get(Calendar.YEAR);
        // Combine day and year to hash stably
        int index = Math.abs((dayOfYear + year * 31) % quotes.size());
        return quotes.get(index);
    }

    public Quote getRandomQuote() {
        if (quotes.isEmpty()) {
            return new Quote("No quotes available.", "System", "Info");
        }
        Random rand = new Random();
        return quotes.get(rand.nextInt(quotes.size()));
    }
}
