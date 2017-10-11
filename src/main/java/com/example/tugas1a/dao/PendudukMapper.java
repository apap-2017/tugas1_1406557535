package com.example.tugas1a.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Many;

import com.example.tugas1a.model.KecamatanModel;
import com.example.tugas1a.model.KeluargaModel;
import com.example.tugas1a.model.KelurahanModel;
import com.example.tugas1a.model.KotaModel;
import com.example.tugas1a.model.PendudukModel;

@Mapper
public interface PendudukMapper {
	// list one of penduduk based on param nik
//    @Select("select id, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, "
//    		+ "is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, "
//    		+ "status_dalam_keluarga, golongan_darah, is_wafat "
//    		+ "from penduduk where nik = #{nik}")
//    PendudukModel selectPenduduk(String nik);
	
	//to select one of penduduk based on NIK
	@Select("SELECT *"
			+ " FROM penduduk pdk"
				+ " JOIN keluarga klg"
			+ " ON pdk.id_keluarga = klg.id"
				+ " JOIN kelurahan klr"
			+ " ON klg.id_kelurahan = klr.id"
				+ " JOIN kecamatan kc"
			+ " ON klr.id_kecamatan = kc.id"
				+ " JOIN kota kt"
			+ " ON kc.id_kota = kt.id"
			+ " WHERE nik = #{nik}")
	@Results(value = { 
			@Result(property = "nik", column = "nik"), 
			@Result(property = "nama", column = "nama"),
			@Result(property = "tempat_lahir", column = "tempat_lahir"),
			@Result(property = "tanggal_lahir", column = "tanggal_lahir"),
			@Result(property = "golongan_darah", column = "golongan_darah"),
			@Result(property = "id_keluarga", column = "id_keluarga"),
			@Result(property = "keluarga", column = "id", 
					javaType = KeluargaModel.class, 
					one = @One(select = "selectKeluarga")) })
	PendudukModel selectPenduduk (@Param("nik") String nik);
	
	//to select one of keluarga based on ID
	@Select("SELECT * FROM keluarga WHERE id = #{id}")
	@Results(value = { 
			@Result(property = "id", column = "id"),
			@Result(property = "nomor_kk", column = "nomor_kk"),
			@Result(property = "alamat", column = "alamat"),
			@Result(property = "RT", column = "RT"),
			@Result(property = "RW", column = "RW"),
			@Result(property = "id_kelurahan", column = "id_kelurahan"),
			@Result(property = "is_tidak_berlaku", column = "is_tidak_berlaku"),
			@Result(property = "kelurahan", column = "id", javaType = KelurahanModel.class, one = @One(select = "selectKelurahan")) })
	KeluargaModel selectKeluarga(@Param("id") String id);
	
	//to select one of Kelurahan based on ID Kelurahan
	@Select("SELECT * FROM kelurahan WHERE id = #{id}")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "id_kecamatan", column = "id_kecamatan"),
			@Result(property = "nama_kelurahan", column = "nama_kelurahan"),
			@Result(property = "kecamatan", column = "id", javaType = KecamatanModel.class, one = @One(select = "selectKecamatan")) })
	KelurahanModel selectKelurahan(@Param("id") String id);
	
	//to select one of kecamatan based on ID Kecamatan
	@Select("SELECT * FROM kecamatan WHERE id = #{id}")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "nama_kecamatan", column = "nama_kecamatan"),
			@Result(property = "kota", column = "id", javaType = KotaModel.class, one = @One(select = "selectKota")) })
	KecamatanModel selectKecamatan(@Param("id") String id);
	
	//to select one of kota based on ID Kota
	@Select("SELECT * FROM kota WHERE id = #{id}")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "nama_kota", column = "nama_kota") })
	KotaModel selectKota(@Param("id") String id);
}
