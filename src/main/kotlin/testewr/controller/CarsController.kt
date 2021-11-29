package testewr.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import testewr.entity.Cars
import testewr.repository.CarsRepository
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@RestController
@RequestMapping(*["/cars"])
class CarsController(var repo: CarsRepository) {

    @GetMapping
    fun get(): ResponseEntity<MutableList<Cars?>> = ResponseEntity.ok(repo.findAll())


    @PostMapping
    fun add(@RequestBody cars: Cars):ResponseEntity<Cars> = ResponseEntity.ok(repo.save(cars))


    @PutMapping(value = ["/{id}"])
    fun update(@PathVariable("id") id: Long?, @RequestBody cars: Cars):ResponseEntity<Cars> {
        val carsDBOptional = repo.findById(id)
        val carsDB = carsDBOptional.orElseThrow {RuntimeException("O id: $repo não existe!") }
        val saved = carsDB?.let {
            repo.save(
                it.copy(id=cars.id, factory_id = cars.factory_id, model =cars.model,
            year= cars.year, fuel=cars.fuel, doors=cars.doors, cost=cars.cost, color=cars.color))
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

    //Metodo para fazer upload de arquivos para o sistema.
    @RequestMapping(
        value = ["/upload"],
        method = [RequestMethod.POST],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    @Throws(
        IOException::class
    )
    fun uploadFile(@RequestParam file: MultipartFile): ResponseEntity<Any> {
        val convertFile =
            File("C:\\Users\\JP\\Downloads\\teste-wr\\teste-wr\\src\\main\\kotlin\\testewr\\planilhas" + file.getOriginalFilename())
        convertFile.createNewFile()
        val fout = FileOutputStream(convertFile)
        fout.write(file.bytes)
        fout.close()
        return ResponseEntity<Any>("File is uploaded successfully", HttpStatus.OK)
    }

    init {
        this.repo = repo
    }
}