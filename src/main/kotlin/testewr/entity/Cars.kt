package testewr.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Cars (
    @Id
    var id: Long? = null,

    @Column(name = "factory_id")
    var factory_id: Long? = null,

    @Column(name = "model")
    var model: String? = null,

    @Column(name = "year")
    var year: Long? = null,

    @Column(name = "fuel")
    var fuel: String? = null,

    @Column(name = "doors")
    var doors: Int = 0,

    @Column(name = "cost")
    var cost: Double? = null,

    @Column(name = "color")
    var color: String? = null
)