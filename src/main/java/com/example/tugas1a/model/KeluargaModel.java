package com.example.tugas1a.model;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel {
	private Long id;
	private String nomor_kk;
	
	@NotNull(message="Wajib diisi!")
	@Size(min=1, message="Wajib diisi!")
	private String alamat,rt, rw;
	
	
	private Integer id_kelurahan;
	private Integer is_tidak_berlaku;
	
	private KelurahanModel kelurahan;
	private KecamatanModel kecamatan;
	private KotaModel kota;
	
	private List<PendudukModel> anggotaKeluarga;
}
