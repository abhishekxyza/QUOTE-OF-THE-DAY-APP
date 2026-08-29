package com.example.quoteapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.quoteapp.databinding.FragmentDashboardBinding;
import com.google.android.material.chip.Chip;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private QuoteDatabase quoteDatabase;
    private FavoritesManager favoritesManager;
    private QuoteDatabase.Quote currentQuote;
    private String selectedCategory = "All";

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

        setupCategoryChips();
        currentQuote = quoteDatabase.getQuoteOfDay();
        updateUI();

        binding.btnRefresh.setOnClickListener(v -> refreshQuote());

        binding.btnFavorite.setOnClickListener(v -> {
            favoritesManager.toggleFavorite(currentQuote);
            updateFavoriteButton();
            boolean isFav = favoritesManager.isFavorited(currentQuote);
            Toast.makeText(getContext(), isFav ? "Added to favorites!" : "Removed from favorites", Toast.LENGTH_SHORT).show();
        });

        binding.btnExport.setOnClickListener(v -> exportQuoteAsImage());
        
        // Long press to copy
        binding.cardQuote.setOnLongClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("quote", currentQuote.toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), "Quote copied to clipboard!", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupCategoryChips() {
        Set<String> categories = new HashSet<>();
        categories.add("All");
        for (QuoteDatabase.Quote q : quoteDatabase.getAllQuotes()) {
            categories.add(q.getCategory());
        }

        binding.chipGroupCategories.removeAllViews();
        for (String category : categories) {
            Chip chip = new Chip(getContext());
            chip.setText(category);
            chip.setCheckable(true);
            if (category.equals(selectedCategory)) chip.setChecked(true);
            
            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedCategory = category;
                    refreshQuote();
                }
            });
            binding.chipGroupCategories.addView(chip);
        }
    }

    private void refreshQuote() {
        QuoteDatabase.Quote newQuote;
        List<QuoteDatabase.Quote> pool = new ArrayList<>();
        
        if (selectedCategory.equals("All")) {
            pool = quoteDatabase.getAllQuotes();
        } else {
            for (QuoteDatabase.Quote q : quoteDatabase.getAllQuotes()) {
                if (q.getCategory().equals(selectedCategory)) pool.add(q);
            }
        }

        if (pool.isEmpty()) return;

        do {
            newQuote = pool.get(new java.util.Random().nextInt(pool.size()));
        } while (currentQuote != null && newQuote.getText().equals(currentQuote.getText()) && pool.size() > 1);

        currentQuote = newQuote;
        
        // Animation
        binding.cardQuote.animate()
                .scaleX(0.9f).scaleY(0.9f).alpha(0.5f)
                .setDuration(150)
                .withEndAction(() -> {
                    updateUI();
                    binding.cardQuote.animate()
                            .scaleX(1.0f).scaleY(1.0f).alpha(1.0f)
                            .setDuration(250)
                            .setInterpolator(new AccelerateDecelerateInterpolator())
                            .start();
                }).start();
    }

    private void exportQuoteAsImage() {
        View view = binding.cardQuote;
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        try {
            File cachePath = new File(requireContext().getCacheDir(), "images");
            cachePath.mkdirs();
            File file = new File(cachePath, "shared_quote.png");
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

            Uri contentUri = FileProvider.getUriForFile(requireContext(), "com.example.quoteapp.fileprovider", file);

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/png");
            intent.putExtra(Intent.EXTRA_STREAM, contentUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(intent, "Export Quote"));

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Failed to export image", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI() {
        binding.textQuote.setText("\"" + currentQuote.getText() + "\"");
        binding.textAuthor.setText("— " + currentQuote.getAuthor());
        binding.textCategory.setText(currentQuote.getCategory());
        updateFavoriteButton();
    }

    private void updateFavoriteButton() {
        if (favoritesManager.isFavorited(currentQuote)) {
            binding.btnFavorite.setText("Favorited");
            binding.btnFavorite.setIconResource(android.R.drawable.btn_star_big_on);
        } else {
            binding.btnFavorite.setText("Favorite");
            binding.btnFavorite.setIconResource(android.R.drawable.btn_star_big_off);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
