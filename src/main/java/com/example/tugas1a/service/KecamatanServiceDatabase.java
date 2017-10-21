package com.example.tugas1a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.tugas1a.dao.KecamatanMapper;
import com.example.tugas1a.model.KecamatanModel;

public class KecamatanServiceDatabase implements KecamatanService {
	
	@Autowired
	private KecamatanMapper kecamatanMapper;
	
	@Override
	public List<KecamatanModel> selectDaftarKecamatan() {
		 return kecamatanMapper.selectDaftarKecamatan();
	}

}
