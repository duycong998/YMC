package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.db.CongViec;
import com.example.myapplication.db.CongViecDao;
import com.example.myapplication.db.CongViecDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edt;
    Button btn;
    //RecyclerView recyclerViewTask;
    ListView lsv;
    ArrayList<CongViec> arrayList = new ArrayList<>();
    CongViecAdapter adapter;

    CongViecDatabase cvDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edt.getText().toString();
                if(ten.equals("")){
                    Toast.makeText(MainActivity.this, "nhap lai di ", Toast.LENGTH_SHORT).show();
                }else {
                    CongViecDao congViecDao = cvDatabase.congviecDao();
                    congViecDao.insert(new CongViec(edt.getText().toString()));
                    Toast.makeText(MainActivity.this, "them thanh cong", Toast.LENGTH_SHORT).show();
                    testData();
                }

            }
        });

    }

    private void initView(){
        edt = (EditText) findViewById(R.id.edtThem);
        btn = (Button) findViewById(R.id.btnThem);
        lsv = (ListView) findViewById(R.id.lsv);
        //recyclerViewTask = (RecyclerView) findViewById(R.id.);


    }

    private void initData(){
        adapter = new CongViecAdapter(this,R.layout.dong_cong_viec,arrayList);
        //   recyclerViewTask.setHasFixedSize(true);
        lsv.setAdapter(adapter);
        testData();

    }

    private void testData(){
        cvDatabase = CongViecDatabase.getDatabase(this);
        CongViecDao congViecDao = cvDatabase.congviecDao();

        arrayList.clear();
        arrayList.addAll( congViecDao.getAlphabetizedWords());
        //   Log.d("aaa", "refresh tasks: " + taskDao.getAlphabetizedWords().size());
        adapter.notifyDataSetChanged();


    }

    public void DiaLogSuaCV(final CongViec congViec) {
        final Dialog dialog = new Dialog(MainActivity.this);
      // dialogSua.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_cong_viec);
        dialog.show();
        final EditText edtSua  = (EditText) dialog.findViewById(R.id.edtEdit);
        Button btnXN     = (Button) dialog.findViewById(R.id.btnXacNhanEdit);
        Button btnHuy    = (Button) dialog.findViewById(R.id.btnHuyEdit);

        edtSua.setText(congViec.getTenCV());
        btnXN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ten = edtSua.getText().toString();
                CongViecDao congViecDao = cvDatabase.congviecDao();
                congViecDao.updateCV(congViec);
                testData();
                 //Log.d("aaa",tenCV);
                Toast.makeText(MainActivity.this, "Edit thanh cong", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void diaLogXoaCV(final CongViec congViec){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xóa công việc " + congViec.getTenCV() + " không???");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CongViecDao congViecDao = cvDatabase.congviecDao();
                congViecDao.deleteCV(congViec);
                Toast.makeText(MainActivity.this, "Bạn đã xóa thành công", Toast.LENGTH_SHORT).show();
                testData();
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

      builder.show();
    }



}
