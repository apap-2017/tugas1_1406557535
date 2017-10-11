package com.example.tugas1a.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KotaModel {
	private Long id;
	private String kode_kota;
	private String nama_kota;
}
