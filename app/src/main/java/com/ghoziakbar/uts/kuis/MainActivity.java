package com.ghoziakbar.uts.kuis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.ghoziakbar.uts.kuis.adapter.MhsAdapter;
import com.ghoziakbar.uts.kuis.dao.MhsDao;
import com.ghoziakbar.uts.kuis.model.Mhs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    ListView lvMhs;
    FloatingActionButton fabTambah;
    AppDatabase db;
    MhsDao mhsDao;

    MhsAdapter adapter;
    List<Mhs> mhsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMhs = findViewById(R.id.lv_Mhs);
        fabTambah = findViewById(R.id.fab_Tambah);

        // Inisialisasi database
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "mhs_db").build();
        mhsDao = db.mhsDao();

        // Penggunaan Executor untuk operasi database
        Executor executor = Executors.newSingleThreadExecutor();

        fabTambah.setOnClickListener(v -> bukaDialogTambah(executor));

        tampilkanSemuaData(executor);

        lvMhs.setOnItemClickListener((parent, view, position, id) -> {
            Mhs mhs = mhsList.get(position);
            bukaDialogUpdateData(mhs, executor);
        });

    }

    private void bukaDialogUpdateData(Mhs mhs, Executor executor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update atau Hapus Mahasiswa");
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.from_update, null);
        builder.setView(dialogView);

        EditText etNama = dialogView.findViewById(R.id.et_nama);
        EditText etNim = dialogView.findViewById(R.id.et_nim);
        EditText etProdi = dialogView.findViewById(R.id.et_prodi);
        Button btnUpdate = dialogView.findViewById(R.id.btn_update);
        Button btnHapus = dialogView.findViewById(R.id.btn_delete);

        etNama.setText(mhs.nama);
        etNim.setText(mhs.nim);
        etProdi.setText(mhs.prodi);

        AlertDialog dialog = builder.create();

        btnUpdate.setOnClickListener(v -> {
            if (etNama.getText().toString().trim().isEmpty() ||
                    etNim.getText().toString().trim().isEmpty() ||
                    etProdi.getText().toString().trim().isEmpty()) {
                if (etNama.getText().toString().trim().isEmpty()) {
                    etNama.setError("Nama Harus Diisi");
                }
                if (etNim.getText().toString().trim().isEmpty()) {
                    etNim.setError("NIM Harus Diisi");
                }
                if (etProdi.getText().toString().trim().isEmpty()) {
                    etProdi.setError("Prodi Harus Diisi");
                }
                return;
            }

            mhs.nama = etNama.getText().toString();
            mhs.nim = etNim.getText().toString();
            mhs.prodi = etProdi.getText().toString();


            executor.execute(() -> {
                mhsDao.update(mhs);
                runOnUiThread(() -> {
                    tampilkanSemuaData(executor);
                    dialog.dismiss();
                });
            });
        });

        btnHapus.setOnClickListener(v -> {
            executor.execute(() -> {
                mhsDao.delete(mhs);
                runOnUiThread(() -> {
                    tampilkanSemuaData(executor);
                    dialog.dismiss();
                });
            });
        });

        dialog.show();
    }

    private void bukaDialogTambah(Executor executor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Mahasiswa");
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.from_tambah, null);
        builder.setView(dialogView);

        EditText etNama = dialogView.findViewById(R.id.lv_nama);
        EditText etNim = dialogView.findViewById(R.id.lv_nim);
        EditText etProdi = dialogView.findViewById(R.id.lv_prodi);
        Button btnSimpan = dialogView.findViewById(R.id.btn_tambah);

        AlertDialog dialog = builder.create();
        btnSimpan.setOnClickListener(v -> {
            if (etNama.getText().toString().trim().isEmpty() ||
                    etNim.getText().toString().trim().isEmpty() ||
                    etProdi.getText().toString().trim().isEmpty()) {
                if (etNama.getText().toString().trim().isEmpty()) {
                    etNama.setError("Nama Harus Diisi");
                }
                if (etNim.getText().toString().trim().isEmpty()) {
                    etNim.setError("NIM Harus Diisi");
                }
                if (etProdi.getText().toString().trim().isEmpty()) {
                    etProdi.setError("Prodi Harus Diisi");
                }
                return;
            }

            Mhs mhs = new Mhs();
            mhs.nama = etNama.getText().toString();
            mhs.nim = etNim.getText().toString();
            mhs.prodi = etProdi.getText().toString();

            executor.execute(() -> {
                mhsDao.insert(mhs);
                runOnUiThread(() -> {
                    tampilkanSemuaData(executor);
                    dialog.dismiss();
                });
            });
        });
        dialog.show();
    }

    private void tampilkanSemuaData(Executor executor) {
        executor.execute(() -> {
            mhsList = mhsDao.getAll();
            runOnUiThread(() -> {
                adapter = new MhsAdapter(this, mhsList);
                lvMhs.setAdapter(adapter);
            });
        });
    }

}