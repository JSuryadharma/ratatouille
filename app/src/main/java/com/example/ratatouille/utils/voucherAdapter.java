package com.example.ratatouille.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;

import com.example.ratatouille.R;
import com.example.ratatouille.controllers.VoucherController;
import com.example.ratatouille.models.Vouchers;
import com.example.ratatouille.vars.VariablesUsed;

import java.util.ArrayList;

//For Voucher Search and Buy..
public class voucherAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<Vouchers> vouchers;
    private static ArrayList<Boolean> touched = new ArrayList<>();

    public voucherAdapter(Context context, ArrayList<Vouchers> vouchers) {
        this.context = context;
        this.vouchers = vouchers;
        for(int i=0; i<vouchers.size(); i++){
            touched.add(false);
        }
    }

    @Override
    public int getCount() {
        return vouchers.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewpager_voucher, container, false);

        TextView voucher_name = view.findViewById(R.id.voucher_name);
        TextView voucher_disc = view.findViewById(R.id.voucher_disc);
        TextView voucher_mode = view.findViewById(R.id.voucher_mode);
        TextView voucher_type = view.findViewById(R.id.voucher_type);
        LinearLayout voucher_button = view.findViewById(R.id.voucher_useButton);

        voucher_name.setText(vouchers.get(position).getVoucherName());
        voucher_disc.setText("Discount:" + vouchers.get(position).getVoucherDisc().toString() + " %");
        voucher_type.setText("Price: Rp" + vouchers.get(position).getPrice().toString());
        voucher_mode.setText("Buy Voucher");

        voucher_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(VariablesUsed.currentUser.getPoints() >= vouchers.get(position).getPrice()) {
                    VoucherController.assignVoucher(VariablesUsed.currentUser, vouchers.get(position));
                    VoucherController.buyVoucher(vouchers.get(position).getPrice());
                    Utils.showDialogMessage(R.drawable.verified_logo, context, "Success Message", "Voucher successfully added\n to your account.");
                } else {
                    Utils.showDialogMessage(R.drawable.ic_warning, context, "Failure Message", "Failed to add : Not enough points.");
                }
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
