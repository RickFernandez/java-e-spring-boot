package med.voll.api.domain.validations;

import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.model.consulta.ConsultaRequest;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsulta {

    public void validar(ConsultaRequest request) {
        var dataConsulta = request.data();
        var dataAtual = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(dataAtual, dataConsulta).toMinutes();

        if(diferencaEmMinutos < 30) {
            throw new ValidacaoException("Consulta deve ser agendada com antecedência mínima de 30 minutos");
        }
    }
}
