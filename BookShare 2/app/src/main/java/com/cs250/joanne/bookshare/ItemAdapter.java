package com.cs250.joanne.bookshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by joanne.
 */
public class ItemAdapter extends ArrayAdapter<Item> {

    int resource;

    public ItemAdapter(Context ctx, int res, List<Item> items)
    {
        super(ctx, res, items);
        resource = res;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemView;
        Item it = getItem(position);

        if (convertView == null) {
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, itemView, true);
        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView availView = (TextView) itemView.findViewById(R.id.card_text);
        TextView infoView = (TextView) itemView.findViewById(R.id.detail);
        String info = it.getTitle() + "\n" + it.getAuthor();
        infoView.setText(info);
        availView.setText(it.getAvailability());
        return itemView;
    }

}
