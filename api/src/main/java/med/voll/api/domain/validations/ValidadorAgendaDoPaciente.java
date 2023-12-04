package med.voll.api.domain.validations;

import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.model.consulta.ConsultaRequest;
import med.voll.api.persistence.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorAgendaDoPaciente implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(ConsultaRequest request){
        var primeiroHorario = request.data().withHour(7);
        var ultimoHorario = request.data().withHour(18);
        var pacientePossuiOutraConsultaNoDia = repository.existsByPacienteIdAndDataBetween(request.idPaciente(), primeiroHorario, ultimoHorario);

        if(pacientePossuiOutraConsultaNoDia) {
            throw new ValidacaoException("Paciente já possui uma consulta agendada neste dia.");
        }
    }
}
