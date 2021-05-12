package com.example.suivi_des_employes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<Employe> mList;
    Context context;
    public MyAdapter(Context context,ArrayList<Employe> mList){
        this.mList=mList;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
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
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nom,prenom,tel,mission,dateDepart,dateFin;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nom=itemView.findViewById(R.id.nom);
            prenom=itemView.findViewById(R.id.prenom);
            tel=itemView.findViewById(R.id.tele);
            mission=itemView.findViewById(R.id.mission);
            dateDepart=itemView.findViewById(R.id.date_deppart);
            dateFin=itemView.findViewById(R.id.date_fin);
        }
    }
}
