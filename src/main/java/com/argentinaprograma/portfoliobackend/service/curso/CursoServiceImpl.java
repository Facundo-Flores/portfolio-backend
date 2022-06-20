package com.argentinaprograma.portfoliobackend.service.curso;

import com.argentinaprograma.portfoliobackend.model.Curso;
import com.argentinaprograma.portfoliobackend.model.Persona;
import com.argentinaprograma.portfoliobackend.repository.ICursoRepository;
import com.argentinaprograma.portfoliobackend.repository.IPersonaRepository;
import com.argentinaprograma.portfoliobackend.response.CursoResponse;
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
public class CursoServiceImpl implements ICurriculumService<Curso> {

    private final ICursoRepository cursoRepository;

    private final IPersonaRepository personaRepository;

    public CursoServiceImpl(ICursoRepository cursoRepository, IPersonaRepository personaRepository) {
        this.cursoRepository = cursoRepository;
        this.personaRepository = personaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CursoResponse> buscar() {
        CursoResponse response = new CursoResponse();

        try {
            List<Curso> cursos = (List<Curso>)cursoRepository.findAll();
            for (Curso item: cursos) {
                if (item.getLogo() != null) {
                    byte[] imagenDescomprimida = ImageUtility.decompressZLib(item.getLogo());
                    item.setLogo(imagenDescomprimida);
                }
            }
            response.setCursos(cursos);
        } catch (Exception e) {
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CursoResponse> buscarPorId(Long id) {

        List<Curso> list = new ArrayList<>();
        CursoResponse response = new CursoResponse();

        try {
            Optional<Curso> curso = cursoRepository.findById(id);
            if (curso.isPresent()) {
                byte[] imagenDescomprimida = ImageUtility.decompressZLib(curso.get().getLogo());
                curso.get().setLogo(imagenDescomprimida);
                list.add(curso.get());
                response.setCursos(list);
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
    public ResponseEntity<CursoResponse> crear(Curso curso, Long personaId) {

        CursoResponse response = new CursoResponse();
        List<Curso> list = new ArrayList<>();

        try {
            //Buscar persona para asociar al objeto curso
            Optional<Persona> persona = personaRepository.findById(personaId);

            if (persona.isPresent()) {
                curso.setPersona(persona.get());
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            // Crear curso
            Curso nuevoCurso = cursoRepository.save(curso);
            list.add(nuevoCurso);
            response.setCursos(list);
        } catch (Exception e) {
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CursoResponse> crear(Curso objeto) {
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<CursoResponse> editar(Curso curso, Long id) {
        CursoResponse response = new CursoResponse();
        List<Curso> list = new ArrayList<>();

        try {
            Optional<Curso> cursoActualizar = cursoRepository.findById(id);

            if (cursoActualizar.isPresent()) {
                cursoActualizar.get().setNombre_curso(curso.getNombre_curso());
                cursoActualizar.get().setInstitucion(curso.getInstitucion());
                cursoActualizar.get().setCarga_horaria(curso.getCarga_horaria());
                cursoActualizar.get().setPeriodo(curso.getPeriodo());
                if (curso.getLogo() != null)
                    cursoActualizar.get().setLogo(curso.getLogo());
                Curso cursoGuardar = cursoRepository.save(cursoActualizar.get());

                list.add(cursoGuardar);
                response.setCursos(list);
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
    public ResponseEntity<CursoResponse> borrar(Long id) {
        CursoResponse response = new CursoResponse();

        try {
            Optional<Curso> cursoBorrar = cursoRepository.findById(id);
            if (cursoBorrar.isPresent()) {
                cursoRepository.delete(cursoBorrar.get());
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
