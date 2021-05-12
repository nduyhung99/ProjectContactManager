package com.example.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.model.Contact;
import com.example.projectcontactmanager.MainActivity;
import com.example.projectcontactmanager.NhanTinActivity;
import com.example.projectcontactmanager.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {
    Activity context;
    int resource;
    List<Contact> objects;
    public ContactAdapter(@NonNull Activity context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=this.context.getLayoutInflater();
        View row=inflater.inflate(this.resource,null);

        TextView txtTen=(TextView) row.findViewById(R.id.txtTen);
        TextView txtPhone=(TextView) row.findViewById(R.id.txtPhone);
        ImageButton btnCall=row.findViewById(R.id.btnCall);
        ImageButton btnSms=row.findViewById(R.id.btnSms);
        ImageButton btnDelete=row.findViewById(R.id.btnDelete);

        final Contact contact=this.objects.get(position);
        txtTen.setText(contact.getName());
        txtPhone.setText(contact.getPhone());

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestPermissions();
                if ( ContextCompat.checkSelfPermission( context, Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED ){
                    xuLyCall(contact);
                }else return;
            }
        });
        
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestPermissions();
                if ( ContextCompat.checkSelfPermission( context, Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED ){
                    xuLySms(contact);
                }else return;
            }
        });
        
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyXoa(contact);
            }
        });
        return row;
        
    }
    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this.context, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this.context, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }

    private void xuLyXoa(Contact contact) {
        this.remove(contact);
    }

    private void xuLySms(Contact contact) {
        Intent intent=new Intent(this.context, NhanTinActivity.class);
        intent.putExtra("CONTACT",contact);
        this.context.startActivity(intent);
    }

    private void xuLyCall(Contact contact) {
        Intent intent=new Intent(Intent.ACTION_CALL);
        Uri uri=Uri.parse("tel:"+contact.getPhone());
        intent.setData(uri);
        this.context.startActivity(intent);
    }
}
