package com.example.gopal.quakereport1;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Gopal on 12/25/2018.
 */


public class CustomAdapter extends ArrayAdapter<Earthquake> {

    private String mPrimaryLocation;
    private  String mLocatioOffset;

    public CustomAdapter(Context context, List<Earthquake> earthquakes){
        super(context,0,earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Earthquake currentEarthQuake = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        magnitudeView.setText( DecimalFormatter( currentEarthQuake.getMagnitude()));

        // for setting background color

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthQuake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);


        stringManipulation(currentEarthQuake.getLocation());
        TextView locationOffset = (TextView) listItemView.findViewById(R.id.location_offset);
        locationOffset.setText(mLocatioOffset);

        TextView primaryLocation = (TextView) listItemView.findViewById(R.id.primary_location);
        primaryLocation.setText(mPrimaryLocation);

        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        String dateString = DateFormatter(currentEarthQuake.getDate());
        dateView.setText(dateString);

        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        String timeString = TimeFormatter(currentEarthQuake.getDate());
        timeView.setText(timeString);


        return listItemView;
    }
    //Formatting the date
    String DateFormatter(long timeInMilliSecond){
        Date dateObject = new Date(timeInMilliSecond);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        return  dateFormat.format(dateObject);
    }

    //Formatting the time
    String TimeFormatter(long timeInMilliSecond){
        Date dateObject = new Date(timeInMilliSecond);
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        return  dateFormat.format(dateObject);
    }

    //Formatting the string
    private void  stringManipulation(String location){

        if(location.contains("of")){
            String[] locationArray = location.split("of");
            mLocatioOffset = locationArray[0] + "of";
            mPrimaryLocation = locationArray[1];
        }
        else {
            mLocatioOffset = "Near the ";
            mPrimaryLocation = location;
        }

    }

    //Formatting the magnitude
    private String  DecimalFormatter(double decimalMagnitude){
        DecimalFormat formatter = new DecimalFormat("0.0");
        return formatter.format(decimalMagnitude);
    }

    // color selection
    private int getMagnitudeColor(double earthQuakeMagnitude){
        int magnitude = (int) Math.floor(earthQuakeMagnitude);
        int colorId;
        switch(magnitude){
            case 0:
            case 1: colorId = R.color.magnitude1;break;
            case 2: colorId = R.color.magnitude2;break;
            case 3: colorId = R.color.magnitude3;break;
            case 4: colorId = R.color.magnitude4;break;
            case 5: colorId = R.color.magnitude5;break;
            case 6: colorId = R.color.magnitude6;break;
            case 7: colorId = R.color.magnitude7;break;
            case 8: colorId = R.color.magnitude8;break;
            case 9: colorId = R.color.magnitude9;break;
            default: colorId =R.color.magnitude10plus;break;
        }
        return ContextCompat.getColor(getContext(),colorId);
    }


}
