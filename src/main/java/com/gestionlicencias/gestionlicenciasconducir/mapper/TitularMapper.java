package com.gestionlicencias.gestionlicenciasconducir.mapper;

import com.gestionlicencias.gestionlicenciasconducir.dto.TitularDTO;
import com.gestionlicencias.gestionlicenciasconducir.dto.TitularRecord;
import com.gestionlicencias.gestionlicenciasconducir.model.Titular;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class TitularMapper {
    public TitularRecord toRecord(Titular titular) {
        return new TitularRecord(
                titular.getTipoDocumento(),
                titular.getDocumento(),
                titular.getNombre(),
                titular.getApellido(),
                new java.sql.Date(titular.getFechaNacimiento().getTime()),
                titular.getDireccion(),
                titular.getGrupoSanguineo(),
                titular.getFactorRH(),
                titular.getDonanteOrganos()
        );
    }
    public TitularRecord toRecord(TitularDTO titularDTO) {
        return new TitularRecord(
                titularDTO.getTipoDocumento(),
                titularDTO.getDocumento(),
                titularDTO.getNombre(),
                titularDTO.getApellido(),
                new Date(titularDTO.getFechaNacimiento().getTime()),
                titularDTO.getDireccion(),
                titularDTO.getGrupoSanguineo(),
                titularDTO.getFactorRH(),
                titularDTO.getDonanteOrganos()
        );
    }

    public Titular toEntity(TitularRecord titularRecord) {
        Titular titular = new Titular();
        titular.setTipoDocumento(titularRecord.tipoDocumento());
        titular.setDocumento(titularRecord.documento());
        titular.setNombre(titularRecord.nombre());
        titular.setApellido(titularRecord.apellido());
        titular.setFechaNacimiento(titularRecord.fechaNacimiento());
        titular.setDireccion(titularRecord.direccion());
        titular.setGrupoSanguineo(titularRecord.grupoSanguineo());
        titular.setFactorRH(titularRecord.factorRH());
        titular.setDonanteOrganos(titularRecord.donanteOrganos());
        return titular;
    }
}
