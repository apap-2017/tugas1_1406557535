package com.example.tugas1a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tugas1a.model.KeluargaModel;
import com.example.tugas1a.service.KeluargaService;

@Controller
public class KeluargaController {
	@Autowired
	KeluargaService keluargaDAO;
	
	@RequestMapping("/keluarga")
	public String keluargaViewNKK(Model model, @RequestParam(value="nkk", required=true) String nomor_kk) {
		KeluargaModel keluarga = keluargaDAO.selectKeluarga(nomor_kk);
		if (keluarga != null) {
			model.addAttribute("keluarga", keluarga);
			return "keluarga/view";
		} else {
			model.addAttribute("errormessage", "Keluarga dengan NKK " + nomor_kk + " tidak ditemukan, mohon cek kembali Nomor Kartu Keluarga Anda.");
			return "layout/error";
		}
	}
	
	
}
