package med.voll.api.domain.validations;

import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.model.consulta.ConsultaRequest;
import med.voll.api.persistence.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorAgendaDoMedico implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(ConsultaRequest request) {
        var agendaLivre = repository.existsByMedicoIdAndData(request.idMedico(), request.data());

        if(agendaLivre) {
            throw new ValidacaoException("Médico já possui outra consulta agendada para este mesmo horário.");
        }
    }
}
