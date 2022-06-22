package com.argentinaprograma.portfoliobackend.service.habilidad;

import com.argentinaprograma.portfoliobackend.model.Habilidad;
import com.argentinaprograma.portfoliobackend.model.Persona;
import com.argentinaprograma.portfoliobackend.repository.IHabilidadRepository;
import com.argentinaprograma.portfoliobackend.repository.IPersonaRepository;
import com.argentinaprograma.portfoliobackend.response.HabilidadResponse;
import com.argentinaprograma.portfoliobackend.service.ICurriculumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HabilidadServiceImpl implements ICurriculumService<Habilidad> {

    private final IHabilidadRepository habilidadRepository;

    private final IPersonaRepository personaRepository;

    public HabilidadServiceImpl(IHabilidadRepository habilidadRepository, IPersonaRepository personaRepository) {
        this.habilidadRepository = habilidadRepository;
        this.personaRepository = personaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<HabilidadResponse> buscar() {

        HabilidadResponse response = new HabilidadResponse();

        try {
            List<Habilidad> habilidades = (List<Habilidad>) habilidadRepository.findAll();
            if (habilidades.size() > 0) {
                response.setHabilidades(habilidades);
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
    @Transactional(readOnly = true)
    public ResponseEntity<HabilidadResponse> buscarPorId(Long id) {

        List<Habilidad> list = new ArrayList<>();
        HabilidadResponse response = new HabilidadResponse();

        try {
            Optional<Habilidad> habilidad = habilidadRepository.findById(id);

            if (habilidad.isPresent()) {
                list.add(habilidad.get());
                response.setHabilidades(list);
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
    public ResponseEntity<HabilidadResponse> crear(Habilidad habilidad, Long personaId) {
        HabilidadResponse response = new HabilidadResponse();
        List<Habilidad> list = new ArrayList<>();

        try {
            //Buscar persona para asociar al objeto habilidad
            Optional<Persona> persona = personaRepository.findById(personaId);

            if (persona.isPresent()) {
                habilidad.setPersona(persona.get());
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // Crear habilidad
            Habilidad nuevaHabilidad = habilidadRepository.save(habilidad);
            list.add(nuevaHabilidad);
            response.setHabilidades(list);
        } catch (Exception e) {
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity crear(Habilidad objeto) {
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<HabilidadResponse> editar(Habilidad habilidad, Long id) {
        HabilidadResponse response = new HabilidadResponse();
        List<Habilidad> list = new ArrayList<>();

        try {
            Optional<Habilidad> habilidadActualizar = habilidadRepository.findById(id);

            if (habilidadActualizar.isPresent()) {
                habilidadActualizar.get().setNombre(habilidad.getNombre());
                habilidadActualizar.get().setDescripcion(habilidad.getDescripcion());
                habilidadActualizar.get().setNivel(habilidad.getNivel());

                Habilidad habilidadGuardar = habilidadRepository.save(habilidadActualizar.get());

                list.add(habilidadGuardar);
                response.setHabilidades(list);
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
    public ResponseEntity<HabilidadResponse> borrar(Long id) {
        HabilidadResponse response = new HabilidadResponse();

        try {
            Optional<Habilidad> habilidadBorrar = habilidadRepository.findById(id);
            if (habilidadBorrar.isPresent()) {
                habilidadRepository.delete(habilidadBorrar.get());
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
