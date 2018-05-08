package id.web.owlstudio.androidsqliterealm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.web.owlstudio.androidsqliterealm.model.Person;
import io.realm.Realm;

public class AddPersonActivity extends AppCompatActivity {

    @BindView(R.id.etPersonName)
    EditText etPersonName;
    @BindView(R.id.etPersonAddr)
    EditText etPersonAddr;
    @BindView(R.id.rbMale)
    RadioButton rbMale;
    @BindView(R.id.rbFemale)
    RadioButton rbFemale;
    @BindView(R.id.rgGender)
    RadioGroup rgGender;
    @BindView(R.id.btnSavePerson)
    Button btnSavePerson;

    // Komponen Realm dibuat global
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        ButterKnife.bind(this);

        // init realm
        realm = Realm.getDefaultInstance(); // membuka database
    }

    @OnClick(R.id.btnSavePerson)
    public void onViewClicked() {

        // dapatkan semua value, masukkan ke variable
        String personName = etPersonName.getText().toString();
        String personAddress = etPersonAddr.getText().toString();

        // mendapatkan value dari radio button group yang terpilih
        RadioButton radioButton = findViewById(rgGender.getCheckedRadioButtonId());

        // Validasi input
        if(personName.isEmpty() || personAddress.isEmpty() || !radioButton.isChecked()){
            // Jika Kosong, Tampilin Toast
            Toast.makeText(AddPersonActivity.this, "Semua input wajib diisi!!!", Toast.LENGTH_SHORT).show();
        } else {
            String personGender = radioButton.getText().toString();
            // Execute Method -- Alt Enter Create Method
            saveToDatabase(personName, personAddress, personGender);
        }
    }

    private void saveToDatabase(final String personName, final String personAddress, final String personGender) {
        // Jalankan perintah insert secara asynchronuous
        // copy dari https://realm.io/docs/java/latest/#asynchronous-transactions
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                // Model Person
                Person person = bgRealm.createObject(Person.class, UUID.randomUUID().toString());

                // Isi data yang akan kita simpan
                person.setPersonName(personName);
                person.setPersonAddress(personAddress);
                person.setPersonGender(personGender);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Log.d("Success", ">>>>>>>>>>>>>>>>>>>>>> DISIMPAN <<<<<<<<<<<<<<<<<<<<");

                // Pindah ke Activity Utama
                startActivity(new Intent(AddPersonActivity.this, MainActivity.class));

                //Akhiri Activity saat ini
                finish();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.e("Failed cuy", error.getMessage());
            }
        });
    }
}
