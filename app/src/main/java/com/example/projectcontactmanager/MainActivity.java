package com.example.projectcontactmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.adapter.ContactAdapter;
import com.example.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText txtTen,txtPhone;
    Button btnLuu;

    ListView lvDanhBa;
    ArrayList<Contact>dsDanhBa;
    ContactAdapter adapterContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAndRequestPermissions();
        addControls();
        addEvents();
    }

    private void addEvents() {
       btnLuu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               xuLyLuuDanhBa();
           }
       });
    }

    private void xuLyLuuDanhBa() {
        Contact contact=new Contact(
                txtTen.getText().toString(),
                txtPhone.getText().toString()
        );
        dsDanhBa.add(contact);
        adapterContact.notifyDataSetChanged();
    }
    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }

    private void addControls() {
        txtTen=findViewById(R.id.txtTen);
        txtPhone=findViewById(R.id.txtPhone);
        btnLuu=findViewById(R.id.btnLuu);

        lvDanhBa=(ListView) findViewById(R.id.lvDanhBa);
        dsDanhBa=new ArrayList<>();
        adapterContact=new ContactAdapter(MainActivity.this,R.layout.item,dsDanhBa);
        lvDanhBa.setAdapter(adapterContact);
    }
}