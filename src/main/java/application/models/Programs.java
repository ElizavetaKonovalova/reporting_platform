package application.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "programs", indexes = {@Index(columnList = "program_name, module_name", name = "programsIndex")})
public class Programs {

    @Id
    @Column(name ="db_id", columnDefinition = "SERIAL", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DB_ID;
    @Column(length = 100, nullable = false)
    @Size(max = 100)
    private String PROGRAM_NAME;
    @Column(length = 100, nullable = false)
    @Size(max = 100)
    private String MODULE_NAME;
    @OneToMany(mappedBy = "PROGRAM", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, targetEntity = FieldRegistry.class)
    private List<FieldRegistry> FIELDS;

    /* Simple getters */
    public Long getDB_ID() { return this.DB_ID; }
    public String getPROGRAM_NAME() { return this.PROGRAM_NAME; }
    public String getMODULE_NAME() { return this.MODULE_NAME; }

    /* Simple setters */
    public void setPROGRAM_NAME( String program_name ) { this.PROGRAM_NAME = program_name; }
    public void setMODULE_NAME( String module_name ) { this.MODULE_NAME = module_name; }
    public void setDB_ID( Long db_id ) { this.DB_ID = db_id; }
}
