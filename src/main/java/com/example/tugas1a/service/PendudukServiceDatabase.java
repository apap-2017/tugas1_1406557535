package com.example.tugas1a.service;

import com.example.tugas1a.model.KecamatanModel;
import com.example.tugas1a.model.KeluargaModel;
import com.example.tugas1a.model.KelurahanModel;
import com.example.tugas1a.model.KotaModel;
import com.example.tugas1a.model.PendudukModel;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1a.dao.PendudukMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService {

	@Autowired
	private PendudukMapper pendudukMapper;

	@Override
	public PendudukModel selectPenduduk(String nik) {
		log.info("select penduduk with nik {}", nik);
        return pendudukMapper.selectPenduduk(nik);
	}
	
	@Override
	public PendudukModel selectPendudukTerakhir(String nikMin, String nikMax) {
		return pendudukMapper.selectPendudukTerakhir(nikMin, nikMax);
	}

	@Override
	public void addPenduduk(PendudukModel penduduk) {
		pendudukMapper.addPenduduk(penduduk);
	}
	
//	@Override
//	public void updatePenduduk(PendudukModel penduduk) {
//		pendudukMapper.updatePenduduk(penduduk);
//		
//	}
	
	@Override
	public List<PendudukModel> selectListPenduduk(Integer id_kelurahan) {
		return pendudukMapper.selectListPenduduk(id_kelurahan);
	}

	@Override
	public PendudukModel selectPendudukTertua(Integer id_kelurahan) {
		return pendudukMapper.selectPendudukTertua(id_kelurahan);
	}

	@Override
	public PendudukModel selectPendudukTermuda(Integer id_kelurahan) {
		return pendudukMapper.selectPendudukTermuda(id_kelurahan);	
	}

	@Override
	public KeluargaModel selectKeluarga(Integer id_keluarga) {
		  log.info ("select keluarga with id {}", id_keluarga);
	        return pendudukMapper.selectKeluarga(id_keluarga);
	}

	@Override
	public List<PendudukModel> selectAnggotaKeluarga(String nkk) {
		 log.info ("select keluarga with nkk {}", nkk);
		 return pendudukMapper.selectAnggotaKeluarga(nkk);
	}

	@Override
	public void updateKeluarga(KeluargaModel keluarga) {
		pendudukMapper.updateKeluarga(keluarga);
		
	}
	
	@Override
	public void setWafat(String nik) {
		log.info ("set wafat penduduk dengan nik {}", nik);
		pendudukMapper.setWafat(nik);
	}
	
	@Override
	public List<KotaModel> selectDaftarKota() {
		 return pendudukMapper.selectDaftarKota();
	}

	@Override
	public List<KecamatanModel> selectDaftarKecamatan() {
		 return pendudukMapper.selectDaftarKecamatan();
	}

	@Override
	public List<KelurahanModel> selectDaftarKelurahan() {
		 return pendudukMapper.selectDaftarKelurahan();
	}
	
//	@Override
//	public KeluargaModel selectKeluarga(Integer id) {
//		log.info ("select keluarga with id {}", id);
//        return pendudukMapper.selectKeluarga(id);
//	}
	
	@Override
	public String getLast(String minNik, String maxNik) {
		log.info ("get max nik between {} and {}", minNik, maxNik);
        return pendudukMapper.selectLast(minNik, maxNik);
	}
	
	@Override
	public void updatePenduduk(String nik, String newNik, String nama, String tempat_lahir, String tanggal_lahir, int jenis_kelamin,
			String golongan_darah, String agama, String status_perkawinan, String pekerjaan, Integer is_wni,
			Integer is_wafat, Integer id_keluarga, String status_dalam_keluarga) {
		
		pendudukMapper.updatePenduduk(nik, newNik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, golongan_darah, agama, status_perkawinan, pekerjaan, is_wni, is_wafat, id_keluarga, status_dalam_keluarga);
		//System.out.println("WWW " + nik );
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<KecamatanModel> selectKecamatanByKota(String id_kota) {
		// TODO Auto-generated method stub
		return pendudukMapper.selectKecamatanByKota(Integer.parseInt(id_kota));
	}
	
	@Override
	public KotaModel selectKotaSearch(Optional<Integer> id_kota) {
		// TODO Auto-generated method stub
		return pendudukMapper.selectKotaSearch(id_kota.get());
	}
	
	@Override
	public KecamatanModel selectKecamatanSearch(Optional<Integer> id_kecamatan) {
		// TODO Auto-generated method stub
		return pendudukMapper.selectKecamatanSearch(id_kecamatan.get());
	}
	
	@Override
	public KelurahanModel selectKelurahanSearch(Optional<Integer> id_kelurahan) {
		// TODO Auto-generated method stub
		return pendudukMapper.selectKelurahanSearch(id_kelurahan.get());
	}
	
	@Override
	public List<PendudukModel> selectPendudukByKelurahan(String id_kelurahan) {
		// TODO Auto-generated method stub
		return pendudukMapper.selectPendudukByDaerah(Integer.parseInt(id_kelurahan));
	}
	
	@Override
	public List<KelurahanModel> selectKelurahansByKecamatan(String id_kecamatan) {
		// TODO Auto-generated method stub
		return pendudukMapper.selectKelurahansByKecamatan(Integer.parseInt(id_kecamatan));
	}
}
