package com.example.tugas1a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.tugas1a.dao.KelurahanMapper;
import com.example.tugas1a.model.KelurahanModel;

public class KelurahanServiceDatabase implements KelurahanService {

	@Autowired
	private KelurahanMapper kelurahanMapper;

	@Override
	public List<KelurahanModel> selectDaftarKelurahan() {
		 return kelurahanMapper.selectDaftarKelurahan();
	}

}
