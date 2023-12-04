package med.voll.api.model.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.model.especialidade.Especialidade;

import java.time.LocalDateTime;

public record ConsultaRequest(
        Long idMedico,

        @NotNull
        Long idPaciente,

        @NotNull
        @Future
        LocalDateTime data,

        Especialidade especialidade
    ) { }
