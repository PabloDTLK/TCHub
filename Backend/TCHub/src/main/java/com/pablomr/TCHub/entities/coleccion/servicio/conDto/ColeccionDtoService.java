package com.pablomr.TCHub.entities.coleccion.servicio.conDto;


import com.pablomr.TCHub.entities.coleccion.dto.ColeccionConVariantesDTO;
import com.pablomr.TCHub.entities.coleccion.dto.ColeccionDTO;
import com.pablomr.TCHub.entities.coleccion.dto.mapper.ColeccionMapper;
import com.pablomr.TCHub.entities.coleccion.modelo.Coleccion;
import com.pablomr.TCHub.entities.coleccion.servicio.ColeccionService;
import com.pablomr.TCHub.entities.coleccionVariante.dto.ColeccionVariantesListDTO;
import com.pablomr.TCHub.entities.coleccionVariante.modelo.ColeccionVariante;
import com.pablomr.TCHub.entities.coleccionVariante.servicio.ColeccionVarianteService;
import com.pablomr.TCHub.entities.variante.servicio.VarianteService;
import com.pablomr.TCHub.generico.servicio.conDTO.GenericDtoServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class ColeccionDtoService extends GenericDtoServiceImpl<Coleccion, Long, ColeccionDTO> {

    @Autowired
    private ColeccionService coleccionService;

    @Autowired
    private ColeccionVarianteService coleccionVarianteService;
    @Autowired
    private VarianteService varianteService;

    @Autowired
    private ColeccionMapper coleccionMapper;

    public ColeccionConVariantesDTO findByIdDto(Long id) {
        Coleccion coleccion = coleccionService.findById(id);

        return ColeccionConVariantesDTO.builder()
                .id(coleccion.getId())
                .nombre(coleccion.getNombre())
                .descripcion(coleccion.getDescripcion())
                .listaVariantes(setToColeccionVariantesListDTO(coleccion.getColeccionVariantes()))
                .build();
    }

    public List<ColeccionConVariantesDTO> findByUsuarioToken() {
        List<Coleccion> lista = coleccionService.findByToken();

        return listToColeccionConVariantesDTO(lista);
    }

    public List<ColeccionConVariantesDTO> findByUsername(String username) {
        List<Coleccion> lista = coleccionService.findByUsername(username);

        return listToColeccionConVariantesDTO(lista);
    }

    public void delete(Long id) {
        coleccionService.deleteById(id);
    }

    public ColeccionDTO saveByToken(ColeccionDTO coleccionDTO) {
        return  coleccionMapper.toDto(
                saveByTokenEntity(coleccionDTO)
        );
    }

    public Coleccion saveByTokenEntity(ColeccionDTO coleccionDTO) {
        return coleccionService.saveByToken(
                        coleccionMapper.toEntity(coleccionDTO)
        );
    }

    @Transactional
    public ColeccionConVariantesDTO updateConVariantes(Long id, ColeccionConVariantesDTO dto) {
        Coleccion coleccionGuardada = coleccionService.findById(id);

        //Obtención de la lista de coleccionVariante
        ColeccionVariante coleccionVariante = new ColeccionVariante();
        for (ColeccionVariantesListDTO coleccionVariantesListDTO: dto.getListaVariantes()) {

            coleccionVariante = new ColeccionVariante();
            coleccionVariante.setVariante(
                    varianteService.findByUniversalId(coleccionVariantesListDTO.getVarianteUniversalId())
            );
            coleccionVariante.setCantidad(coleccionVariantesListDTO.getCantidad());
            coleccionVariante.setColeccion(coleccionGuardada);

            if (coleccionVarianteService.existsByColeccionYVariante(id, coleccionVariantesListDTO.getVarianteUniversalId())) {
                coleccionVariante.setId(
                        coleccionVarianteService.findByColeccionYVariante(id, coleccionVariantesListDTO.getVarianteUniversalId())
                        .getId()
                );

                coleccionVarianteService.update(coleccionVariante, coleccionVariante.getId());
            } else {

                coleccionVarianteService.save(coleccionVariante);
            }
        }
        //Obtención de la colección
        Coleccion coleccion = coleccionService.findById(id);

        coleccion.setId(dto.getId());
        coleccion.setNombre(dto.getNombre());
        coleccion.setDescripcion(dto.getDescripcion());

        coleccion = coleccionService.update(coleccion, id);

        coleccion = coleccionService.findById(coleccion.getId());

        return ColeccionConVariantesDTO.builder()
                .id(coleccion.getId())
                .nombre(coleccion.getNombre())
                .descripcion(coleccion.getDescripcion())
                .listaVariantes(setToColeccionVariantesListDTO(coleccion.getColeccionVariantes()))
                .build();
    }

    public ColeccionConVariantesDTO saveConVariantes(ColeccionConVariantesDTO dto) {
        Coleccion coleccion = saveByTokenEntity(ColeccionDTO.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .build()
        );

        List<ColeccionVariantesListDTO> listColeccionVariantesListDTO =
                coleccionVarianteService.saveDtoList(
                        dto.getListaVariantes(),
                        coleccion.getId()
                );

        return ColeccionConVariantesDTO.builder()
                .id(coleccion.getId())
                .nombre(coleccion.getNombre())
                .descripcion(coleccion.getDescripcion())
                .listaVariantes(listColeccionVariantesListDTO)
                .build();
    }

    private List<ColeccionVariantesListDTO> setToColeccionVariantesListDTO(Set<ColeccionVariante> lista) {
        List<ColeccionVariantesListDTO> response = new ArrayList<>();

        for (ColeccionVariante coleccionVariante:lista) {
            response.add(ColeccionVariantesListDTO.builder()
                    .varianteUniversalId(coleccionVariante.getVariante().getUniversalId())
                    .cantidad(coleccionVariante.getCantidad())
                    .build());
        }

        return response;
    }

    private List<ColeccionConVariantesDTO> listToColeccionConVariantesDTO(List<Coleccion> lista) {
        List<ColeccionConVariantesDTO> response = new ArrayList<>();

        for (Coleccion coleccion:lista) {
            response.add(ColeccionConVariantesDTO.builder()
                    .id(coleccion.getId())
                    .nombre(coleccion.getNombre())
                    .descripcion(coleccion.getDescripcion())
                    .listaVariantes(setToColeccionVariantesListDTO(coleccion.getColeccionVariantes()))
                    .build()
            );
        }

        return response;
    }
}
