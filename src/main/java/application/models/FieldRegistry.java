package application.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "field_registry", indexes = {@Index(unique = true, columnList = "PROGRAM_MOD_ID, field_name, type", name = "fieldRegistryIndex")})
public class FieldRegistry {

    @Id
    @Column(name ="field_id", nullable = false)
    @Type(type = "pg-uuid")
    private UUID FIELD_ID;

    @Column(name = "program_mod_id", insertable = false, updatable = false)
    private Long PROGRAM_MOD_ID;

    @Column(length = 100) @Size(max = 100)
    private String FIELD_NAME;

    private char TYPE;

    @Column(length = 200) @Size(max = 200)
    private String FIELD_DESCRIPTION_ONE;

    @Column(length = 200) @Size(max = 200)
    private String FIELD_DESCRIPTION_TWO;

    @Column(length = 200) @Size(max = 200)
    private String FIELD_DESCRIPTION_THREE;

    @ManyToOne(optional = false, targetEntity = Programs.class,
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="PROGRAM_MOD_ID", referencedColumnName = "db_id")
    private Programs PROGRAM;

    @OneToMany(mappedBy = "REGISRTY", targetEntity = TextData.class)
    private List<TextData> TEXT_RESPONSES;

    @OneToMany(mappedBy = "REGISRTY", targetEntity = NumberData.class)
    private List<NumberData> NUMBER_RESPONSES;

    @OneToMany(mappedBy = "REGISRTY", targetEntity = DemoData.class)
    private List<DemoData> DEMO_RESPONSES;
}
