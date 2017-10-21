package com.example.tugas1a.service;

import java.util.List;

import com.example.tugas1a.model.KecamatanModel;
import com.example.tugas1a.model.KeluargaModel;
import com.example.tugas1a.model.KelurahanModel;
import com.example.tugas1a.model.KotaModel;
import com.example.tugas1a.model.PendudukModel;

public interface PendudukService {
	PendudukModel selectPenduduk(String nik);

	PendudukModel selectPendudukTerakhir(String nikMin, String nikMax);

	PendudukModel selectPendudukTermuda(Integer id_kelurahan);

	PendudukModel selectPendudukTertua(Integer id_kelurahan);

	List<PendudukModel> selectListPenduduk(Integer id_kelurahan);

//	void updatePenduduk(PendudukModel penduduk);

	void addPenduduk(PendudukModel penduduk);

	KeluargaModel selectKeluarga(Integer id_keluarga);

	List<PendudukModel> selectAnggotaKeluarga(String nkk);

	void updateKeluarga(KeluargaModel keluarga);

	void setWafat(String nik);

	List<KotaModel> selectDaftarKota();

	List<KecamatanModel> selectDaftarKecamatan();

	List<KelurahanModel> selectDaftarKelurahan();
	
//	KeluargaModel selectKeluarga(String id);
	
	void updatePenduduk(String nik, String newNik, String nama, String tempat_lahir, String tanggal_lahir, int jenis_kelamin,
			String golongan_darah, String agama, String status_perkawinan, String pekerjaan, Integer is_wni,
			Integer is_wafat, Integer id_keluarga, String status_dalam_keluarga);


	String getLast(String minNik, String maxNik);
}
