package com.example.tugas1a.controller;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

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
import com.example.tugas1a.service.PendudukService;

@Controller
public class PendudukController {
	
	@Autowired
	PendudukService pendudukDAO;
	KeluargaService keluargaDAO;
	KotaService kotaDAO;
	KelurahanService kelurahanDAO;
	KecamatanService kecamatanDAO;

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
			return "error/404";
		}
	}
	
    @RequestMapping(value="/penduduk/tambah", method = RequestMethod.GET)
    public String addPenduduk (Model model, PendudukModel penduduk)
    {
        return "penduduk/add";
    }
    
    @RequestMapping(value= "/penduduk/tambah", method = RequestMethod.POST)
    public String addPendudukSubmit (Model model, 
    		@Valid PendudukModel penduduk, 
    		BindingResult bindingResult)
    {
    	if(bindingResult.hasErrors()) {
    		return "penduduk/add";
    	} else {
    		
    		KeluargaModel keluarga = pendudukDAO.selectKeluarga(penduduk.getId_keluarga());
    		System.out.println("masuk1");
    		String kodeKecamatan = keluarga.getKelurahan().getKecamatan().getKode_kecamatan().substring(0, 6);
    		
    		Date ymd = null;
    		try {
    			ymd = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(penduduk.getTanggal_lahir());			
    		} catch (Exception e) {
    			
    		}
    		
    		String tglLahir = new SimpleDateFormat("dd-MM-yyyy").format(ymd);		
    		String nikTglLahir = tglLahir.replace("-", "");
    		nikTglLahir = nikTglLahir.substring(0,4) + nikTglLahir.substring(6,8);
    		
    		if(penduduk.getJenis_kelamin() == 1){
    			Integer tanggal = Integer.parseInt(nikTglLahir.substring(0,2));
    			tanggal +=40;
    			nikTglLahir = String.valueOf(tanggal) + nikTglLahir.substring(2,6);
    		}
    		
    		String nikMin= kodeKecamatan + nikTglLahir + "0001";
    		String nikMax= kodeKecamatan + nikTglLahir + "9999";
    		PendudukModel cekPenduduk = pendudukDAO.selectPendudukTerakhir(nikMin, nikMax);
    		
    		if(cekPenduduk == null){
    			penduduk.setNik(nikMin);
    			pendudukDAO.addPenduduk(penduduk);
    			model.addAttribute("nomor", nikMin);
    		}else{
    			Long nikBaru = Long.parseLong(cekPenduduk.getNik());
    			String nikPendudukBaru = String.valueOf(nikBaru+1);
    			penduduk.setNik(nikPendudukBaru);
    			pendudukDAO.addPenduduk(penduduk);
    			model.addAttribute("nomor", nikPendudukBaru);
    		}
    		return "penduduk/sukses-tambah";
    	}
    }
    
