package com.example.xheng.welfaresociety.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.xheng.welfaresociety.model.bean.User;

/**
 * Created by xheng on 2017/3/21.
 */

public class DBManager {
    OpenHelperDB mHelper;
    static DBManager mManager = new DBManager();

    public synchronized void initDB(Context context) {
        mHelper = new OpenHelperDB(context);
    }

    public static DBManager getInstance() {
        return mManager;
    }

    public boolean saveUserInfo(User user) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();

            values.put(DBUser.USER_COLUMN_NAME, user.getMuserName());
            values.put(DBUser.USER_COLUMN_NICK, user.getMuserNick());
            values.put(DBUser.USER_COLUMN_AVATAR, user.getMavatarId());
            values.put(DBUser.USER_COLUMN_AVATAR_PATH, user.getMavatarPath());
            values.put(DBUser.USER_COLUMN_AVATAR_SUFFIX, user.getMavatarSuffix());
            values.put(DBUser.USER_COLUMN_AVATAR_TYPE, user.getMavatarType());
            values.put(DBUser.USER_COLUMN_AVATAR_UPDATE_TIEM, user.getMavatarLastUpdateTime());

            return db.replace(DBUser.USER_TABLE_NAME, null, values) != -1;
        }
        return false;
    }

    public User getUserInfo(String userName) {
        SQLiteDatabase database = mHelper.getReadableDatabase();
        if (database.isOpen()) {
            String sql = "select * from " + DBUser.USER_TABLE_NAME + " where "
                    + DBUser.USER_COLUMN_NAME + " = '" + userName + "'";
            Cursor cursor = database.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                User user = new User();
                user.setMuserName(userName);
                user.setMuserNick(cursor.getString(cursor.getColumnIndex(DBUser.USER_COLUMN_NICK)));
                user.setMavatarId(cursor.getInt(cursor.getColumnIndex(DBUser.USER_COLUMN_AVATAR)));
                user.setMavatarPath(cursor.getString(cursor.getColumnIndex(DBUser.USER_COLUMN_AVATAR_PATH)));
                user.setMavatarSuffix(cursor.getString(cursor.getColumnIndex(DBUser.USER_COLUMN_AVATAR_SUFFIX)));
                user.setMavatarType(cursor.getInt(cursor.getColumnIndex(DBUser.USER_COLUMN_AVATAR_TYPE)));
                user.setMavatarLastUpdateTime(cursor.getString(cursor.getColumnIndex(DBUser.USER_COLUMN_AVATAR_UPDATE_TIEM)));
                return user;
            }
        }
        return null;
    }

    public void closeDB() {
        mHelper.closeDB();
    }
}
