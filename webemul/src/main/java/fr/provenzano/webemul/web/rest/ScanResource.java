package fr.provenzano.webemul.web.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import fr.provenzano.webemul.domain.Rom;
import fr.provenzano.webemul.service.ConsoleService;
import fr.provenzano.webemul.service.RomService;
import fr.provenzano.webemul.service.dto.ConsoleDTO;
import fr.provenzano.webemul.service.dto.RomDTO;
import fr.provenzano.webemul.service.dto.ScannedRomDTO;

@RestController
@RequestMapping("/api")
public class ScanResource {

	private final RomService romService;
    
    private final ConsoleService consoleService;
	
    public ScanResource(RomService romService, ConsoleService consoleService) {
        this.romService = romService;
        this.consoleService = consoleService;
    }
    
    @GetMapping("/scan")
    @Timed
    public List<ScannedRomDTO> scanRoms(@RequestParam("consoleId")String consoleId) {
    	
    	List<String> filenames = new ArrayList<>();
    	List<ScannedRomDTO> scannedRomDTOs = new ArrayList<>();
    	
    	ConsoleDTO consoleDTO = consoleService.findOne(Long.parseLong(consoleId));    	
    	String romsFolderPath = consoleDTO.getPathRomsFolder();
        File romsFolder = new File(romsFolderPath);
        File[] filesList = romsFolder.listFiles();
        for(File f : filesList){
            if(f.isFile()){
            	filenames.add(f.getAbsolutePath());
                RomDTO romDTO = new RomDTO();
                romDTO.setPathFile(f.getAbsolutePath());
                romDTO.setName(f.getName());
                romDTO.setConsoleId(consoleDTO.getId());
                boolean added = romService.saveByFilePath(romDTO);
                if (added) {
                	scannedRomDTOs.add(new ScannedRomDTO(f.getName(), ScannedRomDTO.ScannedRomStatus.ADDED));
                }                
            }
        }
        
        List<Rom> databaseRoms = consoleService.findConsoleRoms(Long.parseLong(consoleId));
        for (Rom rom : databaseRoms) {
			if (!filenames.contains(rom.getPathFile())) {
				scannedRomDTOs.add(new ScannedRomDTO(rom.getName(), ScannedRomDTO.ScannedRomStatus.DELETED));             
				romService.delete(rom.getId());
			}
		}
        
        return scannedRomDTOs;    	
    }
    
}
