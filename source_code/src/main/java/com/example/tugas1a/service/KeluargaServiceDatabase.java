package com.example.tugas1a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1a.dao.KeluargaMapper;
import com.example.tugas1a.model.KecamatanModel;
import com.example.tugas1a.model.KeluargaModel;
import com.example.tugas1a.model.KelurahanModel;
import com.example.tugas1a.model.KotaModel;
import com.example.tugas1a.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService {
	@Autowired
	private KeluargaMapper keluargaMapper;
	
	@Override
	public KeluargaModel selectKartuKeluarga(String nkk) {
		 log.info ("select keluarga with nkk {}", nkk);
	        return keluargaMapper.selectKartuKeluarga (nkk);
	}

	@Override
	public List<PendudukModel> selectAnggotaKeluarga(String nkk) {
		 log.info ("select keluarga with nkk {}", nkk);
		 return keluargaMapper.selectAnggotaKeluarga(nkk);
	}

	@Override
	public KeluargaModel selectKeluarga(Integer id_keluarga) {
		  log.info ("select keluarga with id {}", id_keluarga);
	        return keluargaMapper.selectKeluarga(id_keluarga);
	}
	
	@Override
	public void addKeluarga(KeluargaModel keluarga) {
		keluargaMapper.addKeluarga(keluarga);
	}

	@Override
	public KeluargaModel selectKeluargaTerakhir(String nkkMin, String nkkMax) {
		return keluargaMapper.selectKeluargaTerakhir(nkkMin, nkkMax);
	}

	@Override
	public KelurahanModel selectKelurahan(Integer id_kelurahan) {
		log.info ("select kelurahan with id {}", id_kelurahan);
		return keluargaMapper.selectKelurahan(id_kelurahan);
	}
	
	@Override
	public List<KotaModel> selectDaftarKota() {
		 return keluargaMapper.selectDaftarKota();
	}

	@Override
	public List<KecamatanModel> selectDaftarKecamatan() {
		 return keluargaMapper.selectDaftarKecamatan();
	}

	@Override
	public List<KelurahanModel> selectDaftarKelurahan() {
		 return keluargaMapper.selectDaftarKelurahan();
	}
	
	@Override
	public PendudukModel selectPendudukTerakhir(String nikMin, String nikMax) {
		return keluargaMapper.selectPendudukTerakhir(nikMin, nikMax);
	}
	
	@Override
	public void updatePenduduk(PendudukModel penduduk) {
		keluargaMapper.updatePenduduk(penduduk);	
	}
	
	@Override
	public void updateKeluarga(KeluargaModel keluarga) {
		keluargaMapper.updateKeluarga(keluarga);
		
	}
}
