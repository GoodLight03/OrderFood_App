package com.example.orderfood.Activity.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Login.db";

    public DBHelper( Context context) {
        super(context, "Login.db",null,1);
    }
//  sẽ kế thừa class SQLiteOpenHelper(Đây là một class mà Android cho phép bạn xử lý các thao tác đối với
//  database của SQLite,vì vậy bạn có thể tạo một class khác thừa kế nó và tùy chỉnh việc điều khiển database theo ý mình):
    @Override
    //Đây là nơi để chúng ta viết những câu lệnh tạo bảng. Nó được gọi khi database đã được tạo
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT,fullname TEXT,phonenumber TEXT)");
    }

    @Override
//    Nó được gọi khi database được nâng cấp, ví dụ như chỉnh sửa cấu trúc các bảng,
//    thêm những thay đổi cho database
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");

    }
    //chen du lieu, them 1 ng dung moi
    public Boolean insertData(String username, String password,String fullname,String phonenumber){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("fullname", fullname);
        contentValues.put("phonenumber", phonenumber);
        long result = MyDB.insert("users", null, contentValues);
        if(result==-1) return false;//neu chen k duoc thi tra ve false
        else
            return true;
    }
    //load lay du lieu 1 list tai khoan ng dung
    public List<String> getAllUser() {
        List<String>  ls = new ArrayList<>();//tao ds rong

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("users", null,null,null,
                null,null,null);//tao con tro doc bang du lieu
        cursor.moveToFirst();//di chuyen con tro ve ban ghi dau tien
//neu khong phai ban ghi cuoi cung
        while(cursor.isAfterLast() == false) {
            TaiKhoan tk=new TaiKhoan();//tao doi tuong chua du lieu
            tk.setUsername(cursor.getString(0));//doc du lieu tu truong username va dua vao doi tuong
            tk.setPassword(cursor.getString(1));
            tk.setFullname(cursor.getString(2));
            tk.setPhone(cursor.getString(3));
            cursor.moveToNext();
            //chuyen thanh chuoi
            String chuoiTK =tk.getUsername()+"-"+tk.getPassword()+"-"+tk.getFullname()+"-"+tk.getPhone();
            //dua chuoi vao list
            ls.add(chuoiTK);
        }
        cursor.close();
        return ls;
    }
    //load lay du lieu 1 list ds UserName
    public ArrayList<String> getUserName() {
        ArrayList<String>  ls = new ArrayList<>();//tao ds rong
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("users", null,null,null,
                null,null,null);//tao con tro doc bang du lieu
        cursor.moveToFirst();//di chuyen con tro ve ban ghi dau tien
        while(!cursor.isAfterLast()) {
            ls.add(cursor.getString(1));//doc du lieu tu truong username va dua vao doi tuong
            cursor.moveToNext();
        }
        cursor.close();
        return ls;
    }

    //kiem tra ten ng dung da ton tai trong bang chua
    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});//truy van ten ng dung
        if (cursor.getCount() > 0)//ng dung ton tai tra ve true, nguoc lai tra ve false
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
}
