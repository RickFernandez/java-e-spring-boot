package med.voll.api.model.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.model.endereco.EnderecoRequest;

public record MedicoEditarRequest(
        @NotNull
        Long id,
        String nome,
        String telefone,
        EnderecoRequest endereco) {
}
