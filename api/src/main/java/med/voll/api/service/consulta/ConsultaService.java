package med.voll.api.service.consulta;

import med.voll.api.domain.validations.ValidadorAgendamentoDeConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.model.consulta.ConsultaRequest;
import med.voll.api.model.consulta.ConsultaResponse;
import med.voll.api.persistence.entity.Consulta;
import med.voll.api.persistence.entity.Medico;
import med.voll.api.persistence.repository.ConsultaRepository;
import med.voll.api.persistence.repository.MedicoRepository;
import med.voll.api.persistence.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    public ConsultaResponse agendar(ConsultaRequest request) {
        if(!pacienteRepository.existsById(request.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe.");
        }

        if(request.idMedico() != null && !medicoRepository.existsById(request.idMedico())) {
            throw new ValidacaoException("Id do médico informado não existe.");
        }

        validadores.forEach(validador -> validador.validar(request));

        var medico = escolherMedico(request);
        var paciente = pacienteRepository.getReferenceById(request.idPaciente());

        if(medico == null) {
            throw new ValidacaoException("Não há médicos disponíveis para a data escolhida.");
        }

        var consulta = new Consulta(null, medico, paciente, request.data());

        consultaRepository.save(consulta);

        return new ConsultaResponse(consulta);
    }

    private Medico escolherMedico(ConsultaRequest request) {
        if(request.idMedico() != null) {
            return  medicoRepository.getReferenceById(request.idMedico());
        }

        if(request.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatório quando nenhum médico for escolhido.");
        }

        return medicoRepository.sortearMedicoLivre(request.especialidade(), request.data());
    }

}
