package com.example.tugas1a.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tugas1a.model.KecamatanModel;
import com.example.tugas1a.model.KeluargaModel;
import com.example.tugas1a.model.KelurahanModel;
import com.example.tugas1a.model.KotaModel;
import com.example.tugas1a.model.PendudukModel;
import com.example.tugas1a.service.KecamatanService;
import com.example.tugas1a.service.KeluargaService;
import com.example.tugas1a.service.KelurahanService;
import com.example.tugas1a.service.KotaService;

@Controller
public class KeluargaController {
	@Autowired
	KeluargaService keluargaDAO;
	KotaService kotaDAO;
	KelurahanService kelurahanDAO;
	KecamatanService kecamatanDAO;
	
    @RequestMapping(value = "/keluarga",method = RequestMethod.GET)
    public String anggotaKeluarga(Model model,
    		@RequestParam(value="nkk") String nkk){
    	
    	KeluargaModel keluarga = keluargaDAO.selectKartuKeluarga(nkk); 
    	
    	 if(keluarga!= null){
    		List<PendudukModel> anggotaKeluarga = keluargaDAO.selectAnggotaKeluarga(nkk);
    		model.addAttribute("anggota", anggotaKeluarga);
    		model.addAttribute("keluarga", keluarga);
    		return "keluarga/view";
	      }
    	 else{
	    	 model.addAttribute("nomor",nkk);
	    	 return "layout/error";	
    	 }
   }	
    
    @RequestMapping(value="/keluarga/tambah", method = RequestMethod.GET)
    public String addKeluarga (Model model, KeluargaModel keluarga)
    {
    	List<KotaModel> daftarKota = keluargaDAO.selectDaftarKota();
    	List<KecamatanModel> daftarKecamatan = keluargaDAO.selectDaftarKecamatan();
    	List<KelurahanModel> daftarKelurahan = keluargaDAO.selectDaftarKelurahan();
    	
    	model.addAttribute("daftarKota", daftarKota);
    	model.addAttribute("daftarKecamatan", daftarKecamatan);
    	model.addAttribute("daftarKelurahan", daftarKelurahan);
        return "keluarga/add";
    }
    
    @RequestMapping(value= "/keluarga/tambah", method = RequestMethod.POST)
    public String addKeluargaSubmit (Model model, @Valid KeluargaModel keluarga, BindingResult bindingResult,
    		@RequestParam(value = "kota") Integer idKota,
    		@RequestParam(value = "kecamatan") Integer idKecamatan,
    		@RequestParam(value = "kelurahan") Integer idKelurahan){
    	
    	Date date = new Date();
		String currentDate  = new SimpleDateFormat("dd-MM-yyyy").format(date);
		currentDate= currentDate.replace("-", "");
		currentDate= currentDate.substring(0,4) + currentDate.substring(6,8);
		KelurahanModel kelurahan = keluargaDAO.selectKelurahan(idKelurahan);
		String kodeKecamatan = kelurahan.getKecamatan().getKode_kecamatan().substring(0, 6);
		
		
		String nkkMin= kodeKecamatan + currentDate + "0001";
		String nkkMax= kodeKecamatan + currentDate + "9999";
		KeluargaModel cekKeluarga = keluargaDAO.selectKeluargaTerakhir(nkkMin, nkkMax);
		
		if(cekKeluarga == null){
			keluarga.setNomor_kk(nkkMin);
			model.addAttribute("nomor", nkkMin);
		}else{
			Long nkkBaru = Long.parseLong(cekKeluarga.getNomor_kk());
			String nkkKeluargaBaru = String.valueOf(nkkBaru+1);
			keluarga.setNomor_kk(nkkKeluargaBaru);
			model.addAttribute("nomor", nkkKeluargaBaru);
		}
	
		keluarga.setId_kelurahan(idKelurahan);
		keluargaDAO.addKeluarga(keluarga);
    	return "keluarga/sukses-tambah";

    }
    
    
    @RequestMapping(value= "/keluarga/ubah/{nkk}", method = RequestMethod.GET)
    public String editKeluarga (Model model, @PathVariable(value = "nkk") String nkk)
    {
        KeluargaModel keluarga = keluargaDAO.selectKartuKeluarga(nkk);
      
        
        if(keluarga==null){
        	model.addAttribute("nomor",nkk);
        	return "not-found";
        }else{
        	
        	
    		List<KotaModel> daftarKota = keluargaDAO.selectDaftarKota();
        	List<KecamatanModel> daftarKecamatan = keluargaDAO.selectDaftarKecamatan();
        	List<KecamatanModel> daftarKecamatanSaya = new ArrayList<>();
        	List<Integer> temp = new ArrayList<>();
        	for(KecamatanModel e: daftarKecamatan) {
        		if (e.getId_kota() == keluarga.getKelurahan().getKecamatan().getId_kota()) {
        			daftarKecamatanSaya.add(e);
        			temp.add(e.getId());
        		}
        	}
        	List<KelurahanModel> daftarKelurahan = keluargaDAO.selectDaftarKelurahan();
        	List<KelurahanModel> daftarKelurahanSaya = new ArrayList<>();
        	for(KelurahanModel e: daftarKelurahan) {
        		if (temp.contains(e.getId_kecamatan())) {
        			daftarKelurahanSaya.add(e);
        		}
        	}
        	
        	model.addAttribute("daftarKota", daftarKota);
        	model.addAttribute("daftarKecamatan", daftarKecamatan);
        	model.addAttribute("daftarKecamatanSaya", daftarKecamatanSaya);
        	model.addAttribute("daftarKelurahan", daftarKelurahan);
        	model.addAttribute("daftarKelurahanSaya", daftarKelurahanSaya);
        	model.addAttribute("keluargaModel", keluarga);
        }
        
    	return "keluarga/edit";
    }

