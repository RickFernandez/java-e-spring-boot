package med.voll.api.model.medico;

import med.voll.api.model.especialidade.Especialidade;
import med.voll.api.persistence.entity.Medico;

public record MedicoResponse(Long id, String nome, String email, String crm, Especialidade especialidade) {
    public MedicoResponse(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
}
