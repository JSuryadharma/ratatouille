package com.example.ratatouille.utils;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratatouille.R;
import com.example.ratatouille.controllers.VoucherController;
import com.example.ratatouille.models.UserVoucher;
import com.example.ratatouille.models.Users;
import com.example.ratatouille.models.Vouchers;
import com.example.ratatouille.vars.VariablesUsed;
import com.example.ratatouille.views.customerView;
import com.example.ratatouille.views.voucherFragment;

import java.util.ArrayList;

import static com.example.ratatouille.vars.VariablesUsed.currentVoucher;

// For my vouchers
public class voucherRecyclerAdapter extends RecyclerView.Adapter<voucherRecyclerAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Vouchers> voucherList;

    public static Integer selectedItem = -1;
    public Integer voucherAmount = new Integer(0);
    private voucherAmountCallbackHelper cb = new voucherAmountCallbackHelper() {
        @Override
        public void onUserCallback(MyViewHolder holder) {
            setAmountOnHolder(holder);
        }
    };
    private callbackHelper callback = new callbackHelper() {
        @Override
        public void onUserLoadCallback(Context context, Users u) {
            Handler handler = new android.os.Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    currentVoucher = null;
                    Animation anim = AnimationUtils.loadAnimation(context, R.anim.bottom_to_up);
                    customerView.currentVoucher_name.setText("");
                    customerView.currentVoucher_area.startAnimation(anim);
                    customerView.currentVoucher_area.setVisibility(View.GONE);
                    Toast.makeText(context, "Voucher is now able to be used again.", Toast.LENGTH_LONG).show();
                    handler.removeCallbacks(this);
                }
            };
            handler.postDelayed(runnable, 30000);
            Toast.makeText(context, "Voucher successfully used! Cooldown in 30 seconds.", Toast.LENGTH_LONG).show();
        }
    };

    public voucherRecyclerAdapter(Context context, ArrayList<Vouchers> voucherList) {
        this.context = context;
        this.voucherList = voucherList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_voucher, parent, false);
        return new MyViewHolder(view);
    }

    private Utils.response dialogRsp = new Utils.response() {
        @Override
        public void yesResponse() {
            currentVoucher = voucherList.get(selectedItem);
            Animation anim = AnimationUtils.loadAnimation(context, R.anim.up_to_bottom);
            customerView.currentVoucher_name.setText(currentVoucher.getVoucherName() + " " + currentVoucher.getVoucherDisc().toString() + "%");
            customerView.currentVoucher_area.setVisibility(View.VISIBLE);
            customerView.currentVoucher_area.startAnimation(anim);
            VoucherController.useVoucher(context, callback);
        }

        @Override
        public void noResponse() {
            Toast.makeText(context, "Action cancelled.", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Vouchers selectedVoucher = voucherList.get(position);
        holder.voucherName.setText(selectedVoucher.getVoucherName());
        holder.voucherDisc.setText("Discount: " + selectedVoucher.getVoucherDisc().toString() + " %");
        holder.voucherType.setText("Type: Dine-in");
        voucherAmount = 0;

        holder.voucherAmount.setText("Amount : " + voucherAmount);

        voucherAmount = UserVoucher.getVoucherQuantity(this, cb, holder, selectedVoucher);

        holder.voucherButton.setBackgroundResource(R.drawable.voucherbutton_background);

        holder.voucherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentVoucher != null){
                    Utils.showDialogMessage(R.drawable.ic_warning, context, "Action Rejected", "Please wait until the following (30) seconds cooldown.");
                    return;
                }
                selectedItem = position;
                Utils.showOptMessage(context, dialogRsp, "Confirmation", "Are you sure to use : " + selectedVoucher.getVoucherName() + " " + selectedVoucher.getVoucherDisc() + "\n Please be noted that this action can only be done by the Restaurant only!");
            }
        });
    }

    public void setAmountOnHolder(MyViewHolder holder){
        holder.voucherAmount.setText("Amount : " + voucherAmount);
    }

    @Override
    public int getItemCount() {
        return voucherList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView voucherName;
        TextView voucherDisc;
        TextView voucherMode;
        TextView voucherType;
        TextView voucherAmount;
        LinearLayout voucherButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            voucherName = itemView.findViewById(R.id.voucher_name);
            voucherDisc = itemView.findViewById(R.id.voucher_disc);
            voucherMode = itemView.findViewById(R.id.voucher_mode);
            voucherType = itemView.findViewById(R.id.voucher_type);
            voucherAmount = itemView.findViewById(R.id.voucher_amount);
            voucherButton = itemView.findViewById(R.id.voucher_useButton);
        }
    }
}
