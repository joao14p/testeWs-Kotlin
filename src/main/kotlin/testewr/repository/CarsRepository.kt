package testewr.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

import testewr.entity.Cars
import java.util.*

@Repository
interface CarsRepository : JpaRepository<Cars?, Long?> {
   override fun findById(id: Long): Optional<Cars?>
    abstract fun findById(id: Long?): Optional<Cars?>

}