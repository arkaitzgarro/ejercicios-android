package com.arkaitzgarro.earthquakes.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arkaitzgarro.earthquakes.R;
import com.arkaitzgarro.earthquakes.model.EarthQuake;

import java.util.List;

/**
 * Created by arkaitz on 25/03/15.
 */
public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {

    private final String EARTHQUAKE = "EARTHQUAKE";

    private int resource;
    private Context context;

    public EarthQuakeAdapter(Context context, int resource, List<EarthQuake> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;

        if (convertView == null) {
            // Si no existe la vista, la creamos
            layout = new LinearLayout(getContext());

            LayoutInflater li;
            String inflater = Context.LAYOUT_INFLATER_SERVICE;

            li = (LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource, layout, true);
        } else {
            layout = (LinearLayout) convertView;
        }

        EarthQuake item = getItem(position);

        TextView lblMag = (TextView) layout.findViewById(R.id.lblMag);
        TextView lblPlace = (TextView) layout.findViewById(R.id.lblPlace);
        TextView lblTime = (TextView) layout.findViewById(R.id.lblTime);

        lblMag.setText(item.getMagnitudeFormated());
        lblPlace.setText(item.getPlace());
        lblTime.setText(item.getTimeFormated());

        int n = (int)(item.getMagnitude() * 10);
        int color = Color.rgb((255 * n) / 100, (255 * (100 - n)) / 100 , 0);
        lblMag.setBackgroundColor(color);

        return layout;
    }
}
