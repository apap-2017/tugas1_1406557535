package com.example.tugas1a.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.example.tugas1a.model.KotaModel;

public interface KotaMapper {
	@Select("select * " 
			+ "from kota ")
	List<KotaModel> selectDaftarKota();
}
