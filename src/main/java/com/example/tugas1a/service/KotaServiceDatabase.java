package com.example.tugas1a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.tugas1a.dao.KotaMapper;
import com.example.tugas1a.model.KotaModel;

public class KotaServiceDatabase implements KotaService {

	@Autowired
	private KotaMapper kotaMapper;

	@Override
	public List<KotaModel> selectDaftarKota() {
		 return kotaMapper.selectDaftarKota();
	}

}
