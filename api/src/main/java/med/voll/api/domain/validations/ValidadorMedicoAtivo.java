package med.voll.api.domain.validations;

import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.model.consulta.ConsultaRequest;
import med.voll.api.persistence.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private MedicoRepository repository;

    public void validar(ConsultaRequest request) {
        if(request.idMedico() == null) {
            return;
        }

        var medicoAtivo = repository.findAtivoById(request.idMedico());

        if(!medicoAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com um médico inativo.");
        }
    }
}
