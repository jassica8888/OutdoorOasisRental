package com.zhangdi.reggie.dto;

import com.zhangdi.reggie.entity.Setmeal;
import com.zhangdi.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
