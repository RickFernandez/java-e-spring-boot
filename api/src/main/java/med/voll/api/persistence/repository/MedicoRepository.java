package med.voll.api.persistence.repository;

import med.voll.api.model.especialidade.Especialidade;
import med.voll.api.model.medico.MedicoResponse;
import med.voll.api.persistence.entity.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByStatusTrue(Pageable paginacao);

    @Query("""
            select m from Medico m
            where
            m.status = true
            and
            m.especialidade = :especialidade
            and
            m.id not in(
                select c.medico.id from Consulta c
                where
                c.data = :data
            )
            order by rand()
            limit 1
            """)
    Medico sortearMedicoLivre(Especialidade especialidade, LocalDateTime data);

    @Query("""
            select m.status
            from Medico m
            where
            m.id = :id
            """)
    Boolean findAtivoById(Long id);
}
