package id.web.owlstudio.androidsqliterealm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

// Define your model class by extending RealmObject
// Buat table Databasenya
public class Person extends RealmObject {
    // Definisikan kolom table
    @PrimaryKey
            private String id;
    String personName;
    String personAddress;
    String personGender;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonAddress() {
        return personAddress;
    }

    public void setPersonAddress(String personAddress) {
        this.personAddress = personAddress;
    }

    public String getPersonGender() {
        return personGender;
    }

    public void setPersonGender(String personGender) {
        this.personGender = personGender;
    }

    // klik Kanan Generate, Pilih toString(), seleksi semua variablenya
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", personName='" + personName + '\'' +
                ", personAddress='" + personAddress + '\'' +
                ", personGender='" + personGender + '\'' +
                '}';
    }
}
