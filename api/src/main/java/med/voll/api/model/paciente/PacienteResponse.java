package med.voll.api.model.paciente;

import med.voll.api.persistence.entity.Paciente;

public record PacienteResponse(Long id, String nome, String email, String cpf) {

    public PacienteResponse(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }

}