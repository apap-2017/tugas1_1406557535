package com.example.tugas1a.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendudukModel {
	private Long id;
	private String nik;
	private String nama;
	private String tempat_lahir;
	private Date tanggal_lahir;
	private Integer jenis_kelamin;
	private Integer is_wni;
	private Long id_keluarga;
	private String agama;
	private String pekerjaan;
	private String status_perkawinan;
	private String status_dalam_keluarga;
	private String golongan_darah;
	private Integer is_wafat;
	
	private KeluargaModel keluarga;
	private KelurahanModel kelurahan;
	private KotaModel kota;
	
}
