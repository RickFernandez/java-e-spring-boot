package med.voll.api.persistence.repository;

import med.voll.api.model.medico.MedicoResponse;
import med.voll.api.persistence.entity.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByStatusTrue(Pageable paginacao);
}
