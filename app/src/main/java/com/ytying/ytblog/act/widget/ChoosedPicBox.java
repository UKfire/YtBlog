package com.ytying.ytblog.act.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.ytying.ytblog.component.choosephoto.PicBoxAdpter;
import com.ytying.ytblog.utils.DimenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Topbar标题栏 只要给定参数，就能生出一个多功能的Topbar.
 * 
 * @author 是我的海
 */
public class ChoosedPicBox extends RelativeLayout {

	public static final int CHOOSE_PHOTO = 1;
	public static final int PIC_PREVIEW = 2;

	private GridView gvPicBox;
	List<String> pathList;

	public ChoosedPicBox(Context context) {
		super(context);
	}
	
	public ChoosedPicBox(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	public void setPathList(ArrayList<String> list)
	{
		pathList=list;
	}

	public void init(int width, List<String> list) {
		pathList=list;
		gvPicBox = new GridView(getContext());
		
		gvPicBox.setColumnWidth((DimenUtil.dp2px(74)));
		gvPicBox.setHorizontalSpacing(DimenUtil.dp2px(6));
		gvPicBox.setVerticalSpacing(DimenUtil.dp2px(6));
		
		
		gvPicBox.setNumColumns(4);
		gvPicBox.setStretchMode(GridView.NO_STRETCH);
		this.addView(gvPicBox, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

	}

	public void update(PicBoxAdpter adpter)
	{
		gvPicBox.setAdapter(adpter);
	}
}
