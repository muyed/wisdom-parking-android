package com.cn.climax.wisdomparking.base.fragment.state;

import com.cn.climax.wisdomparking.R;

/**
 * author：leo on 2016/11/25 16:19
 * email： leocheung4ever@gmail.com
 * description: 空白碎片状态
 * what & why is modified:
 */

public class EmptyState extends AbstractShowState {

    @Override
    public void show(boolean animate) {
        showViewById(R.id.epf_empty, animate);
    }

    @Override
    public void dismiss(boolean animate) {
        dismissViewById(R.id.epf_empty, animate);
    }
}
