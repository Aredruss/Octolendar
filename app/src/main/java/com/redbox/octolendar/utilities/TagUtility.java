package com.redbox.octolendar.utilities;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.redbox.octolendar.R;
import com.redbox.octolendar.singleton.App;

import java.util.List;

public class TagUtility {
    public static int setTag(View v, App.TagDao tagDao) {
        int tagId = 0;

        List<App.Tag> tags = tagDao.getAll();

        LayoutInflater layoutInflater = LayoutInflater.from(v.getContext());
        View selectTagView = layoutInflater.inflate(R.layout.select_tag_bottom_dialog, null);

        ChipGroup tagsChipGroup = selectTagView.findViewById(R.id.tagsChipGroup);

        for (App.Tag e : tags) {
            Chip tag = new Chip(v.getContext());
           tag.setText(e.text);

            tag.setChipBackgroundColor(ContextCompat.getColorStateList(v.getContext(), R.color.colorAccent));
            tagsChipGroup.addView(tag);
        }

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext());
        builder.setView(selectTagView).setTitle("Set a tag").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setPositiveButton("Ok", (DialogInterface dialogInterface, int i) -> {

        });

        builder.show();

        return tagId;
    }
}
