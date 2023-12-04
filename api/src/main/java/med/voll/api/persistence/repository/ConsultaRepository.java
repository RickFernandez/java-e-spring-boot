package med.voll.api.persistence.repository;

import med.voll.api.persistence.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository  extends JpaRepository<Consulta, Long> {
    boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime data);

    boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);
}
