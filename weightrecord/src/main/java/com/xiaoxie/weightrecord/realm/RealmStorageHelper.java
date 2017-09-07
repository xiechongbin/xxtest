package com.xiaoxie.weightrecord.realm;

import com.xiaoxie.weightrecord.application.BaseApplication;
import com.xiaoxie.weightrecord.bean.BodyData;
import com.xiaoxie.weightrecord.bean.Options;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * desc:relam 操作
 * Created by xiaoxie on 2017/9/7.
 */
public class RealmStorageHelper {
    private static volatile RealmStorageHelper Instance = null;
    private Realm realm;

    public static RealmStorageHelper getInstance() {
        RealmStorageHelper localInstance = Instance;
        if (localInstance == null) {
            synchronized (RealmStorageHelper.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new RealmStorageHelper();
                }
            }
        }
        return localInstance;
    }

    /**
     * 构造方法
     */
    public RealmStorageHelper() {
        openRealm();
    }

    public Realm getRealm() {
        return realm;
    }

    /**
     * 打开
     */
    private void openRealm() {
        Realm.init(BaseApplication.ApplicationContext);//初始化数据库
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("weight.realm")
                .schemaVersion(0).migration(new CustomMigration()).build();
        realm = Realm.getInstance(realmConfiguration);
    }

    /**
     * 插入数据 数据选项是否选择
     */
    public void insertOptions1(final Options options) {
        Realm realm = getRealm();
        if (realm == null) {
            return;
        }
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(options);
            }
        });
    }

    /**
     * 插入数据 数据选项是否选择
     */
    public void insertOptions(final Options options) {
        Realm realm = getRealm();
        if (realm == null) {
            return;
        }
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(options);
        realm.commitTransaction();
    }

    /**
     * 插入数据 身体各项数据
     */
    public void insertBodyData(BodyData data) {
        Realm realm = getRealm();
        if (realm == null) {
            return;
        }
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(data);
        realm.commitTransaction();
    }

    public RealmResults<Options> getOptions() {
        return getRealm().where(Options.class).findAll();
    }

    /**
     * 删除数据
     */
    public void deleteData() {

    }


}
