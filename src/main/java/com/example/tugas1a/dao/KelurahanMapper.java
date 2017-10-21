package com.example.tugas1a.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.example.tugas1a.model.KelurahanModel;

public interface KelurahanMapper {
	@Select("select * " 
			+ "from kelurahan ")
	List<KelurahanModel> selectDaftarKelurahan();
}
