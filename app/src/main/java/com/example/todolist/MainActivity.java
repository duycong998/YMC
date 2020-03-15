package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Database database;
    ListView lsvCV;
    ArrayList<CongViec> arrayCV;
    CongViecAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lsvCV = (ListView) findViewById(R.id.listviewCV);
        arrayCV = new ArrayList<>();

        adapter = new CongViecAdapter(this,R.layout.dong_cong_viec,arrayCV);
        lsvCV.setAdapter(adapter);

        //tạo database GhiChu
        database = new Database(this,"ghichu.sqlite",null,1);

        // Tạo bảng CongViec
        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TENCV VARCHAR(200))");

        // insert data
        //database.QueryData("INSERT INTO CongViec VALUES(null, 'Học bài Android')");

        //truy vấn dữ liệu
        GetDataCongViec();


        lsvCV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,  "" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    //truy vấn dữ liệu
    private void GetDataCongViec(){
        Cursor dataCongViec = database.GetDate("SELECT * FROM CongViec");
        arrayCV.clear(); // xóa data đã add trước đó
        while (dataCongViec.moveToNext()){
            String ten = dataCongViec.getString(1);
            int id     = dataCongViec.getInt(0);
            arrayCV.add(new CongViec(id, ten));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_cv,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.menuAdd){
            DiaLogThemCV();
        }

        return super.onOptionsItemSelected(item);
    }
    public void DiaLogXoaCV(String TenCV , final int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xóa công việc " + TenCV + " không???");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM CongViec WHERE Id = '"+ id +"'");
                Toast.makeText(MainActivity.this, "Bạn đã xóa thành công", Toast.LENGTH_SHORT).show();
                GetDataCongViec();
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();

    }

    public void DiaLogSuaCV(String tenCVEdit , final int id){  // khai báo 2 biến để nó biết sau khi Edit nó trả về vị trí nào
        final Dialog dialogSua = new Dialog(this);
        dialogSua.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSua.setContentView(R.layout.dialog_sua_cong_viec);

        final EditText edtSua  = (EditText) dialogSua.findViewById(R.id.edtEdit);
        Button btnXN     = (Button) dialogSua.findViewById(R.id.btnXacNhanEdit);
        Button btnHuy    = (Button) dialogSua.findViewById(R.id.btnHuyEdit);

        edtSua.setText(tenCVEdit);

        btnXN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi = edtSua.getText().toString().trim();

                database.QueryData("UPDATE CongViec SET TENCV = '"+ tenMoi +"' WHERE Id = '"+ id +"' " );
                Toast.makeText(MainActivity.this, "Đã Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                dialogSua.cancel();
                GetDataCongViec();

            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSua.cancel();
            }
        });

        dialogSua.show();
    }

    private void DiaLogThemCV(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_cong_viec);

        final EditText edtThem = (EditText) dialog.findViewById(R.id.edtThem);
        Button btnThem         = (Button) dialog.findViewById(R.id.btnThem);
        Button btnHuy          = (Button) dialog.findViewById(R.id.btnHuy);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenCViec = edtThem.getText().toString();
                if(tenCViec.equals("")){
                    Toast.makeText(MainActivity.this, "Nhập lại đi bạn ^^", Toast.LENGTH_SHORT).show();
                }else {
                    database.QueryData("INSERT INTO CongViec VALUES(null, '" + tenCViec + "')");
                    Toast.makeText(MainActivity.this, "Bạn đã thêm thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataCongViec();
                }

            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }
}
