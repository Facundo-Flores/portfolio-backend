package com.argentinaprograma.portfoliobackend.service.experiencia;

import com.argentinaprograma.portfoliobackend.model.Experiencia;
import com.argentinaprograma.portfoliobackend.model.Persona;
import com.argentinaprograma.portfoliobackend.repository.IExperienciaRepository;
import com.argentinaprograma.portfoliobackend.repository.IPersonaRepository;
import com.argentinaprograma.portfoliobackend.response.ExperienciaResponse;
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
public class ExperienciaServiceImpl implements ICurriculumService<Experiencia> {

    private final IExperienciaRepository experienciaRepository;
    private final IPersonaRepository personaRepository;

    public ExperienciaServiceImpl(IExperienciaRepository experienciaRepository, IPersonaRepository personaRepository) {
        this.experienciaRepository = experienciaRepository;
        this.personaRepository = personaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ExperienciaResponse> buscar() {

        ExperienciaResponse response = new ExperienciaResponse();

        try {
            List<Experiencia> experiencias = (List<Experiencia>) experienciaRepository.findAll();
            for (Experiencia experiencia : experiencias) {
                if (experiencia.getLogo() != null) {
                    byte[] imagenDescomprimida = ImageUtility.decompressZLib(experiencia.getLogo());
                    experiencia.setLogo(imagenDescomprimida);
                }
            }
            response.setExperiencias(experiencias);
        } catch (Exception e) {
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Búsqueda de experiencia a través del id de la misma.
     * Devuelve una response a modo de lista de experiencias, junto con un Http.Status
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ExperienciaResponse> buscarPorId(Long id) {

        List<Experiencia> list = new ArrayList<>();
        ExperienciaResponse response = new ExperienciaResponse();

        try {
            Optional<Experiencia> experiencia = experienciaRepository.findById(id);

            if (experiencia.isPresent()) {
                byte[] imagenDescomprimida = ImageUtility.decompressZLib(experiencia.get().getLogo());
                experiencia.get().setLogo(imagenDescomprimida);
                list.add(experiencia.get());
                response.setExperiencias(list);
            } else
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ExperienciaResponse> crear(Experiencia experiencia, Long personaId) {

        ExperienciaResponse response = new ExperienciaResponse();
        List<Experiencia> list = new ArrayList<>();

        try {
            //Buscar persona para asociar al objeto experiencia
            Optional<Persona> persona = personaRepository.findById(personaId);

            if (persona.isPresent())
                experiencia.setPersona(persona.get());
            else
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

            //Crear experiencia
            Experiencia nuevaExperiencia = experienciaRepository.save(experiencia);
            list.add(nuevaExperiencia);
            response.setExperiencias(list);
        } catch (Exception e) {
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity crear(Experiencia objeto) {
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<ExperienciaResponse> editar(Experiencia experiencia, Long id) {
        ExperienciaResponse response = new ExperienciaResponse();
        List<Experiencia> list = new ArrayList<>();
        System.out.println(experiencia);
        System.out.println(id);

        try {
            Optional<Experiencia> experienciaActualizar = experienciaRepository.findById(id);

            if (experienciaActualizar.isPresent()) {
                experienciaActualizar.get().setPuesto(experiencia.getPuesto());
                experienciaActualizar.get().setDescripcion(experiencia.getDescripcion());
                experienciaActualizar.get().setEmpresa(experiencia.getEmpresa());
                experienciaActualizar.get().setFecha_inicio(experiencia.getFecha_inicio());
                experienciaActualizar.get().setFecha_fin(experiencia.getFecha_fin());
                if (experiencia.getLogo() != null)
                    experienciaActualizar.get().setLogo(experiencia.getLogo());
                System.out.println(experienciaActualizar.get());

                experienciaRepository.save(experienciaActualizar.get());

                list.add(experienciaActualizar.get());
                response.setExperiencias(list);
                System.out.println(response.getExperiencias());
                //response.setMetadata("OK", "00", "Experiencia actualizada");
            } else {
                //response.setMetadata("Error", "-1", "No existe experiencia");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.getStackTrace();
            //response.setMetadata("Error", "-1", "Error al editar experiencia");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ExperienciaResponse> borrar(Long id) {
        ExperienciaResponse response = new ExperienciaResponse();

        try {
            Optional<Experiencia> experienciaBorrar = experienciaRepository.findById(id);
            if (experienciaBorrar.isPresent()) {
                experienciaRepository.delete(experienciaBorrar.get());
            } else
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