    @RequestMapping(value= "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
    public String updateKeluargaSubmit (@PathVariable(value="nkk") String nkk, Model model,
    		@RequestParam(value = "alamat") String alamat,
    		@RequestParam(value = "rt") String rt,
    		@RequestParam(value = "rw") String rw,
    		@RequestParam(value = "kota") Integer id_kota,
    		@RequestParam(value = "kecamatan") Integer id_kecamatan,
    		@RequestParam(value = "kelurahan") Integer id_kelurahan)
    {
    	KeluargaModel arsipKeluarga = keluargaDAO.selectKartuKeluarga(nkk);
 
    	Date date = new Date();
		String currentDate  = new SimpleDateFormat("dd-MM-yyyy").format(date);
		currentDate= currentDate.replace("-", "");
		currentDate= currentDate.substring(0,4) + currentDate.substring(6,8);
	
   		if(id_kecamatan != arsipKeluarga.getKelurahan().getKecamatan().getId()){
    		
   			KelurahanModel kelurahan = keluargaDAO.selectKelurahan(id_kelurahan);
   			String kodeKecamatanBaru = kelurahan.getKecamatan().getKode_kecamatan().substring(0, 6);
    		String nkkMin= kodeKecamatanBaru + currentDate+ "0001";
    		String nkkMax= kodeKecamatanBaru + currentDate + "9999";
    		
    		KeluargaModel cekKeluarga = keluargaDAO.selectKeluargaTerakhir(nkkMin, nkkMax);
    		
    		if(cekKeluarga == null){
    			arsipKeluarga.setNomor_kk(nkkMin);
    		}else{
    			Long nkkBaru = Long.parseLong(cekKeluarga.getNomor_kk());
    			String nkkKeluargaBaru = String.valueOf(nkkBaru+1);
    			arsipKeluarga.setNomor_kk(nkkKeluargaBaru);
  
    		}  		
    		
    		    		
    		List<PendudukModel> anggotaKeluarga = keluargaDAO.selectAnggotaKeluarga(nkk);
    		for(int i = 0; i< anggotaKeluarga.size();i++){
    			String nikMin= kodeKecamatanBaru + anggotaKeluarga.get(i).getNik().substring(6, 12) + "0001";
        		String nikMax= kodeKecamatanBaru + anggotaKeluarga.get(i).getNik().substring(6, 12) + "9999";
    			PendudukModel cekPenduduk = keluargaDAO.selectPendudukTerakhir(nikMin, nikMax);
    			if(cekPenduduk == null){
    				anggotaKeluarga.get(i).setNik(nikMin);
    				keluargaDAO.updatePenduduk(anggotaKeluarga.get(i));
        			
        			model.addAttribute("nomor", nikMin);
        		}else{
        			Long nikBaru = Long.parseLong(cekPenduduk.getNik());
        			String nikPendudukBaru = String.valueOf(nikBaru+1);
        			anggotaKeluarga.get(i).setNik(nikPendudukBaru);
        			keluargaDAO.updatePenduduk(anggotaKeluarga.get(i));
        			model.addAttribute("nomor", nikPendudukBaru);
        		}
       		}
    		
     	}else{
			String nkkUpdateDate = arsipKeluarga.getNomor_kk().substring(0, 6) + currentDate + arsipKeluarga.getNomor_kk().substring(12, 16);      		
	     
			String nkkMin= nkkUpdateDate.substring(0, 12) + "0001";
	    	String nkkMax= nkkUpdateDate.substring(0, 12)  + "9999";
	    		
    		KeluargaModel cekKeluarga = keluargaDAO.selectKeluargaTerakhir(nkkMin, nkkMax);
    		
    		if(cekKeluarga == null){
    			arsipKeluarga.setNomor_kk(nkkMin);
    		}else{
    			Long nkkBaru = Long.parseLong(cekKeluarga.getNomor_kk());
    			String nkkKeluargaBaru = String.valueOf(nkkBaru+1);
    			arsipKeluarga.setNomor_kk(nkkKeluargaBaru);
  
    		}  		
     	}
   		
   		arsipKeluarga.setAlamat(alamat);
		arsipKeluarga.setRt(rt);
		arsipKeluarga.setRw(rw);
		arsipKeluarga.setId_kelurahan(id_kelurahan);
		keluargaDAO.updateKeluarga(arsipKeluarga);
		model.addAttribute("nomor", arsipKeluarga.getNomor_kk());
    	
    	return"keluarga/sukses-update";
    }
	
}
