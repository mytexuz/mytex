package uz.enterprise.mytex.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.enterprise.mytex.enums.Status;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeStatusDto {
    @NotNull
    private Long id;
    private Status status;
}