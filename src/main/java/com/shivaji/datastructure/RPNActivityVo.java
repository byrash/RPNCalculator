package com.shivaji.datastructure;

import com.shivaji.calculator.Operation;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * @author Shivaji Byrapaneni
 */
@Data
public class RPNActivityVo {

  private List<BigDecimal> values = new ArrayList<>();
  private Operation operation;
}
