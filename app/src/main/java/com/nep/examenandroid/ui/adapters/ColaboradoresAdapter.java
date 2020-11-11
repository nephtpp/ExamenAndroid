package com.nep.examenandroid.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nep.examenandroid.R;
import com.nep.examenandroid.data.dto.Colaborador;
import com.nep.examenandroid.data.dto.employees.Employee;

import java.util.ArrayList;
import java.util.List;

public class ColaboradoresAdapter extends RecyclerView.Adapter<ColaboradoresAdapter.Holder> {

    private ArrayList<Employee> colaboradorList;
    private ListViewClickListener listener;

    public ColaboradoresAdapter(ArrayList<Employee> colaboradors ){
        this.colaboradorList = colaboradors;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.tvName.setText(colaboradorList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return colaboradorList.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.item_persona_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            if(listener != null) listener.clickListener(pos);
            notifyItemChanged(pos);
        }
    }

    public void setClickListener(ListViewClickListener itemClickListener){
        this.listener = itemClickListener;
    }

}
