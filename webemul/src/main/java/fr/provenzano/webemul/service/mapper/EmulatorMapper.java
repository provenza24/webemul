package fr.provenzano.webemul.service.mapper;

import fr.provenzano.webemul.domain.*;
import fr.provenzano.webemul.service.dto.EmulatorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Emulator and its DTO EmulatorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmulatorMapper extends EntityMapper<EmulatorDTO, Emulator> {



    default Emulator fromId(Long id) {
        if (id == null) {
            return null;
        }
        Emulator emulator = new Emulator();
        emulator.setId(id);
        return emulator;
    }
}
