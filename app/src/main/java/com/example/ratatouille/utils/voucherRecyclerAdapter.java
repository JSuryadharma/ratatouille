package com.example.ratatouille.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratatouille.R;
import com.example.ratatouille.models.Vouchers;
import com.example.ratatouille.vars.VariablesUsed;
import com.example.ratatouille.views.voucherFragment;

import java.util.ArrayList;

// For my vouchers
public class voucherRecyclerAdapter extends RecyclerView.Adapter<voucherRecyclerAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Vouchers> voucherList;
    private static ArrayList<Boolean> touched = new ArrayList<>();

    public voucherRecyclerAdapter(Context context, ArrayList<Vouchers> voucherList) {
        this.context = context;
        this.voucherList = voucherList;
        for(int i=0; i<voucherList.size(); i++){
            touched.add(false);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_voucher, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Vouchers selectedVoucher = voucherList.get(position) ;
        holder.voucherName.setText(selectedVoucher.getVoucherName());
        holder.voucherDisc.setText("Discount: " + selectedVoucher.getVoucherDisc().toString() + " %");
        holder.voucherType.setText("Type: Dine-in");

        if(position == voucherFragment.selectedItem){
            holder.voucherButton.setBackgroundResource(R.drawable.voucherbutton_pressed_background);
        } else {
            holder.voucherButton.setBackgroundResource(R.drawable.voucherbutton_background);
        }

        holder.voucherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checking if there's a voucher selected..
                for (int i = 0; i < voucherList.size(); i++) {
                    if (touched.get(i) == true && position != i) {
                        Toast.makeText(context, "You have selected a voucher!", Toast.LENGTH_LONG);
                        Utils.showDialogMessage(R.drawable.ic_warning, context, "Cannot Select Item", "You already have selected an Item!\n Please unselect an item.");
                        return;
                    }
                }

                if (touched.get(position) == false) {
                    voucherFragment.selectedItem = position;
                    holder.voucherButton.setBackgroundResource(R.drawable.voucherbutton_pressed_background);
                    VariablesUsed.currentVoucher = voucherList.get(position);
                    touched.set(position, true);
                    System.out.println("onpressed: " + position);
                } else if (touched.get(position) == true) {
                    voucherFragment.selectedItem = -1;
                    VariablesUsed.currentVoucher = null;
                    touched.set(position, false);
                    System.out.println("offpressed: " + position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return voucherList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView voucherName;
        TextView voucherDisc;
        TextView voucherMode;
        TextView voucherType;
        LinearLayout voucherButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            voucherName = itemView.findViewById(R.id.voucher_name);
            voucherDisc = itemView.findViewById(R.id.voucher_disc);
            voucherMode = itemView.findViewById(R.id.voucher_mode);
            voucherType = itemView.findViewById(R.id.voucher_type);
            voucherButton = itemView.findViewById(R.id.voucher_useButton);
        }
    }
}
