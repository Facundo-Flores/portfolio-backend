package com.argentinaprograma.portfoliobackend.service.educacion;

import com.argentinaprograma.portfoliobackend.model.Educacion;
import com.argentinaprograma.portfoliobackend.model.Persona;
import com.argentinaprograma.portfoliobackend.repository.IEducacionRepository;
import com.argentinaprograma.portfoliobackend.repository.IPersonaRepository;
import com.argentinaprograma.portfoliobackend.response.EducacionResponse;
import com.argentinaprograma.portfoliobackend.service.ICurriculumService;
import com.argentinaprograma.portfoliobackend.util.ImageUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EducacionServiceImpl implements ICurriculumService<Educacion> {

    private final IEducacionRepository educacionRepository;

    private final IPersonaRepository personaRepository;

    public EducacionServiceImpl(IEducacionRepository educacionRepository, IPersonaRepository personaRepository) {
        this.educacionRepository = educacionRepository;
        this.personaRepository = personaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<EducacionResponse> buscar() {
        EducacionResponse response = new EducacionResponse();

        try {
            List<Educacion> educacionList = (List<Educacion>) educacionRepository.findAll();
            for (Educacion item : educacionList) {
                if (item.getLogo() != null) {
                    byte[] imagenDescomprimida = ImageUtility.decompressZLib(item.getLogo());
                    item.setLogo(imagenDescomprimida);
                }
            }
            response.setEducacionList(educacionList);
        } catch (Exception e) {
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<EducacionResponse> buscarPorId(Long id) {

        List<Educacion> list = new ArrayList<>();
        EducacionResponse response = new EducacionResponse();

        try {
            Optional<Educacion> educacion = educacionRepository.findById(id);

            if (educacion.isPresent()) {
                byte[] imagenDescomprimida = ImageUtility.decompressZLib(educacion.get().getLogo());
                educacion.get().setLogo(imagenDescomprimida);
                list.add(educacion.get());
                response.setEducacionList(list);
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<EducacionResponse> crear(Educacion educacion, Long personaId) {
        List<Educacion> list = new ArrayList<>();
        EducacionResponse response = new EducacionResponse();

        try {
            //Buscar persona para asociar al objeto educación
            Optional<Persona> persona = personaRepository.findById(personaId);

            if (persona.isPresent()) {
                educacion.setPersona(persona.get());
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            // Crear item
            Educacion nuevaEducacion = educacionRepository.save(educacion);
            list.add(nuevaEducacion);
            response.setEducacionList(list);
        } catch (Exception e) {
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EducacionResponse> crear(Educacion objeto) {
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<EducacionResponse> editar(Educacion educacion, Long id) {
        List<Educacion> list = new ArrayList<>();
        EducacionResponse response = new EducacionResponse();

        try {
            Optional<Educacion> educacionActualizar = educacionRepository.findById(id);

            if (educacionActualizar.isPresent()) {
                educacionActualizar.get().setTitulo(educacion.getTitulo());
                educacionActualizar.get().setInstitucion(educacion.getInstitucion());
                educacionActualizar.get().setFecha_inicio(educacion.getFecha_inicio());
                educacionActualizar.get().setFecha_fin(educacion.getFecha_fin());
                if (educacion.getLogo() != null)
                    educacionActualizar.get().setLogo(educacion.getLogo());
                Educacion educacionGuardar = educacionRepository.save(educacionActualizar.get());

                list.add(educacionGuardar);
                response.setEducacionList(list);
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<EducacionResponse> borrar(Long id) {
        EducacionResponse response = new EducacionResponse();

        try {
            Optional<Educacion> educacionBorrar = educacionRepository.findById(id);
            if (educacionBorrar.isPresent()) {
                educacionRepository.delete(educacionBorrar.get());
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
