package testewr.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import testewr.entity.Factories
import java.util.*

@Repository
interface FactoriesRepository : JpaRepository<Factories?, Long?> {
    abstract fun findById(id: Long?): Optional<Factories?>
}