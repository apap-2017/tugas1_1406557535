package com.example.tugas1a.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendudukModel {
	private Integer id;
	private String nik;
	@NotNull(message="Wajib diisi!")
	@Size(min=1, message="Wajib diisi!")
	private String nama;
	
	@NotNull(message="Wajib diisi!")
	@Size(min=1, message="Wajib diisi!")
	private String tempat_lahir;
	
	@NotNull(message="Wajib diisi!")
	private String tanggal_lahir;
	
	@NotNull(message="Wajib diisi!")
	private Integer jenis_kelamin;
	
	@NotNull(message="Wajib diisi!")
	private Integer is_wni;
	
	@NotNull(message="Wajib diisi!")
	private Integer id_keluarga;
	
	@NotNull(message="Wajib diisi!")
	@Size(min=1, message="Wajib diisi!")
	private String agama;
	
	@NotNull(message="Wajib diisi!")
	@Size(min=1, message="Wajib diisi!")
	private String pekerjaan;
	
	@NotNull(message="Wajib diisi!")
	@Size(min=1, message="Wajib diisi!")
	private String status_perkawinan;
	
	@NotNull(message="Wajib diisi!")
	@Size(min=1, message="Wajib diisi!")
	private String status_dalam_keluarga;
	
	@NotNull(message="Wajib diisi!")
	@Size(min=1, message="Wajib diisi!")
	private String golongan_darah;
	
	@NotNull(message="Wajib diisi!")
	private Integer is_wafat;
	
	private KeluargaModel keluarga;
	
}
