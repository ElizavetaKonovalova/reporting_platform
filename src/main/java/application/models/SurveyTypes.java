package application.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table( name = "SurveyTypes", indexes = {@Index(unique = true, name = "surveytypesIndex", columnList = "type_name")})
public class SurveyTypes {

    @Id
    @Column(name ="surveytype_id", columnDefinition = "SERIAL", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long SURVEYTYPE_ID;
    private String SUBTYPE_NAME;
    private String TYPE_NAME;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "SURVEY_SUBTYPEID")
    private List<Jobs> JOBS;

    /* Simple getters for the properties */
    public Long getSURVEYTYPE_ID() { return this.SURVEYTYPE_ID; }
    public String getSUBTYPE_NAME() { return this.SUBTYPE_NAME; }
    public String getTYPE_NAME() { return this.TYPE_NAME; }

    /* Simple setters for the properties */
    public void setSURVEYTYPE_ID(Long id) { this.SURVEYTYPE_ID = id; }
    public void setSUBTYPE_NAME(String subtypename) { this.SUBTYPE_NAME = subtypename; }
    public void setTYPE_NAME(String typename) { this.TYPE_NAME = typename; }
}
