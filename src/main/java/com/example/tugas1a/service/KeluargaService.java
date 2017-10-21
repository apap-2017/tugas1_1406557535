package com.example.tugas1a.service;

import java.util.List;

import com.example.tugas1a.model.KecamatanModel;
import com.example.tugas1a.model.KeluargaModel;
import com.example.tugas1a.model.KelurahanModel;
import com.example.tugas1a.model.KotaModel;
import com.example.tugas1a.model.PendudukModel;

public interface KeluargaService {

	List<PendudukModel> selectAnggotaKeluarga(String nkk);

	KeluargaModel selectKartuKeluarga(String nkk);

	KeluargaModel selectKeluarga(Integer id_keluarga);
	
	KeluargaModel selectKeluargaTerakhir(String nkkMin, String nkkMax);
	
	KelurahanModel selectKelurahan(Integer id_kelurahan);

	void addKeluarga(KeluargaModel keluarga);
	
	List<KotaModel> selectDaftarKota();
	
	List<KecamatanModel> selectDaftarKecamatan();
	
	List<KelurahanModel> selectDaftarKelurahan();
	
	PendudukModel selectPendudukTerakhir(String nikMin, String nikMax);
	
	void updatePenduduk(PendudukModel penduduk);
	
	void updateKeluarga(KeluargaModel keluarga);


}
