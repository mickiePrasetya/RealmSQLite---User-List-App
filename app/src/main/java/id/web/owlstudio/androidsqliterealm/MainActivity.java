package id.web.owlstudio.androidsqliterealm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.web.owlstudio.androidsqliterealm.model.Person;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.lvPerson)
    ListView lvPerson;
    @BindView(R.id.fabAddPerson)
    FloatingActionButton fabAddPerson;

    Realm realm;
    private RealmResults<Person> dataPerson; // data person

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // init realm
        realm = Realm.getDefaultInstance();

        // execute method
        // ALT ENTER to create method showPersonData()
        showPersonData();

        // atur ketika item di list di klik "long"
        lvPerson.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                // tampilkan alert konfirmasi
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Warning Notification");
                alert.setMessage("Do you want to delete this list-item");
                alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // execute fungsi hapus
                        deletePerson(position);
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // biarkan
                    }
                });
                alert.setNeutralButton("Edit data", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // pindah ke Edit Activity dengan bawa data
                        Intent intent = new Intent(MainActivity.this, EditPersonActivity.class);

                        // bawa data ke dalam variable intent
                        intent.putExtra("P_ID", dataPerson.get(position).getId());
                        intent.putExtra("P_NAME", dataPerson.get(position).getPersonName());
                        intent.putExtra("P_ADDR", dataPerson.get(position).getPersonAddress());
                        intent.putExtra("P_GENDER", dataPerson.get(position).getPersonGender());

                        // mulai activity
                        startActivity(intent);
                    }
                });

                // tampilkan
                alert.show();
                return false;
            }
        });

        // Atur item di list di klik "short
//        lvPerson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
    }

    private void deletePerson(final int position) {
        // obtain the dataPerson of a query
        dataPerson = realm.where(Person.class).findAll();

        // All changes to data must happen in a transaction
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // remove a single object
                Person person = dataPerson.get(position);
                person.deleteFromRealm();

                //refresh list
                showPersonData();

            }
        });
    }

    private void showPersonData() {
        dataPerson = realm.where(Person.class).findAllAsync(); // Bentuk datanya List

        // tampilkan di log
        // pilih opsi debug, search sesuai tag "Isi Result Person", di logchat
        Log.d("Isi Result Person", dataPerson.toString());

        // Buat aaray penampung data
        ArrayList resultPerson = new ArrayList();

        // Looping for Loop
        for(Person person: dataPerson){
            // sisipkan data ke arraylist data person
            resultPerson.add(person.getPersonName() + "(" + person.getPersonGender() + ")");
        }


        ArrayAdapter adapterLV = new ArrayAdapter(this, android.R.layout.simple_list_item_1, resultPerson);

        //set adapter ke widget list view
        lvPerson.setAdapter(adapterLV);
    }

    @OnClick(R.id.fabAddPerson)
    public void onViewClicked() {

        // TODO: Pindah ke activity tambah
        startActivity(new Intent(this, AddPersonActivity.class));
    }
}
