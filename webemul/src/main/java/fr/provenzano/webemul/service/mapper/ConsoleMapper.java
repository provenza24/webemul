package fr.provenzano.webemul.service.mapper;

import fr.provenzano.webemul.domain.*;
import fr.provenzano.webemul.service.dto.ConsoleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Console and its DTO ConsoleDTO.
 */
@Mapper(componentModel = "spring", uses = {EmulatorMapper.class})
public interface ConsoleMapper extends EntityMapper<ConsoleDTO, Console> {

    @Mapping(source = "defaultEmulator.id", target = "defaultEmulatorId")
    ConsoleDTO toDto(Console console);

    @Mapping(target = "roms", ignore = true)
    @Mapping(source = "defaultEmulatorId", target = "defaultEmulator")
    Console toEntity(ConsoleDTO consoleDTO);

    default Console fromId(Long id) {
        if (id == null) {
            return null;
        }
        Console console = new Console();
        console.setId(id);
        return console;
    }
}
