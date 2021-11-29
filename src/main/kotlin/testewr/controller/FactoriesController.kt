package testewr.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import testewr.entity.Cars
import testewr.entity.Factories
import testewr.repository.CarsRepository
import testewr.repository.FactoriesRepository

@RestController
@RequestMapping(*["/factories"])
class FactoriesController(var repo: FactoriesRepository) {

    @GetMapping
    fun get(): ResponseEntity<MutableList<Factories?>> = ResponseEntity.ok(repo.findAll())

    @PostMapping
    fun add(@RequestBody factories: Factories): ResponseEntity<Factories> = ResponseEntity.ok(repo.save(factories))

    @PutMapping(value = ["/{id}"])
    fun update(@PathVariable("id") id: Long?, @RequestBody factories: Factories):ResponseEntity<Factories> {
        val factoriesDBOptional = repo.findById(id)
        val factoriesDB = factoriesDBOptional.orElseThrow {RuntimeException("O id: $repo não existe!") }
        val saved = factoriesDB?.let {
            repo.save(
                it.copy(id=factories.id, name=factories.name, countryCode=factories.countryCode, cars = factories.cars))
        }
        return ResponseEntity.ok(saved)
    }

    /*Por motivos de segurança(autorização/autenticação), 
	a opção de apagar carros, ficará restrito para uso apenas 
	dos perfis de 'administrador'.*/
    @DeleteMapping(value = ["/{id}"])
    @PreAuthorize("hasRole('ADMIN')")
    fun delete(@PathVariable("id") id: Long?) {
        if (id != null) {
            repo.deleteById(id)
        }
    }

    init {
        this.repo = repo
    }
}