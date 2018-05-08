package id.web.owlstudio.androidsqliterealm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.web.owlstudio.androidsqliterealm.model.Person;
import io.realm.Realm;
import io.realm.RealmResults;

public class EditPersonActivity extends AppCompatActivity {

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

    Realm realm;
    private RealmResults<Person> dataPerson; // data person

    //Variable penampung id person
    String person_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person_activity);
        ButterKnife.bind(this);

        // init realm
        realm = Realm.getDefaultInstance();

        // tangkap data yang dibawa oleh intent
        String person_name = getIntent().getStringExtra("P_NAME");
        String person_addr = getIntent().getStringExtra("P_ADDR");
        String person_gender = getIntent().getStringExtra("P_GENDER");
        person_id = getIntent().getStringExtra("P_ID");

        // isikan data ke widget edit text
        etPersonAddr.setText(person_addr);
        etPersonName.setText(person_name);

        // TODO : Logika untuk menentukan radio button yang tercentang
        if(person_gender.equals("Male")){
            rbMale.setChecked(true);
        } else if (person_gender.equals("Female")) {
            rbFemale.setChecked(true);
        }
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
            Toast.makeText(this, "Semua input wajib diisi!!!", Toast.LENGTH_SHORT).show();
        } else {
            String personGender = radioButton.getText().toString();
            // Execute Method -- Alt Enter Create Method
            saveToDatabase(personName, personAddress, personGender);
        }
    }

    private void saveToDatabase(final String personName, final String personAddress, final String personGender) {
        // Cari dulu row yang akan diedit
        Person toEdit = realm.where(Person.class).equalTo("id", person_id).findFirst();

        // Mulai transaksi
        realm.beginTransaction();

        // Set adata baru
        toEdit.setPersonName(personName);
        toEdit.setPersonAddress(personAddress);
        toEdit.setPersonGender(personGender);

        // Kirim Transaksi
        realm.commitTransaction();

        // pindah ke main activity
        startActivity( new Intent(this, MainActivity.class));
        finish();
    }
}
