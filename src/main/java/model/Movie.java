package model;

import lombok.Getter;
import lombok.Setter;
import model.support.BusinessException;
import model.support.WithCode;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toUnmodifiableList;
import static model.support.ErrorCode.ERROR_NOT_FOUND_MOVIE;

@Schema(
        description = "Movie."
)
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "movie_uk", columnNames = {"code"}),
})
@Getter
@Setter
public class Movie extends WithCode {
    @Column(length = 100)
    public String title;

    @Column(length = 5000)
    public String description;

    public static List<Movie> listByCodes(List<Movie> passed) {
        return find("code in ?1", collectCodes(passed)).list();
    }

    private static List<String> collectCodes(List<Movie> passed) {
        return passed.stream().map(WithCode::getCode).collect(toUnmodifiableList());
    }

    public static Movie findByCode(String code) {
        return find("code = ?1", code).firstResult();
    }

    public static Movie getByCode(String code) {
        return get(findByCode(code), code);
    }

    public static Movie getWithCode(List<Movie> passed, String code) {
        Optional<Movie> optional = passed.stream().filter(p -> p.getCode().equals(code)).findFirst();

        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new BusinessException(ERROR_NOT_FOUND_MOVIE, code, null);
        }
    }

    private static Movie get(Movie movie, String extra) {
        if (movie == null) {
            throw new BusinessException(ERROR_NOT_FOUND_MOVIE, extra, null);
        }

        return movie;
    }

}
