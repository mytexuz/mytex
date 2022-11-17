package uz.enterprise.mytex.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import static uz.enterprise.mytex.constant.TableNames.TB_PREFINANCE;
import uz.enterprise.mytex.entity.audit.Auditable;
import uz.enterprise.mytex.enums.Status;

@Entity
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TB_PREFINANCE)
public class PreFinance extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prefinance_number")
    private String prefinanceNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private Model model;

    @Column(name = "primary_currency")
    private String primaryCurrency;

    @Column(name = "secondary_currency")
    private String secondaryCurrency;

    @Column(name = "tertiary_currency")
    private String tertiaryCurrency;

    @Column(name = "primary_rate")
    private BigDecimal primaryRate;

    @Column(name = "secondary_rate")
    private BigDecimal secondaryRate;

    @Column(name = "tertiary_rate")
    private BigDecimal tertiaryRate;

    @Column(name = "description")
    private String description;

    @Column(name = "overproduction_percent")
    private BigDecimal overproductionPercent;

    @Column(name = "loss_percent")
    private BigDecimal lossPercent;

    @Column(name = "general_expense_percent")
    private BigDecimal generalExpensePercent;

    @Column(name = "extra_expense_percent")
    private BigDecimal extraExpensePercent;

    @Column(name = "target_profit_percent")
    private BigDecimal targetProfitPercent;

    @Column(name = "given_price")
    private BigDecimal givenPrice;

    @Column(name = "given_price_currency")
    private String givenPriceCurrency;

    @Column(name = "discount_percent")
    private BigDecimal discountPercent;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
}
