/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.guoku.guokuv4.view;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-9 上午11:28:35
 * 用来解决GridView和scrollview的冲突
 */


public class HeaderGridView extends GridView{

    public HeaderGridView(Context context, AttributeSet attrs){
         super(context, attrs);
    }

//    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
//         int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
//         super.onMeasure(widthMeasureSpec, mExpandSpec);
//    }
}

