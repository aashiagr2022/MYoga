package com.netra.myoga;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import android.database.Cursor;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

class DBConnection extends SQLiteOpenHelper {

    public static final String db_name = "Netra";
    public static final String netra_table = "Users";
    public static final String netra_id_column = "Id";
    public static final String netra_name_column = "FirstName";
    public static final String netra_email_column = "Email";
    public static final String netra_gender_column = "Gender";
    public static final String netra_age_column = "Age";
    public static final String netra_height_column = "Height";
    public static final String netra_weight_column = "Weight";
    public static final String netra_user_purpose = "Purpose";
    public static final String user_detail_status = "UserDetailStatus";

    public DBConnection(Context context)
    {
        super(context, db_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL(
                "create table Users" +
                        "(Id integer, FirstName text, Email text primary key, Gender text, Age text, " +
                        "Height integer, Weight integer, Purpose text, UserDetailStatus text)"
                );
    }

    public void deleteDb ()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE Users");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS Users");
        onCreate(db);
    }



    public boolean insertUser(String FirstName, String Email, String Gender, String Age, int Height, int Weight, String UserDetailStatus)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FirstName",FirstName);
        contentValues.put("Email", Email);
        contentValues.put("Gender",Gender);
        contentValues.put("Age", Age);
        contentValues.put("Height",Height);
        contentValues.put("Weight",Weight);
        contentValues.put("UserDetailStatus",UserDetailStatus);
//        contentValues.put("Purpose",Purpose);

        db.insert("Users",null,contentValues);
        return true;
    }

    public Cursor getData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from Users ",null);
        return result;
    }

    public ArrayList<String> getAllUsers()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from Users",null);
        result.moveToFirst();

        while(result.isAfterLast()==false)
        {
            array_list.add(result.getString(result.getColumnIndex(netra_name_column)));
            result.moveToNext();
        }

        return  array_list;
    }

    public boolean UpdateUserPurpose (String Purpose, String Email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Purpose",Purpose);
        db.update("Users", contentValues, "Email=?", new String[] {Email});

        //String strSQL = "UPDATE Users SET Purpose = Purpose WHERE columnId = Email";
        //db.execSQL(strSQL);

        return true;
    }

    public Cursor fetchAllData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Users ",null);

        return cursor;
    }

   /* String result = " ";
    PreparedStatement prepstmt;

    @Override
    protected  void onPreExecute()
    {
        super.onPreExecute();

    }

    protected String doInBackground(String... params)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("192.168.0.10:3306","root","learning");

            if(connection.isClosed())
            {
                Log.e("JDBAC","Connection is closed");
            }

            prepstmt = connection.prepareStatement("INSERT INTO `Users`(FirstName,Email,Gender,Age,Height,Weight)" +
                    "VALUES(?,?,?,?,?,?)");

;        }
        catch (Exception ex)
        {
            Log.e("DB Connection", ex.getMessage());
        }

        return result;
    }

    public void updateDB( String name, String email, String gender, int age, int height, int weight)
    {
        try
        {
        prepstmt.setString(1, name);
         prepstmt.setString(2,email);
         prepstmt.setString(3, gender);
         prepstmt.setInt(4,age);
         prepstmt.setInt(5,height);
         prepstmt.setInt(6,weight);
         prepstmt.executeUpdate("INSERT INTO Users(FirstName, Email, Gender, Age, Height, Weight)"+
                 "VALUES(name,email,gender,age,height,weight)");
        }
        catch(Exception ex)
        {
            Log.e("DB Update error", ex.getMessage());
        }
    }*/

}
