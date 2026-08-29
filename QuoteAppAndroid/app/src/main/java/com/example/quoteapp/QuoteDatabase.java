package com.example.quoteapp;

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
        // Leadership
        quotes.add(new Quote("Innovation distinguishes between a leader and a follower.", "Steve Jobs", "Leadership"));
        quotes.add(new Quote("A leader is one who knows the way, goes the way, and shows the way.", "John C. Maxwell", "Leadership"));
        quotes.add(new Quote("The quality of a leader is reflected in the standards they set for themselves.", "Ray Kroc", "Leadership"));
        quotes.add(new Quote("Management is doing things right; leadership is doing the right things.", "Peter Drucker", "Leadership"));
        quotes.add(new Quote("Lead me, follow me, or get out of my way.", "George S. Patton", "Leadership"));
        quotes.add(new Quote("Leadership is the art of giving people a platform for spreading ideas that work.", "Seth Godin", "Leadership"));
        quotes.add(new Quote("The greatest leader is not necessarily the one who does the greatest things. He is the one that gets the people to do the greatest things.", "Ronald Reagan", "Leadership"));
        quotes.add(new Quote("A good leader takes a little more than his share of the blame, a little less than his share of the credit.", "Arnold H. Glasow", "Leadership"));
        quotes.add(new Quote("Earn your leadership every day.", "Michael Jordan", "Leadership"));
        quotes.add(new Quote("To lead people, walk behind them.", "Lao Tzu", "Leadership"));

        // Happiness
        quotes.add(new Quote("Happiness is not something ready made. It comes from your own actions.", "Dalai Lama", "Happiness"));
        quotes.add(new Quote("The most important thing is to enjoy your life—to be happy—it's all that matters.", "Audrey Hepburn", "Happiness"));
        quotes.add(new Quote("Happiness depends upon ourselves.", "Aristotle", "Happiness"));
        quotes.add(new Quote("For every minute you are angry you lose sixty seconds of happiness.", "Ralph Waldo Emerson", "Happiness"));
        quotes.add(new Quote("Happiness is when what you think, what you say, and what you do are in harmony.", "Mahatma Gandhi", "Happiness"));
        quotes.add(new Quote("The purpose of our lives is to be happy.", "Dalai Lama", "Happiness"));
        quotes.add(new Quote("Success is not the key to happiness. Happiness is the key to success.", "Albert Schweitzer", "Happiness"));
        quotes.add(new Quote("Be happy for this moment. This moment is your life.", "Omar Khayyam", "Happiness"));
        quotes.add(new Quote("The only way to find true happiness is to risk being completely cut open.", "Chuck Palahniuk", "Happiness"));
        quotes.add(new Quote("Happiness is a warm puppy.", "Charles M. Schulz", "Happiness"));

        // Potential
        quotes.add(new Quote("It is never too late to be what you might have been.", "George Eliot", "Potential"));
        quotes.add(new Quote("Potential is a priceless treasure, like gold. All of us have gold hidden within, but we have to dig to get it out.", "Joyce Meyer", "Potential"));
        quotes.add(new Quote("The only limit to our realization of tomorrow will be our doubts of today.", "Franklin D. Roosevelt", "Potential"));
        quotes.add(new Quote("Continuous effort - not strength or intelligence - is the key to unlocking our potential.", "Winston Churchill", "Potential"));
        quotes.add(new Quote("The big challenge is to become all that you have the possibility of becoming.", "Jim Rohn", "Potential"));
        quotes.add(new Quote("What lies behind us and what lies before us are tiny matters compared to what lies within us.", "Ralph Waldo Emerson", "Potential"));
        quotes.add(new Quote("The will to win, the desire to succeed, the urge to reach your full potential... these are the keys that will unlock the door to personal excellence.", "Confucius", "Potential"));
        quotes.add(new Quote("Your present circumstances don't determine where you can go; they merely determine where you start.", "Nido Qubein", "Potential"));
        quotes.add(new Quote("Maximize your potential. You were born for greatness.", "Lailah Gifty Akita", "Potential"));
        quotes.add(new Quote("Success is not the end, but the beginning of your potential.", "Unknown", "Potential"));

        // Responsibility
        quotes.add(new Quote("We do not inherit the earth from our ancestors, we borrow it from our children.", "Native American Proverb", "Responsibility"));
        quotes.add(new Quote("With great power comes great responsibility.", "Stan Lee", "Responsibility"));
        quotes.add(new Quote("The price of greatness is responsibility.", "Winston Churchill", "Responsibility"));
        quotes.add(new Quote("You cannot escape the responsibility of tomorrow by evading it today.", "Abraham Lincoln", "Responsibility"));
        quotes.add(new Quote("Liberty means responsibility. That is why most men dread it.", "George Bernard Shaw", "Responsibility"));
        quotes.add(new Quote("Responsibility is the price of freedom.", "Elbert Hubbard", "Responsibility"));
        quotes.add(new Quote("It is a painful thing to look at your own trouble and know that you yourself and no one else has made it.", "Sophocles", "Responsibility"));
        quotes.add(new Quote("Accept responsibility for your life. Know that it is you who will get you where you want to go, no one else.", "Les Brown", "Responsibility"));
        quotes.add(new Quote("The greatest day in your life and mine is when we take total responsibility for our attitudes.", "John C. Maxwell", "Responsibility"));
        quotes.add(new Quote("Be the change that you wish to see in the world.", "Mahatma Gandhi", "Responsibility"));

        // Impact
        quotes.add(new Quote("Act as if what you do makes a difference. It does.", "William James", "Impact"));
        quotes.add(new Quote("The best way to find yourself is to lose yourself in the service of others.", "Mahatma Gandhi", "Impact"));
        quotes.add(new Quote("Never doubt that a small group of thoughtful, committed citizens can change the world.", "Margaret Mead", "Impact"));
        quotes.add(new Quote("Your life is your message to the world. Make sure it's inspiring.", "Unknown", "Impact"));
        quotes.add(new Quote("We can change the world and make it a better place. It is in our hands.", "Nelson Mandela", "Impact"));
        quotes.add(new Quote("I alone cannot change the world, but I can cast a stone to create many ripples.", "Mother Teresa", "Impact"));
        quotes.add(new Quote("What you do has an impact on who you are and what you can become.", "Tony Robbins", "Impact"));
        quotes.add(new Quote("Don't just count your days, make your days count.", "Muhammad Ali", "Impact"));
        quotes.add(new Quote("Success is not just about what you accomplish; it's about what you inspire others to do.", "Unknown", "Impact"));
        quotes.add(new Quote("The value of a man should be seen in what he gives.", "Albert Einstein", "Impact"));

        // Justice
        quotes.add(new Quote("In the end, we will remember not the words of our enemies, but the silence of our friends.", "Martin Luther King Jr.", "Justice"));
        quotes.add(new Quote("Injustice anywhere is a threat to justice everywhere.", "Martin Luther King Jr.", "Justice"));
        quotes.add(new Quote("Justice cannot be for one side alone, but must be for both.", "Eleanor Roosevelt", "Justice"));
        quotes.add(new Quote("True peace is not merely the absence of tension; it is the presence of justice.", "Martin Luther King Jr.", "Justice"));
        quotes.add(new Quote("The arc of the moral universe is long, but it bends toward justice.", "Theodore Parker", "Justice"));
        quotes.add(new Quote("A society that has more justice is a society that needs less charity.", "Mary Robinson", "Justice"));
        quotes.add(new Quote("Justice is the first virtue of social institutions.", "John Rawls", "Justice"));
        quotes.add(new Quote("Equal justice under law is perhaps the most inspiring ideal in our society.", "Lewis F. Powell Jr.", "Justice"));
        quotes.add(new Quote("There can be no deep disappointment where there is not deep love.", "Martin Luther King Jr.", "Justice"));
        quotes.add(new Quote("Peace and justice are two sides of the same coin.", "Dwight D. Eisenhower", "Justice"));

        // Inspiration
        quotes.add(new Quote("The only way to do great work is to love what you do.", "Steve Jobs", "Inspiration"));
        quotes.add(new Quote("Everything you can imagine is real.", "Pablo Picasso", "Inspiration"));
        quotes.add(new Quote("The mind is everything. What you think you become.", "Buddha", "Inspiration"));
        quotes.add(new Quote("Your time is limited, so don't waste it living someone else's life.", "Steve Jobs", "Inspiration"));
        quotes.add(new Quote("Don't watch the clock; do what it does. Keep going.", "Sam Levenson", "Inspiration"));
        quotes.add(new Quote("Believe you can and you're halfway there.", "Theodore Roosevelt", "Inspiration"));
        quotes.add(new Quote("It always seems impossible until it's done.", "Nelson Mandela", "Inspiration"));
        quotes.add(new Quote("Keep your face always toward the sunshine—and shadows will fall behind you.", "Walt Whitman", "Inspiration"));
        quotes.add(new Quote("The best way to predict your future is to create it.", "Abraham Lincoln", "Inspiration"));
        quotes.add(new Quote("If you can dream it, you can do it.", "Walt Disney", "Inspiration"));

        // Success
        quotes.add(new Quote("Success is not final, failure is not fatal: it is the courage to continue that counts.", "Winston Churchill", "Success"));
        quotes.add(new Quote("Success usually comes to those who are too busy to be looking for it.", "Henry David Thoreau", "Success"));
        quotes.add(new Quote("The secret of success is to do the common thing uncommonly well.", "John D. Rockefeller Jr.", "Success"));
        quotes.add(new Quote("I find that the harder I work, the more luck I seem to have.", "Thomas Jefferson", "Success"));
        quotes.add(new Quote("The starting point of all achievement is desire.", "Napoleon Hill", "Success"));
        quotes.add(new Quote("Success is walking from failure to failure with no loss of enthusiasm.", "Winston Churchill", "Success"));
        quotes.add(new Quote("To succeed in life, you need two things: ignorance and confidence.", "Mark Twain", "Success"));
        quotes.add(new Quote("Opportunities don't happen. You create them.", "Chris Grosser", "Success"));
        quotes.add(new Quote("Great things are done by a series of small things brought together.", "Vincent Van Gogh", "Success"));
        quotes.add(new Quote("The distance between insanity and genius is measured only by success.", "Bruce Feirstein", "Success"));

        // Creativity
        quotes.add(new Quote("Creativity is intelligence having fun.", "Albert Einstein", "Creativity"));
        quotes.add(new Quote("You can't use up creativity. The more you use, the more you have.", "Maya Angelou", "Creativity"));
        quotes.add(new Quote("Creativity takes courage.", "Henri Matisse", "Creativity"));
        quotes.add(new Quote("Creativity is a wild mind and a disciplined eye.", "Dorothy Parker", "Creativity"));
        quotes.add(new Quote("Don't think. Thinking is the enemy of creativity.", "Ray Bradbury", "Creativity"));
        quotes.add(new Quote("To live a creative life, we must lose our fear of being wrong.", "Joseph Chilton Pearce", "Creativity"));
        quotes.add(new Quote("The creative adult is the child who survived.", "Ursula K. Le Guin", "Creativity"));
        quotes.add(new Quote("You don't make art out of good intentions.", "Gustave Flaubert", "Creativity"));
        quotes.add(new Quote("Creativity is making the simple, awesomely simple.", "Charles Mingus", "Creativity"));
        quotes.add(new Quote("Simplicity is the ultimate sophistication.", "Leonardo da Vinci", "Creativity"));

        // Optimism
        quotes.add(new Quote("Optimism is the faith that leads to achievement.", "Helen Keller", "Optimism"));
        quotes.add(new Quote("A pessimist sees difficulty in every opportunity; an optimist sees opportunity in every difficulty.", "Winston Churchill", "Optimism"));
        quotes.add(new Quote("Choose to be optimistic, it feels better.", "Dalai Lama", "Optimism"));
        quotes.add(new Quote("Even the darkest night will end and the sun will rise.", "Victor Hugo", "Optimism"));
        quotes.add(new Quote("The man who is a pessimist before 48 knows too much; an optimist after it, he knows too little.", "Mark Twain", "Optimism"));
        quotes.add(new Quote("Part of being a person is about helping others.", "Regis Philbin", "Optimism"));
        quotes.add(new Quote("Keep your face to the sunshine and you cannot see a shadow.", "Helen Keller", "Optimism"));
        quotes.add(new Quote("The only way to see a rainbow is to look through the rain.", "Unknown", "Optimism"));
        quotes.add(new Quote("Optimism is a strategy for making a better future.", "Noam Chomsky", "Optimism"));
        quotes.add(new Quote("Every day may not be good... but there's something good in every day.", "Alice Morse Earle", "Optimism"));

        // Practicality
        quotes.add(new Quote("Do what you can, with what you have, where you are.", "Theodore Roosevelt", "Practicality"));
        quotes.add(new Quote("Focus on being productive instead of busy.", "Tim Ferriss", "Practicality"));
        quotes.add(new Quote("The simple things are also the most extraordinary things.", "Paulo Coelho", "Practicality"));
        quotes.add(new Quote("Small deeds done are better than great deeds planned.", "Peter Marshall", "Practicality"));
        quotes.add(new Quote("Action is the foundational key to all success.", "Pablo Picasso", "Practicality"));
        quotes.add(new Quote("Start where you are. Use what you have. Do what you can.", "Arthur Ashe", "Practicality"));
        quotes.add(new Quote("The best way to get started is to quit talking and begin doing.", "Walt Disney", "Practicality"));
        quotes.add(new Quote("Efficiency is doing things right; effectiveness is doing the right things.", "Peter Drucker", "Practicality"));
        quotes.add(new Quote("Simplicity is the soul of efficiency.", "Austin Freeman", "Practicality"));
        quotes.add(new Quote("An ounce of practice is worth more than tons of preaching.", "Mahatma Gandhi", "Practicality"));
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
