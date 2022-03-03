package moshe.shim.jera.payload;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TeaProductSeriesDTO {

    private long id;

    @NotNull
    private String name;

    protected Date uploadDate;

    @NotNull
    private String description;

    @Builder.Default
    private String imageUrl = "default url";

    @NotNull
    private String prices;

    @NotNull
    private Boolean isTeaBag;

    private List<TeaDTO> teaList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeaProductSeriesDTO that = (TeaProductSeriesDTO) o;

        if (id == that.id) return true;
        if (!name.equals(that.name)) return false;
        if (!description.equals(that.description)) return false;
        if (!imageUrl.equals(that.imageUrl)) return false;
        if (!prices.equals(that.prices)) return false;
        if (!isTeaBag.equals(that.isTeaBag)) return false;
        return Objects.equals(teaList, that.teaList);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + imageUrl.hashCode();
        result = 31 * result + prices.hashCode();
        result = 31 * result + isTeaBag.hashCode();
        result = 31 * result + (teaList != null ? teaList.hashCode() : 0);
        return result;
    }
}
