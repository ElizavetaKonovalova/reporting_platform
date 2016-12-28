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
    @Column(name ="field_id", nullable = false, unique = true)
    @Type(type = "pg-uuid")
    private UUID FIELD_ID;

    @Column(name = "program_mod_id", insertable = false, updatable = false)
    private Long PROGRAM_MOD_ID;

    @Column(length = 100, nullable = false, unique = true)
    @Size(max = 100)
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

    /* Simple getters */
    public UUID getFIELD_ID() { return this.FIELD_ID; }
    public Long getPROGRAM_MOD_ID() { return this.PROGRAM_MOD_ID; }
    public String getFIELD_NAME() { return this.FIELD_NAME; }
    public char getTYPE() { return this.TYPE; }
    public String getFIELD_DESCRIPTION_ONE() { return this.FIELD_DESCRIPTION_ONE; }
    public String getFIELD_DESCRIPTION_TWO() { return this.FIELD_DESCRIPTION_TWO; }
    public String getFIELD_DESCRIPTION_THREE() { return this.FIELD_DESCRIPTION_THREE; }
    public Programs getPROGRAM() { return this.PROGRAM; }
    public List<TextData> getTEXT_RESPONSES() { return this.TEXT_RESPONSES; }
    public List<NumberData> getNUMBER_RESPONSES() { return this.NUMBER_RESPONSES; }
    public List<DemoData> getDEMO_RESPONSES() { return this.DEMO_RESPONSES; }

    /* Simple setters */
    public void setFIELD_ID(UUID field_id) { this.FIELD_ID = field_id; }
    public void setPROGRAM_MOD_ID(Long program_mod_id) {this.PROGRAM_MOD_ID = program_mod_id; }
    public void setFIELD_NAME(String field_name) { this.FIELD_NAME = field_name; }
    public void setTYPE(String type) {this.TYPE = type.charAt(0); }
    public void setFIELD_DESCRIPTION_ONE(String field_description_one) { this.FIELD_DESCRIPTION_ONE = field_description_one; }
    public void setFIELD_DESCRIPTION_TWO(String field_description_two) { this.FIELD_DESCRIPTION_TWO = field_description_two; }
    public void setFIELD_DESCRIPTION_THREE(String field_description_three) { this.FIELD_DESCRIPTION_THREE = field_description_three; }
    public void setPROGRAM(Programs program) { this.PROGRAM = program; }
    public void setTEXT_RESPONSES(TextData text_responses) { this.TEXT_RESPONSES.add(text_responses); }
    public void setNUMBER_RESPONSES(NumberData number_responses) { this.NUMBER_RESPONSES.add(number_responses); }
    public void setDEMO_RESPONSES(DemoData demo_responses) { this.DEMO_RESPONSES.add(demo_responses); }
}
