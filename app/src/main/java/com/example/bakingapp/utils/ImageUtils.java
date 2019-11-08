package com.example.bakingapp.utils;

import android.widget.ImageView;

import com.example.bakingapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ImageUtils {
    private static final String[] foodNames = {"cheesecake", "cake", "brownie", "pie"};
    private static final int[] foodResources = {R.drawable.ic_cheesecake, R.drawable.ic_cake,
            R.drawable.ic_brownie, R.drawable.ic_pie};
    private static final Map<String, Integer> sFoodResourceMap = getFoodResourceMap();

    public static void setImage(ImageView imageView, String path, String name) {
        setImage(imageView, path, getMatchingImage(name));
    }

    public static void setImage(ImageView imageView, String path, int defaultImage) {
        path = path.isEmpty() ? "invalid_path" : path;
        Picasso.get()
                .load(path)
                .error(defaultImage)
                .placeholder(defaultImage)
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

    private static Integer getMatchingImage(String name) {
        Pattern pattern = Pattern.compile(String.join("|", foodNames), Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        String matchString = matcher.find() ? matcher.group(0) : "";
        return sFoodResourceMap.getOrDefault(matchString.toLowerCase(), R.drawable.ic_food);
    }

    private static Map<String, Integer> getFoodResourceMap() {
        return IntStream
                .range(0, foodNames.length)
                .boxed()
                .collect(Collectors.toMap(i -> foodNames[i], i -> foodResources[i]));
    }
}
