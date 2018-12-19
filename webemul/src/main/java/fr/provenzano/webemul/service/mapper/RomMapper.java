package fr.provenzano.webemul.service.mapper;

import fr.provenzano.webemul.domain.*;
import fr.provenzano.webemul.service.dto.RomDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Rom and its DTO RomDTO.
 */
@Mapper(componentModel = "spring", uses = {ConsoleMapper.class, GenreMapper.class})
public interface RomMapper extends EntityMapper<RomDTO, Rom> {

    @Mapping(source = "console.id", target = "consoleId")
    RomDTO toDto(Rom rom);

    @Mapping(source = "consoleId", target = "console")
    Rom toEntity(RomDTO romDTO);

    default Rom fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rom rom = new Rom();
        rom.setId(id);
        return rom;
    }
}
