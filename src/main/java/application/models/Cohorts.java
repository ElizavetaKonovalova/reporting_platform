package application.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "cohorts", indexes = {@Index(columnList = "parent_id", name = "cohortIndex")})
public class Cohorts {

    @Id
    @Column(name ="cohort_id", columnDefinition = "SERIAL", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long COHORT_ID;

    @Column(length = 300) @Size(max = 300)
    private String DESCRIPTION;

    @Column(length = 100) @Size(max = 100)
    private String COHORT_NAME;

    private Long PARENT_ID;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            targetEntity = DemoData.class)
    @JoinColumn(name = "cohort_id", referencedColumnName = "demo_cohort_id")
    private List<DemoData> DEMO_RESPONSES;

    /* Simple getters */
    public Long getCOHORT_ID() { return this.COHORT_ID; }
    public Long getPARENT_ID() { return this.PARENT_ID; }
    public String getDESCRIPTION() { return this.DESCRIPTION; }
    public String getCOHORT_NAME() { return this.COHORT_NAME; }

    /* Simple setters */
    public void setCOHORT_ID( Long cohort_id ) { this.COHORT_ID = cohort_id;}
    public void setDESCRIPTION( String description ) { this.DESCRIPTION = description; }
    public void setCOHORT_NAME( String cohort_name ) { this.COHORT_NAME = cohort_name; }
    public void setPARENT_ID( Long parent_id ) { this.PARENT_ID = parent_id; }
}
