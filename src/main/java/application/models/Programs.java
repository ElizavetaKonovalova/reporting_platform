package application.models;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "programs", indexes = {@Index(columnList = "field_id, program_name, module_name", name = "programsIndex")})
public class Programs {

    @Id
    @Column(name ="db_id", columnDefinition = "SERIAL", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DB_ID;
    @Column(insertable = false, updatable = false)
    private Long FIELD_ID;
    @Column(length = 100) @Size(max = 100)
    private String PROGRAM_NAME;
    @Column(length = 100) @Size(max = 100)
    private String MODULE_NAME;

    /* Simple getters */
    public Long getDB_ID() { return this.DB_ID; }
    public Long getFIELD_ID() { return this.FIELD_ID; }
    public String getPROGRAM_NAME() { return this.PROGRAM_NAME; }
    public String getMODULE_NAME() { return this.MODULE_NAME; }

    /* Simple setters */
    public void setFIELD_ID( Long field_id ) { this.FIELD_ID = field_id; }
    public void setPROGRAM_NAME( String program_name ) { this.PROGRAM_NAME = program_name; }
    public void setMODULE_NAME( String module_name ) { this.MODULE_NAME = module_name; }
    public void setDB_ID( Long db_id ) { this.DB_ID = db_id; }
}
