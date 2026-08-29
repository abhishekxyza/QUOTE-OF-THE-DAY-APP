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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quoteapp.databinding.FragmentFavoritesBinding;
import com.example.quoteapp.databinding.ItemFavoriteQuoteBinding;

import java.util.List;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;
    private FavoritesManager favoritesManager;
    private FavoritesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favoritesManager = new FavoritesManager(requireContext());
        List<QuoteDatabase.Quote> favorites = favoritesManager.getFavorites();

        adapter = new FavoritesAdapter(favorites, new FavoritesAdapter.OnQuoteClickListener() {
            @Override
            public void onCopyClick(QuoteDatabase.Quote quote) {
                ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("quote", quote.toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Quote copied to clipboard!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRemoveClick(QuoteDatabase.Quote quote, int position) {
                favoritesManager.removeFavorite(quote);
                adapter.removeItem(position);
                updateEmptyState();
                Toast.makeText(getContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
            }
        });

        binding.recyclerFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerFavorites.setAdapter(adapter);

        updateEmptyState();
    }

    private void updateEmptyState() {
        if (adapter.getItemCount() == 0) {
            binding.textEmptyState.setVisibility(View.VISIBLE);
            binding.recyclerFavorites.setVisibility(View.GONE);
        } else {
            binding.textEmptyState.setVisibility(View.GONE);
            binding.recyclerFavorites.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    interface OnQuoteClickListener {
        void onCopyClick(QuoteDatabase.Quote quote);
        void onRemoveClick(QuoteDatabase.Quote quote, int position);
    }

    private final List<QuoteDatabase.Quote> quotes;
    private final OnQuoteClickListener listener;

    FavoritesAdapter(List<QuoteDatabase.Quote> quotes, OnQuoteClickListener listener) {
        this.quotes = quotes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFavoriteQuoteBinding binding = ItemFavoriteQuoteBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuoteDatabase.Quote quote = quotes.get(position);
        holder.binding.textFavQuote.setText("\"" + quote.getText() + "\"");
        holder.binding.textFavAuthor.setText("— " + quote.getAuthor());

        holder.binding.btnFavCopy.setOnClickListener(v -> listener.onCopyClick(quote));
        holder.binding.btnFavRemove.setOnClickListener(v -> listener.onRemoveClick(quote, holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }

    public void removeItem(int position) {
        quotes.remove(position);
        notifyItemRemoved(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemFavoriteQuoteBinding binding;

        ViewHolder(ItemFavoriteQuoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
