package com.example.bakingapp.utils;

import android.graphics.Color;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;

import com.example.bakingapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageUtils {
    private static final Pattern mPattern = Pattern.compile("cheesecake|cake|brownie|pie",
            Pattern.CASE_INSENSITIVE);

    private static final Map<String, Integer> sPlaceHolderMap = new HashMap<>();
    static {
        sPlaceHolderMap.put("cheesecake", R.drawable.ic_cheesecake);
        sPlaceHolderMap.put("cake", R.drawable.ic_cake);
        sPlaceHolderMap.put("brownie", R.drawable.ic_brownie);
        sPlaceHolderMap.put("pie", R.drawable.ic_pie);
    }

    public static void setImage(ImageView imageView, CardView cardView, String path, String name) {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        cardView.setBackgroundColor(color);

        if (path.isEmpty()) {
            imageView.setImageResource(getPlaceholder(name));
            return;
        }
        Picasso.get()
                .load(path)
                .placeholder(getPlaceholder(name))
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private static Integer getPlaceholder(String name) {
        Matcher matcher = mPattern.matcher(name);
        String matchString = matcher.find() ? matcher.group(0) : "";
        return sPlaceHolderMap.getOrDefault(matchString.toLowerCase(), R.drawable.ic_food);
    }
}
