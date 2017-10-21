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
	@Select("select * " 
			+ "from keluarga "
			+ "where keluarga.id = #{id_keluarga}")
	@Results(value = { @Result(property = "id", column = "id"), 
			@Result(property = "nomor_kk", column = "nomor_kk"),
			@Result(property = "alamat", column = "alamat"),
			@Result(property = "rt", column = "rt"),
			@Result(property = "rw", column = "rw"),
			@Result(property = "id_kelurahan", column = "id_kelurahan"),
			@Result(property = "is_tidak_berlaku", column = "is_tidak_berlaku"),
			@Result(property = "kelurahan", column = "id_kelurahan", 
			javaType = KelurahanModel.class,
			many = @Many(select = "selectKelurahan")) })
	KeluargaModel selectKeluarga(@Param("id_keluarga") Integer id_keluarga);

	
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

	@Select("select * " 
			+ "from penduduk, keluarga "
			+ "where keluarga.id_kelurahan= #{id_kelurahan} and penduduk.id_keluarga = keluarga.id")
	List<PendudukModel> selectListPenduduk(@Param("id_kelurahan") Integer id_kelurahan);

	
	@Insert("INSERT INTO penduduk (nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, "
			+ "id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat) "
			+ "VALUES (#{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{is_wni}, "
			+ "#{id_keluarga}, #{agama}, #{pekerjaan}, #{status_perkawinan}, #{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat})")
	void addPenduduk(PendudukModel penduduk);
	
	
	@Select("select * " 
			+ "from penduduk "
			+ "where nik BETWEEN #{nikMin} and #{nikMax}"
			+ "order by nik "
			+ "DESC LIMIT 1 ")
	PendudukModel selectPendudukTerakhir(@Param("nikMin")String nikMin,@Param("nikMax") String nikMax);

	
//	@Update("update penduduk set nik = #{nik}, nama= #{nama}, tempat_lahir = #{tempat_lahir}, tanggal_lahir = #{tanggal_lahir}, "
//			+ "jenis_kelamin = #{jenis_kelamin}, is_wni = #{is_wni}, is_wafat = #{is_wafat}, "
//			+ "id_keluarga = #{id_keluarga}, agama = #{agama}, pekerjaan =  #{pekerjaan}, "
//			+ "status_perkawinan = #{status_perkawinan}, status_dalam_keluarga = #{status_dalam_keluarga}, golongan_darah = #{golongan_darah}, is_wafat = #{is_wafat} "
//			+ "WHERE id=#{id}")
//	void updatePenduduk(PendudukModel penduduk);

	
	@Select("select * " 
			+ "from penduduk,keluarga "
			+ "where keluarga.id_kelurahan= #{id_kelurahan} and penduduk.id_keluarga = keluarga.id "
			+ "order by tanggal_lahir "
			+ "LIMIT 1 ")
	PendudukModel selectPendudukTertua(@Param("id_kelurahan") Integer id_kelurahan);

	
	@Select("select * " 
			+ "from penduduk,keluarga "
			+ "where keluarga.id_kelurahan= #{id_kelurahan} and penduduk.id_keluarga = keluarga.id "
			+ "order by tanggal_lahir "
			+ "DESC LIMIT 1 ")
	PendudukModel selectPendudukTermuda(@Param("id_kelurahan") Integer id_kelurahan);

	@Select("select * " 
			+ "from penduduk, keluarga "
			+ "where keluarga.nomor_kk= #{nkk} and penduduk.id_keluarga = keluarga.id")
	List<PendudukModel> selectAnggotaKeluarga(@Param("nkk") String nkk);

	@Update("update keluarga set nomor_kk = #{nomor_kk}, alamat= #{alamat}, rt = #{rt}, rw = #{rw}, "
			+ "id_kelurahan = #{id_kelurahan}, is_tidak_berlaku = #{is_tidak_berlaku} "
			+ "WHERE id=#{id}")
	void updateKeluarga(KeluargaModel keluarga);
	
	@Update("UPDATE penduduk SET is_wafat=1 WHERE nik=#{nik}")
	void setWafat(@Param("nik") String nik);

	@Select("select * " 
			+ "from kota ")
	List<KotaModel> selectDaftarKota();
	
	@Select("select * " 
			+ "from kecamatan ")
	List<KecamatanModel> selectDaftarKecamatan();
	
	@Select("select * " 
			+ "from kelurahan ")
	List<KelurahanModel> selectDaftarKelurahan();
	

	@Select("SELECT nik FROM penduduk WHERE nik BETWEEN #{minNik} AND #{maxNik}"
			+ "ORDER BY nik DESC LIMIT 1")
	String selectLast(@Param("minNik") String minNik, @Param("maxNik") String maxNik);
	
	@Update("update penduduk SET nik = #{newNik}, nama =#{nama}, tempat_lahir = #{tempat_lahir}, "
			+ "tanggal_lahir = #{tanggal_lahir}, jenis_kelamin = #{jenis_kelamin}, "
			+ "golongan_darah = #{golongan_darah}, agama = #{agama}, status_perkawinan = #{status_perkawinan}, "
			+ "pekerjaan = #{pekerjaan}, is_wni = #{is_wni}, is_wafat = #{is_wafat}, id_keluarga = #{id_keluarga}, "
			+ "status_dalam_keluarga = #{status_dalam_keluarga} WHERE nik = #{nik}")
	 void updatePenduduk(@Param("nik") String nik, @Param("newNik") String newNik, 
			 @Param("nama") String nama, 
			 @Param("tempat_lahir") String tempat_lahir, 
			 @Param("tanggal_lahir")String tanggal_lahir, 
			 @Param("jenis_kelamin") int jenis_kelamin, 
			 @Param("golongan_darah")String golongan_darah, 
			 @Param("agama") String agama, 
			 @Param("status_perkawinan")String status_perkawinan,
			 @Param("pekerjaan")String pekerjaan, @Param("is_wni")int is_wni, 
			 @Param("is_wafat")int is_wafat, @Param("id_keluarga") int id_keluarga,
			 @Param("status_dalam_keluarga") String status_dalam_keluarga);

}
