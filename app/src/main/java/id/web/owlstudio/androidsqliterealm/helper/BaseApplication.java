package id.web.owlstudio.androidsqliterealm.helper;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // The default Realm file is "default.realm" in Context.getFilesDir();
        // we'll change it to "myrealm.realm"
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(2) // Must be bumped when the schema changes
                //.migration(new MyMigration()) // Migration to run instead of throwing an exception
                .name("dbperson.realm")
                .build();
        Realm.setDefaultConfiguration(config);

        // Lihat di Manifest, perlu ada penambahan di app:namenya
    }
}
