package med.voll.api.model.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.model.endereco.EnderecoRequest;

public record PacienteEditarRequest(
        @NotNull
        Long id,
        String nome,
        String telefone,
        EnderecoRequest endereco) {
}
