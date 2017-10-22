package com.example.tugas1a.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.example.tugas1a.model.KecamatanModel;

public interface KecamatanMapper {
	@Select("select * " 
			+ "from kecamatan ")
	List<KecamatanModel> selectDaftarKecamatan();
}
