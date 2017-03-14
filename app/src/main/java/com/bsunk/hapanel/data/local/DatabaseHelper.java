package com.bsunk.hapanel.data.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bsunk.hapanel.data.model.DeviceModel;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Created by Bryan on 3/8/2017.
 */

public class DatabaseHelper {

    private SQLiteOpenHelper dbhandler;
    private SQLiteDatabase database;

    private static final String[] allColumns = {
            DatabaseContract.HAPanel.COLUMN_ENTITY_ID,
            DatabaseContract.HAPanel.COLUMN_STATE,
            DatabaseContract.HAPanel.COLUMN_LAST_CHANGED,
            DatabaseContract.HAPanel.COLUMN_ATTRIBUTES
    };

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        dbhandler = dbOpenHelper;
    }

    private void open(){
        Timber.v("Database Opened");
        database = dbhandler.getWritableDatabase();
    }

    private void close(){
        Timber.v("Database Closed");
        dbhandler.close();
    }

    public Observable<Long> addDevice(DeviceModel deviceModel) {
        return Observable.create(e -> {
            open();
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.HAPanel.COLUMN_ENTITY_ID, deviceModel.getEntity_id());
            values.put(DatabaseContract.HAPanel.COLUMN_STATE, deviceModel.getState());
            values.put(DatabaseContract.HAPanel.COLUMN_ATTRIBUTES, deviceModel.getAttributes());
            values.put(DatabaseContract.HAPanel.COLUMN_LAST_CHANGED, deviceModel.getLast_changed());
            values.put(DatabaseContract.HAPanel.COLUMN_HIDE_FLAG, false);
            long id = database.insert(DatabaseContract.HAPanel.TABLE_NAME, null, values);
            if(id!=-1) {
                e.onNext(id);
                Timber.v("Added device with id: " + id);
            }
            else {
                e.onError(new Throwable("Error inserting into database!"));
            }
            close();
        });
    }

    public Observable<Long> updateDevice(DeviceModel deviceModel) {
        return Observable.create(e -> {
            open();
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.HAPanel.COLUMN_ENTITY_ID, deviceModel.getEntity_id());
            values.put(DatabaseContract.HAPanel.COLUMN_STATE, deviceModel.getState());
            values.put(DatabaseContract.HAPanel.COLUMN_ATTRIBUTES, deviceModel.getAttributes());
            values.put(DatabaseContract.HAPanel.COLUMN_LAST_CHANGED, deviceModel.getLast_changed());
            values.put(DatabaseContract.HAPanel.COLUMN_HIDE_FLAG, false);
            long id = database.insert(DatabaseContract.HAPanel.TABLE_NAME, null, values);
            if(id!=-1) {
                e.onNext(id);
                Timber.v("Added device with id: " + id);
            }
            else {
                e.onError(new Throwable("Error inserting into database!"));
            }
            close();
        });
    }

    public Observable<Void> bulkAddDevices(ArrayList<DeviceModel> list) {
        return Observable.create(e -> {
            open();
            for(int i=0; i<list.size(); i++) {
                ContentValues values = new ContentValues();
                DeviceModel deviceModel = list.get(i);
                values.put(DatabaseContract.HAPanel.COLUMN_ENTITY_ID, deviceModel.getEntity_id());
                values.put(DatabaseContract.HAPanel.COLUMN_STATE, deviceModel.getState());
                values.put(DatabaseContract.HAPanel.COLUMN_ATTRIBUTES, deviceModel.getAttributes());
                values.put(DatabaseContract.HAPanel.COLUMN_LAST_CHANGED, deviceModel.getLast_changed());
                values.put(DatabaseContract.HAPanel.COLUMN_HIDE_FLAG, false);
                long id = database.insert(DatabaseContract.HAPanel.TABLE_NAME, null, values);
                Timber.v("Added device with id: " + id);
                if(id==-1) {e.onError(new Throwable("Error inserting into database!"));}
            }
            e.onComplete();
            close();
        });
    }

    public Observable<DeviceModel> getDevice(String entityID) {
        return Observable.create(e -> {
            Cursor cursor = database.query(DatabaseContract.HAPanel.TABLE_NAME,
                    allColumns,
                    DatabaseContract.HAPanel.COLUMN_ENTITY_ID + "=?",new String[]{entityID},null,null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                DeviceModel deviceModel = new DeviceModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                Timber.v("Fetched device: " + deviceModel.toString());
                e.onNext(deviceModel);
            }
            else {
                e.onError(new Throwable("No device found with entityID: " + entityID));
            }
            cursor.close();
        });
    }
}
