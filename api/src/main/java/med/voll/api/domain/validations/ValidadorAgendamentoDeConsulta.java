package med.voll.api.domain.validations;

import med.voll.api.model.consulta.ConsultaRequest;

public interface ValidadorAgendamentoDeConsulta {
    void validar(ConsultaRequest request);
}
