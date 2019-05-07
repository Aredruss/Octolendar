package com.redbox.octolendar.utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.redbox.octolendar.R;
import com.redbox.octolendar.adapters.EventAdapter;
import com.redbox.octolendar.singleton.App;

import java.util.List;

public class TagUtility {
    private static long tagId;
    private static boolean interaction;

    public static void setTag(View v, App.TagDao tagDao, App.Event event, App.EventDao eventDao, EventAdapter adapter, int pos) {

        List<App.Tag> tags = tagDao.getAll();

        LayoutInflater layoutInflater = LayoutInflater.from(v.getContext());
        View selectTagView = layoutInflater.inflate(R.layout.select_tag_bottom_dialog, null);

        ChipGroup tagsChipGroup = selectTagView.findViewById(R.id.tagsChipGroup);

        for (App.Tag e : tags) {
            Chip tag = new Chip(v.getContext());
            tag.setText(e.text);
            tag.setCheckable(true);
            tag.setChipBackgroundColor(ContextCompat.getColorStateList(v.getContext(), e.color));
            tag.setCheckedIcon(v.getResources().getDrawable(R.drawable.ic_check_circle));
            tagsChipGroup.addView(tag);

            tag.setOnClickListener((View view) -> {
                tagId = e.id;
            });

            tag.setOnLongClickListener((View view) ->{
//                tagDao.delete(e);
//                tagsChipGroup.removeView(tag);
//                event.tagId = 0;
//                eventDao.update(event);
//                adapter.notifyItemChanged(pos);
                return true;
            });
        }
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext());
        builder.setView(selectTagView).setTitle("Set a tag").setNegativeButton("Cancel", (DialogInterface dialog, int i) -> {
                    interaction = true;
                    tagId = 0;
                    adapter.notifyItemChanged(pos);
                }
        ).setPositiveButton("Ok", (DialogInterface dialogInterface, int i) -> {
            Log.d("", "setTag: " + tagId);
            event.tagId = tagId;
            eventDao.update(event);
            adapter.notifyItemChanged(pos);
        }).show();

    }
}
