package com.argentinaprograma.portfoliobackend.service.proyecto;

import com.argentinaprograma.portfoliobackend.model.Persona;
import com.argentinaprograma.portfoliobackend.model.Proyecto;
import com.argentinaprograma.portfoliobackend.repository.IPersonaRepository;
import com.argentinaprograma.portfoliobackend.repository.IProyectoRepository;
import com.argentinaprograma.portfoliobackend.response.ProyectoResponse;
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
public abstract class ProyectoServiceImpl implements ICurriculumService<Proyecto> {

    private final IProyectoRepository proyectoRepository;

    private final IPersonaRepository personaRepository;

    public ProyectoServiceImpl(IProyectoRepository proyectoRepository, IPersonaRepository personaRepository) {
        this.proyectoRepository = proyectoRepository;
        this.personaRepository = personaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProyectoResponse> buscar() {

        ProyectoResponse response = new ProyectoResponse();

        try {
            List<Proyecto> proyectos = (List<Proyecto>) proyectoRepository.findAll();
            for (Proyecto item : proyectos) {
                if (item.getImg() != null) {
                    byte[] imagenDescomprimida = ImageUtility.decompressZLib(item.getImg());
                    item.setImg(imagenDescomprimida);
                }
            }
            response.setProyectos(proyectos);
        } catch (Exception e) {
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProyectoResponse> buscarPorId(Long id) {

        List<Proyecto> list = new ArrayList<>();
        ProyectoResponse response = new ProyectoResponse();

        try {
            Optional<Proyecto> proyecto = proyectoRepository.findById(id);

            if (proyecto.isPresent()) {
                byte[] imagenDescomprimida = ImageUtility.decompressZLib(proyecto.get().getImg());
                proyecto.get().setImg(imagenDescomprimida);
                list.add(proyecto.get());
                response.setProyectos(list);
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
    public ResponseEntity<ProyectoResponse> crear(Proyecto proyecto, Long personaId) {
        ProyectoResponse response = new ProyectoResponse();
        List<Proyecto> list = new ArrayList<>();

        try {
            // Buscar person apara sociar al objeto Proyecto
            Optional<Persona> persona = personaRepository.findById(personaId);

            if (persona.isPresent()) {
                proyecto.setPersona(persona.get());
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            // Crear item
            Proyecto nuevoProyecto = proyectoRepository.save(proyecto);
            list.add(nuevoProyecto);
            response.setProyectos(list);
        } catch (Exception e) {
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ProyectoResponse> editar(Proyecto proyecto, Long id) {
        ProyectoResponse response = new ProyectoResponse();
        List<Proyecto> list = new ArrayList<>();

        try {
            Optional<Proyecto> proyectoActualizar = proyectoRepository.findById(id);

            if (proyectoActualizar.isPresent()) {
                proyectoActualizar.get().setNombre(proyecto.getNombre());
                proyectoActualizar.get().setDescripcion(proyecto.getDescripcion());
                proyectoActualizar.get().setUrl(proyecto.getUrl());
                if (proyecto.getImg() != null)
                    proyectoActualizar.get().setImg(proyecto.getImg());
                Proyecto proyectoGuardar = proyectoRepository.save(proyectoActualizar.get());

                list.add(proyectoGuardar);
                response.setProyectos(list);
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
    public ResponseEntity<ProyectoResponse> borrar(Long id) {
        ProyectoResponse response = new ProyectoResponse();

        try {
            Optional<Proyecto> proyectoBorrar = proyectoRepository.findById(id);
            if (proyectoBorrar.isPresent()) {
                proyectoRepository.delete(proyectoBorrar.get());
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
