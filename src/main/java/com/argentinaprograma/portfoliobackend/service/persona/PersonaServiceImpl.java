package com.argentinaprograma.portfoliobackend.service.persona;

import com.argentinaprograma.portfoliobackend.model.Persona;
import com.argentinaprograma.portfoliobackend.repository.IPersonaRepository;
import com.argentinaprograma.portfoliobackend.response.PersonaResponse;
import com.argentinaprograma.portfoliobackend.service.ICurriculumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public abstract class PersonaServiceImpl implements ICurriculumService<Persona> {

    private final IPersonaRepository personaRepository;

    public PersonaServiceImpl(IPersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<PersonaResponse> buscar() {

        PersonaResponse response = new PersonaResponse();

        try {
            List<Persona> personaList = (List<Persona>)personaRepository.findAll();
            response.setPersonas(personaList);
            //response.setMetadata("OK", "00", "Salió todo bien");
        } catch (Exception e) {
            //response.setMetadata("Error", "-1", "Error al consultar");
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<PersonaResponse> buscarPorId(Long id) {

        List<Persona> list = new ArrayList<>();
        PersonaResponse response = new PersonaResponse();

        try{

            Optional<Persona> persona = personaRepository.findById(id);

            if (persona.isPresent()) {
                list.add(persona.get());
                response.setPersonas(list);
                //response.setMetadata("OK", "00", "Persona encontrada");
            } else {
                //response.setMetadata("Error", "-1", "Persona no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {

            //response.setMetadata("Error", "-1", "Error al consultar por id");
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<PersonaResponse> crear(Persona persona) {

        List<Persona> list = new ArrayList<>();
        PersonaResponse response = new PersonaResponse();

        try{

            Persona personaCreada = personaRepository.save(persona);
            list.add(personaCreada);
            response.setPersonas(list);
            //response.setMetadata("OK", "00", "Persona creada");

        } catch (Exception e) {

            //response.setMetadata("Error", "-1", "Error al grabar persona");
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<PersonaResponse> editar(Persona persona, Long id) {
        List<Persona> list = new ArrayList<>();
        PersonaResponse response = new PersonaResponse();

        try{

            Optional<Persona> buscarPersona = personaRepository.findById(id);

            if (buscarPersona.isPresent()) {
                // Se actualiza el registro
                buscarPersona.get().setNombre(persona.getNombre());
                buscarPersona.get().setApellido(persona.getApellido());
                buscarPersona.get().setPerfil(persona.getPerfil());
                buscarPersona.get().setTitulo(persona.getTitulo());
                buscarPersona.get().setTrabajo_actual(persona.getTrabajo_actual());

                Persona personaAActualizar = personaRepository.save(buscarPersona.get());

                list.add(personaAActualizar);
                response.setPersonas(list);
                //response.setMetadata("OK", "00", "Persona actualizada");

            } else {
                //response.setMetadata("Error", "-1", "Persona no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {

            //response.setMetadata("Error", "-1", "Error al actualizar persona");
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<PersonaResponse> borrar(Long id) {
        PersonaResponse response = new PersonaResponse();

        /*try{

            personaDao.deleteById(id);
            response.setMetadata("OK", "00", "Registro eliminado");

        } catch (Exception e) {

            response.setMetadata("Error", "-1", "Error al eliminar");
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }*/

        try {
            Optional<Persona> personaBorrar = personaRepository.findById(id);
            if (personaBorrar.isPresent()) {
                personaRepository.delete(personaBorrar.get());
                //response.setMetadata("OK", "00", "Registro eliminado");
            } else {
                //response.setMetadata("Error", "-1", "No existe registro");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            //response.setMetadata("Error", "-2", "Ocurrió un error al eliminar el registro");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
