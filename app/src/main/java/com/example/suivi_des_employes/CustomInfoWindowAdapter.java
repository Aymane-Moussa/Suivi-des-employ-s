package com.example.suivi_des_employes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import org.jetbrains.annotations.NotNull;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private  final View mWindow;
    private Context mContext;

    public CustomInfoWindowAdapter(Context context) {
        this.mContext = context;
        mWindow= LayoutInflater.from(context).inflate(R.layout.custom_info_window,null);
    }
    private void windowText(Marker marker,View view){
        String title= marker.getTitle();
        TextView mTitle=view.findViewById(R.id.title);
        if(!title.equals(""))
        {
            mTitle.setText(title);
        }
        String snippet= marker.getSnippet();
        TextView mSnippet=view.findViewById(R.id.snippet);
        if(!snippet.equals(""))
        {
            mSnippet.setText(snippet);
        }
    }
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View getInfoWindow(@NonNull @NotNull Marker marker) {
        windowText(marker, mWindow);
        return mWindow;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View getInfoContents(@NonNull @NotNull Marker marker) {
        windowText(marker, mWindow);
        return mWindow;
    }
}
