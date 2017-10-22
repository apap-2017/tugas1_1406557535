package com.example.tugas1a.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KecamatanModel {
	private Integer id;
	private Integer id_kota;
	private String kode_kecamatan;
	private String nama_kecamatan;
	private KotaModel kota;
}
