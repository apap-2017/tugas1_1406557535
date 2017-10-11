package com.example.tugas1a.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Many;

import com.example.tugas1a.model.KecamatanModel;
import com.example.tugas1a.model.KeluargaModel;
import com.example.tugas1a.model.KelurahanModel;
import com.example.tugas1a.model.KotaModel;
import com.example.tugas1a.model.PendudukModel;

@Mapper
public interface KeluargaMapper {
	
//	@Select("SELECT * FROM keluarga WHERE nomor_kk = #{nomor_kk}")
	@Select("SELECT *"
			+ " FROM keluarga klg"
				+ " JOIN penduduk pdk"
			+ " ON klg.id=pdk.id_keluarga"
				+ " JOIN kelurahan klr"
			+ " ON klg.id_kelurahan = klr.id"
				+ " JOIN kecamatan kc"
			+ " ON klr.id_kecamatan = kc.id"
				+ " JOIN kota kt"
			+ " ON kc.id_kota = kt.id"
			+ " WHERE nomor_kk = #{nomor_kk}")
	@Results(value= {
			@Result(property="id", column="id"),
			@Result(property="nomor_kk", column="nomor_kk"),
			@Result(property="alamat", column="alamat"),
			@Result(property="RT", column="RT"),
			@Result(property="RW", column="RW"),
			@Result(property="id_kelurahan", column="id_kelurahan"),
			@Result(property="is_tidak_berlaku", column="is_tidak_berlaku"),
			@Result(property="penduduks", column="id_keluarga", javaType=List.class, many=@Many(select="selectPenduduks")),
			@Result(property = "kelurahan", column = "id_kelurahan", javaType = KelurahanModel.class, one = @One(select = "selectKelurahan"))
	})
	KeluargaModel selectKeluarga (@Param("nomor_kk") String nomor_kk);
	
    //list of all penduduk pada penduduk dengan id keluarga tertentu
    @Select("SELECT * "+
    		"FROM penduduk p JOIN keluarga k " +
    		"ON p.id_keluarga = k.id " +
    		"WHERE p.id_keluarga = #{id_keluarga}")    
    List<PendudukModel> selectPenduduks(@Param("id_keluarga") String id_keluarga);
	
	//to select one of Kelurahan based on ID Kelurahan
	@Select("SELECT * FROM kelurahan WHERE id = #{id_kelurahan}")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "id_kecamatan", column = "id_kecamatan"),
			@Result(property = "nama_kelurahan", column = "nama_kelurahan"),
			@Result(property = "kecamatan", column = "id_kecamatan", javaType = KecamatanModel.class, one = @One(select = "selectKecamatan")) })
	KelurahanModel selectKelurahan(@Param("id_kelurahan") String id_kelurahan);
	
	//to select one of kecamatan based on ID Kecamatan
	@Select("SELECT * FROM kecamatan WHERE id = #{id_kecamatan}")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "nama_kecamatan", column = "nama_kecamatan"),
			@Result(property = "kota", column = "id_kota", javaType = KotaModel.class, one = @One(select = "selectKota")) })
	KecamatanModel selectKecamatan(@Param("id_kecamatan") String id_kecamatan);
	
	//to select one of kota based on ID Kota
	@Select("SELECT * FROM kota WHERE id = #{id_kota}")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "nama_kota", column = "nama_kota") })
	KotaModel selectKota(@Param("id_kota") String id_kota);
	
}
