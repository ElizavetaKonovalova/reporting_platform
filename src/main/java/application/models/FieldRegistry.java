package application.models;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "field_registry", indexes = {@Index(unique = true, columnList = "field_name, type", name = "fieldRegistryIndex")})
public class FieldRegistry {

    @Id
    @Column(name ="field_id", columnDefinition = "SERIAL", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long FIELD_ID;
    @Column(length = 100) @Size(max = 100)
    private String FIELD_NAME;
    private char TYPE;
    @Column(length = 200) @Size(max = 200)
    private String FIELD_DESCRIPTION_ONE;
    @Column(length = 200) @Size(max = 200)
    private String FIELD_DESCRIPTION_TWO;
    @Column(length = 200) @Size(max = 200)
    private String FIELD_DESCRIPTION_THREE;
}
