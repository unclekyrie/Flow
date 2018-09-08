package dgsw.hs.kr.flow.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dgsw.hs.kr.flow.model.OutgoDocVO;

/**
 * Created by Administrator on 2018-04-10.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String userTableName = "Users";
    public static final String tokenTableName = "Tokens";
    public static final String outGoTableName = "OutgoDoc";
    public static final String outSleepTableName = "OutSleepDoc";

    public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("tag","db 생성_db가 없을때만 최초로 실행함");
        createUserTable(db);
        createTokenTable(db);
        createOutgoDocTable(db);
        createOutSleepDocTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
    }

    public void createUserTable(SQLiteDatabase db){
//        String sql = "CREATE TABLE IF NOT EXISTS" + tableName +
//                "(" +
//                    "email TEXT NOT NULL," +
//
//                    "pw TEXT NOT NULL," +
//
//                    "name TEXT NOT NULL," +
//
//                    "gender TEXT NOT NULL," +
//
//                    "mobile TEXT NOT NULL," +
//
//                    "class_idx INTEGER NOT NULL," +
//
//                    "class_number INTEGER NOT NULL," +
//
//                    "PRIMARY KEY(`email`)" +
//                ");";
        String sql =  "CREATE TABLE " + userTableName + "(email TEXT NOT NULL, pw TEXT NOT NULL, name TEXT NOT NULL, gender TEXT NOT NULL, mobile TEXT NOT NULL, class_idx INTEGER NOT NULL, class_number INTEGER NOT NULL, PRIMARY KEY(`email`))";

        try {
            db.execSQL(sql);
        }catch (SQLException e){
        }
    }

    public void createTokenTable(SQLiteDatabase db){
        String sql =  "CREATE TABLE " + tokenTableName + "(token TEXT)";
        try {
            db.execSQL(sql);
        }catch (SQLException e){
        }
    }

    public void createOutgoDocTable(SQLiteDatabase db){
        String sql =  "CREATE TABLE " + outGoTableName + "(accept INTEGER NOT NULL, idx INTEGER NOT NULL, StartTime TEXT NOT NULL, EndTime TEXT NOT NULL, reason TEXT NOT NULL, classIdx INTEGER NOT NULL, email TEXT NOT NULL)";

        try {
            db.execSQL(sql);
        }catch (SQLException e){
        }
    }

    public void createOutSleepDocTable(SQLiteDatabase db){
        String sql =  "CREATE TABLE " + outSleepTableName + "(accept INTEGER NOT NULL, idx INTEGER NOT NULL, StartTime TEXT NOT NULL, EndTime TEXT NOT NULL, reason TEXT NOT NULL, classIdx INTEGER NOT NULL, email TEXT NOT NULL)";

        try {
            db.execSQL(sql);
        }catch (SQLException e){
        }
    }

    public void insertUser(SQLiteDatabase db, String email, String pw, String name, String gender, String mobile, int class_idx, int class_number){
        Log.i("tag","회원가입을 했을때 실행함");
        db.beginTransaction();
        try {
            String sql = "INSERT INTO " + userTableName + "(email, pw, name, gender, mobile, class_idx, class_number)" +
                    "values('"+ email +"', '"+ pw +"', '"+ name +"', '"+ gender +"', '"+ mobile +"', '"+ class_idx +"', '"+ class_number +"')";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

    public void insertToken(SQLiteDatabase db, String token){
        Log.i("tag","로그인 성공 시에 실행함");
        db.beginTransaction();
        try {
            String sql = "INSERT INTO " + tokenTableName + "(token)" +
                    "values('"+ token +"')";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

    public void insertOutDocument(SQLiteDatabase db, int accept, int idx, String startTime, String endTime,
                                  String reason, int class_idx, String student_email){
        Log.i("tag","외출증 발급 시 실행");
        db.beginTransaction();
        try {
            String sql = "INSERT INTO " + outGoTableName + "(accept, idx, StartTime, EndTime, reason, classIdx, email)" +
                    "values('"+ accept +"', '"+ idx +"', '"+ startTime +"', '"+ endTime +"', '"+ reason +"', '"+ class_idx +"', '"+ student_email +"')";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

    public void insertOutSleepDocument(SQLiteDatabase db, int accept, int idx, String startTime, String endTime,
                                  String reason, int class_idx, String student_email){
        Log.i("tag","외출증 발급 시 실행");
        db.beginTransaction();
        try {
            String sql = "INSERT INTO " + outSleepTableName + "(accept, idx, StartTime, EndTime, reason, classIdx, email)" +
                    "values('"+ accept +"', '"+ idx +"', '"+ startTime +"', '"+ endTime +"', '"+ reason +"', '"+ class_idx +"', '"+ student_email +"')";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

    public void acceptOutgo(SQLiteDatabase db, int go_out_idx){
        Log.i("tag","외출증 발급 시 실행");
        db.beginTransaction();
        try {
            String sql = "UPDATE " + outGoTableName + " SET accept = 1 WHERE idx = " + go_out_idx;
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

//    public List getAlloutgoDocData(SQLiteDatabase db){
//        String sql = "SELECT * FROM " + outGoTableName;
//        Cursor cursor = db.rawQuery(sql, null);
//
//        List docList = new ArrayList();
//        OutgoDocVO outgoDocList = null;
//
//        while (cursor.moveToNext()){
//            outgoDocList = new OutgoDocVO();
//
//            outgoDocList.setAccept(cursor.getInt(0));
//
//            outgoDocList.setIdx(cursor.getInt(1));
//
//            outgoDocList.setStartTime(cursor.getString(2));
//
//            outgoDocList.setEndTime(cursor.getString(3));
//
//            outgoDocList.setReason(cursor.getString(4));
//
//            outgoDocList.setClass_idx(cursor.getInt(5));
//
//            outgoDocList.setStudent_email(cursor.getString(6));
//
//            docList.add(outgoDocList);
//        }
//        return docList;
//    }

}
