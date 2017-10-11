package com.example.tugas1a.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel {
	private Long id;
	private String nomor_kk;
	private String alamat;
	private String RT;
	private String RW;
	private String id_kelurahan;
	private Integer is_tidak_berlaku;
	private KelurahanModel kelurahan;
	private KecamatanModel kecamatan;
	private KotaModel kota;
	private List<PendudukModel> penduduks;
}