//    @RequestMapping(value= "/penduduk/cari")
//    public String cariPenduduk(Model model) 
//    {
//    	List<KotaModel> daftarKota = pendudukDAO.selectDaftarKota();
//    	List<KecamatanModel> daftarKecamatan = pendudukDAO.selectDaftarKecamatan();
//    	List<KelurahanModel> daftarKelurahan = pendudukDAO.selectDaftarKelurahan();
//    	
//    	model.addAttribute("daftarKota", daftarKota);
//    	model.addAttribute("daftarKecamatan", daftarKecamatan);
//    	model.addAttribute("daftarKelurahan", daftarKelurahan);
//        return "penduduk/cari";
//    }
    
    @RequestMapping(value= "/penduduk/cari", method = RequestMethod.GET)
    public String cariPendudukSubmit (Model model, 
    		@RequestParam(value = "kt") Optional<Integer> idKota,
    		@RequestParam(value = "kc") Optional<Integer> idKecamatan,
    		@RequestParam(value = "kl") Optional<Integer> idKelurahan)
    {
    	// Cari penduduknya
    	if(idKota.isPresent() && idKecamatan.isPresent() && idKelurahan.isPresent()) {
    		List <PendudukModel> listPenduduk = pendudukDAO.selectListPenduduk(idKelurahan.get());
    	
    		PendudukModel pendudukTertua = pendudukDAO.selectPendudukTertua(idKelurahan.get());
    		PendudukModel pendudukTermuda = pendudukDAO.selectPendudukTermuda(idKelurahan.get());

    		model.addAttribute("pendudukTertua", pendudukTertua);
    		model.addAttribute("pendudukTermuda", pendudukTermuda);
    		model.addAttribute("listPenduduk", listPenduduk);

    		return "penduduk/list"; 
    		
    	} 
    	// Return form pencarian
    	else {
    		List<KotaModel> daftarKota = kotaDAO.selectDaftarKota();
        	List<KecamatanModel> daftarKecamatan = kecamatanDAO.selectDaftarKecamatan();
        	List<KelurahanModel> daftarKelurahan = kelurahanDAO.selectDaftarKelurahan();
        	
        	model.addAttribute("daftarKota", daftarKota);
        	model.addAttribute("daftarKecamatan", daftarKecamatan);
        	model.addAttribute("daftarKelurahan", daftarKelurahan);
            return "penduduk/cari";
    	}
    }
    
    @RequestMapping(value="/penduduk/ubah/{nik}", method=RequestMethod.GET)
	public String formUbahPenduduk(@PathVariable(value = "nik") String nik, Model model) {
		// Select penduduk
		PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
		penduduk.setTanggal_lahir(formatDate(penduduk.getTanggal_lahir()));
		model.addAttribute("pendudukModel", penduduk);
//		model.addAttribute("golonganDarah", GOLONGAN_DARAH);
//		model.addAttribute("agama", AGAMA);
//		model.addAttribute("statusPerkawinan", STATUS_PERKAWINAN);
//		model.addAttribute("statusDalamKeluarga", STATUS_DALAM_KELUARGA);
		return "penduduk/edit";
	}
    
    @RequestMapping(value="/penduduk/ubah/{nik}", method=RequestMethod.POST)
	public String ubahPenduduk(
			@PathVariable(value = "nik") String nik, 
			@Valid PendudukModel penduduk, 
			BindingResult bindingResult, 
			Model model,
			@RequestParam(value = "nama") String nama,
			@RequestParam (value = "tempat_lahir") String tempat_lahir,
			@RequestParam (value = "tanggal_lahir") String tanggal_lahir,
			@RequestParam (value = "golongan_darah") String golongan_darah,
			@RequestParam (value = "agama") String agama,
			@RequestParam (value = "status_perkawinan") String status_perkawinan,
			@RequestParam (value = "pekerjaan") String pekerjaan,
			@RequestParam (value = "is_wni") Integer is_wni,
			@RequestParam (value = "is_wafat") Integer is_wafat,
			@RequestParam (value = "id_keluarga") Integer id_keluarga,
			@RequestParam (value = "status_dalam_keluarga") String status_dalam_keluarga
		) {
		if(bindingResult.hasErrors()) {
			return "penduduk/edit";
		} else {
			PendudukModel oldPenduduk = pendudukDAO.selectPenduduk(nik);
			int jenis_kelamin = 1;
			String newNik = "";
			if(!penduduk.getTanggal_lahir().equals(formatDate(oldPenduduk.getTanggal_lahir())) || 
					penduduk.getId_keluarga() != oldPenduduk.getId_keluarga()) {
				// Construct new NIK
				KeluargaModel archive = pendudukDAO.selectKeluarga(penduduk.getId_keluarga());
				newNik = constructNik(archive.getKelurahan().getKecamatan().getKode_kecamatan(),
						constructTanggal(penduduk.getTanggal_lahir()), penduduk.getJenis_kelamin());
			} else {
				newNik = nik;
			}
			System.out.println(newNik);
			// Update database

			pendudukDAO.updatePenduduk(nik, newNik, nama, tempat_lahir, 
					formatDB(penduduk.getTanggal_lahir()), jenis_kelamin,  
					golongan_darah, agama, status_perkawinan, pekerjaan, is_wni, is_wafat, id_keluarga, status_dalam_keluarga);
			model.addAttribute("nomor_lama", nik);
	    	model.addAttribute("nomor_baru", newNik);
			return "penduduk/sukses-update";		
		}
	}
    
    
    @RequestMapping(value= "/penduduk/mati", method = RequestMethod.POST)
    public String updateStatusKematian (Model model, 
    		@RequestParam(value = "nik") String nik)
    {
    	PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
    	int wafat = 1;
    	
    	pendudukDAO.setWafat(nik);
    	
		List<PendudukModel> anggotaKeluarga = pendudukDAO.selectAnggotaKeluarga(penduduk.getKeluarga().getNomor_kk());
		int jmlWafat=0;
		
		for(int i = 0; i< anggotaKeluarga.size();i++){
			if(anggotaKeluarga.get(i).getIs_wafat() == wafat){
				jmlWafat++;
			}
		}
		
		int tidakBerlaku= 1;
		if(jmlWafat == anggotaKeluarga.size()){
			penduduk.getKeluarga().setIs_tidak_berlaku(tidakBerlaku);
			pendudukDAO.updateKeluarga(penduduk.getKeluarga());
		}
		
		model.addAttribute("nomor_lama", nik);
    	model.addAttribute("nomor_baru", penduduk.getNik());
        return "penduduk/sukses-update";
    }
    
    
    /*
     * Other methods
     */
	private String formatDate(String data) {
		Date oldDate = stringToDateYMD(data);
		return dateToStringDMY(oldDate);
	}
	
	private String dateToStringDMY(Date data) {
		return new SimpleDateFormat("dd-MM-yyyy").format(data);
	}
	
	private Date stringToDateYMD(String data) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(data);				
		} catch(Exception e) {
			
		}
		return date;
	}
	
	private String formatDB(String data) {
		Date oldDate = stringToDateDMY(data);
		return dateToStringYMD(oldDate);
	}
	
	private Date stringToDateDMY(String data) {
		Date date = null;
		try {
			date = new SimpleDateFormat("dd-MM-yyyy").parse(data);				
		} catch(Exception e) {
			
		}
		return date;
	}
	
	private String dateToStringYMD(Date data) {
		return new SimpleDateFormat("yyyy-MM-dd").format(data);
	}
	
	private String constructTanggal(String tanggal) {
		return tanggal.replace("-", "");
	}
	
	private String constructNik(String kodeDaerah, String tanggal, int jenis_kelamin) {
		String newTanggal = "";
		if(jenis_kelamin == 1) {
			newTanggal = String.valueOf(Integer.parseInt(tanggal.substring(0,2))+40) + tanggal.substring(2, tanggal.length());			
		} else {
			newTanggal = tanggal;
		}
		String minNik = kodeDaerah.substring(0, 6) + newTanggal.substring(0, 4) + newTanggal.substring(newTanggal.length()-2, newTanggal.length()) + "0001";
		String maxNik = String.valueOf(Long.parseLong(minNik)+9999);
		String lastNoUrutNik = pendudukDAO.getLast(minNik, maxNik);
		if(lastNoUrutNik == null) {
			return minNik;
		} else {
			return String.valueOf(Long.parseLong(lastNoUrutNik)+1);
		}
	}
	
}
