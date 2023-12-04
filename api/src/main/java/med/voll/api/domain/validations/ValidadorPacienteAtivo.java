package med.voll.api.domain.validations;

import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.model.consulta.ConsultaRequest;
import med.voll.api.persistence.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private PacienteRepository repository;

    public void validar(ConsultaRequest request) {
        var pacienteAtivo = repository.findAtivoById(request.idPaciente());

        if(!pacienteAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada pois o paciente está inativo.");
        }
    }
}
