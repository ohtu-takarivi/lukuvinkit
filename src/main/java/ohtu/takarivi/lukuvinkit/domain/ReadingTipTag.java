package ohtu.takarivi.lukuvinkit.domain;

import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ReadingTipTag extends AbstractPersistable<Long> {
    private String name;
    
    public ReadingTipTag(String name) {
        super();
        this.name = name;
    }
}
