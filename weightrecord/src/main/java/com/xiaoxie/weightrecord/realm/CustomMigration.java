package com.xiaoxie.weightrecord.realm;

import com.xiaoxie.weightrecord.bean.Options;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * desc:数据库升级操作
 * Created by xiaoxie on 2017/9/7.
 */
public class CustomMigration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
        if (oldVersion == 0 && newVersion == 1) {
            RealmObjectSchema bodyData = schema.get("BodyData");
            bodyData.addField("nightWeight", Float.class).transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(DynamicRealmObject obj) {
                    obj.set("nightWeight", "0");//为id设置值
                }
            });

            if (oldVersion == 1 && newVersion == 2) {
                RealmObjectSchema options = schema.get("Options");
                options.addField("nightWeightStatus", Integer.class).transform(new RealmObjectSchema.Function() {
                    @Override
                    public void apply(DynamicRealmObject obj) {
                        obj.set("nightWeightStatus", "1");//为id设置值

                    }
                });
            }
            oldVersion++;
        }
    }
}

