package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.model.paciente.DadosDetalhamentoPaciente;
import med.voll.api.model.paciente.PacienteEditarRequest;
import med.voll.api.model.paciente.PacienteRequest;
import med.voll.api.model.paciente.PacienteResponse;
import med.voll.api.persistence.entity.Paciente;
import med.voll.api.persistence.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid PacienteRequest dados, UriComponentsBuilder uriBuilder) {
        var paciente = new Paciente(dados);
        repository.save(paciente);

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<PacienteResponse>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(PacienteResponse::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/atualizar")
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid PacienteEditarRequest dados) {
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizar(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/excluir/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }


}