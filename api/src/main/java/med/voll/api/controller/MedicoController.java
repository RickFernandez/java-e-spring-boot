package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.model.medico.DadosDetalhamentoMedico;
import med.voll.api.model.medico.MedicoEditarRequest;
import med.voll.api.model.medico.MedicoRequest;
import med.voll.api.model.medico.MedicoResponse;
import med.voll.api.persistence.entity.Medico;
import med.voll.api.persistence.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> cadastrar(@RequestBody @Valid MedicoRequest request, UriComponentsBuilder urlBuilder) {
        var medico = new Medico(request);
        repository.save(medico);

        var uri = urlBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<MedicoResponse>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        var page = repository.findAllByStatusTrue(paginacao).map(MedicoResponse::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/atualizar")
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> atualizar(@RequestBody @Valid MedicoEditarRequest request) {
        var medico = repository.getReferenceById(request.id());
        medico.atualizar(request);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/excluir/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @DeleteMapping("/alterar-status/{id}")
    @Transactional
    public ResponseEntity alterarStatus(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.atualizarStatus();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoMedico> buscarPorId(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

}
