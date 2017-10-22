package com.example.tugas1a.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;

import com.example.tugas1a.model.KecamatanModel;
import com.example.tugas1a.model.KeluargaModel;
import com.example.tugas1a.model.KelurahanModel;
import com.example.tugas1a.model.KotaModel;
import com.example.tugas1a.model.PendudukModel;

@Mapper
public interface KeluargaMapper {
	
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
	
	
	@Select("select * " 
			+ "from kelurahan " 
			+ "where kelurahan.id = #{id_kelurahan}")	
	@Results(value = { @Result(property = "id", column = "id"), 
			@Result(property = "kode_kelurahan", column = "kode_kelurahan"),
			@Result(property = "id_kecamatan", column = "id_kecamatan"),
			@Result(property = "nama_kelurahan", column = "nama_kelurahan"),
			@Result(property = "kode_pos", column = "kode_pos"),
			@Result(property = "kecamatan", column = "id_kecamatan", 
			javaType = KecamatanModel.class,
			many = @Many(select = "selectKecamatan")) })
	KelurahanModel selectKelurahan(@Param("id_kelurahan") Integer id_kelurahan);
	
	@Select("select * " 
			+ "from kecamatan "
			+ "where kecamatan.id = #{id_kecamatan}")
	@Results(value = { @Result(property = "id", column = "id"), 
			@Result(property = "kode_kecamatan", column = "kode_kecamatan"),
			@Result(property = "id_kota", column = "id_kota"),
			@Result(property = "nama_kecamatan", column = "nama_kecamatan"),
			@Result(property = "kota", column = "id_kota", 
			javaType = KotaModel.class,
			many = @Many(select = "selectKota")) })
	KecamatanModel selectKecamatan(@Param("id_kelurahan") Integer id_kecamatan);
	
	@Select("select * " 
			+ "from  	kota "
			+ "where kota.id = #{id_kota}")
	KotaModel selectKota(@Param("id_kota") Integer id_kota);
	
	@Select("select * " 
			+ "from keluarga "
			+ "where keluarga.nomor_kk = #{nkk}")
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
	KeluargaModel selectKartuKeluarga(@Param("nkk") String nkk);
	
	@Select("select * " 
			+ "from penduduk, keluarga "
			+ "where keluarga.nomor_kk= #{nkk} and penduduk.id_keluarga = keluarga.id")
	List<PendudukModel> selectAnggotaKeluarga(@Param("nkk") String nkk);

	@Insert("INSERT INTO keluarga (nomor_kk, alamat, rt, rw, id_kelurahan) "
			+ "VALUES (#{nomor_kk}, #{alamat}, #{rt}, #{rw}, #{id_kelurahan})")
	void addKeluarga(KeluargaModel keluarga);

	
	@Select("select * " 
			+ "from keluarga "
			+ "where nomor_kk BETWEEN #{nkkMin} and #{nkkMax}"
			+ "order by nomor_kk "
			+ "DESC LIMIT 1 ")
	KeluargaModel selectKeluargaTerakhir(@Param("nkkMin")String nkkMin,@Param("nkkMax") String nkkMax);


	@Select("select * " 
			+ "from kota ")
	List<KotaModel> selectDaftarKota();
	
	@Select("select * " 
			+ "from kecamatan ")
	List<KecamatanModel> selectDaftarKecamatan();
	
	@Select("select * " 
			+ "from kelurahan ")
	List<KelurahanModel> selectDaftarKelurahan();

	@Select("select * " 
			+ "from penduduk "
			+ "where nik BETWEEN #{nikMin} and #{nikMax}"
			+ "order by nik "
			+ "DESC LIMIT 1 ")
	PendudukModel selectPendudukTerakhir(@Param("nikMin")String nikMin,@Param("nikMax") String nikMax);


	@Update("update penduduk set nik = #{nik}, nama= #{nama}, tempat_lahir = #{tempat_lahir}, tanggal_lahir = #{tanggal_lahir}, "
			+ "jenis_kelamin = #{jenis_kelamin}, is_wni = #{is_wni}, is_wafat = #{is_wafat}, "
			+ "id_keluarga = #{id_keluarga}, agama = #{agama}, pekerjaan =  #{pekerjaan}, "
			+ "status_perkawinan = #{status_perkawinan}, status_dalam_keluarga = #{status_dalam_keluarga}, golongan_darah = #{golongan_darah}, is_wafat = #{is_wafat} "
			+ "WHERE id=#{id}")
	void updatePenduduk(PendudukModel penduduk);
	
	@Update("update keluarga set nomor_kk = #{nomor_kk}, alamat= #{alamat}, rt = #{rt}, rw = #{rw}, "
			+ "id_kelurahan = #{id_kelurahan}, is_tidak_berlaku = #{is_tidak_berlaku} "
			+ "WHERE id=#{id}")
	void updateKeluarga(KeluargaModel keluarga);

}
