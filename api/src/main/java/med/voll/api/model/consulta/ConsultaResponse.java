package med.voll.api.model.consulta;

import med.voll.api.persistence.entity.Consulta;

import java.time.LocalDateTime;

public record ConsultaResponse(Long id, Long idMedico, Long idPaciente, LocalDateTime data) {
    public ConsultaResponse(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
    }
}
