package com.example.suivi_des_employes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static androidx.core.content.ContextCompat.startActivity;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Employe> mList;
    private Context context;
    private OnItemListner onItemListner;

    public MyAdapter(Context context, ArrayList<Employe> mList, OnItemListner onItemListner) {
        this.mList = mList;
        this.context = context;
        this.onItemListner = onItemListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v, onItemListner);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Employe employe = mList.get(position);
        holder.nom.setText(employe.getNom());
        holder.prenom.setText(employe.getPrenom());
        holder.tel.setText(employe.getTele());
        holder.mission.setText(employe.getMission());
        holder.dateDepart.setText(employe.getDateDepart());
        holder.dateFin.setText(employe.getDateFin());
        holder.latitude.setText(employe.getLatitude());
        holder.longitude.setText(employe.getLongitude());
        /* La partie editer dans adpter*/
        holder.editer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.editer.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true, 1750)
                        .create();
                View myview = dialogPlus.getHolderView();
                final EditText nom = myview.findViewById(R.id.nomupd);
                final EditText prenom = myview.findViewById(R.id.prenomupd);
                final EditText tele = myview.findViewById(R.id.teleupd);
                final EditText mission = myview.findViewById(R.id.missionupd);
                final EditText dateDepart = myview.findViewById(R.id.date_deppartupd);
                final EditText latitude = myview.findViewById(R.id.latitudeupd);
                final EditText longitude = myview.findViewById(R.id.longitudeupd);
                Button submit = myview.findViewById(R.id.usubmit);
                final EditText dateFin = myview.findViewById(R.id.date_finupd);

                nom.setText(employe.getNom());
                prenom.setText(employe.getPrenom());
                tele.setText(employe.getTele());
                mission.setText(employe.getMission());
                dateDepart.setText(employe.getDateDepart());
                dateFin.setText(employe.getDateFin());
                latitude.setText(employe.getLatitude());
                longitude.setText(employe.getLongitude());
                dialogPlus.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("nom", nom.getText().toString());
                        map.put("prenom", prenom.getText().toString());
                        map.put("tele", tele.getText().toString());
                        map.put("mission", mission.getText().toString());
                        map.put("dateDepart", dateDepart.getText().toString());
                        map.put("dateFin", dateFin.getText().toString());
                        map.put("latitude",latitude.getText().toString());
                        map.put("longitude",longitude.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("Employés")
                                .child(String.valueOf(position)).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }


        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nom, prenom, tel, mission, dateDepart, dateFin, latitude, longitude;
        Button editer;
        OnItemListner onItemListner;

        public MyViewHolder(@NonNull View itemView, OnItemListner onItemListner) {
            super(itemView);
            nom = itemView.findViewById(R.id.nom);
            prenom = itemView.findViewById(R.id.prenom);
            tel = itemView.findViewById(R.id.tele);
            mission = itemView.findViewById(R.id.mission);
            dateDepart = itemView.findViewById(R.id.date_deppart);
            dateFin = itemView.findViewById(R.id.date_fin);
            latitude=itemView.findViewById(R.id.latitude);
            longitude=itemView.findViewById(R.id.longitude);
            /* La partie editer dans adpter*/
            editer = itemView.findViewById(R.id.edit_data);

            this.onItemListner = onItemListner;
            View deleteButtonView = itemView.findViewById(R.id.delete);
            deleteButtonView.setOnClickListener(this);
            View editButtonView = itemView.findViewById(R.id.edit_data);
            editButtonView.setOnClickListener(this::clickOnEdit);

        }

        private void clickOnEdit(View view) {
            TextView text = itemView.findViewById(R.id.edit_data);
            text.setText(nom.getText().toString());
        }

        @Override
        public void onClick(View v) {
            onItemListner.onItemClick(getAdapterPosition());
        }
    }

    public Employe getEmploye(int position) {
        return mList.get(position);
    }

    public interface OnItemListner {
        void onItemClick(int position);
    }
}
