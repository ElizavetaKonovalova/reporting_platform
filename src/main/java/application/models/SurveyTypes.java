package application.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;

@Entity
@Table( name = "SurveyTypes", indexes = {@Index(name = "surveytypesIndex", columnList = "subtype_name, type_name")})
public class SurveyTypes {

    @Id
    @Column(name ="survey_type_id", columnDefinition = "SERIAL", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long SURVEYTYPE_ID;
    @Column(nullable = false, length = 100)
    @Size(max = 100)
    private String SUBTYPE_NAME;
    @Column(nullable = false, length = 100)
    @Size(max = 100)
    private String TYPE_NAME;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "SURVEY_SUBTYPEID")
    private List<Jobs> JOBS;
    @Column(nullable = false)
    private Date DATE_MODIFIED;

    /* Simple getters for the properties */
    public Long getSURVEYTYPE_ID() { return this.SURVEYTYPE_ID; }
    public String getSUBTYPE_NAME() { return this.SUBTYPE_NAME; }
    public String getTYPE_NAME() { return this.TYPE_NAME; }
    public Date getDATE_MODIFIED() { return this.DATE_MODIFIED; }

    /* Simple setters for the properties */
    public void setSURVEYTYPE_ID(Long id) { this.SURVEYTYPE_ID = id; }
    public void setSUBTYPE_NAME(String subtypename) { this.SUBTYPE_NAME = subtypename; }
    public void setTYPE_NAME(String typename) { this.TYPE_NAME = typename; }
    public void setDATE_MODIFIED(Date date_modified) { this.DATE_MODIFIED = date_modified; }
}
