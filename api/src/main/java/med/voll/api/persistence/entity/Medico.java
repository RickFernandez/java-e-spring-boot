package med.voll.api.persistence.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.model.especialidade.Especialidade;
import med.voll.api.model.medico.MedicoEditarRequest;
import med.voll.api.model.medico.MedicoRequest;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    private Boolean status;

    public Medico(MedicoRequest medico) {
        this.nome = medico.nome();
        this.email = medico.email();
        this.telefone = medico.telefone();
        this.crm = medico.crm();
        this.especialidade = medico.especialidade();
        this.endereco = new Endereco(medico.endereco());
        this.status = true;
    }

    public void atualizar(MedicoEditarRequest medico) {
        if (medico.nome() != null) {
            this.nome = medico.nome();
        }
        if (medico.telefone() != null) {
            this.telefone = medico.telefone();
        }
        if (medico.endereco() != null) {
            this.endereco.atualizarEndereco(medico.endereco());
        }
    }

    public void atualizarStatus() {
        this.status = false;
    }
}
