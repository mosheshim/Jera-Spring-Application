package moshe.shim.jera.utils;

import lombok.val;
import moshe.shim.jera.entities.Tea;
import moshe.shim.jera.payload.TeaDTO;
import moshe.shim.jera.models.Weight;

import java.util.*;
import java.util.stream.Collectors;

public class Mappers {


    public static TeaDTO toTeaDTO(List<Tea> teaList) {
        val teaDTO = toTeaDTO(teaList.get(0));
        if (teaList.size() > 1) {
            teaDTO.setWeights(new ArrayList<>());
            for (Tea tea : teaList) {
                teaDTO.getWeights().add(new Weight(
                        tea.getWeight(),
                        tea.getPrice(),
                        tea.isInStock()));
            }
            teaDTO.getWeights().sort(Comparator.comparing(Weight::getWeight));
            teaDTO.setPrice(teaDTO.getWeights().get(0).getPrice());
            teaDTO.setInStock(teaDTO.getWeights().get(0).getInStock());
        }

        return teaDTO;
    }

    private static TeaDTO toTeaDTO(Tea tea) {
        return TeaDTO.builder()
                .id(tea.getId())
                .name(tea.getName())
                .price(tea.getPrice())
                .imageUrl(tea.getImageUrl())
                .inStock(tea.isInStock())
                .description(tea.getDescription())
                .build();
    }


    public static Set<TeaDTO> toProductSeriesDTOTeaDTO(Set<Tea> teaSet) {
        HashSet<TeaDTO> teaDTOSet = new HashSet<>();
        if (teaSet != null) {
            val teaSetGroupedByName = groupByName(teaSet);
            Set<TeaDTO> collect = teaSetGroupedByName.stream()
                    .map(Mappers::toTeaDTO)
                    .collect(Collectors.toSet());
            teaDTOSet.addAll(collect);
        }
        return teaDTOSet;
    }

    /**
     * Combines all the same tea names into one list
     *
     * @param teaSet of all the tea
     * @return A list of list<Tea> which is grouped by name
     */
    private static Set<List<Tea>> groupByName(Set<Tea> teaSet) {
        val map = new HashMap<String, List<Tea>>();
        for (Tea tea : teaSet) {
            map.putIfAbsent(tea.getName(), new ArrayList<>());
            map.get(tea.getName()).add(tea);
        }
        return new HashSet<>(map.values());
    }


}
