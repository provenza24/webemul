package fr.provenzano.webemul.web.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
import fr.provenzano.webemul.service.dto.ScanDTO;
import fr.provenzano.webemul.service.dto.ScannedRomDTO;

@RestController
@RequestMapping("/api")
public class ScanResource {

	private final RomService romService;
    
    private final ConsoleService consoleService;
    
    private final SimpMessagingTemplate template;
	
    public ScanResource(RomService romService, ConsoleService consoleService, SimpMessagingTemplate template) {
        this.romService = romService;
        this.consoleService = consoleService;
        this.template = template;
    }
    
    @GetMapping("/scan")
    @Timed
    public List<ScannedRomDTO> scanRoms(@RequestParam("consoleId")String consoleId) {
    	
    	List<String> filenames = new ArrayList<>();
    	List<ScannedRomDTO> scannedRomDTOs = new ArrayList<>();
    	
    	List<Rom> databaseRoms = consoleService.findConsoleRoms(Long.parseLong(consoleId));
    	
    	ConsoleDTO consoleDTO = consoleService.findOne(Long.parseLong(consoleId));    	
    	String romsFolderPath = consoleDTO.getPathRomsFolder();
        File romsFolder = new File(romsFolderPath);
        File[] filesList = romsFolder.listFiles();
        int totalFilesToCheck = filesList.length + databaseRoms.size();
        //System.err.println("########### Total ########### ->"+totalFilesToCheck);
        int index = 0;
        for(File f : filesList){
            if(f.isFile()){
            	filenames.add(f.getAbsolutePath());
                RomDTO romDTO = new RomDTO();
                romDTO.setPathFile(f.getAbsolutePath());
                String name = f.getName();
                name = StringUtils.substringBeforeLast(name, ".");
                name = name.replaceAll("\\(.*\\)|\\[.*\\]", "");
                if (StringUtils.endsWith(name, " The")) {
                	name = StringUtils.substringBeforeLast(name, " The");
                }
                name = StringUtils.trim(name);
                romDTO.setName(name);
                romDTO.setConsoleId(consoleDTO.getId());
                boolean added = romService.saveByFilePath(romDTO);
                if (added) {                	
                	scannedRomDTOs.add(new ScannedRomDTO(f.getName(), ScannedRomDTO.ScannedRomStatus.ADDED));                	                
                }               
            }
            index++;
            int percent = (int)(index*100 / totalFilesToCheck);
            //System.err.println("### sending ### ->"+percent);
            template.convertAndSend("/topic/scan-console", new ScanDTO("Vérification des roms du répertoire ...", percent));                	        	           
        }
                
        for (Rom rom : databaseRoms) {
			if (!filenames.contains(rom.getPathFile())) {				
				scannedRomDTOs.add(new ScannedRomDTO(rom.getName(), ScannedRomDTO.ScannedRomStatus.DELETED));             
				romService.delete(rom.getId());				
			}
			index++;
			int percent = (int)(index*100 / totalFilesToCheck);
            //System.err.println("### sending ### ->"+percent);
			template.convertAndSend("/topic/scan-console", new ScanDTO("Vérification des roms de la base de données ...", percent));                	
		}
        
        //System.err.println("##### index ##### ->"+index);
        
        return scannedRomDTOs;    	
    }
    
}
