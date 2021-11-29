package testewr.entity

import javax.persistence.*


@Table(name = "factories")
@Entity
data class Factories (
    @Id
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "name")
    var name: String? = null,

    @Column(name = "country_code")
    var countryCode: Long? = null,

    @OneToMany
    @JoinColumn(name = "factory_id")
    var cars: List<Cars>? = null
)
