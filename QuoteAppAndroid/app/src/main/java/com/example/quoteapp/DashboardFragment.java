package com.example.quoteapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quoteapp.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private QuoteDatabase quoteDatabase;
    private FavoritesManager favoritesManager;
    private QuoteDatabase.Quote currentQuote;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        quoteDatabase = new QuoteDatabase();
        favoritesManager = new FavoritesManager(requireContext());

        currentQuote = quoteDatabase.getQuoteOfDay();
        updateUI();

        binding.btnRefresh.setOnClickListener(v -> {
            QuoteDatabase.Quote newQuote;
            do {
                newQuote = quoteDatabase.getRandomQuote();
            } while (newQuote.getText().equals(currentQuote.getText()));
            currentQuote = newQuote;
            updateUI();
            Toast.makeText(getContext(), "Random quote loaded!", Toast.LENGTH_SHORT).show();
        });

        binding.btnFavorite.setOnClickListener(v -> {
            favoritesManager.toggleFavorite(currentQuote);
            updateFavoriteButton();
            boolean isFav = favoritesManager.isFavorited(currentQuote);
            Toast.makeText(getContext(), isFav ? "Added to favorites!" : "Removed from favorites", Toast.LENGTH_SHORT).show();
        });

        binding.btnShare.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("quote", currentQuote.toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), "Quote copied to clipboard!", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateUI() {
        binding.textQuote.setText("\"" + currentQuote.getText() + "\"");
        binding.textAuthor.setText("— " + currentQuote.getAuthor());
        binding.textCategory.setText(currentQuote.getCategory());
        updateFavoriteButton();
    }

    private void updateFavoriteButton() {
        if (favoritesManager.isFavorited(currentQuote)) {
            binding.btnFavorite.setText("★ Favorited");
        } else {
            binding.btnFavorite.setText("☆ Favorite");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
