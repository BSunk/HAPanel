package com.bsunk.hapanel.data.local;

import android.arch.lifecycle.LiveData;

import com.bsunk.hapanel.data.local.entity.DeviceModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

/**
 * Created by bryan on 5/19/17.
 */

public class DeviceRepository {

    private final ModelDatabase modelDatabase;

    @Inject
    public DeviceRepository(ModelDatabase modelDatabase) {
        this.modelDatabase = modelDatabase;
    }

    public long addDevice(DeviceModel lightModel) {
        return modelDatabase.deviceModelDao().insertDevice(lightModel);
    }

    public int updateDevice(DeviceModel deviceModel) {
        return modelDatabase.deviceModelDao().updateDevice(deviceModel);
    }

    public Completable deleteLight(DeviceModel deviceModel) {
        return Completable.fromAction(() -> modelDatabase.deviceModelDao().deleteDevice(deviceModel));
    }

    public Observable<List<DeviceModel>> getAllDevices() {
        return Observable.create((ObservableEmitter<List<DeviceModel>> e) -> e.onNext(modelDatabase.deviceModelDao().loadAllDevices()));
    }

    public LiveData<List<DeviceModel>> getAllDevicesLiveData() {
        return modelDatabase.deviceModelDao().getDevices();
    }

}
