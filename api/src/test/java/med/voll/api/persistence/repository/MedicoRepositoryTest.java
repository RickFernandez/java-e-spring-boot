package med.voll.api.persistence.repository;

import med.voll.api.model.endereco.EnderecoRequest;
import med.voll.api.model.especialidade.Especialidade;
import med.voll.api.model.medico.MedicoRequest;
import med.voll.api.model.paciente.PacienteRequest;
import med.voll.api.persistence.entity.Consulta;
import med.voll.api.persistence.entity.Medico;
import med.voll.api.persistence.entity.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deveria devolver null quando o único médico cadastrado não está disponível na data")
    void sortearMedicoLivreCenario1() {
        // given / arrange
        var proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

        var medico = cadastrarMedico("Médico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "11122233344");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        // when / act
        var medicoLivre = medicoRepository.sortearMedicoLivre(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        // then / assert
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deveria devolver médico quando ele estiver disponível")
    void sortearMedicoLivreCenario2() {
        var proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

        var medico = cadastrarMedico("Médico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

        var medicoLivre = medicoRepository.sortearMedicoLivre(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        assertThat(medicoLivre).isEqualTo(medico);
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, medico, paciente, data));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }

    private MedicoRequest dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new MedicoRequest(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private PacienteRequest dadosPaciente(String nome, String email, String cpf) {
        return new PacienteRequest(
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
    }

    private EnderecoRequest dadosEndereco() {
        return new EnderecoRequest(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}