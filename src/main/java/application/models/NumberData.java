package application.models;

import javax.persistence.*;

/**
 * Created by ekonovalova on 08-Dec-16.
 */

@Entity
@Table(name = "number_data", indexes = {@Index(unique = true, columnList = "field_name", name = "numbdataIndex")})
public class NumberData {

    @Id
    @Column(name ="field_id", columnDefinition = "SERIAL", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long FIELD_ID;
    private String FIELD_NAME;
}
