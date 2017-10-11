package com.example.tugas1a.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tugas1a.model.PendudukModel;
import com.example.tugas1a.service.PendudukService;

@Controller
public class PendudukController {
	
	@Autowired
	PendudukService pendudukDAO;

	@RequestMapping("/master")
	public String master() {
		return "layout/master";
	}
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/penduduk")
	public String pendudukViewNIK(Model model, @RequestParam(value="nik", required=true) String nik) {
		PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
		if (penduduk != null) {
			model.addAttribute("penduduk", penduduk);
			return "penduduk/view";
		} else {
			model.addAttribute("errormessage", "Penduduk dengan NIK " + nik + " tidak ditemukan, mohon cek kembali Nomor Induk Kependudukan Anda.");
			return "layout/error";
		}
	}
	
	@RequestMapping("/penduduk/add")
	public String pendudukAdd() {
		return "penduduk/add";
	}

}
