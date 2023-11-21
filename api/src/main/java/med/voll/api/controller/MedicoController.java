package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.model.medico.MedicoEditarRequest;
import med.voll.api.model.medico.MedicoRequest;
import med.voll.api.model.medico.MedicoResponse;
import med.voll.api.persistence.entity.Medico;
import med.voll.api.persistence.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping("/cadastrar")
    @Transactional
    public void cadastrar(@RequestBody @Valid MedicoRequest request) {
        repository.save(new Medico(request));
    }

    @GetMapping("/listar")
    public Page<MedicoResponse> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        return repository.findAllByStatusTrue(paginacao).map(MedicoResponse::new);
    }

    @PutMapping("/atualizar")
    @Transactional
    public void atualizar(@RequestBody @Valid MedicoEditarRequest request) {
        var medico = repository.getReferenceById(request.id());
        medico.atualizar(request);
    }

    @DeleteMapping("/excluir/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @DeleteMapping("/alterar-status/{id}")
    @Transactional
    public void alterarStatus(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.atualizarStatus();
    }

}
